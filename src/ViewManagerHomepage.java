import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewManagerHomepage extends JPanel implements HomepageView{
    private JButton signOutButton;
    private JPanel panelMain;
    private JTabbedPane tabbedPaneOptions;
    private JButton approveButton;
    private JTable StudentDecisionTable;
    private JPanel panelStudentDecision;
    private JPanel panelStudent;
    private JComboBox courseEnrolDropDown;
    private JTextField emailDecisionText;
    private JButton searchDecisionButton;
    private JLabel nameDecisionLabel;
    private JRadioButton awardRadioButton;
    private JRadioButton resitRadioButton;
    private JRadioButton withdrawRadioButton;
    private JButton confirmDecisionButton;
    private JLabel emailDecisionLabel;
    private JLabel userNameLabel;
    private JLabel userEmailLabel;
    private JButton changePasswordButton;
    private JTextField emailAssignText;
    private JButton searchAssignButton;
    private JLabel nameAssignLabel;
    private JButton assignButton;
    private JComboBox AssignDropdown;
    private JLabel emailAssignLabel;
    private JButton activateButton;
    private JButton deactivateButton;
    private JButton passwordResetButton;
    private JComboBox awaitingDropdown;
    private JButton awaitingSelectButton;
    private JTextField emailExistingText;
    private JButton searchExistingButton;
    private JLabel nameExistingLabel;
    private JLabel userTypeExistingLabel;
    private JLabel EmailExistingLabel;
    private JTextField compensateText;
    private JButton compensateUpdateButton;
    private JComboBox exisingModuleSelectDropdown;
    private JButton exisingModuleShowButton;
    private JTextField adjustCourseNameText;
    private JButton adjustCourseUpdateButton;
    private JLabel currentDecisionLabel;
    private JComboBox adjustCoursedDropdown;
    private JButton addCourseButton;
    private JComboBox addModuleSemesterDropdown;
    private JComboBox addCourseQualificationDropdown;
    private JComboBox addCourseDepartmentDropdown;
    private JButton addModuleButton;
    private JTextField addCourseCodeText;
    private JTextField addCourseNameText;
    private JTextArea addCourseDescriptionText;
    private JComboBox adjustCourseQualificationDropdown;
    private JComboBox adjustCourseDepartmentDropdown;
    private JTextArea adjustCourseDescriptionText;
    private JPanel ModuleTab;
    private JPanel StudentTab;
    private JPanel LecturerTab;
    private JPanel CourseTab;
    private JPanel MiscTab;
    private JButton adjustCourseSelectButton;
    private JLabel adjustCourseCodeLabel;
    private JTextField addModuleCodeText;
    private JTextField addModuleNameText;
    private JTextField addModuleCreditText;
    private JLabel existingModuleCodeLabel;
    private JTextField existingModuleNameText;
    private JTextField existingModuleCreditText;
    private JComboBox existingModuleSemesterDropdown;
    private JButton existingModuleUpdateButton;
    private JComboBox addModuleCourseDropdown;
    private JComboBox existingModuleCourseDropdown;
    private JLabel courseDecisionLabel;
    private JTabbedPane tabbedPaneMaxAttempts;
    private JComboBox maxAttemptCourseDropdown;
    private JTextField maxAttemptCourseNumberText;
    private JTable tableBusinessRule;
    private JButton updateAllSelectedModulesButton;
    private JButton maxAttemptCourseUpdate;
    private JButton declineButton;
    private JLabel awaitingEmailLabel;
    private JLabel awaitingTypeLabel;
    private JLabel awaitingNameLabel;
    private JLabel stateExistingLabel;
    private JPanel enrolStudentPanel;
    private JTextField addModuleAttemptText;
    private JTextArea addModuleDescriptionText;
    private JTextField existingModuleAttemptsText;
    private JTextArea existingModuleDescriptionText;
    private JComboBox departmentEnrolDropdown;
    private JLabel qualificationEnrolLabel;
    private JPanel enrolLecturePanel;
    private JLabel compensationLabel;

    public ViewManagerHomepage(){
        String[] tableHeaders = {"Module", "Type", "Grade (%)", "Attempts", "Max Attempts", "Pass/Fail"};
        Object[][] tableData = {};
        DefaultTableModel tableModel = new DefaultTableModel(tableData, tableHeaders);
        getStudentDecisionTable().setModel(tableModel);
        getStudentDecisionTable().setEnabled(false);

        String[] businessTableHeader = {"Code", "Module", "Attempts", "Change"};
        Object[][] businessTableData = {};
        DefaultTableModel BusinessModel = new DefaultTableModel(businessTableData, businessTableHeader) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3;
            }
        };
        getTableBusinessRule().setModel(BusinessModel);

        getEnrolStudentPanel().setVisible(false);
        getEnrolLecturePanel().setVisible(false);
    }

    public JButton getSignOutButton() {
        return signOutButton;
    }

    public void setSignOutButton(JButton signOutButton) {
        this.signOutButton = signOutButton;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTabbedPane getTabbedPaneOptions() {
        return tabbedPaneOptions;
    }

    public void setTabbedPaneOptions(JTabbedPane tabbedPaneOptions) {
        this.tabbedPaneOptions = tabbedPaneOptions;
    }

    public JButton getApproveButton() {
        return approveButton;
    }

    public void setApproveButton(JButton approveButton) {
        this.approveButton = approveButton;
    }

    public JTable getStudentDecisionTable() {
        return StudentDecisionTable;
    }

    public void setStudentDecisionTable(JTable studentDecisionTable) {
        StudentDecisionTable = studentDecisionTable;
    }

    public JPanel getPanelStudentDecision() {
        return panelStudentDecision;
    }

    public void setPanelStudentDecision(JPanel panelStudentDecision) {
        this.panelStudentDecision = panelStudentDecision;
    }

    public JPanel getPanelStudent() {
        return panelStudent;
    }

    public void setPanelStudent(JPanel panelStudent) {
        this.panelStudent = panelStudent;
    }

    public JComboBox getCourseEnrolDropDown() {
        return courseEnrolDropDown;
    }

    public void setCourseEnrolDropDown(JComboBox courseEnrolDropDown) {
        this.courseEnrolDropDown = courseEnrolDropDown;
    }

    public JTextField getEmailDecisionText() {
        return emailDecisionText;
    }

    public void setEmailDecisionText(JTextField emailDecisionText) {
        this.emailDecisionText = emailDecisionText;
    }

    public JButton getSearchDecisionButton() {
        return searchDecisionButton;
    }

    public void setSearchDecisionButton(JButton searchDecisionButton) {
        this.searchDecisionButton = searchDecisionButton;
    }

    public JLabel getNameDecisionLabel() {
        return nameDecisionLabel;
    }

    public void setNameDecisionLabel(JLabel nameDecisionLabel) {
        this.nameDecisionLabel = nameDecisionLabel;
    }

    public JRadioButton getAwardRadioButton() {
        return awardRadioButton;
    }

    public void setAwardRadioButton(JRadioButton awardRadioButton) {
        this.awardRadioButton = awardRadioButton;
    }

    public JRadioButton getResitRadioButton() {
        return resitRadioButton;
    }

    public void setResitRadioButton(JRadioButton resitRadioButton) {
        this.resitRadioButton = resitRadioButton;
    }

    public JRadioButton getWithdrawRadioButton() {
        return withdrawRadioButton;
    }

    public void setWithdrawRadioButton(JRadioButton withdrawRadioButton) {
        this.withdrawRadioButton = withdrawRadioButton;
    }

    public JButton getConfirmDecisionButton() {
        return confirmDecisionButton;
    }

    public void setConfirmDecisionButton(JButton confirmDecisionButton) {
        this.confirmDecisionButton = confirmDecisionButton;
    }

    public JLabel getEmailDecisionLabel() {
        return emailDecisionLabel;
    }

    public void setEmailDecisionLabel(JLabel emailDecisionLabel) {
        this.emailDecisionLabel = emailDecisionLabel;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(JLabel userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public JLabel getUserEmailLabel() {
        return userEmailLabel;
    }

    public void setUserEmailLabel(JLabel userEmailLabel) {
        this.userEmailLabel = userEmailLabel;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public void setChangePasswordButton(JButton changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
    }

    public JTextField getEmailAssignText() {
        return emailAssignText;
    }

    public void setEmailAssignText(JTextField emailAssignText) {
        this.emailAssignText = emailAssignText;
    }

    public JButton getSearchAssignButton() {
        return searchAssignButton;
    }

    public void setSearchAssignButton(JButton searchAssignButton) {
        this.searchAssignButton = searchAssignButton;
    }

    public JLabel getNameAssignLabel() {
        return nameAssignLabel;
    }

    public void setNameAssignLabel(JLabel nameAssignLabel) {
        this.nameAssignLabel = nameAssignLabel;
    }

    public JButton getAssignButton() {
        return assignButton;
    }

    public void setAssignButton(JButton assignButton) {
        this.assignButton = assignButton;
    }

    public JComboBox getAssignDropdown() {
        return AssignDropdown;
    }

    public void setAssignDropdown(JComboBox assignDropdown) {
        AssignDropdown = assignDropdown;
    }

    public JLabel getEmailAssignLabel() {
        return emailAssignLabel;
    }

    public void setEmailAssignLabel(JLabel emailAssignLabel) {
        this.emailAssignLabel = emailAssignLabel;
    }

    public JButton getActivateButton() {
        return activateButton;
    }

    public void setActivateButton(JButton activateButton) {
        this.activateButton = activateButton;
    }

    public JButton getDeactivateButton() {
        return deactivateButton;
    }

    public void setDeactivateButton(JButton deactivateButton) {
        this.deactivateButton = deactivateButton;
    }

    public JButton getPasswordResetButton() {
        return passwordResetButton;
    }

    public void setPasswordResetButton(JButton passwordResetButton) {
        this.passwordResetButton = passwordResetButton;
    }

    public JComboBox getAwaitingDropdown() {
        return awaitingDropdown;
    }

    public void setAwaitingDropdown(JComboBox awaitingDropdown) {
        this.awaitingDropdown = awaitingDropdown;
    }

    public JButton getAwaitingSelectButton() {
        return awaitingSelectButton;
    }

    public void setAwaitingSelectButton(JButton awaitingSelectButton) {
        this.awaitingSelectButton = awaitingSelectButton;
    }

    public JTextField getEmailExistingText() {
        return emailExistingText;
    }

    public void setEmailExistingText(JTextField emailExistingText) {
        this.emailExistingText = emailExistingText;
    }

    public JButton getSearchExistingButton() {
        return searchExistingButton;
    }

    public void setSearchExistingButton(JButton searchExistingButton) {
        this.searchExistingButton = searchExistingButton;
    }

    public JLabel getNameExistingLabel() {
        return nameExistingLabel;
    }

    public void setNameExistingLabel(JLabel nameExistingLabel) {
        this.nameExistingLabel = nameExistingLabel;
    }

    public JLabel getUserTypeExistingLabel() {
        return userTypeExistingLabel;
    }

    public void setUserTypeExistingLabel(JLabel userTypeExistingLabel) {
        this.userTypeExistingLabel = userTypeExistingLabel;
    }

    public JLabel getEmailExistingLabel() {
        return EmailExistingLabel;
    }

    public void setEmailExistingLabel(JLabel emailExistingLabel) {
        EmailExistingLabel = emailExistingLabel;
    }

    public JTextField getCompensateText() {
        return compensateText;
    }

    public void setCompensateText(JTextField compensateText) {
        this.compensateText = compensateText;
    }

    public JButton getCompensateUpdateButton() {
        return compensateUpdateButton;
    }

    public void setCompensateUpdateButton(JButton compensateUpdateButton) {
        this.compensateUpdateButton = compensateUpdateButton;
    }

    public JComboBox getExisingModuleSelectDropdown() {
        return exisingModuleSelectDropdown;
    }

    public void setExisingModuleSelectDropdown(JComboBox exisingModuleSelectDropdown) {
        this.exisingModuleSelectDropdown = exisingModuleSelectDropdown;
    }

    public JButton getExisingModuleShowButton() {
        return exisingModuleShowButton;
    }

    public void setExisingModuleShowButton(JButton exisingModuleShowButton) {
        this.exisingModuleShowButton = exisingModuleShowButton;
    }

    public JTextField getAdjustCourseNameText() {
        return adjustCourseNameText;
    }

    public void setAdjustCourseNameText(JTextField adjustCourseNameText) {
        this.adjustCourseNameText = adjustCourseNameText;
    }

    public JButton getAdjustCourseUpdateButton() {
        return adjustCourseUpdateButton;
    }

    public void setAdjustCourseUpdateButton(JButton adjustCourseUpdateButton) {
        this.adjustCourseUpdateButton = adjustCourseUpdateButton;
    }

    public JLabel getCurrentDecisionLabel() {
        return currentDecisionLabel;
    }

    public void setCurrentDecisionLabel(JLabel currentDecisionLabel) {
        this.currentDecisionLabel = currentDecisionLabel;
    }

    public JComboBox getAdjustCoursedDropdown() {
        return adjustCoursedDropdown;
    }

    public void setAdjustCoursedDropdown(JComboBox adjustCoursedDropdown) {
        this.adjustCoursedDropdown = adjustCoursedDropdown;
    }

    public JButton getAddCourseButton() {
        return addCourseButton;
    }

    public void setAddCourseButton(JButton addCourseButton) {
        this.addCourseButton = addCourseButton;
    }

    public JComboBox getAddModuleSemesterDropdown() {
        return addModuleSemesterDropdown;
    }

    public void setAddModuleSemesterDropdown(JComboBox addModuleSemesterDropdown) {
        this.addModuleSemesterDropdown = addModuleSemesterDropdown;
    }

    public JComboBox getAddCourseQualificationDropdown() {
        return addCourseQualificationDropdown;
    }

    public void setAddCourseQualificationDropdown(JComboBox addCourseQualificationDropdown) {
        this.addCourseQualificationDropdown = addCourseQualificationDropdown;
    }

    public JComboBox getAddCourseDepartmentDropdown() {
        return addCourseDepartmentDropdown;
    }

    public void setAddCourseDepartmentDropdown(JComboBox addCourseDepartmentDropdown) {
        this.addCourseDepartmentDropdown = addCourseDepartmentDropdown;
    }

    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public void setAddModuleButton(JButton addModuleButton) {
        this.addModuleButton = addModuleButton;
    }

    public JTextField getAddCourseCodeText() {
        return addCourseCodeText;
    }

    public void setAddCourseCodeText(JTextField addCourseCodeText) {
        this.addCourseCodeText = addCourseCodeText;
    }

    public JTextField getAddCourseNameText() {
        return addCourseNameText;
    }

    public void setAddCourseNameText(JTextField addCourseNameText) {
        this.addCourseNameText = addCourseNameText;
    }

    public JTextArea getAddCourseDescriptionText() {
        return addCourseDescriptionText;
    }

    public void setAddCourseDescriptionText(JTextArea addCourseDescriptionText) {
        this.addCourseDescriptionText = addCourseDescriptionText;
    }

    public JComboBox getAdjustCourseQualificationDropdown() {
        return adjustCourseQualificationDropdown;
    }

    public void setAdjustCourseQualificationDropdown(JComboBox adjustCourseQualificationDropdown) {
        this.adjustCourseQualificationDropdown = adjustCourseQualificationDropdown;
    }

    public JComboBox getAdjustCourseDepartmentDropdown() {
        return adjustCourseDepartmentDropdown;
    }

    public void setAdjustCourseDepartmentDropdown(JComboBox adjustCourseDepartmentDropdown) {
        this.adjustCourseDepartmentDropdown = adjustCourseDepartmentDropdown;
    }

    public JTextArea getAdjustCourseDescriptionText() {
        return adjustCourseDescriptionText;
    }

    public void setAdjustCourseDescriptionText(JTextArea adjustCourseDescriptionText) {
        this.adjustCourseDescriptionText = adjustCourseDescriptionText;
    }

    public JPanel getModuleTab() {
        return ModuleTab;
    }

    public void setModuleTab(JPanel moduleTab) {
        this.ModuleTab = moduleTab;
    }

    public JPanel getMiscTab() {
        return MiscTab;
    }

    public void setMiscTab(JPanel miscTab) {
        MiscTab = miscTab;
    }

    public JButton getAdjustCourseSelectButton() {
        return adjustCourseSelectButton;
    }

    public void setAdjustCourseSelectButton(JButton adjustCourseSelectButton) {
        this.adjustCourseSelectButton = adjustCourseSelectButton;
    }

    public JLabel getAdjustCourseCodeLabel() {
        return adjustCourseCodeLabel;
    }

    public void setAdjustCourseCodeLabel(JLabel adjustCourseCodeLabel) {
        this.adjustCourseCodeLabel = adjustCourseCodeLabel;
    }

    public JTextField getAddModuleCodeText() {
        return addModuleCodeText;
    }

    public void setAddModuleCodeText(JTextField addModuleCodeText) {
        this.addModuleCodeText = addModuleCodeText;
    }

    public JTextField getAddModuleNameText() {
        return addModuleNameText;
    }

    public void setAddModuleNameText(JTextField addModuleNameText) {
        this.addModuleNameText = addModuleNameText;
    }

    public JTextField getAddModuleCreditText() {
        return addModuleCreditText;
    }

    public void setAddModuleCreditText(JTextField addModuleCreditText) {
        this.addModuleCreditText = addModuleCreditText;
    }

    public JLabel getExistingModuleCodeLabel() {
        return existingModuleCodeLabel;
    }

    public void setExistingModuleCodeLabel(JLabel existingModuleCodeLabel) {
        this.existingModuleCodeLabel = existingModuleCodeLabel;
    }

    public JTextField getExistingModuleNameText() {
        return existingModuleNameText;
    }

    public void setExistingModuleNameText(JTextField existingModuleNameText) {
        this.existingModuleNameText = existingModuleNameText;
    }

    public JTextField getExistingModuleCreditText() {
        return existingModuleCreditText;
    }

    public void setExistingModuleCreditText(JTextField existingModuleCreditText) {
        this.existingModuleCreditText = existingModuleCreditText;
    }

    public JComboBox getExistingModuleSemesterDropdown() {
        return existingModuleSemesterDropdown;
    }

    public void setExistingModuleSemesterDropdown(JComboBox existingModuleSemesterDropdown) {
        this.existingModuleSemesterDropdown = existingModuleSemesterDropdown;
    }

    public JButton getExistingModuleUpdateButton() {
        return existingModuleUpdateButton;
    }

    public void setExistingModuleUpdateButton(JButton existingModuleUpdateButton) {
        this.existingModuleUpdateButton = existingModuleUpdateButton;
    }

    public JComboBox getAddModuleCourseDropdown() {
        return addModuleCourseDropdown;
    }

    public void setAddModuleCourseDropdown(JComboBox addModuleCourseDropdown) {
        this.addModuleCourseDropdown = addModuleCourseDropdown;
    }

    public JComboBox getExistingModuleCourseDropdown() {
        return existingModuleCourseDropdown;
    }

    public void setExistingModuleCourseDropdown(JComboBox existingModuleCourseDropdown) {
        this.existingModuleCourseDropdown = existingModuleCourseDropdown;
    }

    public JLabel getCourseDecisionLabel() {
        return courseDecisionLabel;
    }

    public void setCourseDecisionLabel(JLabel courseDecisionLabel) {
        this.courseDecisionLabel = courseDecisionLabel;
    }

    public JComboBox getMaxAttemptCourseDropdown() {
        return maxAttemptCourseDropdown;
    }

    public void setMaxAttemptCourseDropdown(JComboBox maxAttemptCourseDropdown) {
        this.maxAttemptCourseDropdown = maxAttemptCourseDropdown;
    }

    public JTextField getMaxAttemptCourseNumberText() {
        return maxAttemptCourseNumberText;
    }

    public void setMaxAttemptCourseNumberText(JTextField maxAttemptCourseNumberText) {
        this.maxAttemptCourseNumberText = maxAttemptCourseNumberText;
    }

    public JTable getTableBusinessRule() {
        return tableBusinessRule;
    }

    public void setTableBusinessRule(JTable tableBusinessRule) {
        this.tableBusinessRule = tableBusinessRule;
    }

    public JButton getUpdateAllSelectedModulesButton() {
        return updateAllSelectedModulesButton;
    }

    public void setUpdateAllSelectedModulesButton(JButton updateAllSelectedModulesButton) {
        this.updateAllSelectedModulesButton = updateAllSelectedModulesButton;
    }
    public JButton getMaxAttemptCourseUpdate() {
        return maxAttemptCourseUpdate;
    }

    public void setMaxAttemptCourseUpdate(JButton maxAttemptCourseUpdate) {
        this.maxAttemptCourseUpdate = maxAttemptCourseUpdate;
    }
    public JButton getDeclineButton() {
        return declineButton;
    }

    public void setDeclineButton(JButton declineButton) {
        this.declineButton = declineButton;
    }

    public JLabel getAwaitingEmailLabel() {
        return awaitingEmailLabel;
    }

    public void setAwaitingEmailLabel(JLabel awaitingEmailLabel) {
        this.awaitingEmailLabel = awaitingEmailLabel;
    }

    public JLabel getAwaitingTypeLabel() {
        return awaitingTypeLabel;
    }

    public void setAwaitingTypeLabel(JLabel awaitingTypeLabel) {
        this.awaitingTypeLabel = awaitingTypeLabel;
    }

    public JLabel getAwaitingNameLabel() {
        return awaitingNameLabel;
    }

    public void setAwaitingNameLabel(JLabel awaitingNameLabel) {
        this.awaitingNameLabel = awaitingNameLabel;
    }

    public JLabel getStateExistingLabel() {
        return stateExistingLabel;
    }

    public void setStateExistingLabel(JLabel stateExistingLabel) {
        this.stateExistingLabel = stateExistingLabel;
    }

    public JPanel getEnrolPanel() {
        return enrolStudentPanel;
    }

    public void setEnrolPanel(JPanel enrolPanel) {
        this.enrolStudentPanel = enrolPanel;
    }

    public JTextField getAddModuleAttemptText() {
        return addModuleAttemptText;
    }

    public void setAddModuleAttemptText(JTextField addModuleAttemptText) {
        this.addModuleAttemptText = addModuleAttemptText;
    }

    public JTextArea getAddModuleDescriptionText() {
        return addModuleDescriptionText;
    }

    public void setAddModuleDescriptionText(JTextArea addModuleDescriptionText) {
        this.addModuleDescriptionText = addModuleDescriptionText;
    }

    public JTextField getExistingModuleAttemptsText() {
        return existingModuleAttemptsText;
    }

    public void setExistingModuleAttemptsText(JTextField existingModuleAttemptsText) {
        this.existingModuleAttemptsText = existingModuleAttemptsText;
    }

    public JTextArea getExistingModuleDescriptionText() {
        return existingModuleDescriptionText;
    }

    public void setExistingModuleDescriptionText(JTextArea existingModuleDescriptionText) {
        this.existingModuleDescriptionText = existingModuleDescriptionText;
    }
    public JPanel getStudentTab() {
        return StudentTab;
    }

    public void setStudentTab(JPanel studentTab) {
        StudentTab = studentTab;
    }

    public JPanel getLecturerTab() {
        return LecturerTab;
    }

    public void setLecturerTab(JPanel lecturerTab) {
        LecturerTab = lecturerTab;
    }

    public JPanel getCourseTab() {
        return CourseTab;
    }

    public void setCourseTab(JPanel courseTab) {
        CourseTab = courseTab;
    }

    public JPanel getEnrolStudentPanel() {
        return enrolStudentPanel;
    }

    public void setEnrolStudentPanel(JPanel enrolStudentPanel) {
        this.enrolStudentPanel = enrolStudentPanel;
    }

    public JComboBox getDepartmentEnrolDropdown() {
        return departmentEnrolDropdown;
    }

    public void setDepartmentEnrolDropdown(JComboBox departmentEnrolDropdown) {
        this.departmentEnrolDropdown = departmentEnrolDropdown;
    }

    public JLabel getQualificationEnrolLabel() {
        return qualificationEnrolLabel;
    }

    public void setQualificationEnrolLabel(JLabel qualificationEnrolLabel) {
        this.qualificationEnrolLabel = qualificationEnrolLabel;
    }

    public JPanel getEnrolLecturePanel() {
        return enrolLecturePanel;
    }

    public void setEnrolLecturePanel(JPanel enrolLecturePanel) {
        this.enrolLecturePanel = enrolLecturePanel;
    }
    public JTabbedPane getTabbedPaneMaxAttempts() {
        return tabbedPaneMaxAttempts;
    }

    public void setTabbedPaneMaxAttempts(JTabbedPane tabbedPaneMaxAttempts) {
        this.tabbedPaneMaxAttempts = tabbedPaneMaxAttempts;
    }

    public JLabel getCompensationLabel() {
        return compensationLabel;
    }

    public void setCompensationLabel(JLabel compensationLabel) {
        this.compensationLabel = compensationLabel;
    }
}
