import javax.swing.*;

public class App implements PageHandler{

    private JFrame mainFrame;
    private static PageHandler handler;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { new App().start();});
    }

    public void start(){
        mainFrame = new JFrame("USMS - Title Error?!?");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        loadWelcome();
        mainFrame.setVisible(true);
    }
    @Override
    public void switchView(View view, DBUser user) {
        switch (view) {
            case WELCOME: loadWelcome(); break;
            case LOGIN: loadLogin(); break;
            case SIGNUP: loadSignup(); break;
            case STUDENT: loadStudent(user); break;
            case LECTURER: loadLecturer(user); break;
            case MANAGER: loadManager(user); break;
        }
    }

    private void loadWelcome(){
        ViewWelcome vWelcome = new ViewWelcome();
        new ControllerWelcome(vWelcome, this);
        mainFrame.setTitle(View.WELCOME.getWindowTitle());
        mainFrame.setContentPane(vWelcome.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }

    private void loadLogin(){
        ViewLogin vLogin = new ViewLogin();
        new ControllerLogin(vLogin, this);
        mainFrame.setTitle(View.LOGIN.getWindowTitle());
        mainFrame.setContentPane(vLogin.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }

    private void loadSignup(){
        ViewSignup vSignup = new ViewSignup();
        new ControllerSignup(vSignup, this);
        mainFrame.setTitle(View.SIGNUP.getWindowTitle());
        mainFrame.setContentPane(vSignup.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }

    private void loadStudent(DBUser user){
        ViewStudentHomepage vStudent = new ViewStudentHomepage();
        new ControllerStudent(vStudent, this, user);
        mainFrame.setTitle(View.STUDENT.getWindowTitle());
        mainFrame.setContentPane(vStudent.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }

    private void loadLecturer(DBUser user){
        ViewLecturerHomepage vLecturer = new ViewLecturerHomepage();
        new ControllerLecturer(vLecturer, this, user);
        mainFrame.setTitle(View.LECTURER.getWindowTitle());
        mainFrame.setContentPane(vLecturer.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }

    private void loadManager(DBUser user){
        ViewManagerHomepage vManager = new ViewManagerHomepage();
        new ControllerManager(vManager,this, user);
        mainFrame.setTitle(View.MANAGER.getWindowTitle());
        mainFrame.setContentPane(vManager.getPanelMain());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.repaint();
    }
}
