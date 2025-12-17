import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCourse {


    private String courseCode;
    private String courseName;
    private CourseType courseType;
    private String courseDescription;
    private String departmentCode;
    private int maxModuleCompensated;

    // Constructor for inserting a new course into db
    public DBCourse(String courseCode, String courseName, CourseType courseType, String courseDescription, String  departmentCode, int maxModuleCompensated){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseType = courseType;
        this.courseDescription = courseDescription;
        this.departmentCode = departmentCode;
        this.maxModuleCompensated = maxModuleCompensated;
    }

    public DBCourse(ResultSet result){
        try{
            this.courseCode = result.getString("courseCode");
            this.courseName = result.getString("course_name");
            this.courseType = CourseType.fromString(result.getString("course_type"));
            this.courseDescription = result.getString("course_description");
            this.departmentCode = result.getString("department_code");
            this.maxModuleCompensated = result.getInt("max_module_compensated");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public String getCourseCode(){
        return this.courseCode;
    }

    public String getCourseName(){
        return this.courseName;
    }
    public CourseType getCourseType(){
        return this.courseType;
    }
    public String getCourseDescription(){
        return this.courseDescription;
    }

    public String getDepartmentCode(){return this.departmentCode;}

    public int getMaxModuleCompensated(){
        return this.maxModuleCompensated;
    }

    public void setCourseCode(String courseID){
        this.courseCode = courseID;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setCourseType(CourseType courseType){
        this.courseType = courseType;
    }

    public void setCourseDescription(String courseDescription){
        this.courseDescription = courseDescription;
    }

    public void setDepartmentCode(String departmentCode){
        this.departmentCode = departmentCode;
    }

    public void setMaxModuleCompensated(int maxModuleCompensated) {this.maxModuleCompensated = maxModuleCompensated;}

    public void printCourse(){
        System.out.println("Course Code: " + this.courseCode);
        System.out.println("Course Name: " + this.courseName);
        System.out.println("Course Type: " + CourseType.asString(courseType));
        System.out.println("Course Description: " + this.courseDescription);
        System.out.println("Department Code: " + this.departmentCode);
    }
}


