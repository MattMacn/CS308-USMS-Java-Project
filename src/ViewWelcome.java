import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ViewWelcome {
    private JButton loginButton;
    private JButton signupButton;
    private JPanel panelMain;

    public JPanel getPanelMain(){return panelMain;}

    public JPanel setPanelMain(){return panelMain;}

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public void setSignupButton(JButton signupButton) {
        this.signupButton = signupButton;
    }


    public ViewWelcome() {


    }
}













        /*class Controller {
            private static final String LOG_FILE = "user_actions.txt";

            public void logAction(String action) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                    writer.write(action + " at " + java.time.LocalDateTime.now());
                    writer.newLine();
                    System.out.println(action);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error writing to log file: " + e.getMessage(),
                            "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}     not how ive ever written files               */