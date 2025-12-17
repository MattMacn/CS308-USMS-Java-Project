import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewLogin extends JPanel {
    private JPasswordField passwordField1;
    private JTextField emailTxt;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JButton returnButton;
    private JPanel panelMain;


    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public void setPasswordField1(JPasswordField passwordField1) {
        this.passwordField1 = passwordField1;
    }

    public JTextField getEmailTxt() {
        return emailTxt;
    }

    public void setEmailTxt(JTextField emailTxt) {
        this.emailTxt = emailTxt;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public void setForgotPasswordButton(JButton forgotPasswordButton) {
        this.forgotPasswordButton = forgotPasswordButton;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(JButton returnButton) {
        this.returnButton = returnButton;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public ViewLogin() {
        //private Controller controller??

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

    }
}
