import java.sql.ResultSet;
import java.sql.SQLException;

public class DBModuleMaterial {

    private String moduleID;
    private String materialID;
    private int wkNumber;

    public DBModuleMaterial(String modID, String matID, int wkNum){
        setModuleID(modID);
        setMaterialID(matID);
        setWeekNumber(wkNum);
    }

    public DBModuleMaterial(ResultSet result){
       try {
           setModuleID(result.getString("moduleID"));
           setMaterialID(result.getString("materialID"));
           setWeekNumber(result.getInt("wkNumber"));
       }
       catch (SQLException e){
           e.printStackTrace();
       }
    }

    public String getModuleID() {
        return this.moduleID;
    }

    public String getMaterialID() {
        return this.materialID;
    }

    public int getWkNumber() {
        return wkNumber;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public void setWeekNumber(int wkNumber) {
            this.wkNumber = wkNumber;
    }
}

