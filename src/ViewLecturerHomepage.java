import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewLecturerHomepage extends JPanel implements HomepageView {
    private JPanel panelMain;
    private JComboBox comboBox1;
    private JTextArea INFORMATIONTextArea;
    private JButton updateButton;
    private JButton changePasswordButton;
    private JComboBox comboBox2;
    private JButton selectFileButton;
    private JTable StudentDecisionTable;
    private JTextField xxxxTxtTextField;
    private JButton uploadFilesButton;
    private JLabel userEmailLabel;
    private JButton signOutButton;
    private JButton selectModuleButton;
    private JRadioButton labMaterialRadioButton;
    private JRadioButton lectureMaterialRadioButton;
    private JLabel userNameLabel;
    private JLabel userDepartmentLabel;
    private JButton editTableButton;
    private JTextField markField;
    private JTextField forenameField;
    private JTextField emailField;
    private JTextField attnumField;
    private JComboBox resultTypeDropdown;

    public ViewLecturerHomepage(){
        String[] tableHeaders = {"Email", "First Name", "Surname", "Grade %", "Attempt No.", "Result Type"};
        Object[][] tableData = {};
        DefaultTableModel tableModel = new DefaultTableModel(tableData, tableHeaders);
        getStudentDecisionTable().setModel(tableModel);
        getStudentDecisionTable().setEnabled(false);
        getResultTypeDropdown().addItem(ModuleResult.asString(ModuleResult.EXAM));
        getResultTypeDropdown().addItem(ModuleResult.asString(ModuleResult.LAB));
        getResultTypeDropdown().addItem(ModuleResult.asString(ModuleResult.LABANDEXAM));
        getResultTypeDropdown().addItem(ModuleResult.asString(ModuleResult.OTHER));
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public JTextField getForenameField(){return forenameField;}


    public JTextField getMarkField(){return markField;}

    public JTextField getAttnumField(){return attnumField;}

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JTextArea getINFORMATIONTextArea() {
        return INFORMATIONTextArea;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JComboBox getComboBox2() {
        return comboBox2;
    }

    public JButton getSelectFileButton() {
        return selectFileButton;
    }

    public JButton getEditTableButton(){
        return editTableButton;
    }

    public JTextField getXxxxTxtTextField() {
        return xxxxTxtTextField;
    }

    public JButton getUploadFilesButton() {
        return uploadFilesButton;
    }

    public JTable getStudentDecisionTable() {
        return StudentDecisionTable;
    }

    public JLabel getUserEmail(){return userEmailLabel;}

    // --- Setters ---

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public void setForenameField(JTextField forenameField) {this.forenameField = forenameField;}

    public void setMarkField(JTextField markField) {this.markField = markField;}

    public void setAttnumField(JTextField attnumField) {this.attnumField = attnumField;}

    public void setComboBox1(JComboBox comboBox1) {
        this.comboBox1 = comboBox1;
    }

    public void setINFORMATIONTextArea(JTextArea INFORMATIONTextArea) {
        this.INFORMATIONTextArea = INFORMATIONTextArea;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public void setEditTableButton(JButton editTableButton) {
        this.editTableButton = editTableButton;
    }
    public void setChangePasswordButton(JButton changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
    }

    public void setComboBox2(JComboBox comboBox2) {
        this.comboBox2 = comboBox2;
    }

    public void setSelectFileButton(JButton selectFileButton) {
        this.selectFileButton = selectFileButton;
    }

    public void setXxxxTxtTextField(JTextField xxxxTxtTextField) {
        this.xxxxTxtTextField = xxxxTxtTextField;
    }

    public void setUploadFilesButton(JButton uploadFilesButton) {
        this.uploadFilesButton = uploadFilesButton;
    }

    public void setStudentDecisionTable(JTable studentDecisionTable) {
        StudentDecisionTable = studentDecisionTable;
    }

    public void setUserEmail(JLabel userEmail){this.userEmailLabel = userEmail;}

    public JButton getSignOutButton() {
        return signOutButton;
    }

    public void setSignOutButton(JButton signOutButton) {
        this.signOutButton = signOutButton;
    }

    public JButton getSelectModuleButton() {
        return selectModuleButton;
    }

    public void setSelectModuleButton(JButton selectModuleButton) {
        this.selectModuleButton = selectModuleButton;
    }

    public JRadioButton getLabMaterialRadioButton() {
        return labMaterialRadioButton;
    }

    public void setLabMaterialRadioButton(JRadioButton labMaterialRadioButton) {
        this.labMaterialRadioButton = labMaterialRadioButton;
    }

    public JRadioButton getLectureMaterialRadioButton() {
        return lectureMaterialRadioButton;
    }

    public void setLectureMaterialRadioButton(JRadioButton lectureMaterialRadioButton) {
        this.lectureMaterialRadioButton = lectureMaterialRadioButton;
    }

    public JLabel getUserEmailLabel() {
        return userEmailLabel;
    }

    public void setUserEmailLabel(JLabel userEmailLabel) {
        this.userEmailLabel = userEmailLabel;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(JLabel userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public JLabel getUserCourseLabel() {
        return userDepartmentLabel;
    }

    public void setUserCourseLabel(JLabel userCourseLabel) {
        this.userDepartmentLabel = userCourseLabel;
    }

    public JLabel getUserDepartmentLabel() {
        return userDepartmentLabel;
    }

    public void setUserDepartmentLabel(JLabel userDepartmentLabel) {
        this.userDepartmentLabel = userDepartmentLabel;
    }

    public JComboBox getResultTypeDropdown() {
        return resultTypeDropdown;
    }

    public void setResultTypeDropdown(JComboBox resultTypeDropdown) {
        this.resultTypeDropdown = resultTypeDropdown;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }
}

