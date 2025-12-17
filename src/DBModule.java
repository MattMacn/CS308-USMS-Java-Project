import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class DBModule {
    private String moduleID;
    private String moduleCode;
    private String moduleName;
    private Semester semester;
    private String moduleDescription;
    private int maxAttempts;
    private int credit;

    //Constructor for reading in data from database
    public DBModule(ResultSet result){
        try{
            this.moduleID = result.getString("moduleID");
            this.moduleCode= result.getString("module_code");
            this.moduleName = result.getString("module_name");
            this.semester = Semester.fromString(result.getString("semester"));
            this.moduleDescription = result.getString("module_description");
            this.maxAttempts = result.getInt("max_attempts");
            this.credit = result.getInt("credit");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public DBModule( String modCode, String moduleName, Semester semester, String modDesc, int maxAtt, int cred ){
        this.moduleID = "mod-" + UUID.randomUUID();
        this.moduleCode = modCode;
        this.moduleName = moduleName;
        this.semester = semester;
        this.moduleDescription = modDesc;
        this.maxAttempts = maxAtt;
        this.credit = cred;
    }

    public String getModuleID(){
        return this.moduleID;
    }
    public String getModuleName(){
        return this.moduleName;
    }
    public String getModuleCode(){
        return this.moduleCode;
    }
    public Semester getSemester(){
        return this.semester;
    }
    public String getModuleDescription(){
        return this.moduleDescription;
    }
    public int getMaxAttempts(){return this.maxAttempts;}
    public int getCredit(){return this.credit;}


    public void setModuleCode(String modCode){
        //Space to add input validation
        this.moduleCode = modCode;
    }

    public void setModuleName(String modName){
        //Space to add input validation
        this.moduleName = modName;
    }
    public void setModuleSemester(Semester semester){
        //Space to add input validation
        this.semester = semester;
    }
    public void setModuleDescription(String modDesc){
        //Space to add input validation
        this.moduleDescription = modDesc;
    }
    public void setMaxAttempts(int maxAttempts){
        this.maxAttempts = maxAttempts;
    }
    public void setCredit(int cred){
        this.credit = cred;
    }

    public void printModule(){
        System.out.println("Module ID: " + this.moduleID);
        System.out.println("Module Code: " + this.moduleCode);
        System.out.println("Module Name: " + this.moduleName);
        System.out.println("Semester: " + Semester.asString(this.semester));
        System.out.println("Module Description: " + this.moduleDescription);
        System.out.println("Maximum Attempts: " + this.maxAttempts);
        System.out.println("Module Credit: " + this.credit);
    }

}


