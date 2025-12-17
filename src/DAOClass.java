import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class DAOClass {

    private static Connection connection;

    public DAOClass() {
        connection = DBConnect.getMysqlConnection();
    }

    // CRUD Unapproved
    public boolean createUnapprovedUser(DBUnapproved unapproved){
        String sql = "INSERT INTO `Unapproved` (`firstname`, `surname`, `pword`, `gender`, `email`, `DOB`, `qualification`) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, unapproved.getFirstName());
            statement.setString(2, unapproved.getSurname());
            statement.setString(3, unapproved.getPassword());
            statement.setString(4, unapproved.getGender());
            statement.setString(5, unapproved.getEmail());
            statement.setString(6, unapproved.getDOB());
            statement.setString(7, unapproved.getQualification());
            if (statement.executeUpdate() == 1) {
                System.out.println("Unapproved user: \"" + unapproved.getEmail() + "\" Successfully Created");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<DBUnapproved> readUnapprovedUsers() {
        String sql = "SELECT * FROM `Unapproved` WHERE 1 = 1;";

        ArrayList<DBUnapproved> unapproveds = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()){
                unapproveds.add(new DBUnapproved(result));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return unapproveds;
    }

    public ArrayList<DBUnapproved> readUnapprovedStudents() {
        String sql = "SELECT * FROM `Unapproved` WHERE qualification IS NULL;";

        ArrayList<DBUnapproved> unapproveds = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()){
                unapproveds.add(new DBUnapproved(result));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return unapproveds;
    }

    public ArrayList<DBUnapproved> readUnapprovedLecturers() {
        String sql = "SELECT * FROM `Unapproved` WHERE qualification IS NOT NULL;";

        ArrayList<DBUnapproved> unapproveds = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()){
                unapproveds.add(new DBUnapproved(result));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return unapproveds;
    }

    public DBUnapproved readUnapprovedFromEmail(String email) {
        String sql = "SELECT * FROM `Unapproved` WHERE email = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new DBUnapproved(result);
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return null;
    }

    public boolean deleteUnapprovedUser(String email){
        String sql = "DELETE FROM `Unapproved` WHERE email = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            if (statement.executeUpdate() == 1) {
                System.out.println("Unapproved: \"" + email + "\" Successfully Deleted");
                return true;
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    // CRUD User ##################################################################################################
    public boolean createUser(DBUser user) {

        String sql = "INSERT INTO `Users` (`userID`,`firstname`, `surname`, `pword`, `gender`, `email`, `DOB`, `is_manager`, `is_activated`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getDOB());
            statement.setBoolean(8, user.getIsManager());
            statement.setBoolean(9, user.getIsActivated());
            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + user.getUserID() + "\" Successfully Created");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DBUser readUserFromUserID(String userID) {
        String sql = "SELECT * FROM `Users` WHERE userID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,userID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new DBUser(result);
            }
        } catch (SQLException err) {
            System.out.println("readUserFromUserID: Could not create a user from userID \"" + userID + "\"");
            err.printStackTrace();
        }

        return null;
    }



    public DBUser readUserFromEmail(String email) {
        String sql = "SELECT * FROM `Users` WHERE email = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new DBUser(result);
            }
        } catch (SQLException err) {
            System.out.println("readUserFromEmail: Could not read user from email \"" + email + "\"");
            err.printStackTrace();
        }
        return null;
    }

    public boolean updateUserPassword(DBUser user) {
        String sql = "Update `Users` SET `pword` = ? WHERE userID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUserID());
            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + user.getUserID() + "\" Successfully Updated Password");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateUserManagerID(DBUser user) {
        String sql = "Update `Users` SET `managerID` = ? WHERE userID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getManagerID());
            statement.setString(2, user.getUserID());
            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + user.getUserID() + "\" Successfully Updated ManagerID");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateUserIsActivated(DBUser user) {
        String sql = "Update `Users` SET `is_Activated` = ? WHERE userID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, user.getIsActivated());
            statement.setString(2, user.getUserID());
            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + user.getUserID() + "\" Successfully Updated is_activated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    public boolean deleteUser(String userID) {
        String sql = "DELETE FROM `Users` WHERE userID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);

            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + userID + "\" Successfully Deleted");
                return true;
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    //CRUD Student ##################################################################################################
    public boolean createStudent(DBStudent student) {
        String sql = "INSERT INTO `Students` (`studentID`, `userID`, `courseCode`) VALUES (?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getStudentID());
            statement.setString(2, student.getUser().getUserID());
            statement.setString(3, student.getCourse().getCourseCode());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 1) {
                System.out.println("Student " + student.getStudentID() + "Inserted successfully");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DBStudent readStudentFromStudentID(String studentID) {
        String sql = "SELECT * FROM `Students` WHERE studentID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new DBStudent(result);
            }
        } catch (SQLException e) {
            System.out.println("readStudentFromStudentID: Could not read a student from studentID \"" + studentID + "\"");
            e.printStackTrace();
        }
        return null;
    }

    public DBStudent readStudentFromUserID(String userID) {
        String sql = "SELECT * FROM `Students` WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new DBStudent(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("readStudentFromUserID: Could not read a student from userID \"" + userID + "\"");
        return null;
    }

    public DBStudent readStudentFromEmail(String email){
        DBUser user = readUserFromEmail(email);
        if(user==null) return null;
        return readStudentFromUserID(user.getUserID());
    }

    public ArrayList<DBStudent> readStudentsFromCourseCode(String courseID){
        String sql = "SELECT * FROM `Students` WHERE courseCode = ?;";
        ArrayList<DBStudent> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseID);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                users.add(new DBStudent(result));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("readStudentsFromCourseCode: Could not read a students from courseID \"" + courseID + "\"");
        return users;
    }


    public ArrayList<DBStudent> readStudentsFromModuleID(String moduleID){
        String module_course = "SELECT * FROM `Course_Module` WHERE moduleID = ?;";
        String course_student = "SELECT * FROM `Students` WHERE courseCode = ?;";

        ArrayList<DBStudent> students = new ArrayList<>();

        try (PreparedStatement statement1 = connection.prepareStatement(module_course);
             PreparedStatement statement2 = connection.prepareStatement(course_student)) {

            statement1.setString(1, moduleID);
            ResultSet result1 = statement1.executeQuery();

            while(result1.next()){
                statement2.setString(1, result1.getString("courseCode"));
                ResultSet result2 = statement2.executeQuery();

                while(result2.next()){
                    students.add(new DBStudent(result2));
                }
            }

            return students;


        } catch (SQLException err) {
            err.printStackTrace();
        }

        System.out.println("readStudentsFromModuleID: Could not read a students from moduleID \"" + moduleID + "\"");
        return students;
    }

    public boolean deleteStudent(String studentID){
        String deleteStudent = "DELETE FROM `Students` WHERE studentID = ?;";
        String deleteResults = "DELETE FROM `Results` WHERE studentID = ?;";

        try (PreparedStatement studentStatement = connection.prepareStatement(deleteStudent);
             PreparedStatement resultStatement = connection.prepareStatement(deleteResults)){

            connection.setAutoCommit(false);

            studentStatement.setString(1,studentID);
            resultStatement.setString(1,studentID);

            int resultsDeleted = resultStatement.executeUpdate();
            if (resultsDeleted > 0) {
                System.out.println("Successfully deleted " + resultsDeleted + " results/enrollments for student \"" + studentID + "\"");
            }
            int studentsDeleted = studentStatement.executeUpdate();
            if (studentsDeleted == 1) {
                System.out.println("Successfully deleted student \"" + studentID + "\" from Student table");
            }

            connection.commit();

            return studentsDeleted > 0;

        } catch (SQLException err) {
            try {
                connection.rollback();
            }catch (SQLException error){
                error.printStackTrace();
            }
            err.printStackTrace();
        }
        return false;
    }

    //CRUD Decision ####################################################################################################

        public boolean createDecision(DBDecision decision){
            String sql = "INSERT INTO `Decisions` (`studentID`, `course_year`, `award`) VALUES (?, ?, ?);";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, decision.getStudentID());
                statement.setString(2, decision.getYear());
                statement.setString(3, Award.asString(decision.getAward()));
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Student " + decision.getStudentID() + decision.getYear() +  "Inserted successfully");
                    return true;
                }
            }
            catch(SQLException e ){
                e.printStackTrace();
            }
            return false;
        }

    public ArrayList<DBDecision> readDecisionsFromStudentID(String studentID){
        String sql = "SELECT * FROM `Decisions` WHERE studentID = ?; ";

        ArrayList<DBDecision> decisions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            ResultSet resultset = statement.executeQuery();
            while (resultset.next()) {
                decisions.add(new DBDecision(resultset));
            }
            return decisions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return decisions;
    }

    public DBDecision readDecisionFromStudentIDAndYear(String studentID, String year){
        String sql = "SELECT * FROM `Decisions` WHERE studentID = ? AND course_year = ?;";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            statement.setString(2, year);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()) {
                return new DBDecision(resultset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateDecision (DBDecision decision){
        if (readDecisionFromStudentIDAndYear(decision.getStudentID(),decision.getYear()) == null){
            createDecision(decision);
            return true;
        }

        String sql = "UPDATE `Decisions` SET award = ? WHERE studentID = ? AND course_year = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Award.asString(decision.getAward()));
            statement.setString(2, decision.getStudentID());
            statement.setString(3, decision.getYear());
            if (statement.executeUpdate() == 1) {
                System.out.println("User: \"" + decision.getStudentID() + decision.getYear() + " updated." );
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    public boolean deleteDecision(DBDecision decision){
        String sql = "DELETE FROM `Decisions` WHERE studentID = ? AND course_year = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,decision.getStudentID());
            statement.setString(2,decision.getYear());

            if(statement.executeUpdate() == 1){
                System.out.println("Successfully deleted Decision for " + decision.getStudentID() + " for " + decision.getYear());
                return true;
            }
        }catch (SQLException err){
            err.printStackTrace();
        }
        return false;
    }


    //CRUD Lecturer ##################################################################################################
    public boolean createLecturer(DBLecturer lecturer) {
        String sql = "INSERT INTO `Lecturers` (`lecturerID`, `userID`, `qualification`, `department_code`) VALUES (?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lecturer.getlecturerID());
            statement.setString(2, lecturer.getUser().getUserID());
            statement.setString(3, lecturer.getQualification());
            statement.setString(4, lecturer.getDepartmentCode());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("lecturer " + lecturer.getlecturerID() + "Inserted successfully");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DBLecturer readLecturerFromLecturerID(String lecturerID) {
        String sql = "SELECT * FROM `Lecturers` WHERE lecturerID = ?;";

        ArrayList<DBLecturer> lecturers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lecturerID);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()){
                return new DBLecturer(resultset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DBLecturer readLecturerFromUserID(String userID) {
        String sql = "SELECT * FROM `Lecturers` WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new DBLecturer(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DBLecturer readLecturerFromEmail(String email){
        DBUser user = readUserFromEmail(email);
        if (user==null) return null;
        return readLecturerFromUserID(user.getUserID());
    }

    public ArrayList<DBLecturer> readLecturerFromModuleID(String moduleID){
        String sql = "SELECT * FROM `Module_Lecturer` WHERE moduleID = ?;";
        ArrayList<DBLecturer> lecturers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleID);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                lecturers.add(readLecturerFromLecturerID(result.getString("lecturerID")));
            }
            return lecturers;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteLecturer(String lecturerID){
        String deleteFromLecturer = "DELETE FROM `Lecturers` WHERE lecturerID = ?;";
        String deleteModuleLecturers = "DELETE FROM `Module_Lecturer` WHERE lecturerID = ?;";


        try (PreparedStatement moduleStatement = connection.prepareStatement(deleteFromLecturer);
             PreparedStatement moduleLecturerStatement = connection.prepareStatement(deleteModuleLecturers);){

            connection.setAutoCommit(false);

            moduleStatement.setString(1,lecturerID);
            moduleLecturerStatement.setString(1,lecturerID);

            int linksDeleted = moduleLecturerStatement.executeUpdate();
            if (linksDeleted > 0) {
                System.out.println("Successfully deleted " + linksDeleted + " links from Module_Lecturer table");
            }

            int lecturerDeleted = moduleStatement.executeUpdate();
            if (lecturerDeleted == 1) {
                System.out.println("Successfully deleted lecturer \"" + lecturerID + "\" from Lecturers table");
            }

            connection.commit();

            return lecturerDeleted > 0;

        } catch (SQLException err) {
            try{
                connection.rollback();
            } catch (SQLException error){
                error.printStackTrace();
            }
            err.printStackTrace();
        }
        return false;
    }

    //CRUD Course ##################################################################################################
    public boolean createCourse(DBCourse course){
        String sql = "INSERT INTO `Course` (`courseCode`, `course_name`, `course_type`, `course_description`, `department_code`) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,course.getCourseCode());
            statement.setString(2, course.getCourseName());
            statement.setString(3, CourseType.asString(course.getCourseType()));
            statement.setString(4, course.getCourseDescription());
            statement.setString(5, course.getDepartmentCode());
            int rowsInserted = statement.executeUpdate();
            if( rowsInserted > 0){
                System.out.println("Course " + course.getCourseCode() + "Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<DBCourse> readCourses (){
        String sql = "SELECT * FROM `Course`;";

        ArrayList<DBCourse> courses = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            while(result.next()){
                DBCourse course = new DBCourse(result);
                courses.add(course);
            }
            return courses;
        }
        catch(SQLException err ){
            err.printStackTrace();
        }
        return null;
    }

    public DBCourse readCourseFromCourseCode(String courseCode){
        String sql = "SELECT * FROM `Course` WHERE courseCode = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseCode);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new DBCourse(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DBCourse readCourseFromModuleID(String moduleID) {
        String sql = "SELECT courseCode FROM `Course_Module` WHERE moduleID = ?;";
        String foundCourseCode = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleID);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return readCourseFromCourseCode(result.getString("courseCode"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCourseName(DBCourse course) {
        String sql = "Update `Course` SET `course_name` = ? WHERE courseCode = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseCode());
            if (statement.executeUpdate() == 1) {
                System.out.println("Course: \"" + course.getCourseCode() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateCourseMaxModulesCompensated(DBCourse course) {
        String sql = "Update `Course` SET `max_module_compensated` = ? WHERE courseCode = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, course.getMaxModuleCompensated());
            statement.setString(2, course.getCourseCode());
            if (statement.executeUpdate() == 1) {
                System.out.println("Course: \"" + course.getCourseCode() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateCourseType(DBCourse course) {
        String sql = "Update `Course` SET `course_type` = ? WHERE courseCode = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, CourseType.asString(course.getCourseType()));
            statement.setString(2, course.getCourseCode());
            if (statement.executeUpdate() == 1) {
                System.out.println("Course: \"" + course.getCourseCode() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateCourseDepartment(DBCourse course) {
        String sql = "Update `Course` SET `department_code` = ? WHERE courseCode = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getDepartmentCode());
            statement.setString(2, course.getCourseCode());
            if (statement.executeUpdate() == 1) {
                System.out.println("Course: \"" + course.getCourseCode() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateCourseDescription(DBCourse course) {
        String sql = "Update `Course` SET `course_description` = ? WHERE courseCode = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getCourseDescription());
            statement.setString(2, course.getCourseCode());
            if (statement.executeUpdate() == 1) {
                System.out.println("Course: \"" + course.getCourseCode() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    public boolean deleteCourse(String courseCode){
        String deleteCourse = "DELETE FROM `Course` WHERE courseCode = ?;";
        String deleteCourseModule = "DELETE FROM `Course_Module` WHERE courseCode = ?;";


        try (PreparedStatement courseStatement = connection.prepareStatement(deleteCourse);
             PreparedStatement moduleCourseStatement = connection.prepareStatement(deleteCourseModule);){

            connection.setAutoCommit(false);

            courseStatement.setString(1,courseCode);
            moduleCourseStatement.setString(1,courseCode);

            int linksDeleted = moduleCourseStatement.executeUpdate();
            if (linksDeleted > 0) {
                System.out.println("Successfully deleted " + linksDeleted + " associations for course \"" + courseCode + "\" from Course_Module table");
            }
            int coursesDeleted = courseStatement.executeUpdate();
            if (coursesDeleted > 0) {
                System.out.println("Successfully deleted course \"" + courseCode + "\" from Course table");
            }

            connection.commit();

            return coursesDeleted > 0;

        } catch (SQLException err) {
            try{
                connection.rollback();
            }catch (SQLException error){
                error.printStackTrace();
            }
            err.printStackTrace();
        }
        return false;
    }

    //CRUD Module ##################################################################################################
    public boolean createModule(DBModule module){
        String sql = "INSERT INTO `Modules` (`moduleID`, `module_code`, `module_name`, `semester`, `module_description`, `max_attempts`, `credit`) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, module.getModuleID());
            statement.setString(2, module.getModuleCode());
            statement.setString(3, module.getModuleName());
            statement.setString(4, Semester.asString(module.getSemester()));
            statement.setString(5, module.getModuleDescription());
            statement.setInt(6, module.getMaxAttempts());
            statement.setInt(7, module.getCredit());
            if( statement.executeUpdate() == 1){
                System.out.println("Module \"" + module.getModuleID() + "\" Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<DBModule> readModules (){
        String sql = "SELECT * FROM `Modules`;";

        ArrayList<DBModule> modules = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            while(result.next()){
               DBModule module = new DBModule(result);
                modules.add(module);
            }
            return modules;
        }
        catch(SQLException err ){
          err.printStackTrace();
        }
        return null;
    }

    public DBModule readModuleFromModuleID(String moduleID){
        String sql = "SELECT * FROM `Modules` WHERE moduleID = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new DBModule(result);
            }
        }
        catch(SQLException e){
            System.out.println("readModuleFromModuleID: Could not read a module from moduleID \"" + moduleID + "\"");
            e.printStackTrace();
        }

        return null;
    }

    public DBModule readModuleFromModuleCode(String moduleCode){
        String sql = "SELECT * FROM `Modules` WHERE module_code = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleCode);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new DBModule(result);
            }
        }
        catch(SQLException e){
            System.out.println("readModuleFromModuleCode: Could not read a module from module code \"" + moduleCode + "\"");
            e.printStackTrace();
        }
        return null;
    }

    public DBModule readModuleFromModuleName(String moduleName){
        String sql = "SELECT * FROM `Modules` WHERE module_name = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleName);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new DBModule(result);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("readModuleFromModuleName: Could not read a module from module_name \"" + moduleName + "\"");
        return null;
    }


    public ArrayList<DBModule> readModulesFromStudentID(String studentID){
        DBStudent student = readStudentFromStudentID(studentID);
        if (student==null) return null;
        return readModulesFromCourseCode(student.getCourse().getCourseCode());
    }


    public ArrayList<DBModule> readModulesFromLecturerID(String lecturerID){

        String sql = "SELECT * FROM `Module_Lecturer` WHERE lecturerID = ?;";

        ArrayList<DBModule> modules = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lecturerID);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                modules.add(readModuleFromModuleID(result.getString("moduleID")));
            }
            return modules;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<DBModule> readModulesFromCourseCode(String courseCode){
        String sql = "SELECT * FROM `Course_Module` WHERE courseCode = ?;";
        ArrayList<DBModule> modules = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseCode);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                modules.add(readModuleFromModuleID(result.getString("moduleID")));
            }
            return modules;
        }
        catch(SQLException err){
            err.printStackTrace();
        }
        System.out.println("readModulesFromCourseCode: Could not read modules from courseID \"" + courseCode + "\"");
        return null;
    }

    public boolean updateModuleName(DBModule module) {
        String sql = "Update `Modules` SET `module_name` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, module.getModuleName());
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() > 0) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleSemester(DBModule module) {
        String sql = "Update `Modules` SET `semester` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Semester.asString(module.getSemester()));
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() == 1) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleDescription(DBModule module) {
        String sql = "Update `Modules` SET `module_description` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, module.getModuleDescription());
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() == 1) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleMaxAttempts(DBModule module) {
        String sql = "Update `Modules` SET `max_attempts` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, module.getMaxAttempts());
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() == 1) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleCredits(DBModule module) {
        String sql = "Update `Modules` SET `credit` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, module.getCredit());
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() == 1) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleCourse(DBModule module, DBCourse course) {
        String sql = "Update `Course_Module` SET `courseCode` = ? WHERE moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getCourseCode());
            statement.setString(2, module.getModuleID());
            if (statement.executeUpdate() == 1) {
                System.out.println("Module: \"" + module.getModuleID() + "\" Successfully Updated");
                return true;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    public boolean deleteModule(String moduleID){
        String deleteModule = "DELETE FROM `Modules` WHERE moduleID = ?;";
        String deleteModuleLecturers = "DELETE FROM `Module_Lecturer` WHERE moduleID = ?;";
        String deleteCourseModule = "DELETE FROM `Course_Module` WHERE moduleID = ?;";
        String deleteModuleMaterial = "DELETE FROM `Module_Materials` WHERE moduleID = ?;";

        try (PreparedStatement moduleStatement = connection.prepareStatement(deleteModule);
            PreparedStatement moduleLecturerStatement = connection.prepareStatement(deleteModuleLecturers);
            PreparedStatement courseModuleStatement = connection.prepareStatement(deleteCourseModule);
             PreparedStatement moduleMaterialsStatement = connection.prepareStatement(deleteModuleMaterial)){

            // Start a SQL transaction
            connection.setAutoCommit(false);

            moduleStatement.setString(1,moduleID);
            moduleLecturerStatement.setString(1,moduleID);
            courseModuleStatement.setString(1,moduleID);
            moduleMaterialsStatement.setString(1, moduleID);



            if (moduleLecturerStatement.executeUpdate() >= 1) {
                System.out.println("Successfully deleted lecturers associated with module \"" + moduleID + "\" from Module_Lecturer table");
            }

            if (courseModuleStatement.executeUpdate() >= 1) {
                System.out.println("Successfully deleted associated courses  \"" + moduleID + "\" from Course_Module table");
            }

            if (moduleMaterialsStatement.executeUpdate() >= 1) {
                System.out.println("Successfully deleted module \"" + moduleID + "\" from Module_Materials table");
            }

            if (moduleStatement.executeUpdate() >= 1) {
                System.out.println("Successfully deleted module \"" + moduleID + "\" from Modules table");
            }



            connection.commit();
            return true;

        } catch (SQLException err) {
            try {
                connection.rollback();
            } catch (SQLException error) {
                error.printStackTrace();
            }
            err.printStackTrace();
            System.out.println("Error in deleteModule with module ID: " + moduleID + "Exception Error");
        }
        return false;
    }

    //CRUD Department ##################################################################################################

    public boolean createDepartment(DBDepartment department){
        String sql = "INSERT INTO `Departments` (`department_code`, `department_name`) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,department.getDepartmentCode());
            statement.setString(2, department.getDepartmentName());
            if(statement.executeUpdate() > 0){
                System.out.println("Department " + department.getDepartmentName() + " Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<DBDepartment> readDepartments (){
        String sql = "SELECT * FROM `Departments`;";

        ArrayList<DBDepartment> departments = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            while(result.next()){
                DBDepartment department = new DBDepartment(result);
                departments.add(department);
            }
            return departments;
        }
        catch(SQLException err ){
            err.printStackTrace();
        }
        return null;
    }

    public DBDepartment readDepartment(String departmentID){
        String sql = "SELECT * FROM `Departments` WHERE department_code = ?;";

        ArrayList<DBDepartment> departments = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departmentID);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()){
                return new DBDepartment(resultset);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean deleteDepartment(String departmentCode) {
        String delDpt = "DELETE FROM `Departments` WHERE department_code = ?;";
        String delCrs = "DELETE FROM `Course` WHERE department_code = ?;";

        try (PreparedStatement DptStatement = connection.prepareStatement(delDpt);
             PreparedStatement CrsStatement = connection.prepareStatement(delCrs)) {

            connection.setAutoCommit(false);

            DptStatement.setString(1, departmentCode);
            CrsStatement.setString(1, departmentCode);

            int CrsResult = CrsStatement.executeUpdate();
            int DptResult = DptStatement.executeUpdate();


            connection.commit();

            if (CrsResult > 0) {
                System.out.println("Successfully deleted Department \"" + departmentCode + "\" from Course");
            }

            if (DptResult == 1) {
                System.out.println("Successfully deleted Department \"" + departmentCode + "\" from Department");
            }

            return DptResult > 0;

        } catch (SQLException err) {
            try{
                connection.rollback();
            }catch (SQLException error){
                error.printStackTrace();
            }
            err.printStackTrace();
        }
        return false;
    }


    //CRUD Materials ##################################################################################################

    public boolean createMaterials(DBMaterial material){
        String sql = "INSERT INTO `Materials` (`materialID`, `lectureNote`, `labNote`) VALUES (?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, material.getMaterialID());

            File lecFile = material.getLectureNote();
            if (lecFile != null){
                statement.setBytes(2, Files.readAllBytes(lecFile.toPath()));
            } else {
                statement.setNull(2, java.sql.Types.BLOB);
            }

            File labFile = material.getLabNote();
            if (labFile != null){
                statement.setBytes(3, Files.readAllBytes(labFile.toPath()));
            } else {
                statement.setNull(3, java.sql.Types.BLOB);
            }

            int rowsInserted = statement.executeUpdate();
            if( rowsInserted >0){
                System.out.println("Material " + material.getMaterialID() + "Inserted successfully");
                return true;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
            System.out.println("Material " + material.getMaterialID() + "Error implementing this material, an exception occurred");
        }
        return false;
    }

    public DBMaterial readMaterials(String materialID){
        String sql = "SELECT * FROM `Materials` WHERE materialID = ?;";


        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, materialID);
            ResultSet resultset = statement.executeQuery();
            if( resultset.next()) {
                DBMaterial material = new DBMaterial(resultset);
                return material;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public DBMaterial readMaterialsLectureNote(String materialID){
        String sql = "SELECT * FROM `Materials` WHERE materialID = ?;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, materialID);
            ResultSet resultset = statement.executeQuery();
            if( resultset.next()){
                DBMaterial material = new DBMaterial(resultset);
                return material;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Material " + materialID + " ERROR! Not read successfully");
        }
        return null;
    }

    public DBMaterial readMaterialsLabNote(String materialID){
        String sql = "SELECT * FROM `Materials` WHERE materialID = ?;";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, materialID);
            ResultSet resultset = statement.executeQuery();
            if( resultset.next()){
                DBMaterial material = new DBMaterial(resultset);
                return material;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Material " + materialID + " ERROR! Not read successfully");
        }
        return null;
    }

    public boolean updateMaterialsLectureNote(DBMaterial material) {
        String sql = "UPDATE `Materials` SET `lectureNote` = ? WHERE materialID = ?;";
        File temp = material.getLectureNote();

        // specific check to ensure we have a file to upload
        if (temp == null) return false;

        try (FileInputStream inputStream = new FileInputStream(temp);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBytes(1, inputStream.readAllBytes());
            statement.setString(2, material.getMaterialID());

            if (statement.executeUpdate() == 1) {
                System.out.println("Lecture Note for material: \"" + material.getMaterialID() + "\" Successfully Updated ");
                return true;
            }
        } catch (SQLException | IOException err) {
            err.printStackTrace();
            System.out.println("Error updating Lecture Note: " + err.getMessage());
        }
        return false;
    }

    public boolean updateMaterialsLabNote(DBMaterial material) {
        String sql = "UPDATE `Materials` SET `labNote` = ? WHERE materialID = ?;";
        File temp = material.getLabNote();

        if (temp == null) return false;

        try (FileInputStream inputStream = new FileInputStream(temp);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBytes(1, inputStream.readAllBytes());
            statement.setString(2, material.getMaterialID());

            if (statement.executeUpdate() == 1) {
                System.out.println("Lab Note for material: \"" + material.getMaterialID() + "\" Successfully Updated ");
                return true;
            }
        } catch (SQLException | IOException err) {
            err.printStackTrace();
            System.out.println("Error updating Lab Note: " + err.getMessage());
        }
        return false;
    }

    //Download specific materials using the Module selected and Week selected
    public DBMaterial downloadModuleMaterial(String moduleCode, int weekNumber) {

    DBModule module = readModuleFromModuleCode(moduleCode);
    if (module == null) {
        System.err.println("Material Download Error");
        return null;
    }
    String moduleID = module.getModuleID();

    DBModuleMaterial moduleMaterial = readModuleMaterials(moduleID, weekNumber);

    if (moduleMaterial == null) {
        System.err.println("Material download error, No material link found for " + moduleCode + " and week " + weekNumber);
        return null;
    }
    String materialID = moduleMaterial.getMaterialID();

    String materialDataSql = "SELECT * FROM `Materials` WHERE materialID = ?;";

    try (PreparedStatement statement = connection.prepareStatement(materialDataSql)) {
        statement.setString(1, materialID);

        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return new DBMaterial(rs);
            }
        }
    } catch (SQLException e) { // Should never need this (I think?):
        System.err.println("SQL Error reading material data for materialID: " + materialID);
        e.printStackTrace();
    }

    return null;
}


    public boolean deleteMaterial(String materialID){
        String sql = "DELETE FROM Materials WHERE materialID = ?;";
        String sql2 = "DELETE FROM Module_Materials WHERE materialID = ?;";

        try(PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql2))
        {
            connection.setAutoCommit(false);

            statement.setString(1, materialID);
            statement2.setString(1, materialID);

            int result1 = statement.executeUpdate();
            int result2 = statement2.executeUpdate();

            connection.commit();

            if (result1 >0 ){
                System.out.println("Material with materialID: " + materialID + " Successfully deleted");
            }

            if (result2 >0 ){
                System.out.println("Material with materialID: " + materialID + " Successfully deleted Module_Materials");
            }

            return result1 > 0 || result2 > 0;


        } catch (SQLException e){
            try{
                connection.rollback();
            }catch (SQLException error){
                error.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Material with materialID: " + materialID + " was not deleted");
        }

        return false;
    }


    //CRUD Course_Module ###############################################################################################

    public boolean createCourseModule(DBCourse course, DBModule module){
        String sql = "INSERT INTO `Course_Module` (`courseCode`, `moduleID`) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,course.getCourseCode());
            statement.setString(2, module.getModuleID());
            int rowsInserted = statement.executeUpdate();
            if( rowsInserted > 0){
                System.out.println("The Course_Module connection: " + course.getCourseCode() + module.getModuleID() + "Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateModuleCourseLink(String moduleID, String newCourseCode) {
        String updateSql = "UPDATE `Course_Module` SET `courseCode` = ? WHERE `moduleID` = ?;";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            updateStmt.setString(1, newCourseCode);
            updateStmt.setString(2, moduleID);
            int rows = updateStmt.executeUpdate();

            if (rows > 0) return true; // It worked! The link was updated.

            String insertSql = "INSERT INTO `Course_Module` (`courseCode`, `moduleID`) VALUES (?, ?);";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, newCourseCode);
                insertStmt.setString(2, moduleID);
                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException err) {
            err.printStackTrace();
            return false;
        }
    }


    // CRUD Module_Lecturer ###########################################################################################

    public boolean createModuleLecturer(DBModule module, DBLecturer lecturer){
        String sql = "INSERT INTO `Module_Lecturer` (`moduleID`, `lecturerID`) VALUES (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, module.getModuleID());
            statement.setString(2, lecturer.getlecturerID());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0){
                System.out.println("The Module_Lecturer connection: " + lecturer.getlecturerID() + module.getModuleID() + " Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isModuleAssignedToLecturer(String moduleID, String lecturerID) {
        String sql = "SELECT * FROM `Module_Lecturer` WHERE moduleID = ? AND lecturerID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleID);
            statement.setString(2, lecturerID);
            ResultSet result = statement.executeQuery();

            // If result.next() is true, a row exists, meaning they are already linked
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteModuleLectuerer(DBModuleLecturer moduleLecturer){
        String sql = "DELETE FROM `Module_Lecturer` WHERE lecturerID = ? AND moduleID = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,moduleLecturer.getLecturerID());
            statement.setString(2,moduleLecturer.getModuleID());
            int rows = statement.executeUpdate();
            if(rows == 1){
                return true;
            }
        }catch (SQLException err){
            err.printStackTrace();
        }
        return false;
    }

    // CRUD Results ####################################################################################################

    public boolean createResult(DBStudent student, DBResult result) {
        String sql = "INSERT INTO `Results` ( `resultID`, `studentID`, `moduleID`, `result_type`, `mark`, `attempt_number`) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, result.getResultID());
            statement.setString(2, student.getStudentID());
            statement.setString(3, result.getModuleID());
            statement.setString(4, ModuleResult.asString(result.getResultType()));
            statement.setDouble(5, result.getMark());
            statement.setInt(6, result.getAttemptNumber());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 1) {
                System.out.printf("Result %s for %s and %s created\n", result.getResultID(), student.getStudentID(), result.getModuleID());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DBResult readLatestResultsFromStudentIDModuleID(String studentID, String moduleID) {
        String sql = "SELECT * FROM `Results` WHERE studentID = ? AND moduleID = ? ORDER BY attempt_number DESC LIMIT 1;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            statement.setString(2, moduleID);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return new DBResult(results);
            }
        } catch (SQLException err) {
            System.out.println("Database error fetching result for Student: " + studentID);
            err.printStackTrace();
        }
        return null;
    }

    public ArrayList<DBResult> readAllResultsFromStudentIDModuleID(String studentID, String moduleID) {
        String sql = "SELECT * FROM `Results` WHERE studentID = ? AND moduleID = ? ORDER BY attempt_number ASC;";
        ArrayList<DBResult> history = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            statement.setString(2, moduleID);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                history.add(new DBResult(results));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return history;
    }


    //CRUD Module_Materials ############################################################################################

    public boolean createModuleMaterials(DBModuleMaterial mm){
        String sql = "INSERT INTO `Module_Materials` (`moduleID`, `wkNumber`, `materialID`) VALUES (?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,mm.getModuleID());
            statement.setInt(2, mm.getWkNumber());
            statement.setString(3,mm.getMaterialID());
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted >0){
                System.out.println("The Module_Material connection: " + mm.getModuleID() + mm.getMaterialID() + "Inserted successfully");
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // When given a module and week, will return that module's materials for the week
    public DBModuleMaterial readModuleMaterials(String moduleID, int wkNumber){

        String sql = "SELECT * FROM `Module_Materials` WHERE moduleID = ? AND wkNumber = ?;";


        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moduleID);
            statement.setInt(2, wkNumber);
            ResultSet resultset = statement.executeQuery();
            if( resultset.next()){
                DBModuleMaterial material = new DBModuleMaterial(resultset);
                return material;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("**readModuleMaterials** Could not read moduleID: " + moduleID + "week number: " + wkNumber);
        }
        return null;
    }

}
