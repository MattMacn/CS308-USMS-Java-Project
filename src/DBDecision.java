import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DBDecision {

    private String studentID;
    private String courseYear;
    private Award award;

    public DBDecision(String stuID, String year, Award award) {
        this.studentID = stuID;
        this.courseYear = year;
        this.award = award;
    }

    public DBDecision(ResultSet result){
        try{
            this.studentID = result.getString("studentID");
            this.courseYear = result.getString("course_year");
            this.award = Award.fromString(result.getString("award"));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getStudentID(){
        return this.studentID;
    }

    public String getYear(){
        return this.courseYear;
    }

    public Award getAward(){
        return this.award;
    }

    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    public void setYear(String year){
        this.courseYear = year;
    }

    public void setAward(Award award) {
        this.award = award;
    }
}

