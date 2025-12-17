import javax.swing.*;

public abstract class HomepageController {

    protected HomepageView homepageView;
    protected DAOClass dao;
    protected PageHandler handler;
    protected DBUser currentUser;

    public HomepageController(HomepageView view, DAOClass dao, PageHandler handler, DBUser user){
        this.homepageView = view;
        this.dao = dao;
        this.handler = handler;
        this.currentUser = user;

        setupCommonListeners();
    }

    private void setupCommonListeners(){
        homepageView.getSignOutButton().addActionListener(e -> handleSignOut());
        homepageView.getChangePasswordButton().addActionListener(e -> handleUserPasswordChange());
    }

    protected void handleSignOut(){
        System.out.println("Sign Out button pressed");
        int msg = showMessage("Are you sure you want to sign out?","Sign Out",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (handler != null && msg==JOptionPane.YES_OPTION) {
            handler.switchView(View.WELCOME, null);
        }
    }

    protected void handleUserPasswordChange() {
        System.out.println("Change Password button pressed");
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(homepageView.getPanelMain());
        ViewChangePassword passwordDialog = new ViewChangePassword(parentFrame, currentUser.getEmail(), dao);
        passwordDialog.setVisible(true);
    }

    void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(homepageView.getPanelMain(), message, title, messageType);
    }

    int showMessage(String message, String title, int messageType, int messageImage) {
        return JOptionPane.showConfirmDialog(homepageView.getPanelMain(), message, title, messageType, messageImage);
    }

    // Checks if the email format is valid (must contain '@' and '.' in correct positions with surrounding characters)
    boolean validEmail(String email) {
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

    int parsePositiveInt(String numString, String msg){
        try {
            int numInt = Integer.parseInt(numString);
            if (numInt > 0) return numInt;
        } catch (NumberFormatException e) {}
        showMessage(
                "%s must be valid positive numbers.".formatted(msg),
                "Input Error", JOptionPane.WARNING_MESSAGE);
        return -1;
    }

}
