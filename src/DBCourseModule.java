import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCourseModule {

    private String courseCode;
    private String moduleID;

    // For Creating the relationship
    public DBCourseModule(String modID, String courseCode){
        setModuleID(modID);
        setCourseCode(courseCode);
    }

    public DBCourseModule(ResultSet result){
        try {
            setCourseCode(result.getString("courseCode"));
            setModuleID(result.getString("moduleID"));
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setCourseCode(String courseID) {
        this.courseCode = courseCode;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }
}

