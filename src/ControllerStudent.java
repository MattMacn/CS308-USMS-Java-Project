import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ControllerStudent extends HomepageController{

    private ViewStudentHomepage vStudentHomepage;
    private DBStudent currentStudent;

    // Used by most callers/constructions and automatically uses the constructor bellow as well
    public ControllerStudent(ViewStudentHomepage vStudentHomepage, PageHandler handler, DBUser user){
        this(vStudentHomepage, new DAOClass(), handler, user);
    }

    // Called by tester class directly
    public ControllerStudent(ViewStudentHomepage vStudentHomepage, DAOClass dao, PageHandler handler, DBUser user) {
        super(vStudentHomepage, dao, handler, user);
        this.vStudentHomepage = vStudentHomepage;
        currentStudent = dao.readStudentFromUserID(currentUser.getUserID());

        // fill in header
        vStudentHomepage.getUserEmailLabel().setText(user.getEmail());
        vStudentHomepage.getUserNameLabel().setText(currentUser.getFirstName() + " " + currentUser.getSurname());
        vStudentHomepage.getUserCourseLabel().setText(
                currentStudent.getCourse().getCourseCode()
                        + " - " + currentStudent.getCourse().getCourseName());
        DBDepartment dep = dao.readDepartment(currentStudent.getCourse().getDepartmentCode());
        vStudentHomepage.getUserDepartmentLabel().setText(dep.getDepartmentCode() + " - " + dep.getDepartmentName());

        setupListeners();
        refreshModuleDropdown();
        displayGrades();
        displayDecision();

    }

    void setupListeners(){
        this.vStudentHomepage.getSelectButton().addActionListener( e-> handleSelectButton());
        this.vStudentHomepage.getDownloadButton().addActionListener(e -> handleDownloadButton());
    }

    // Drop down menu will display Modules
    private void refreshModuleDropdown() {

        ArrayList<DBModule> modules = dao.readModulesFromStudentID(currentStudent.getStudentID());

        vStudentHomepage.getSelectModuleCombo().removeAllItems();

        if (modules != null && !modules.isEmpty()) {
            for (DBModule module : modules) {
                String item = module.getModuleCode() + " : " + module.getModuleName();
                vStudentHomepage.getSelectModuleCombo().addItem(item);
            }
        }
    }

    // When "Select" is pressed all data below will be populated ("Module Info" & "Grades" in separate fields)
    private void handleSelectButton() {
        System.out.println("Select button pressed");

        String selectedItem = (String) vStudentHomepage.getSelectModuleCombo().getSelectedItem();

        if (selectedItem == null || !selectedItem.contains(" : ")) {
            showMessage("Please select a module from the dropdown",
                            "Selection error",
                            JOptionPane.WARNING_MESSAGE);
            vStudentHomepage.getTextArea1().setText(""); //clear
            //vStudentHomepage.getModuleGradesTable().setTableHeader(null); //clear
            return;
        }

        String modCode = selectedItem.split(" : ", 2)[0];

        DBModule module = dao.readModuleFromModuleCode(modCode);
        if (module != null) {
            String moduleInfo = String.format(
                    "Module Code: %s\nModule Name: %s\n\n--- Description ---\n%s",
                    module.getModuleCode(),
                    module.getModuleName(),
                    module.getModuleDescription()
            );
            vStudentHomepage.getTextArea1().setText(moduleInfo);

        } else {
            showMessage(
                    "Could not find module data for %'s'".formatted(modCode),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        refreshModuleDropdown();
        displayDecision();
        displayGrades();
    }

    void displayDecision(){
        ArrayList<DBDecision> studentDecisionArray = dao.readDecisionsFromStudentID(currentStudent.getStudentID());
        String studentDecision = "N/A";
        if(!studentDecisionArray.isEmpty()) {
            studentDecision = Award.asString(studentDecisionArray.getLast().getAward());
        }
        vStudentHomepage.getCurrentDecisionLabel().setText(studentDecision);
    }

    void displayGrades(){

        DefaultTableModel tableModel = (DefaultTableModel) vStudentHomepage.getModuleGradesTable().getModel();
        tableModel.setRowCount(0);

        ArrayList<DBModule> modules = dao.readModulesFromStudentID(currentStudent.getStudentID());
        if (modules == null) return;
        for(DBModule m : modules){
            ArrayList<DBResult> allResults = dao.readAllResultsFromStudentIDModuleID(currentStudent.getStudentID(), m.getModuleID());
            int maxAttempts = m.getMaxAttempts();

            if (allResults != null && !allResults.isEmpty()) {
                for (DBResult res : allResults) {

                    String passFail = "Fail";
                    if (res.getMark() > 49 && res.getAttemptNumber() <= maxAttempts) {
                        passFail = "Pass";
                    }

                    String[] tmpRow = {
                            m.getModuleName(),
                            ModuleResult.asString(res.getResultType()),
                            String.valueOf(res.getMark()),
                            String.valueOf(res.getAttemptNumber()),
                            String.valueOf(maxAttempts),
                            passFail
                    };
                    tableModel.addRow(tmpRow);
                }
            } else {
                String[] tmpRow = {
                        m.getModuleName(),
                        "n/a",
                        "n/a",
                        "0",
                        String.valueOf(maxAttempts),
                        "n/a"
                };
                tableModel.addRow(tmpRow);
            }
        }
    }

    // When the week of Modules & Lab |& Lecture is selected, download will download the files to local
    private void handleDownloadButton() {
        System.out.println("Download button pressed");

        String selectedModule = (String) vStudentHomepage.getSelectModuleCombo().getSelectedItem();
        String selectedWeek = (String) vStudentHomepage.getSelectWeekCombo().getSelectedItem();
        boolean downloadLab = vStudentHomepage.getLabCheckBox().isSelected();
        boolean downloadLecture = vStudentHomepage.getLectureCheckBox().isSelected();

        if (selectedModule == null || !selectedModule.contains(" : ")) {
            showMessage("Please select a module first", "DOWNLOAD ERROR", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedWeek == null) {
            showMessage("Please select a week first", "DOWNLOAD ERROR", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!downloadLab && !downloadLecture) {
            showMessage("Please select a lab or lecture to download first", "DOWNLOAD ERROR", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String moduleCode = selectedModule.split(" : ", 2)[0];
        int weekNumber;

        try {
            String weekNumStr = selectedWeek.split(" ")[1];
            weekNumber = Integer.parseInt(weekNumStr);
        } catch (Exception e) {
            showMessage("Week number selection error", "DOWNLOAD ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve the material object (which contains the temporary Files)
        DBMaterial material = dao.downloadModuleMaterial(moduleCode, weekNumber);

        if (material != null) {
            StringBuilder details = new StringBuilder();
            boolean successfulDownload = false;

            // --- Save Lecture Notes ---
            if (downloadLecture) {
                if (material.getLectureNote() != null) {
                    // Create a pretty filename for the user
                    String fileName = moduleCode + "_Week" + weekNumber + "_Lecture.txt";

                    if (saveFileToDisk(material.getLectureNote(), fileName)) {
                        details.append("- Lecture Notes saved successfully.\n");
                        successfulDownload = true;
                    } else {
                        details.append("- Lecture Notes save cancelled or failed.\n");
                    }
                } else {
                    details.append("- No Lecture Notes available for this week.\n");
                }
            }

            // --- Save Lab Notes ---
            if (downloadLab) {
                if (material.getLabNote() != null) {
                    String fileName = moduleCode + "_Week" + weekNumber + "_Lab.txt";

                    if (saveFileToDisk(material.getLabNote(), fileName)) {
                        details.append("- Lab Notes saved successfully.\n");
                        successfulDownload = true;
                    } else {
                        details.append("- Lab Notes save cancelled or failed.\n");
                    }
                } else {
                    details.append("- No Lab Notes available for this week.\n");
                }
            }

            if (successfulDownload) {
                showMessage(details.toString(), "Download Process Completed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage(details.toString(), "Download Warning", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            showMessage("No materials found for " + moduleCode + " Week " + weekNumber,
                    "Download Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean saveFileToDisk(File sourceFile, String defaultName) {
        if (sourceFile == null || !sourceFile.exists()) return false;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(defaultName));
        fileChooser.setDialogTitle("Save " + defaultName);

        int userSelection = fileChooser.showSaveDialog(vStudentHomepage.getPanelMain());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destinationFile = fileChooser.getSelectedFile();
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException ex) {
                showMessage("Error saving file: " + ex.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
}

