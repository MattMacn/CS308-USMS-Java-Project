import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewStudentHomepage extends JPanel implements HomepageView{
    private JPanel panelMain;
    private JButton signOutButton;
    private JButton changePasswordButton;
    private JTextArea moduleTextArea;
    private JComboBox selectModuleCombo;
    private JComboBox selectWeekCombo;
    private JCheckBox labCheckBox;
    private JCheckBox lectureCheckBox;
    private JButton downloadButton;
    private JButton selectButton;
    private JLabel userEmailLabel;
    private JTable ModuleGradesTable;
    private JLabel currentDecisionLabel;
    private JScrollPane GradesTable;
    private JLabel userNameLabel;
    private JLabel userCourseLabel;
    private JLabel userDepartmentLabel;

    public ViewStudentHomepage() {
        String[] tableHeaders = {"Module", "Type", "Grade (%)", "Attempts", "Max Attempts", "Pass/Fail"};
        Object[][] tableData = {};
        DefaultTableModel tableModel = new DefaultTableModel(tableData, tableHeaders);
        getModuleGradesTable().setModel(tableModel);
        getModuleGradesTable().setEnabled(false);
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JButton getSignOutButton() {
        return signOutButton;
    }

    public void setSignOutButton(JButton signOutButton) {
        this.signOutButton = signOutButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public void setChangePasswordButton(JButton ChangePasswordButton) {
        this.changePasswordButton = ChangePasswordButton;
    }

    public JTextArea getTextArea1() {
        return moduleTextArea;
    }

    public void setTextArea1(JTextArea textArea1) {
        this.moduleTextArea = textArea1;
    }

    public JTable setModuleGradesTable() {
        return ModuleGradesTable;
    }

    public void ModuleGradesTable(JTable ModuleGradesTable) {
        this.ModuleGradesTable = ModuleGradesTable;
    }

    public JComboBox getSelectModuleCombo() {
        return selectModuleCombo;
    }

    public void setSelectModuleCombo(JComboBox SelectModuleCombo) {
        this.selectModuleCombo = SelectModuleCombo;
    }

    public JComboBox getSelectWeekCombo() {
        return selectWeekCombo;
    }

    public void setSelectWeekCombo(JComboBox SelectWeekCombo) {
        this.selectWeekCombo = SelectWeekCombo;
    }

    public JCheckBox getLabCheckBox() {
        return labCheckBox;
    }

    public void setLabCheckBox(JCheckBox labCheckBox) {
        this.labCheckBox = labCheckBox;
    }

    public JCheckBox getLectureCheckBox() {
        return lectureCheckBox;
    }

    public void setLectureCheckBox(JCheckBox lectureCheckBox) {
        this.lectureCheckBox = lectureCheckBox;
    }

    public JButton getDownloadButton() {
        return downloadButton;
    }

    public void setDownloadButton(JButton downloadButton) {
        this.downloadButton = downloadButton;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(JButton selectButton) {
        this.selectButton = selectButton;
    }

    public JLabel getUserEmailLabel() {
        return userEmailLabel;
    }

    public void setUserEmailLabel(JLabel userEmailLabel) {
        this.userEmailLabel = userEmailLabel;
    }

    public JTable getModuleGradesTable() {
        return ModuleGradesTable;
    }

    public void setModuleGradesTable(JTable moduleGradesTable) {
        this.ModuleGradesTable = moduleGradesTable;
    }

    public JLabel getCurrentDecisionLabel() {
        return currentDecisionLabel;
    }

    public void setCurrentDecisionLabel(JLabel currentDecisionLabel) {
        this.currentDecisionLabel = currentDecisionLabel;
    }

    public JTextArea getModuleTextArea() {
        return moduleTextArea;
    }

    public void setModuleTextArea(JTextArea moduleTextArea) {
        this.moduleTextArea = moduleTextArea;
    }

    public JScrollPane getGradesTable() {
        return GradesTable;
    }

    public void setGradesTable(JScrollPane gradesTable) {
        GradesTable = gradesTable;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(JLabel userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public JLabel getUserCourseLabel() {
        return userCourseLabel;
    }

    public void setUserCourseLabel(JLabel userCourseLabel) {
        this.userCourseLabel = userCourseLabel;
    }

    public JLabel getUserDepartmentLabel() {
        return userDepartmentLabel;
    }

    public void setUserDepartmentLabel(JLabel userDepartmentLabel) {
        this.userDepartmentLabel = userDepartmentLabel;
    }
}



