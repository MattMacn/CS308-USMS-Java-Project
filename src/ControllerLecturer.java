import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;

public class ControllerLecturer extends HomepageController {

    private ViewLecturerHomepage vLecturerHome;
    private DBModule chosenModule;
    public DBLecturer currentLecturer;
    private boolean uploadFile;
    File fileP;

    // Used by most callers
    public ControllerLecturer(ViewLecturerHomepage vLecturerHome, PageHandler handler, DBUser user) {
        this(vLecturerHome, new DAOClass(), handler, user);
    }

    // Called by tester class directly
    public ControllerLecturer(ViewLecturerHomepage vLecturerHome, DAOClass dao, PageHandler handler, DBUser user) {
        super(vLecturerHome, dao, handler, user);
        this.vLecturerHome = vLecturerHome;
        vLecturerHome.getUserEmail().setText(user.getEmail());
        currentLecturer = dao.readLecturerFromUserID(user.getUserID());
        vLecturerHome.getUserNameLabel().setText(currentUser.getFirstName() + " " + currentUser.getSurname());
        DBDepartment tmpDep = dao.readDepartment(currentLecturer.getDepartmentCode());
        vLecturerHome.getUserDepartmentLabel().setText(tmpDep.getDepartmentCode() + " - " + tmpDep.getDepartmentName());
        uploadFile = false;

        DefaultTableModel tableModel = (DefaultTableModel) vLecturerHome.getStudentDecisionTable().getModel();
        tableModel.setRowCount(0);

        refreshModuleComboBox();
        setupListeners();
    }


    private void setupListeners() {
        this.vLecturerHome.getUpdateButton().addActionListener(e -> handleUpdateModuleInfo());
        this.vLecturerHome.getSelectFileButton().addActionListener(e -> selectFileForUpload());
        this.vLecturerHome.getUploadFilesButton().addActionListener(e -> handleUploadSelectedFiles());
        this.vLecturerHome.getSelectModuleButton().addActionListener(e -> handleSelectModule());
        this.vLecturerHome.getEditTableButton().addActionListener(e -> handleResultUpdate());

    }

    public void refreshModuleComboBox() {
        //fill out modulecombo box with modules allocated to logged in lecturer
        vLecturerHome.getComboBox1().removeAllItems();
        String lecID = currentLecturer.getlecturerID();
        ArrayList<DBModule> lecMods = dao.readModulesFromLecturerID(lecID);
        if (!lecMods.isEmpty()) {
            for (DBModule mod : lecMods) {
                String currMod = mod.getModuleName();
                vLecturerHome.getComboBox1().addItem(currMod);
            }
        } else {
            showMessage("No modules assigned to Lecturer",
                    "Empty Database", JOptionPane.WARNING_MESSAGE);
        }

    }

    void handleSelectModule() {
        System.out.println("Select Module button pressed");
        String selectedModuleName = (String) vLecturerHome.getComboBox1().getSelectedItem();

        if (selectedModuleName == null) {
            showMessage("No module displayed in dropdown".formatted(selectedModuleName),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        chosenModule = dao.readModuleFromModuleName(selectedModuleName);

        if (chosenModule == null) {
            showMessage("%s module could not be found in database".formatted(selectedModuleName),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshModuleInfo();
        refreshTableData();
    }

    void refreshModuleInfo() {
        if (chosenModule == null) {
            showMessage("No Module Selected from dropdown",
                    "Input Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        vLecturerHome.getINFORMATIONTextArea().setText(chosenModule.getModuleDescription());
    }

    public void handleUpdateModuleInfo() {
        String information = vLecturerHome.getINFORMATIONTextArea().getText().trim();
        System.out.println("Updating Lecturer Information: " + information);

        if (chosenModule == null) {
            showMessage("No Module Selected from dropdown",
                    "Input Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        chosenModule.setModuleDescription(information);
        if (dao.updateModuleDescription(chosenModule)) {
            showMessage("%s description was updated successfully".formatted(chosenModule.getModuleName()),
                    "Update Successful", JOptionPane.INFORMATION_MESSAGE);
        }

    }


    public void selectFileForUpload() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(vLecturerHome.getPanelMain());
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            fileP = fileChooser.getSelectedFile();
            vLecturerHome.getXxxxTxtTextField().setText(filePath);
            System.out.println("File selected: " + filePath);
            uploadFile = true;
        } else {
            uploadFile = false;
        }
    }

    public void handleUploadSelectedFiles() {
        if (!uploadFile || fileP == null) {
            showMessage("Select a file to continue upload", "Input Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (chosenModule == null) {
            showMessage("No Module Selected from dropdown",
                    "Input Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String modID = chosenModule.getModuleID();
        int weekNo = vLecturerHome.getComboBox2().getSelectedIndex() + 1;

        boolean isLab = vLecturerHome.getLabMaterialRadioButton().isSelected();
        boolean isLecture = vLecturerHome.getLectureMaterialRadioButton().isSelected();

        if (!isLab && !isLecture) {
            showMessage("Select type of material (Lecture/Lab) to continue upload",
                    "Input Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DBModuleMaterial existingLink = dao.readModuleMaterials(modID, weekNo);

        if (existingLink == null) {
            System.out.println("No existing material for Week " + weekNo + ". Creating new.");

            File lecFile = isLecture ? fileP : null;
            File labFile = isLab ? fileP : null;

            DBMaterial newMaterial = new DBMaterial(lecFile, labFile);

            if (dao.createMaterials(newMaterial)) {
                DBModuleMaterial newLink = new DBModuleMaterial(modID, newMaterial.getMaterialID(), weekNo);
                if (dao.createModuleMaterials(newLink)) {
                    showMessage("Files Uploaded Successfully (New Entry)",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showMessage("Error linking material to module",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("Error creating material record",
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            System.out.println("Found existing material ID: " + existingLink.getMaterialID());

            String existingMatID = existingLink.getMaterialID();

            File lecFile = isLecture ? fileP : null;
            File labFile = isLab ? fileP : null;

            DBMaterial updateMaterial = new DBMaterial(existingMatID, lecFile, labFile);

            boolean success = false;
            if (isLecture) {
                success = dao.updateMaterialsLectureNote(updateMaterial);
            } else {
                success = dao.updateMaterialsLabNote(updateMaterial);
            }

            if (success) {
                showMessage("File Added to Week " + weekNo + " Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage("Update failed", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        fileP = null;
        uploadFile = false;
        vLecturerHome.getXxxxTxtTextField().setText("Select a file...");
    }


    public void refreshTableData() {
        System.out.println("refresh table pressed");

        String selectedModuleName = (String) vLecturerHome.getComboBox1().getSelectedItem();

        if (chosenModule == null) {
            showMessage("Please select a module from the module dropdown",
                    "Input Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        chosenModule = dao.readModuleFromModuleName(selectedModuleName);
        String modID = chosenModule.getModuleID();
        ArrayList<DBStudent> studsInMod = dao.readStudentsFromModuleID(modID);
        DefaultTableModel tableModel = (DefaultTableModel) vLecturerHome.getStudentDecisionTable().getModel();

        tableModel.setRowCount(0);

        if (studsInMod != null) {
            for (DBStudent stud : studsInMod) {
                DBResult tmpResult = dao.readLatestResultsFromStudentIDModuleID(stud.getStudentID(), modID);
                if (tmpResult != null) {
                    DBUser studUsr = stud.getUser();
                    String[] tmpRow = {
                            studUsr.getEmail(),
                            studUsr.getFirstName(),
                            studUsr.getSurname(),
                            String.valueOf(tmpResult.getMark()),
                            String.valueOf(tmpResult.getAttemptNumber()),
                            ModuleResult.asString(tmpResult.getResultType())
                    };
                    tableModel.addRow(tmpRow);
                }
            }
        }
    }

    public void handleResultUpdate(){

        String newEmail = vLecturerHome.getEmailField().getText().trim();
        String newMarkStr = vLecturerHome.getMarkField().getText().trim();
        String newAttNumStr = vLecturerHome.getAttnumField().getText().trim();
        String newResType = (String) vLecturerHome.getResultTypeDropdown().getSelectedItem();

        if(newEmail.isEmpty()||newMarkStr.isEmpty()|| newAttNumStr.isEmpty()|| newResType == null){
            showMessage("Empty input fields, please enter all the details.",
                    "Input Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validEmail(newEmail)) {
            showMessage("Please enter a valid student email.",
                    "Input Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int newAttnum = parsePositiveInt(newAttNumStr, "Attempt Number");
        if (newAttnum == -1) return;

        double newMark;
        try {
            newMark = Double.parseDouble(newMarkStr);
            if (newMark < 0 || newMark > 100) {
                showMessage("Mark must be between 0 and 100.",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Mark must be a valid number.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (chosenModule == null) {
            showMessage("System Error: Selected module could not be found.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DBStudent stud = dao.readStudentFromEmail(newEmail);
        if (stud != null) {

            boolean moduleFound = false;
            ArrayList<DBModule> modules = dao.readModulesFromStudentID(stud.getStudentID());
            if(modules==null) {
                showMessage("No modules found in database",
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (DBModule m : modules){
                if(Objects.equals(m.getModuleCode(), chosenModule.getModuleCode())){
                    moduleFound = true;
                    break;
                }
            }

            if(!moduleFound){
                showMessage("Selected student not enrolled in selected module",
                        "Input Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DBResult previousResult = dao.readLatestResultsFromStudentIDModuleID(stud.getStudentID(), chosenModule.getModuleID());

            int expectedAttempt = 1;
            if (previousResult != null) {
                expectedAttempt = previousResult.getAttemptNumber() + 1;
            }

            if (newAttnum != expectedAttempt) {
                showMessage("Invalid Attempt Number.\n\nCurrent Attempt: " + (expectedAttempt - 1) +
                                "\nExpected New Attempt: " + expectedAttempt,
                        "Logic Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DBResult newRes = new DBResult(chosenModule.getModuleID(), ModuleResult.fromString(newResType), newMark, newAttnum);

            if (dao.createResult(stud, newRes)) {
                showMessage("Result updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTableData();
            } else {
                showMessage("Database Error: Result was not updated.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showMessage("Student with email '%s' not found.".formatted(newEmail),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}