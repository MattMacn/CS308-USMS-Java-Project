import javax.swing.*;
import java.awt.*;


public class ViewChangePassword extends JDialog {
    private JPanel panelMain;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton changePasswordButton;
    private JButton cancelButton;
    private JLabel userEmailLabel;

    private DAOClass dao;

    public ViewChangePassword(Frame parent, String email, DAOClass dao) {
        super(parent, "Change Password", true);
        this.dao = dao;
        userEmailLabel.setText(email);

        this.setContentPane(panelMain);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        changePasswordButton.addActionListener(e -> handleChangePassword());
        cancelButton.addActionListener(e -> handleCancel());
    }

    private void handleChangePassword() {
        String pass1 = String.valueOf((passwordField1.getPassword()));
        String pass2 = String.valueOf((passwordField2.getPassword()));

        String email = getUserEmailLabel().getText();
        DBUser user = dao.readUserFromEmail(email);
        if (user==null){
            showMessage("%s was not found in database".formatted(email),
                    "Invalid Email",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (pass1.length() < 8 || pass1.length() > 32) {
            JOptionPane.showMessageDialog(this,
                    "Error: Passwords must be between 8 and 32 characters.",
                    "Password Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pass1.equals(pass2)) {
            showMessage(
                    "Error: Passwords do not match.",
                    "Password Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user.setPassword(pass1);

        if (dao.updateUserPassword(user)){
            showMessage("%s password updated.".formatted(email),
                    "Password Updated Successfully",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessage("Database error occurred. Password for %s was not updated".formatted(email),
                    "Password Updated Failed",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }

    private void handleCancel() {
        dispose();
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public JLabel getUserEmailLabel() {
        return userEmailLabel;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

}
