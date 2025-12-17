import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBResult {

    private String resultID;
    // private String studentID NOT needed as we store results in the student object
    private String moduleID;
    private ModuleResult resultType;
    private double mark;
    private int attemptNumber;

    // For Creating the entry in the relationship table
    public DBResult(String moduleID, ModuleResult resultType, double mark, int attemptNumber){
        this.resultID = "res-" + UUID.randomUUID();
        this.moduleID = moduleID;
        this.resultType = resultType;
        this.mark = mark;
        this.attemptNumber = attemptNumber;
    }

    //For reading from the database
    public DBResult(ResultSet result){
        try {
            this.resultID = result.getString("resultID");
            this.moduleID = result.getString("moduleID");
            this.resultType = ModuleResult.fromString(result.getString("result_type"));
            this.mark = result.getDouble("mark");
            this.attemptNumber = result.getInt("attempt_number");
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
    }

    public String getResultID() {return this.resultID;}

    public String getModuleID() {
        return this.moduleID;
    }

    public ModuleResult getResultType() { return this.resultType; }

    public Double getMark() {
        return this.mark;
    }

    public int getAttemptNumber() { return this.attemptNumber; }



    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public void setResultType(ModuleResult result){
        this.resultType = result;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setAttemptNumber(int attemptNumber) { this.attemptNumber = attemptNumber; }

    public void printResult() {
        System.out.println("resultID: " + resultID);
        System.out.println("moduleID: " + moduleID);
        System.out.println("Result type: " + ModuleResult.asString(resultType));
        System.out.println("Mark: " + String.format("%.2f", mark));
        System.out.println("Attempt number: " + attemptNumber);
    }

}
