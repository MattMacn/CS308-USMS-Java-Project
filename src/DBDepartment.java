import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
public class DBDepartment {

    private String departmentCode;
    private String departmentName;

    //Constructor for reading from database
    public DBDepartment(ResultSet result){
        try{
            this.departmentCode = result.getString("department_code");
            setDepartmentName(result.getString("department_name"));
        }
        catch(SQLException e ){
            e.printStackTrace();
        }

    }

    public DBDepartment(String departmentCode, String departmentName){
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public String getDepartmentCode(){
        return departmentCode;
    }

    public String getDepartmentName(){
        return departmentName;
    }

    public void setDepartmentName(String deptName){
        this.departmentName = deptName;
    }
}

