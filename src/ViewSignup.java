import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSignup extends JPanel{

    private JPanel panelMain;
    private JPanel InfoPanel;
    private JPanel PersonPanel;
    private JPanel DetailsPanel;
    private JPanel RoleSelect;
    private JTextField forenameTxt;
    private JTextField surnameTxt;
    private JComboBox genderBox;
    private JTextField dobTxt;
    private JTextField emailTxt;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JRadioButton studentRadioButton;
    private JRadioButton lecturerRadioButton;
    private JPanel OptionPanel;
    private JButton returnButton;
    private JButton signUpButton;
    private JComboBox QualificationBox1;
    private JLabel qualificationLabel;



    //setters


    public void setForenameTxt(JTextField forenameTxt) {
        this.forenameTxt = forenameTxt;
    }

    public void setSurnameTxtTextField(JTextField surnameTxtTextField) {
        this.surnameTxt = surnameTxtTextField;
    }

    public void setGenderBox(JComboBox genderBox) {
        this.genderBox = genderBox;
    }

    public void setDobTxt(JTextField dobTxt) {
        this.dobTxt = dobTxt;
    }

    public void setEmailTxt(JTextField emailTxt) {
        this.emailTxt = emailTxt;
    }

    public void setPasswordField1(JPasswordField passwordField1) {
        this.passwordField1 = passwordField1;
    }

    public void setPasswordField2(JPasswordField passwordField2) {
        this.passwordField2 = passwordField2;
    }

    public void setQualificationBox1(JComboBox qualificationBox1) {
        QualificationBox1 = qualificationBox1;
    }

    public void setRoleSelect(JPanel roleSelect) {
        RoleSelect = roleSelect;
    }



    //getters
    public JTextField getForenameTxt() {return forenameTxt;}

    public JTextField getSurnameTxtTextField() {return surnameTxt;}

    public JComboBox getGenderBox() {return genderBox;}

    public JTextField getDobTxt() {return dobTxt;}

    public JTextField getEmailTxt() {return emailTxt;}

    public JPasswordField getPasswordField1() {return passwordField1;}

    public JPasswordField getPasswordField2() {return passwordField2;}

    public JPanel getRoleSelect() {return RoleSelect;}

    public JComboBox getQualificationBox1() {return QualificationBox1;}

    public JPanel getPanelMain() {return panelMain;}

    public JRadioButton getLecturerRadioButton(){return lecturerRadioButton;}

    public JRadioButton getStudentRadioButton(){return studentRadioButton;}


    //buttons

    public JButton getReturnButton() {return returnButton;}

    public JButton getSignUpButton() {return signUpButton;}


    public ViewSignup() {

        qualificationLabel.setVisible(false);
        QualificationBox1.setVisible(false);

        lecturerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qualificationLabel.setVisible(true);
                QualificationBox1.setVisible(true);
            }
        });
        studentRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qualificationLabel.setVisible(false);
                QualificationBox1.setVisible(false);
            }
        });
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTextField getSurnameTxt() {
        return surnameTxt;
    }

    public void setSurnameTxt(JTextField surnameTxt) {
        this.surnameTxt = surnameTxt;
    }

    public void setStudentRadioButton(JRadioButton studentRadioButton) {
        this.studentRadioButton = studentRadioButton;
    }

    public void setLecturerRadioButton(JRadioButton lecturerRadioButton) {
        this.lecturerRadioButton = lecturerRadioButton;
    }

    public JPanel getOptionPanel() {
        return OptionPanel;
    }

    public void setOptionPanel(JPanel optionPanel) {
        OptionPanel = optionPanel;
    }

    public void setReturnButton(JButton returnButton) {
        this.returnButton = returnButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    public JLabel getQualificationLabel() {
        return qualificationLabel;
    }

    public void setQualificationLabel(JLabel qualificationLabel) {
        this.qualificationLabel = qualificationLabel;
    }
}