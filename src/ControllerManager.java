import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerManager extends HomepageController{

    private final ViewManagerHomepage vManagerHomepage;

    // Used by most callers
    public ControllerManager(ViewManagerHomepage vManagerHomepage, PageHandler handler, DBUser user){
        this(vManagerHomepage, new DAOClass(), handler, user);
    }

    // Used by tester
    public ControllerManager(ViewManagerHomepage vManagerHomepage, DAOClass dao, PageHandler handler, DBUser user) {
        super(vManagerHomepage,dao,handler, user);
        this.vManagerHomepage = vManagerHomepage;
        vManagerHomepage.getUserEmailLabel().setText(user.getEmail());
        vManagerHomepage.getUserNameLabel().setText(user.getFirstName()  + " " + user.getSurname());
        setupListeners();
    }

    void setupListeners(){
        // Student Tab
        this.vManagerHomepage.getSearchDecisionButton().addActionListener(e -> handleDecisionSearch());
        this.vManagerHomepage.getConfirmDecisionButton().addActionListener( e -> handleAwardDecision());

        // Lecturer Tab
        this.vManagerHomepage.getSearchAssignButton().addActionListener( e -> handleAssignModuleSearch());
        this.vManagerHomepage.getAssignButton().addActionListener( e -> handleAssignModule());

        // Course Tab
        this.vManagerHomepage.getAddCourseButton().addActionListener( e -> handleAddCourse());
        this.vManagerHomepage.getAdjustCourseSelectButton().addActionListener( e -> handleAdjustCourseSelect());
        this.vManagerHomepage.getAdjustCourseUpdateButton().addActionListener( e -> handleAdjustCourseUpdate());

        // Module Tab
        this.vManagerHomepage.getAddModuleButton().addActionListener( e -> handleAddModule());
        this.vManagerHomepage.getExisingModuleShowButton().addActionListener( e -> handleAdjustModuleShow());
        this.vManagerHomepage.getExistingModuleUpdateButton().addActionListener( e -> handleAdjustModuleUpdate());

        // Misc. Tab
        this.vManagerHomepage.getAwaitingSelectButton().addActionListener( e -> handleAwaitingUserSelect());
        this.vManagerHomepage.getApproveButton().addActionListener( e -> handleApproveNewUser());
        this.vManagerHomepage.getDeclineButton().addActionListener( e -> handleDeclineNewUser());

        this.vManagerHomepage.getSearchExistingButton().addActionListener( e -> handleExistingUserSearch());
        this.vManagerHomepage.getPasswordResetButton().addActionListener( e -> handleExistingUserPasswordChange());
        this.vManagerHomepage.getActivateButton().addActionListener( e -> handleUserActivate());
        this.vManagerHomepage.getDeactivateButton().addActionListener( e -> handleUserDeactivate());


        this.vManagerHomepage.getCompensateUpdateButton().addActionListener( e -> handleCompensationRuleUpdate());
        this.vManagerHomepage.getUpdateAllSelectedModulesButton().addActionListener( e -> handleMaxAttemptsModule());
        this.vManagerHomepage.getMaxAttemptCourseUpdate().addActionListener( e-> handleMaxAttemptsCourse());

        // Helper listeners
        this.vManagerHomepage.getTabbedPaneOptions().addChangeListener( e -> handleTabChanged());
        this.vManagerHomepage.getTabbedPaneMaxAttempts().addChangeListener(e -> refreshMaxAttemptsInfo());
    }

    void handleDecisionSearch() {
        System.out.println("search Student button pressed");
        String studentEmail = vManagerHomepage.getEmailDecisionText().getText().trim();

        vManagerHomepage.getNameDecisionLabel().setText("n/a");
        vManagerHomepage.getEmailDecisionLabel().setText("n/a");
        vManagerHomepage.getCurrentDecisionLabel().setText("n/a");
        vManagerHomepage.getCourseDecisionLabel().setText("n/a");
        vManagerHomepage.getAwardRadioButton().setSelected(false);
        vManagerHomepage.getResitRadioButton().setSelected(false);
        vManagerHomepage.getWithdrawRadioButton().setSelected(false);

        DefaultTableModel tableModel = (DefaultTableModel) vManagerHomepage.getStudentDecisionTable().getModel();
        tableModel.setRowCount(0);

        if (validEmail(studentEmail)) {

            DBStudent student = dao.readStudentFromEmail(studentEmail);

            if (student == null) {
                showMessage(
                        "No student found with email: %s".formatted(studentEmail),
                        "Email Search Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                DBUser user = dao.readUserFromEmail(studentEmail);
                String studentName = user.getFirstName() + " " + user.getSurname();

                ArrayList<DBDecision> studentDecisionArray = dao.readDecisionsFromStudentID(student.getStudentID());
                String studentDecision = "n/a";
                if(!studentDecisionArray.isEmpty()) {
                    studentDecision = Award.asString(studentDecisionArray.getLast().getAward());
                }

                DBCourse course = dao.readCourseFromCourseCode(student.getCourse().getCourseCode());
                String studentCourse = course.getCourseCode() + " " + course.getCourseName();

                int maxCompensatedAllowed = course.getMaxModuleCompensated();
                vManagerHomepage.getCompensationLabel().setText(String.valueOf(maxCompensatedAllowed));
                vManagerHomepage.getNameDecisionLabel().setText(studentName);
                vManagerHomepage.getEmailDecisionLabel().setText(studentEmail);
                vManagerHomepage.getCurrentDecisionLabel().setText(studentDecision);
                vManagerHomepage.getCourseDecisionLabel().setText(studentCourse);

                int currentCompensated = 0;
                int failedModules = 0;
                boolean suggestWithdraw = false;

                ArrayList<DBModule> moduleArrayList = dao.readModulesFromStudentID(student.getStudentID());

                for (DBModule m : moduleArrayList) {
                    ArrayList<DBResult> allResults = dao.readAllResultsFromStudentIDModuleID(student.getStudentID(), m.getModuleID());

                    if (allResults != null && !allResults.isEmpty()) {
                        DBResult latestResult = allResults.getLast();

                        double latestMark = latestResult.getMark();
                        int latestAttempt = latestResult.getAttemptNumber();
                        int maxAttempts = m.getMaxAttempts();

                        if (latestMark < 50 && latestAttempt >= maxAttempts) {
                            suggestWithdraw = true;
                        }

                        if (latestMark < 40) {
                            failedModules++;
                        } else if (latestMark < 50) {
                            currentCompensated++;
                        }
                    } else {
                        // Logic for students with NO results yet (optional handling)
                    }

                    if (allResults != null && !allResults.isEmpty()) {
                        for (DBResult res : allResults) {
                            String passFail = "Fail";
                            if (res.getMark() > 49 && res.getAttemptNumber() <= m.getMaxAttempts()) {
                                passFail = "Pass";
                            }

                            String[] tmpRow = {
                                    m.getModuleName(),
                                    ModuleResult.asString(res.getResultType()),
                                    String.valueOf(res.getMark()),
                                    String.valueOf(res.getAttemptNumber()),
                                    String.valueOf(m.getMaxAttempts()),
                                    passFail
                            };
                            tableModel.addRow(tmpRow);
                        }
                    } else {
                        String[] tmpRow = {
                                m.getModuleName(),
                                "n/a",
                                "0",
                                "0",
                                String.valueOf(m.getMaxAttempts()),
                                "n/a"
                        };
                        tableModel.addRow(tmpRow);
                    }
                }

                if (suggestWithdraw) {
                    vManagerHomepage.getWithdrawRadioButton().setSelected(true);
                } else if (failedModules > 0 || currentCompensated > maxCompensatedAllowed) {
                    vManagerHomepage.getResitRadioButton().setSelected(true);
                } else {
                    vManagerHomepage.getAwardRadioButton().setSelected(true);
                }
            }
        }
    }


    void handleAwardDecision(){
        System.out.println("assign Award button pressed");
        String studentEmail = vManagerHomepage.getEmailDecisionLabel().getText().trim();
        if (!validEmail(studentEmail)) return;
        Award studentAward;
        if (vManagerHomepage.getAwardRadioButton().isSelected()){
            System.out.println("Award Radio Button Selected");
            studentAward = Award.AWARD;
        } else if (vManagerHomepage.getResitRadioButton().isSelected()){
            System.out.println("Resit Radio Button Selected");
            studentAward = Award.RESIT;
        } else if (vManagerHomepage.getWithdrawRadioButton().isSelected()){
            System.out.println("Withdraw Radio Button Selected");
            studentAward = Award.WITHDRAW;
        } else {
            return;
        }

        String studentID = dao.readStudentFromEmail(studentEmail).getStudentID();
        String year = String.valueOf(LocalDate.now());
        DBDecision dbDecision = new DBDecision(studentID, year, studentAward);
        // SQL DATA - update Award Decision (use studentEmail, studentAward)
        if (dao.updateDecision(dbDecision)) {
            showMessage(
                    "Decision for %s has been updated to %s".formatted(studentEmail, Award.asString(studentAward)),
                    "Decision Update Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            vManagerHomepage.getCurrentDecisionLabel().setText(Award.asString(studentAward));
        } else {
            showMessage(
                    "Database Error Occurred. Decision for %s has not been updated to %s".formatted(studentEmail,
                            Award.asString(studentAward)),
                    "Decision Update Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void handleAssignModuleSearch(){
        System.out.println("Search Assign button pressed");
        vManagerHomepage.getNameAssignLabel().setText("n/a");
        vManagerHomepage.getEmailAssignLabel().setText("n/a");
        vManagerHomepage.getAssignDropdown().removeAllItems();

        String lecturerEmail = vManagerHomepage.getEmailAssignText().getText().trim();

        if(validEmail(lecturerEmail)){
            DBLecturer lecturer = dao.readLecturerFromEmail(lecturerEmail);
            if (lecturer==null){
                showMessage(
                        "No lecturer found with email: %s".formatted(lecturerEmail),
                        "Email Search Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            DBUser user = dao.readUserFromEmail(lecturerEmail);
            String lecturerName = user.getFirstName() + " " + user.getSurname();
            vManagerHomepage.getNameAssignLabel().setText(lecturerName);
            vManagerHomepage.getEmailAssignLabel().setText(lecturerEmail);

            ArrayList<DBModule> moduleArrayList = dao.readModules();
            if (moduleArrayList.isEmpty()) {
                showMessage(
                        "No modules found in database.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                for (DBModule m : moduleArrayList) {
                    vManagerHomepage.getAssignDropdown().addItem(m.getModuleName());
                }
            }
        }
    }

    void handleAssignModule(){
        System.out.println("Assign button pressed");
        String assignedLecturer = vManagerHomepage.getNameAssignLabel().getText().trim();
        String assignedEmail = vManagerHomepage.getEmailAssignLabel().getText().trim();
        String assignedModule = (String)vManagerHomepage.getAssignDropdown().getSelectedItem();
        if (validEmail(assignedEmail)){
            DBLecturer lecturer = dao.readLecturerFromEmail(assignedEmail);
            DBModule module = dao.readModuleFromModuleName(assignedModule);
            if (lecturer == null || module == null) {
                showMessage(
                        "System Error: Could not retrieve Lecturer or Module details.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dao.isModuleAssignedToLecturer(module.getModuleID(), lecturer.getlecturerID())) {
                showMessage(
                    "This lecturer is already assigned to this module.",
                    "Already Assigned",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (dao.createModuleLecturer(module, lecturer)) {
                showMessage(
                        "Successfully assigned %s to %s.".formatted(assignedModule, assignedLecturer),
                        "Assignment Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage(
                        "Database Error Occurred. %s was not assigned to %s".formatted(assignedModule, assignedLecturer),
                        "Assignment Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void handleAddCourse() {
        System.out.println("Add Course button pressed");
        String courseCode = vManagerHomepage.getAddCourseCodeText().getText().trim();
        String courseName = vManagerHomepage.getAddCourseNameText().getText().trim();
        String courseQualification = (String) vManagerHomepage.getAddCourseQualificationDropdown().getSelectedItem();
        String courseDepartment = (String) vManagerHomepage.getAddCourseDepartmentDropdown().getSelectedItem();
        String courseDescription = vManagerHomepage.getAddCourseDescriptionText().getText().trim();

        if (courseCode.isEmpty() || courseName.isEmpty() || courseDescription.isEmpty()) {
            showMessage(
                    "All fields (Code, Name, Description) must be filled out.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (courseDepartment == null || courseQualification == null) {
            showMessage(
                    "Please select a Department and Qualification type.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (courseCode.length()>12){showMessage(
                "Course Code is too long (Max 12 characters).",
                "Input Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (courseName.length()>512){showMessage(
                "Error adding course, course name is too long. Must be less than 513 characters.",
                "Adding Course Failed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (courseDescription.length()>10001){showMessage(
                "Error adding course, course description is too long. Must be less than 10,000 characters.",
                "Adding Course Failed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (dao.readCourseFromCourseCode(courseCode) != null) {
            showMessage(
                    "Course Code '%s' already exists.".formatted(courseCode),
                    "Duplicate Entry",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<DBCourse> courseAL = dao.readCourses();
        if(!courseAL.isEmpty()) {
            for (DBCourse c :courseAL) {
                if (c.getCourseName().equals(courseName)) {
                    showMessage(
                            "Course Name '%s' already exists.".formatted(courseName),
                            "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        CourseType typeEnum;

        try{
            typeEnum = CourseType.fromString(courseQualification);
        } catch (IllegalArgumentException e){showMessage(
                String.valueOf(e), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int compensation = -1;
        ArrayList<DBCourse> courses = dao.readCourses();
        if(courses.isEmpty() || courses.getLast().getMaxModuleCompensated()==0){
            showMessage("Compensation Business Rule is outdated. Please update as soon as possible.",
                    "Compensation Business Rule Notification", JOptionPane.ERROR_MESSAGE);
        } else {
            compensation = courses.getLast().getMaxModuleCompensated();
            System.out.println("Comp: " + compensation);
        }

        DBCourse newCourse = new DBCourse(courseCode, courseName, typeEnum, courseDescription,
                                            courseDepartment, compensation);
        if(dao.createCourse(newCourse)){
            showMessage(
                    "Course '%s' added successfully.".formatted(courseName),
                    "New Course Added Successfully",
                    JOptionPane.INFORMATION_MESSAGE);
            vManagerHomepage.getAddCourseCodeText().setText("");
            vManagerHomepage.getAddCourseNameText().setText("");
            vManagerHomepage.getAddCourseDescriptionText().setText("");
            if(vManagerHomepage.getAddCourseQualificationDropdown().getItemCount() > 0){
                vManagerHomepage.getAddCourseQualificationDropdown().setSelectedIndex(0);
            }
            if(vManagerHomepage.getAdjustCourseDepartmentDropdown().getItemCount() > 0){
                vManagerHomepage.getAdjustCourseDepartmentDropdown().setSelectedIndex(0);
            }
            handleRefreshCourseDropdown();
            dao.updateCourseMaxModulesCompensated(newCourse);
            return;
        }

        showMessage(
                "Database failed to save the course. Check console for SQL errors.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }

    void handleTabChanged(){
        System.out.println("Switched to " + vManagerHomepage.getTabbedPaneOptions().getTitleAt(
                vManagerHomepage.getTabbedPaneOptions().getSelectedIndex()) + " Tab");

        if (vManagerHomepage.getCourseTab().isShowing()) {
            vManagerHomepage.getAddCourseDepartmentDropdown().removeAllItems();
            vManagerHomepage.getAdjustCourseDepartmentDropdown().removeAllItems();
            ArrayList<DBDepartment> departments = dao.readDepartments();
            if (departments != null) {
                for (DBDepartment d : departments) {
                    String tmpDep = d.getDepartmentCode();
                    vManagerHomepage.getAddCourseDepartmentDropdown().addItem(tmpDep);
                    vManagerHomepage.getAdjustCourseDepartmentDropdown().addItem(tmpDep);
                }
            }
            handleRefreshCourseDropdown();
        } else if (vManagerHomepage.getModuleTab().isShowing()){
            vManagerHomepage.getAddModuleCourseDropdown().removeAllItems();
            vManagerHomepage.getExistingModuleCourseDropdown().removeAllItems();
            ArrayList<DBCourse> courses = dao.readCourses();
            if (courses != null) {
                for (DBCourse c : courses) {
                    String tmpCourse = c.getCourseCode() + " : " + c.getCourseName();
                    vManagerHomepage.getAddModuleCourseDropdown().addItem(tmpCourse);
                    vManagerHomepage.getExistingModuleCourseDropdown().addItem(tmpCourse);
                }
            }
            handleRefreshModuleDropdown();
        } else if (vManagerHomepage.getMiscTab().isShowing()){
            refreshMaxAttemptsInfo();
            refreshAwaitingUserDropdown();
        }
    }

    void handleAdjustCourseSelect(){
        System.out.println("Adjust Course Select button pressed");

        String selectedItem = (String) vManagerHomepage.getAdjustCoursedDropdown().getSelectedItem();
        if (selectedItem == null || !selectedItem.contains(" : ")) {
            return;
        }

        String courseCode = selectedItem.split(" : ", 2)[0];

        DBCourse course = dao.readCourseFromCourseCode(courseCode);
        if (course != null) {
            vManagerHomepage.getAdjustCourseCodeLabel().setText(course.getCourseCode());
            vManagerHomepage.getAdjustCourseNameText().setText(course.getCourseName());
            vManagerHomepage.getAdjustCourseDescriptionText().setText(course.getCourseDescription());
            vManagerHomepage.getAdjustCourseDepartmentDropdown().setSelectedItem(course.getDepartmentCode());
            String qualString = CourseType.asString(course.getCourseType());
            vManagerHomepage.getAdjustCourseQualificationDropdown().setSelectedItem(qualString);
        } else {
            showMessage(
                    "Could not find course data for " + courseCode,
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void handleRefreshCourseDropdown(){
        System.out.println("refreshed Adjust Course Dropdown");
        vManagerHomepage.getAdjustCoursedDropdown().removeAllItems();
        ArrayList<DBCourse> courses = dao.readCourses();
        if (courses != null) {
            for (DBCourse c : courses) {
                String tmpCourse = c.getCourseCode() + " : " + c.getCourseName();
                vManagerHomepage.getAdjustCoursedDropdown().addItem(tmpCourse);            }
        }
    }

    public void handleAdjustCourseUpdate(){
        System.out.println("Adjust Course Update button pressed");

        String courseCode = vManagerHomepage.getAdjustCourseCodeLabel().getText().trim();
        if (courseCode.equals("n/a") || courseCode.isEmpty()) {
            showMessage(
                    "Please select a course first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseName = vManagerHomepage.getAdjustCourseNameText().getText().trim();
        String courseDesc = vManagerHomepage.getAdjustCourseDescriptionText().getText().trim();
        String courseDep = (String) vManagerHomepage.getAdjustCourseDepartmentDropdown().getSelectedItem();
        String courseQual = (String) vManagerHomepage.getAdjustCourseQualificationDropdown().getSelectedItem();

        if (courseName.length() > 512) {
            showMessage( "Name too long (Max 512).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (courseName.isEmpty() || courseDesc.isEmpty()) {
            showMessage( "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<DBCourse> courseAL = dao.readCourses();
        if(!courseAL.isEmpty()) {
            for (DBCourse c :courseAL) {
                if (c.getCourseName().equals(courseName) && !c.getCourseCode().equals(courseCode)) {
                    showMessage(
                            "Course Name '%s' already exists.".formatted(courseName),
                            "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        CourseType typeEnum = CourseType.fromString(courseQual);
        DBCourse dbCourse = new DBCourse(courseCode, courseName, typeEnum, courseDesc, courseDep ,-1);

        try {
            dao.updateCourseName(dbCourse);
            dao.updateCourseDescription(dbCourse);
            dao.updateCourseDepartment(dbCourse);
            dao.updateCourseType(dbCourse);

            showMessage(
                    "Course '%s' updated successfully.".formatted(courseCode),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            handleRefreshCourseDropdown();
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(
                    "Error updating course: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void handleAddModule() {
        System.out.println("Add Module button pressed");

        String moduleCode = vManagerHomepage.getAddModuleCodeText().getText().trim();
        String moduleName = vManagerHomepage.getAddModuleNameText().getText().trim();
        String moduleCreditStr = vManagerHomepage.getAddModuleCreditText().getText().trim();
        String moduleSemesterStr = (String) vManagerHomepage.getAddModuleSemesterDropdown().getSelectedItem();
        String moduleCourseStr = (String) vManagerHomepage.getAddModuleCourseDropdown().getSelectedItem();
        String moduleAttemptsStr = vManagerHomepage.getAddModuleAttemptText().getText().trim();
        String moduleDesc = vManagerHomepage.getAddModuleDescriptionText().getText().trim();

        if (moduleCode.isEmpty() || moduleName.isEmpty() || moduleCreditStr.isEmpty() ||
                moduleDesc.isEmpty() || moduleAttemptsStr.isEmpty()) {
            showMessage(
                    "All fields (Code, Name, Credit, Description, Attempts) must be filled.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (moduleCourseStr == null || moduleSemesterStr == null) {
            showMessage("There is an error with the semester and course dropdown boxes.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (moduleCode.length() > 12) {
            showMessage("Module Code is too long (Max 12).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (dao.readModuleFromModuleCode(moduleCode) != null) {
            showMessage(
                    "Module Code '%s' already exists.".formatted(moduleCode),
                    "Duplicate Entry",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (moduleName.length() > 512) {
            showMessage("Module Name is too long (Max 512).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<DBModule> moduleAL = dao.readModules();
        if(!moduleAL.isEmpty()) {
            for (DBModule m :moduleAL) {
                if (m.getModuleName().equals(moduleName) && !m.getModuleCode().equals(moduleCode)) {
                    showMessage(
                            "Module Name '%s' already exists.".formatted(moduleName),
                            "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        if (moduleDesc.length()>10001){showMessage(
                "Error adding course, course description is too long. Must be less than 10,000 characters.",
                "Adding Course Failed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int credit = parsePositiveInt(moduleCreditStr, "Module Credit");
        if (credit==-1) return;

        int attempts = parsePositiveInt(moduleAttemptsStr, "Module Attempts");
        if(attempts==-1) return;

        if (dao.readModuleFromModuleCode(moduleCode) != null) {
            showMessage(
                    "Module Code '%s' already exists.".formatted(moduleCode),
                    "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Semester semEnum = Semester.fromString(moduleSemesterStr);
            String courseCode = moduleCourseStr.split(" : ")[0];
            DBCourse linkedCourse = dao.readCourseFromCourseCode(courseCode);
            if (linkedCourse==null){
                System.out.println("ERROR on course inread");
                return;
            }

            DBModule newModule = new DBModule(moduleCode, moduleName, semEnum, moduleDesc, attempts, credit);

            if (dao.createModule(newModule)) {
                if(!dao.createCourseModule(linkedCourse, newModule)){
                    showMessage(
                            "Module '%s' was not linked to a course.".formatted(moduleName),
                            "Database Error", JOptionPane.INFORMATION_MESSAGE);
                }

                showMessage(
                        "Module '%s' added successfully.".formatted(moduleName),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                vManagerHomepage.getAddModuleCodeText().setText("");
                vManagerHomepage.getAddModuleNameText().setText("");
                vManagerHomepage.getAddModuleCreditText().setText("");
                vManagerHomepage.getAddModuleDescriptionText().setText("");
                vManagerHomepage.getAddModuleAttemptText().setText("");
                if(vManagerHomepage.getAddModuleSemesterDropdown().getItemCount() > 0)
                    vManagerHomepage.getAddModuleSemesterDropdown().setSelectedIndex(0);
                if(vManagerHomepage.getAddModuleSemesterDropdown().getItemCount() > 0)
                    vManagerHomepage.getAddModuleSemesterDropdown().setSelectedIndex(0);
                handleRefreshModuleDropdown();
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
            showMessage(
                    "Error adding module: " + e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRefreshModuleDropdown(){
        System.out.println("Refreshed Module Select Course Dropdown");
        ArrayList<DBModule> modules = dao.readModules();
        vManagerHomepage.getExisingModuleSelectDropdown().removeAllItems();
        if (modules != null) {
            for (DBModule m : modules) {
                String tmpMsg = m.getModuleCode() + " : " + m.getModuleName();
                vManagerHomepage.getExisingModuleSelectDropdown().addItem(tmpMsg);
            }
        }
    }

    void handleAdjustModuleShow(){
        System.out.println("Adjust Module Show button pressed");

        boolean validModule = true;
        String moduleCode = "";
        DBModule module = null;
        String selectedItem = (String) vManagerHomepage.getExisingModuleSelectDropdown().getSelectedItem();
        if (selectedItem != null && selectedItem.contains(" : ")) {
            moduleCode = selectedItem.split(" : ")[0];
        } else {
            validModule = false;
        }

        if (validModule && !moduleCode.isEmpty()){
            module = dao.readModuleFromModuleCode(moduleCode);
        }

        if (!validModule){
            showMessage("No module selected, presumably due to database error",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (module != null) {
            vManagerHomepage.getExistingModuleCodeLabel().setText(moduleCode);
            vManagerHomepage.getExistingModuleNameText().setText(module.getModuleName());
            vManagerHomepage.getExistingModuleCreditText().setText(String.valueOf(module.getCredit()));
            vManagerHomepage.getExistingModuleDescriptionText().setText(module.getModuleDescription());
            vManagerHomepage.getExistingModuleAttemptsText().setText(String.valueOf(module.getMaxAttempts()));
            String semesterString = Semester.asString(module.getSemester());
            vManagerHomepage.getExistingModuleSemesterDropdown().setSelectedItem(semesterString);
            String moduleID = module.getModuleID();
            DBCourse linkedCourse = dao.readCourseFromModuleID(moduleID);
            if (linkedCourse != null) {
                String courseItem = linkedCourse.getCourseCode() + " : " + linkedCourse.getCourseName();
                vManagerHomepage.getExistingModuleCourseDropdown().setSelectedItem(courseItem);
            } else {
                showMessage("No course found linked to Module %s".formatted(moduleCode),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            showMessage(
                    "Could not retrieve data for module %s.".formatted(moduleCode),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void handleAdjustModuleUpdate() {
        System.out.println("Update Module button pressed");

        String moduleCode = vManagerHomepage.getExistingModuleCodeLabel().getText().trim();

        if (moduleCode.isEmpty() || moduleCode.equals("n/a")) {
            showMessage("Please select a module first.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DBModule module = dao.readModuleFromModuleCode(moduleCode);
        if (module == null) {
            showMessage("Module %s was not found in the database.".formatted(moduleCode),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the Module itself
        String name = vManagerHomepage.getExistingModuleNameText().getText().trim();

        String description = vManagerHomepage.getExistingModuleDescriptionText().getText().trim();

        String semesterStr = (String)vManagerHomepage.getExistingModuleSemesterDropdown().getSelectedItem();
        if (semesterStr == null || semesterStr.isEmpty()) {
            showMessage("Semester not selected.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Semester semester = Semester.fromString(semesterStr);

        int credit = parsePositiveInt(vManagerHomepage.getExistingModuleCreditText().getText().trim(), "Module Credit");
        if (credit==-1) return;

        int attempts = parsePositiveInt(vManagerHomepage.getExistingModuleAttemptsText().getText().trim(), "Max Attempts");
        if (attempts==-1) return;


        int changes = 0;
        int successes = 0;

        if (!name.equals(module.getModuleName())) {
            changes++;
            module.setModuleName(name);
            if (dao.updateModuleName(module)) successes++;
        }

        if (!description.equals(module.getModuleDescription())) {
            changes++;
            module.setModuleDescription(description);
            if (dao.updateModuleDescription(module)) successes++;
        }

        if (credit != module.getCredit()) {
            changes++;
            module.setCredit(credit);
            if (dao.updateModuleCredits(module)) successes++;
        }

        if (attempts != module.getMaxAttempts()) {
            changes++;
            module.setMaxAttempts(attempts);
            if (dao.updateModuleMaxAttempts(module)) successes++;
        }

        if (semester != module.getSemester()) {
            changes++;
            module.setModuleSemester(semester);
            if (dao.updateModuleSemester(module)) successes++;
        }

        // Update which course it's linked to
        DBCourse currentCourse = dao.readCourseFromModuleID(module.getModuleID());
        if (currentCourse == null){
            showMessage("Course %s was not found in database.".formatted(currentCourse),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentCourseCode = currentCourse.getCourseCode();

        String selectedDropdownItem = (String) vManagerHomepage.getExistingModuleCourseDropdown().getSelectedItem();
        String newCourseCode = "";
        if (selectedDropdownItem != null && selectedDropdownItem.contains(" : ")) {
            newCourseCode = selectedDropdownItem.split(" : ")[0];
        }

        if (!newCourseCode.equals(currentCourseCode)) {
            changes++;
            if (dao.updateModuleCourseLink(moduleCode, newCourseCode)) successes++;
        }

        if (changes == 0) {
            showMessage(
                    "No changes were selected and therefore no changes were made.",
                    "Update Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (successes == changes) {
            showMessage("%s Module updated successfully.".formatted(moduleCode),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            handleRefreshModuleDropdown();
        } else if (successes == 0) {
            showMessage("%s Module was not updated.".formatted(moduleCode),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } else {
            showMessage(
                    "%s Module was only partially updated. Please check the display/input form".formatted(moduleCode),
                    "Partial Update", JOptionPane.WARNING_MESSAGE);
            handleRefreshModuleDropdown(); // Refresh so they can see what stuck
        }
    }

    void handleAwaitingUserSelect(){
        vManagerHomepage.getEnrolStudentPanel().setVisible(false);
        vManagerHomepage.getEnrolLecturePanel().setVisible(false);

        System.out.println("Select Awaiting User button pressed");
        String email = (String) vManagerHomepage.getAwaitingDropdown().getSelectedItem();

        DBUnapproved unUser = dao.readUnapprovedFromEmail(email);
        if (unUser==null){
            showMessage("Unapproved user with email %s not found.".formatted(email),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = unUser.getFirstName() + " " + unUser.getSurname();

        String type = "Lecturer";
        if (unUser.getQualification()==null) {
            type = "Student";
            ArrayList<DBCourse> courseAL = dao.readCourses();
            if(courseAL.isEmpty()){
                showMessage(
                        "Could not find any courses in the database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            vManagerHomepage.getCourseEnrolDropDown().removeAllItems();
            for (DBCourse c : courseAL) {
                String tmpMsg = c.getCourseCode() + " : " + c.getCourseName();
                vManagerHomepage.getCourseEnrolDropDown().addItem(tmpMsg);
            }
            vManagerHomepage.getEnrolStudentPanel().setVisible(true);

        } else {
            vManagerHomepage.getQualificationEnrolLabel().setText(unUser.getQualification());
            ArrayList<DBDepartment> departmentAL = dao.readDepartments();
            vManagerHomepage.getDepartmentEnrolDropdown().removeAllItems();
            if(departmentAL.isEmpty()){
                showMessage(
                        "Could not find any departments in the database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (DBDepartment d : departmentAL) {
                String tmpDep = d.getDepartmentCode();
                vManagerHomepage.getDepartmentEnrolDropdown().addItem(tmpDep);
            }

            vManagerHomepage.getEnrolLecturePanel().setVisible(true);
        }

        vManagerHomepage.getAwaitingNameLabel().setText(name);
        vManagerHomepage.getAwaitingTypeLabel().setText(type);
        vManagerHomepage.getAwaitingEmailLabel().setText(email);
    }

    void handleApproveNewUser() {
        System.out.println("Approve Awaiting User button pressed");

        String email = vManagerHomepage.getAwaitingEmailLabel().getText();
        String type = vManagerHomepage.getAwaitingTypeLabel().getText();

        if(!validEmail(email)){
            showMessage("Please select a unapproved user.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DBUnapproved unUser = dao.readUnapprovedFromEmail(email);
        if (unUser==null){
            showMessage("Unapproved user with email %s not found.".formatted(email),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DBUser user = new DBUser(unUser);
        if(!dao.createUser(user)){
            showMessage("Failed to create base User record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user.setManagerID(currentUser.getManagerID());
        boolean success = false;

        if (type.equals("Student")){

            String selectedItem = (String) vManagerHomepage.getCourseEnrolDropDown().getSelectedItem();
            if (selectedItem == null || !selectedItem.contains(" : ")) {
                showMessage("Please select a valid course.",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                dao.deleteUser(user.getUserID());
                return;
            }

            String courseCode = selectedItem.split(" : ", 2)[0];
            DBCourse course = dao.readCourseFromCourseCode(courseCode);

            if (course==null) {
                showMessage("Course with code %s not found.".formatted(courseCode),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                dao.deleteUser(user.getUserID());
                return;
            }

            DBStudent student = new DBStudent(user, course);
            if(dao.createStudent(student)){
                success = true;
                showMessage("%s has been approved as a student in %s.".formatted(email, courseCode),
                        "Enrollment Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (type.equals("Lecturer")){

            String departmentCode = (String) vManagerHomepage.getDepartmentEnrolDropdown().getSelectedItem();
            if (departmentCode == null) {
                showMessage("Please select a department.", "Input Error", JOptionPane.WARNING_MESSAGE);
                dao.deleteUser(user.getUserID());
                return;
            }

            DBLecturer lecturer = new DBLecturer(user, unUser.getQualification(), departmentCode);
            if(dao.createLecturer(lecturer)){
                success = true;
                showMessage("%s approved as lecturer in %s.".formatted(email, departmentCode),
                        "Lecturer Approved", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (success) {
            dao.deleteUnapprovedUser(email);
            resestAwaitingUserUI();
        } else {
            showMessage("%s was not approved.".formatted(email), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            dao.deleteUser(user.getUserID());
        }
        refreshAwaitingUserDropdown();
    }

    void handleDeclineNewUser(){
        System.out.println("Decline Awaiting User button pressed");

        String email = vManagerHomepage.getAwaitingEmailLabel().getText();
        if(!validEmail(email)){
            showMessage("Please select a unapproved user.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = showMessage(
                "Are you sure you want to decline and remove the application for %s?".formatted(email),
                "Confirm Decline",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (choice== JOptionPane.NO_OPTION) {
            return;
        }

        if (dao.deleteUnapprovedUser(email)) {
            showMessage("%s has been declined and removed from the list.".formatted(email),
                    "Application Declined",
                    JOptionPane.INFORMATION_MESSAGE);
            resestAwaitingUserUI();
        } else {
            showMessage("%s was not removed.".formatted(email),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        refreshAwaitingUserDropdown();
    }

    private void resestAwaitingUserUI(){
        vManagerHomepage.getAwaitingNameLabel().setText("n/a");
        vManagerHomepage.getAwaitingEmailLabel().setText("n/a");
        vManagerHomepage.getAwaitingTypeLabel().setText("n/a");
        vManagerHomepage.getQualificationEnrolLabel().setText("n/a");
        if (vManagerHomepage.getDepartmentEnrolDropdown().getItemCount() > 0) {
            vManagerHomepage.getDepartmentEnrolDropdown().setSelectedIndex(0);
        }
        if (vManagerHomepage.getCourseEnrolDropDown().getItemCount() > 0) {
            vManagerHomepage.getCourseEnrolDropDown().setSelectedIndex(0);
        }
        vManagerHomepage.getEnrolStudentPanel().setVisible(false);
        vManagerHomepage.getEnrolLecturePanel().setVisible(false);
    }


    void handleExistingUserSearch(){
        System.out.println("Search for User button pressed");

        String email = vManagerHomepage.getEmailExistingText().getText().trim();

        if (!validEmail(email)) return;

        if (currentUser.getEmail().equals(email)){
            showMessage("Managers can't manage themselves.",
                    "Conflicting Users", JOptionPane.WARNING_MESSAGE);
            return;
        }

        vManagerHomepage.getNameExistingLabel().setText("n/a");
        vManagerHomepage.getUserTypeExistingLabel().setText("n/a");
        vManagerHomepage.getStateExistingLabel().setText("n/a");
        vManagerHomepage.getEmailExistingLabel().setText("n/a");

        DBUser user = dao.readUserFromEmail(email);
        if (user == null) {
            showMessage(
                    "Could not retrieve user details. Check valid email was entered.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = user.getFirstName() + " " + user.getSurname();
        String type = "Manager";
        if (dao.readStudentFromUserID(user.getUserID())!=null) type = "Student";
        else if (dao.readLecturerFromUserID(user.getUserID())!=null) type = "Lecturer";

        String state = "n/a";
        if (user.getIsActivated()) state = "Activated";
        else state = "Deactivated";
        vManagerHomepage.getNameExistingLabel().setText(name);
        vManagerHomepage.getUserTypeExistingLabel().setText(type);
        vManagerHomepage.getStateExistingLabel().setText(state);
        vManagerHomepage.getEmailExistingLabel().setText(email);
    }

    void handleExistingUserPasswordChange(){
        System.out.println("Change Password of Existing User button pressed");

        String email = vManagerHomepage.getEmailExistingLabel().getText();
        if (!validEmail(email)) {
            showMessage(
                    "Please select a valid user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(vManagerHomepage.getPanelMain());

        ViewChangePassword passwordDialog = new ViewChangePassword(parentFrame, email, dao);
        passwordDialog.setVisible(true);
    }

    void handleUserActivate(){
        System.out.println("Activate User button pressed");

        changeStateUserAccount(true);
    }

    void handleUserDeactivate(){
        System.out.println("Deactivate User button pressed");

        changeStateUserAccount(false);
    }

    void changeStateUserAccount(boolean activate){
        String email = vManagerHomepage.getEmailExistingLabel().getText();

        DBUser user = dao.readUserFromEmail(email);
        if(user==null) {
            showMessage(
                    "%s not found in database.".formatted(email),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String stateMsg = (activate) ? "Activated" : "Deactivated";
        if (activate==user.getIsActivated()){
            showMessage("%s is already %s.".formatted(email,stateMsg), "No Change Made",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        user.setIsActivated(activate);

        if (dao.updateUserIsActivated(user)) {
            showMessage(
                    "%s has been %s.".formatted(email, stateMsg),
                    "User %s Successfully".formatted(stateMsg),
                    JOptionPane.INFORMATION_MESSAGE);

            vManagerHomepage.getStateExistingLabel().setText(stateMsg);
        } else {
            showMessage(
                    "$s has not been %s.".formatted(email, stateMsg),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void handleCompensationRuleUpdate(){
        System.out.println("Update Compensation Rule button pressed");

        String numString = vManagerHomepage.getCompensateText().getText().trim();
        int numInt = parsePositiveInt(numString, "Max Compensation");
        if (numInt == -1) return;

        ArrayList<DBCourse> courses = dao.readCourses();
        if(courses.isEmpty()){
            showMessage("No courses found in database.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean successful = true;
        for (DBCourse c : courses){
            c.setMaxModuleCompensated(numInt);
            if(!dao.updateCourseMaxModulesCompensated(c)){
                successful = false;
            }
        }

        if(!successful){
            showMessage("Something went wrong when updating the Compensation Bussiness Rule to %d.".formatted(numInt),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } else {
            showMessage("The Compensation Bussiness Rule was updated to %d.".formatted(numInt),
                    "Update Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshAwaitingUserDropdown(){
        System.out.println("refreshed Awaiting User Dropdown");

        vManagerHomepage.getAwaitingDropdown().removeAllItems();

        ArrayList<DBUnapproved> unapprovedAL = dao.readUnapprovedUsers();
        if(unapprovedAL==null){
            showMessage("No unapproved users found","Search Results Inconclusive", JOptionPane.ERROR_MESSAGE);
            vManagerHomepage.getAwaitingDropdown().addItem("No Awaiting");
        }

        if(unapprovedAL.isEmpty()){
            vManagerHomepage.getAwaitingDropdown().addItem("No Awaiting Unapproved Users found");
        }

        for (DBUnapproved u: unapprovedAL){
            vManagerHomepage.getAwaitingDropdown().addItem(u.getEmail());
        }

    }

    void handleMaxAttemptsModule() {
        System.out.println("Update Module Business Rule Show button pressed");

        DefaultTableModel tableModel = (DefaultTableModel) vManagerHomepage.getTableBusinessRule().getModel();
        int rowCount = tableModel.getRowCount();

        ArrayList<DBModule> pendingUpdates = new ArrayList<>();
        boolean selectionMade = false;

        // Input Validation (column 3 has tick, column 2 has number)
        for (int i = 0; i < rowCount; i++) {
            Object changeObj = tableModel.getValueAt(i, 3);
            if (changeObj == null || !(Boolean) changeObj) {
                continue;
            }

            selectionMade = true;
            String moduleID = (String) tableModel.getValueAt(i, 0);

            String value = (String) tableModel.getValueAt(i, 2);

            int newMax = parsePositiveInt(value, "Max Attmepts");
            if (newMax==-1) return;

            DBModule module = dao.readModuleFromModuleID(moduleID);
            if (module != null) {
                module.setMaxAttempts(newMax);
                pendingUpdates.add(module);
            } else {
                showMessage("Module " + moduleID + " not found in database.",
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                return; // STOP EVERYTHING
            }

        }

        if (!selectionMade) {
            showMessage("No modules selected to update.", "Input Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int successCount = 0;
        int failCount = 0;

        for (DBModule m : pendingUpdates) {
            if (dao.updateModuleMaxAttempts(m)) {
                successCount++;
            } else {
                failCount++;
                System.out.println("Failed to update module: " + m.getModuleID());
            }
        }

        if (failCount == 0) {
            showMessage(successCount + " modules updated successfully.",
                    "Modules Successfully Updated", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessage("Update complete with database errors.\nSuccess: " + successCount + "\nFailed: " + failCount,
                    "Partially Successful Update", JOptionPane.WARNING_MESSAGE);
        }

        refreshMaxAttemptsInfo();
    }

    private void refreshMaxAttemptsInfo(){
        vManagerHomepage.getTableBusinessRule().getModel();
        DefaultTableModel tableModel = (DefaultTableModel) vManagerHomepage.getTableBusinessRule().getModel();
        tableModel.setRowCount(0);

        ArrayList<DBModule> moduleAL = dao.readModules();
        if(moduleAL.isEmpty()){
            showMessage(
                    "Could not find any modules in the database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (DBModule m : moduleAL) {
            Object[] tmpRow = {
                    m.getModuleID(),
                    m.getModuleName(),
                    m.getMaxAttempts()
            };
            tableModel.addRow(tmpRow);
        }

        ArrayList<DBCourse> courseAL = dao.readCourses();
        if(courseAL.isEmpty()){
            showMessage(
                    "Could not find any courses in the database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        vManagerHomepage.getMaxAttemptCourseDropdown().removeAllItems();
        for (DBCourse c : courseAL) {
            String tmpMsg = c.getCourseCode() + " : " + c.getCourseName();
            vManagerHomepage.getMaxAttemptCourseDropdown().addItem(tmpMsg);
        }
    }

    void handleMaxAttemptsCourse(){
        System.out.println(" Update Max Attempts by Course Select button pressed");

        String selectedItem = (String) vManagerHomepage.getMaxAttemptCourseDropdown().getSelectedItem();
        if (selectedItem == null || !selectedItem.contains(" : ")) {
            showMessage(
                    "No item selected from the dropdown.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String courseCode = selectedItem.split(" : ", 2)[0];

        DBCourse course = dao.readCourseFromCourseCode(courseCode);
        if (course == null) {
            showMessage(
                    "No course found with code: %s".formatted(courseCode),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maxAttemptStr = vManagerHomepage.getMaxAttemptCourseNumberText().getText();

        int maxAttemptInt = parsePositiveInt(maxAttemptStr, "Max Attempts");
        if (maxAttemptInt == -1) return;

        ArrayList<DBModule> moduleAL = dao.readModulesFromCourseCode(courseCode);
        if (moduleAL.isEmpty()){
            showMessage(
                    "No modules found in '%s' course".formatted(courseCode),
                    "Empty Search",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int successCount = 0;
        int failCount = 0;

        for (DBModule m : moduleAL){
            m.setMaxAttempts(maxAttemptInt);
            if (dao.updateModuleMaxAttempts(m)) {
                successCount++;
            } else {
                failCount++;
                System.out.println("Failed to update module: " + m.getModuleID());
            }
        }

        if (failCount == 0) {
            showMessage(
                    "All modules in course %s are updated to max attempts of %d.".formatted(courseCode, maxAttemptInt),
                    "Update Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessage("Update complete with database errors.\nSuccess: " + successCount + "\nFailed: " + failCount,
                    "Partially Successful Update", JOptionPane.WARNING_MESSAGE);
        }
    }



    public static void main(String[] args) {
        JFrame  mainFrame=new JFrame("USMS - Manager Homepage");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PageHandler handler = new PageHandler() {
            @Override
            public void switchView(View view, DBUser user) {}
        };

        DBUser user = new DBUser("John", "Smithy", "12345678", "Z", "my.email.is@nonesense.com", "1000-01-01", true, true);

        ViewManagerHomepage vManagerHomepage = new ViewManagerHomepage();
        new ControllerManager(vManagerHomepage, handler, user);

        mainFrame.add(vManagerHomepage.getPanelMain());

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}

record ModuleUpdateRecord(String moduleID, int currentMax, boolean change){};