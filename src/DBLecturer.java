import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBLecturer {

    private String lecturerID;
    private DBUser user;
    private String qualification;
    private String departmentCode;

    // Constructor for reading from database
    public DBLecturer(ResultSet result){
        DAOClass dao = new DAOClass();
        try {
            this.lecturerID = result.getString("lecturerID");
            this.user = dao.readUserFromUserID(result.getString("userID"));
            this.qualification = result.getString("qualification");
            this.departmentCode = result.getString("department_code");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    //Constructor used to pass to DAOClass create lecturer method
    public DBLecturer(DBUser user, String qualification, String departmentCode){
        this.lecturerID = "lec-" + UUID.randomUUID();
        this.user = user;
        this.qualification = qualification;
        this.departmentCode = String.valueOf(departmentCode);
    }

    public String getlecturerID(){
        return this.lecturerID;
    }

    public DBUser getUser(){
        return this.user;
    }
    public String getQualification(){
        return this.qualification;
    }
    public String getDepartmentCode(){
        return this.departmentCode;
    }

    public void setUser(DBUser user){
        this.user = user;
    }
    public void setQualification(String qualification){
        this.qualification = qualification;
}
    public void setDepartmentCode(String deptCode){
        this.departmentCode = deptCode;
    }



    public void printLecturer(){
        System.out.println("lecturerID: " + this.lecturerID);
        System.out.println("userID: " + this.user.getUserID());
        System.out.println("qualification: " + this.qualification);
        System.out.println("department Code: " + this.departmentCode);
    }

}

