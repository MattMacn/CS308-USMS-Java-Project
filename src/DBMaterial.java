import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class DBMaterial {
    private String materialID;
    private File lectureNoteFile;
    private File labNoteFile;

    String home = System.getProperty("user.home");
    File theDir = new File(home + "/Downloads/");

    //Constructor for reading in data from database
    public DBMaterial(ResultSet result){
        try{
            this.materialID = result.getString("materialID");

            byte[] lectureNote = result.getBytes("lectureNote");
            byte[] labNote = result.getBytes("labNote");

            if (lectureNote != null){
                lectureNoteFile = bytesToTempFile(lectureNote, "lectureNote", ".txt");
            }

            if (labNote != null){
                labNoteFile = bytesToTempFile(labNote, "labNote", ".txt");
            }
        }
        catch(SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public DBMaterial( File lecNote, File labNote){
        this.materialID = "mat" + UUID.randomUUID();
        setLectureNote(lecNote);
        setLabNote(labNote);
    }

    //Function to convert byte[] into File
    public File bytesToTempFile(byte[] input, String baseName, String extension) throws IOException {
        File temp = new File (theDir, baseName + "_" + UUID.randomUUID() + extension);
        try (FileOutputStream fos = new FileOutputStream(temp)) {
            fos.write(input);
        }
        temp.deleteOnExit(); // optional
        return temp;
    }


    public String getMaterialID(){
        return this.materialID;
    }
    public File getLectureNote(){
        return this.lectureNoteFile;
    }
    public File getLabNote(){
        return this.labNoteFile;
    }


    public void setLectureNote(File lecNote){
        //Space to add input validation
        this.lectureNoteFile = lecNote;
    }
    public void setLabNote(File labNote){
        //Space to add input validation
        this.labNoteFile = labNote;
    }

    public void printMaterial(){
        System.out.println("Material ID: " + this.materialID);
        System.out.println("Lecture Note: " + this.lectureNoteFile);
        System.out.println("labNote: " + this.labNoteFile);
    }
    public DBMaterial(String existingID, File lecNote, File labNote) {
        this.materialID = existingID;
        this.lectureNoteFile = lecNote;
        this.labNoteFile = labNote;
    }
}
