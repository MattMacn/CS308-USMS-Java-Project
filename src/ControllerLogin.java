import javax.swing.*;

public class ControllerLogin {

    private ViewLogin vLogin;
    private DAOClass dao;
    private PageHandler handler;

    public ControllerLogin(ViewLogin vLogin, PageHandler handler){
        this(vLogin, new DAOClass(), handler);
    }

    public ControllerLogin(ViewLogin vLogin, DAOClass dao, PageHandler handler){
        this.vLogin = vLogin;
        this.vLogin = vLogin;
        this.dao = dao;
        this.handler = handler;

        this.vLogin.getLoginButton().addActionListener(e -> handleLogin());
        this.vLogin.getReturnButton().addActionListener(e -> handleReturn());
        this.vLogin.getForgotPasswordButton().addActionListener(e -> handlePasswordForgotten());
    }



    public void handleLogin() {
        System.out.println("Login button pressed");

        String email = vLogin.getEmailTxt().getText().trim();
        String password = String.valueOf(vLogin.getPasswordField1().getPassword());

        if (!validEmail(email)){
            inputErrorLoginMsg(email);
            return;
        }

        DBUser user = dao.readUserFromEmail(email);
        if (user == null){
            inputErrorLoginMsg(email);
            return;
        }

        if (!user.getPassword().equals(password)){
            inputErrorLoginMsg(email);
            return;
        }

        if(!user.getIsActivated()){
            showMessage(
                    ("This account has been deactivated, please contact a manager for support.").formatted(email),
                    "Account Deactivated",
                    JOptionPane.ERROR_MESSAGE);
            handler.switchView(View.WELCOME, null);
            return;
        }

        if(user.getIsManager()){
            handler.switchView(View.MANAGER, user);
        } else if (dao.readStudentFromEmail(email)!=null){
            handler.switchView(View.STUDENT, user);
        } else if (dao.readLecturerFromEmail(email)!=null){
            handler.switchView(View.LECTURER, user);
        }
    }

    private void inputErrorLoginMsg(String email){
        showMessage(
                "Incorrect data entered for %s.".formatted(email),
                "Log In Failed",
                JOptionPane.ERROR_MESSAGE);
    }

    public void handleReturn(){
        System.out.println("Return button pressed");
        handler.switchView(View.WELCOME, null);
    }

    public void handlePasswordForgotten(){
        showMessage(
                "Please contact a manager to reset password.",
                "Error: Unlucky", JOptionPane.INFORMATION_MESSAGE);
        handler.switchView(View.WELCOME, null);
    }

    public boolean validEmail(String email) {
        if (email == null) return false;
        if (email.length() < 5) return false;
        int atPos = email.indexOf('@');
        int dotPos = email.lastIndexOf('.');
        if (atPos == -1 || dotPos == -1) return false;
        if (atPos < 1) return false;
        if (dotPos <= atPos) return false;
        if (dotPos <= atPos + 1) return false;
        if (dotPos == email.length() - 1) return false;
        return true;
    }

    void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(vLogin.getPanelMain(), message, title, messageType);
    }
}
