import java.util.ArrayList;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBStudent {

    private String studentID;
    private DBUser user;
    private DBCourse course;

    // Constructor for reading from database
    public DBStudent(ResultSet result){
        DAOClass dao = new DAOClass();
        try {
           this.studentID = result.getString("studentID");
           this.user = dao.readUserFromUserID(result.getString("userID"));
           this.course = dao.readCourseFromCourseCode(result.getString("courseCode"));
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }
    //Constructor used to pass to DAOClass create student method
    public DBStudent(DBUser user, DBCourse course){
        this.studentID = "std-" + UUID.randomUUID();
        this.user = user;
        this.course = course;
    }

    public String getStudentID(){
        return this.studentID;
    }

    public DBUser getUser() { return this.user;}

    public DBCourse getCourse(){
        return this.course;
    }

    public void setCourse(DBCourse course){
        this.course = course;
    }

    public void printStudent(){
        System.out.println("StudentID: " + this.studentID);
        System.out.println("userID: " + this.user.getUserID());
        System.out.println("courseCode: " + this.course.getCourseCode());
    }

}
