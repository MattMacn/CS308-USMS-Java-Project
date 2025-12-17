import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class DBUser {
    private String userID;
    private String firstName;
    private String surname;
    private String password;
    private String gender;
    private String email;
    private String dob;
    private String managerID;
    private boolean isManager;
    private boolean isActivated;

    public DBUser(String firstName, String surname, String password, String gender,  String email,  String dob, boolean isManager, boolean isActivated) {
        this.userID = "usr-" + UUID.randomUUID();
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.managerID = null;
        this.isManager = isManager;
        this.isActivated = isActivated;
    }

    public DBUser(DBUnapproved unapproved) {
        this.userID = "usr-" + UUID.randomUUID();
        this.firstName = unapproved.getFirstName();
        this.surname = unapproved.getSurname();
        this.password = unapproved.getPassword();
        this.gender = unapproved.getGender();
        this.email = unapproved.getEmail();
        this.dob = unapproved.getDOB();
        this.managerID = null;
        this.isManager = false;
        this.isActivated = true;
    }

    public DBUser(ResultSet result){
        try {
            this.userID = result.getString("userID");
            this.firstName = result.getString("firstname");
            this.surname = result.getString("surname");
            this.password = result.getString("pword");
            this.gender = result.getString("gender");
            this.email = result.getString("email");
            this.dob = result.getString("DOB");
            this.managerID = result.getString("managerID");
            this.isManager = result.getBoolean("is_manager");
            this.isActivated = result.getBoolean("is_activated");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public String getUserID(){
        return this.userID;
    }

    public String getFirstName(){
        return this.firstName;

    }

    public String getSurname(){
        return this.surname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail(){
        return this.email;

    }

    public String getGender() {
        return this.gender;

    }

    public String getDOB(){
        return this.dob;
    }

    public String getManagerID() {
        return this.managerID;
    }

    public boolean getIsManager(){return this.isManager;}

    public boolean getIsActivated(){return this.isActivated;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public void printUser(){
        System.out.println("userID: " + getUserID());
        System.out.println("Firstname: " + getFirstName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Password: " + getPassword());
        System.out.println("Gender: " + getGender());
        System.out.println("Email: " + getEmail());
        System.out.println("DOB: " + getDOB());
        System.out.println("managerID: " + getManagerID());
    }


}


