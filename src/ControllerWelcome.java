import javax.swing.*;

public class ControllerWelcome {

    private ViewWelcome vWelcome;
    private static PageHandler handler;

    public ControllerWelcome(ViewWelcome vWelcome, PageHandler handler) {
        this.vWelcome = vWelcome;
        this.handler = handler;

        this.vWelcome.getLoginButton().addActionListener(e -> handleLogin());
        this.vWelcome.getSignupButton().addActionListener(e -> handleSignup());
    }


    public void handleLogin() {
        System.out.println("going to login");
        handler.switchView(View.LOGIN, null);
    }

    public void handleSignup() {
        System.out.println("going to signup");
        handler.switchView(View.SIGNUP, null);
    }

//    public static void main(String[] args) {
//
//        JFrame mainFrame = new JFrame("USMS - User Signup Page");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        ViewWelcome ViewWelcome = new ViewWelcome();
//        ControllerWelcome WelcomeController = new ControllerWelcome(ViewWelcome, handler);
//
//        mainFrame.add(ViewWelcome.getPanelMain());
//
//        mainFrame.pack();
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setVisible(true);
//    }
}
