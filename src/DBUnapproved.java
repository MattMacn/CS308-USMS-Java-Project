import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUnapproved {
    private String firstName;
    private String surname;
    private String password;
    private String gender;
    private String email;
    private String dob;
    private String qualification;

    public DBUnapproved(String firstName, String surname, String password, String gender,  String email,  String dob, String qualification) {
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.qualification = qualification;
    }

    public DBUnapproved(String firstName, String surname, String password, String gender,  String email,  String dob) {
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.qualification = null;
    }

    public DBUnapproved(ResultSet result){
        try {
            this.firstName = result.getString("firstname");
            this.surname = result.getString("surname");
            this.password = result.getString("pword");
            this.gender = result.getString("gender");
            this.email = result.getString("email");
            this.dob = result.getString("DOB");
            this.qualification = result.getString("qualification");
        } catch (SQLException err) {
            err.printStackTrace();
        }
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

    public String getQualification() {
        return this.qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

