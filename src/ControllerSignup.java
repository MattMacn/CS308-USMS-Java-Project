import javax.swing.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class ControllerSignup {

    private ViewSignup vSignup;
    private DAOClass dao;
    private PageHandler handler;

    public ControllerSignup(ViewSignup vSignup, PageHandler handler){
        this(vSignup, new DAOClass(), handler);
    }

    public ControllerSignup(ViewSignup vSignup, DAOClass dao, PageHandler handler){
        this.vSignup = vSignup;
        this.dao = dao;
        this.handler = handler;
        setupListeners();
    }

    void setupListeners(){
        vSignup.getSignUpButton().addActionListener( e -> handleSignUp());
        vSignup.getReturnButton().addActionListener( e -> handleReturn());
        vSignup.getLecturerRadioButton().addActionListener(e -> handleShowQualifications());
        vSignup.getStudentRadioButton().addActionListener(e -> handleHideQualifications());
    }

    void handleSignUp() {
        System.out.println("Sign Up button pressed");

        String msgErrors = "";

        //get input and validate it
        //  First Name
        String name = vSignup.getForenameTxt().getText().trim();
        if (name.length() < 3 || name.length() > 25){
            msgErrors = msgErrors + "\n   - Forename must be between 3 and 25 characters long";
        }

        // Surname
        String surname = vSignup.getSurnameTxt().getText().trim();
        if (surname.length() < 3 || surname.length() > 25){
            msgErrors = msgErrors + "\n   - Surname must be between 3 and 25 characters long";
        }

        // Gender
        String gender = (String) vSignup.getGenderBox().getSelectedItem();
        if (gender == null || gender.isEmpty()){
            msgErrors = msgErrors + "\n   - A gender option must be selected from the dropdown";
        }

        // DOB
        String dob = vSignup.getDobTxt().getText().trim();
        try {
            LocalDate birthDate = LocalDate.parse(dob);
            if (birthDate.isAfter(LocalDate.now())) {
                msgErrors = msgErrors + "\n   - Date of Birth cannot be in the future";
            }
        } catch (DateTimeParseException e) {
            msgErrors = msgErrors + "\n   - Date of Birth must be in format YYYY-MM-DD";
        }

        // Email
        String email = vSignup.getEmailTxt().getText().trim();
        if (!validEmail(email)){
            msgErrors = msgErrors + "\n   - Email must be a valid email";
        } else {
            if (dao.readUserFromEmail(email)!=null){
                msgErrors = msgErrors + "\n   - Email already registered in the system";
            }
            if (dao.readUnapprovedFromEmail(email)!=null){
                msgErrors = msgErrors + "\n   - Application with %s already registered in the system".formatted(email);
            }
        }

        // Password and rePassword
        String pass1 = String.valueOf((vSignup.getPasswordField1().getPassword()));
        String pass2 = String.valueOf((vSignup.getPasswordField2().getPassword()));

        if (pass1.length() < 8 || pass1.length() > 32) {
            msgErrors = msgErrors + "\n   - Passwords must be between 8 and 32 characters";
        }

        if (!pass1.equals(pass2)) {
            msgErrors = msgErrors + "\n   - Passwords must match";
        }

        // Student || Lecturer
        String qualification = null;
        if (vSignup.getLecturerRadioButton().isSelected()){
            qualification = (String) vSignup.getQualificationBox1().getSelectedItem();

            if(qualification.isEmpty() || qualification.equals("Please Select Qualification")){
                msgErrors = msgErrors + "\n   - Lecturers need to select a qualification";
            }

        }

        // deal with entered data
        if(!msgErrors.equals("")){
            showMessage("The following errors need to be resolved before the application can proceed:" + msgErrors,
                    "Input Validation", JOptionPane.WARNING_MESSAGE);
        } else {
            DBUnapproved user = new DBUnapproved(name,surname,pass1,gender,email,dob, qualification);
            if (dao.createUnapprovedUser(user)){
                showMessage("Your application has been submitted and once a manager approves it you will be able to login in.","Application Successful", JOptionPane.INFORMATION_MESSAGE);
                handler.switchView(View.WELCOME, null);
            } else {
                showMessage("The application failed and was not added to the system.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void handleReturn() {
        System.out.println("Return button pressed");
        handler.switchView(View.WELCOME, null);
    }

    void handleShowQualifications(){
        vSignup.getQualificationLabel().setVisible(true);
        vSignup.getQualificationBox1().setVisible(true);
    }

    void handleHideQualifications(){
        vSignup.getQualificationLabel().setVisible(false);
        vSignup.getQualificationBox1().setVisible(false);
    }

    void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(vSignup.getPanelMain(), message, title, messageType);
    }

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

}
