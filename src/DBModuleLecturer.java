import java.sql.ResultSet;
import java.sql.SQLException;

public class DBModuleLecturer {

    private String moduleID;
    private String lecturerID;


    // For Creating the relationship
    public DBModuleLecturer(String modID, String lecturerID){
        setModuleID(modID);
        setLecturerID(lecturerID);
    }

    public DBModuleLecturer(ResultSet result){
        try {
            setModuleID(result.getString("moduleID"));
            setLecturerID(result.getString("lecturerID"));
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setLecturerID(String lecID) {
        this.lecturerID = lecID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }
}

