import javax.swing.*;


public class SignUpController {

    private ViewSignup vSignUp;
    private DAOClass dao;
    public boolean lecturer = false;

    public SignUpController(ViewSignup vSignup) {
        this.vSignUp = vSignup;
        this.dao = new DAOClass();
        vSignUp.getPanelMain().setVisible(true);

        this.vSignUp.getSignUpButton().addActionListener(e -> validateInput());
        this.vSignUp.getReturnButton().addActionListener(e -> goHome());
        this.vSignUp.getLecturerRadioButton().addActionListener(e -> isLecturer());
    }

    public void validateInput(){
        if(checkEmail()&&checkPasswords()){
            System.out.println("Credentials good...creating account");


            String firstName = vSignUp.getForenameTxt().getText();
            String lastName = vSignUp.getSurnameTxtTextField().getText();
            String email = vSignUp.getEmailTxt().getText();
            String password = vSignUp.getPasswordField1().getText();
            String gender = vSignUp.getGenderBox().getSelectedItem().toString();
            String dob = vSignUp.getDobTxt().getText();
            boolean isManager = false;
            boolean isActivated = false;

            if(lecturer){
                String qualification = vSignUp.getQualificationBox1().getSelectedItem().toString();
                //pass info to lecturer DB
                System.out.println("ur a lecturer. this is your qualification " + qualification);
            }

            DBUser newUser = new DBUser(firstName, lastName, password, gender, email, dob, isManager, isActivated);
            dao.createUser(newUser);



            //go to student home page
        }
    }



    public void goHome(){
        //go to welcome page

    }

    public void isLecturer(){
        lecturer = true;
    }

    public boolean checkEmail(){                                //need to check if user already exixsts
        String studentEmail = vSignUp.getEmailTxt().getText();
        if(studentEmail.contains("@")){
            System.out.println("valid-ish email");
            return true;
        }
        else{
            System.out.println("pls use valid email");
            return false;
        }
    }

    public boolean checkPasswords(){
        char[] password1Chars = vSignUp.getPasswordField1().getPassword();
        String pass1 = new String(password1Chars);
        char[] password2Chars = vSignUp.getPasswordField2().getPassword();
        String pass2 = new String(password2Chars);


        if(pass1.length() > 8 && (pass1.contains("!") ||  pass1.contains("?") || pass1.contains("$"))){
            if (pass1.equals(pass2)){
                System.out.println("Valid password & they match");
                return true;
            }
            else{
                System.out.println("Valid password but re-entered passowrd does not match");
                return false;
            }
        }
        else {
            System.out.println("Invalid password");
            return false;
        }
    }



    public static void main(String[] args) {

        JFrame  mainFrame=new JFrame("USMS - User Signup Page");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ViewSignup viewSignup = new ViewSignup();
        SignUpController signUpManager = new SignUpController(viewSignup);

        mainFrame.add(viewSignup.getPanelMain());

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}

