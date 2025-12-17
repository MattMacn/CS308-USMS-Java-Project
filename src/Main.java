import java.util.ArrayList;

public class Main {

//    public static void main(String[] args) {
//        DAOClass dao = new DAOClass();
//
//        DBUser user0 = new DBUser("Anna", "Banana", "12345678", "something", "a@b.com", "2013-12-12", false, true);
//        DBUser user1 = new DBUser("Charlie", "Date", "12345678", "tbd", "c@d.com", "1984-01-01", false, true);
//        DBUser user2 = new DBUser("Erin", "Fig", "12345678", "unknown", "e@f.com", "1900-12-01", true, true);
//        DBUser user3 = new DBUser("Garret", "Huckleberry", "12345678", "undefined", "g@h.com", "1800-12-01", true, false);
//        ArrayList<DBUser> users = new ArrayList<>();
//        users.add(user0);
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//
//        DBUnapproved unapproved0 = new DBUnapproved("Tody", "Armstrong", "pass5678", "A", "t@no.com","2100-08-06", "PhD");
//        DBUnapproved unapproved1 = new DBUnapproved("Kody", "Armstrong", "pass5678", "A", "k@no.com","2100-08-06", null);
//        DBUnapproved unapproved2 = new DBUnapproved("Mody", "Armstrong", "pass5678", "A", "m@no.com","2100-08-06", "");
//        DBUnapproved unapproved3 = new DBUnapproved("Rody", "Armstrong", "pass5678", "A", "r@no.com","2100-08-06", "Specil");
//        ArrayList<DBUnapproved> unapproved = new ArrayList<>();
//        unapproved.add(unapproved0);
//        unapproved.add(unapproved1);
//        unapproved.add(unapproved2);
//        unapproved.add(unapproved3);
//
//        DBDepartment dep0 = new DBDepartment("Dep0", "best department 0");
//        DBDepartment dep1 = new DBDepartment("Dep1", "best department 1");
//        DBDepartment dep2 = new DBDepartment("Dep2", "best department 2");
//        DBDepartment dep3 = new DBDepartment("Dep3", "best department 3");
//        ArrayList<DBDepartment> deps = new ArrayList<>();
//        deps.add(dep0);
//        deps.add(dep1);
//        deps.add(dep2);
//        deps.add(dep3);
//
//        ArrayList<DBCourse> courses = new ArrayList<>();
//        for(int i = 0; i < 16; i++) {
//            DBCourse tmpcourse = new DBCourse("CS"+i, "Course of the "+i, CourseType.UNDERGRAD, "Description", "", -1);
//            courses.add(tmpcourse);
//        }
//
//        ArrayList<DBModule> modules = new ArrayList<>();
//        for (int i = 0; i < 16*4; i++) {
//            DBModule tmpMod = new DBModule("mod" + i, "'The and Only Module " + i + "'", Semester.SEMESTER1AND2, "D", 0, 10);
//            modules.add(tmpMod);
//        }
//
////        for (DBUser u : users){
////            dao.createUser(u);
////        }
//
////        for (DBDepartment d : deps){
////            dao.createDepartment(d);
////        }
//
////        ArrayList<DBDepartment> readDeps = dao.readDepartments();
////        DBDepartment useDep = readDeps.getFirst();
////        int l = 0;
////        int n = 0;
////        for (DBCourse c : courses){
////            if ((l++ % 4) == 0){
////                useDep = readDeps.get(n++);
////            }
////            c.setDepartmentCode(useDep.getDepartmentCode());
////            dao.createCourse(c);
////        }
//
////        for(DBModule m : modules){
////            dao.createModule(m);
////        }
////
////        ArrayList<DBCourse> readCourses = dao.readCourses();
////        ArrayList<DBModule> readModules = dao.readModules();
////        DBCourse useCourse = readCourses.getFirst();
////        int j = 0;
////        int k = 0;
////        for(DBModule m : readModules){
////            if ((j % 4) == 0){
////                useCourse = readCourses.get(k++);
////            }
////            dao.createCourseModule(useCourse, m);
////            j++;
////        }
//
//        DBStudent readStudentIn = dao.readStudentFromEmail("a@b.com");
//
//        DBCourse courseInread = dao.readCourseFromCourseCode("DEF");
//        System.out.println("course inread: " + courseInread);
//        DBStudent studentInread = dao.readStudentFromEmail("g@h.com");
//        for (int i = 0; i <9; i++) {
//            DBModule module = new DBModule("ModuleName%d".formatted(i), Semester.SEMESTER1AND2, "something on other/hand, no!", 1, 10);
//            dao.createModule(module);
//
//            DBResult results = new DBResult(module.getModuleID(), ModuleResult.EXAM, 46+i, 10-i);
//
//            dao.createResult(studentInread, results);
//        }
//
//
//
//        for(DBUnapproved u : unapproved){
//            dao.createUnapprovedUser(u);
//        }
//        DBUser uStudent = dao.readUserFromEmail("a@b.com");
//        DBCourse readCourse = dao.readCourseFromCourseCode("CS2");
//        DBStudent student = new DBStudent(uStudent, readCourse);
//        DBUser uLecturer = dao.readUserFromEmail("c@D.com");
//        DBLecturer lecturer = new DBLecturer(uLecturer, "PhD", "Dep1");
//        dao.createStudent(student);
//        dao.createLecturer(lecturer);
//
//
//        System.out.println("Looks like Database stuff went well");
//    }

    public static void main(String[] args) {
        DAOClass dao = new DAOClass();
//        DBUser user = new DBUser("z", "y", "12345678", "d", "z@y.com", "2020-02-02", false, true);
//        dao.createUser(user);
//        DBCourse course = dao.readCourseFromCourseCode("CS9");
//        DBUser user = dao.readUserFromUserID("usr-b8e37f8b-6241-4a19-8e30-69af8453004f");
//        DBStudent student = new DBStudent(user, course);
//        dao.createStudent(student);


        DBStudent student = dao.readStudentFromStudentID("std-6d4cd4e2-8d9b-4dd2-8372-84a86a77cfba");


//        // ---------------------------------------------------------
//        // 1. SETUP: Create Basic Department, Course, and Modules
//        // ---------------------------------------------------------
//        System.out.println("--- Setting up Course and Modules ---");
//
//        // Create Department
//        DBDepartment dep = new DBDepartment("CS_DEP", "Computer Science Department");
//        dao.createDepartment(dep);
//
//        // Create Course (CS2) - Allowing 20 credits of compensation
//        // Note: Check your DBCourse constructor, assuming the last int is maxCompensation
//        DBCourse course = new DBCourse("CS2A", "Computer Science II", CourseType.UNDERGRAD, "Test Course", "CS_DEP", 20);
//        dao.createCourse(course);
//
//        // Create Modules
//        // mod-java: 20 credits, Max Attempts 3
//        DBModule modJava = new DBModule("mod-java-01", "Advanced Java", Semester.SEMESTER1AND2, "Java Swing", 3, 20);
//        dao.createModule(modJava);
//        dao.createCourseModule(course, modJava);
//
//        // mod-db: 20 credits, Max Attempts 3
//        DBModule modDB = new DBModule("mod-db-01", "Database Design", Semester.SEMESTER1AND2, "SQL Logic", 3, 20);
//        dao.createModule(modDB);
//        dao.createCourseModule(course, modDB);
//
//
//        // ---------------------------------------------------------
//        // SCENARIO A: HAPPY PATH (Target: Award)
//        // Student passes both modules
//        // ---------------------------------------------------------
//        System.out.println("--- Creating Scenario A: Student gets Award ---");
//
//        DBUser userA = new DBUser("Alice", "Award", "pass1", "f", "alice@test.com", "2000-01-01", true, true);
//        if(dao.createUser(userA)) {
//            DBStudent studentA = new DBStudent(userA, course);
//            dao.createStudent(studentA);
//
//            // Pass Java (75%)
//            DBResult resA1 = new DBResult(modJava.getModuleID(), ModuleResult.EXAM, 75, 1);
//            dao.createResult(studentA, resA1);
//
//            // Pass DB (62%)
//            DBResult resA2 = new DBResult(modDB.getModuleID(), ModuleResult.LABANDEXAM, 62, 1);
//            dao.createResult(studentA, resA2);
//        }
//
//        // ---------------------------------------------------------
//        // SCENARIO B: RESIT PATH (Target: Resit)
//        // Student passes one, fails one hard (<40)
//        // ---------------------------------------------------------
//        System.out.println("--- Creating Scenario B: Student needs Resit ---");
//
//        DBUser userB = new DBUser("Bob", "Resit", "pass2", "m", "bob@test.com", "2000-01-01", true, true);
//        if(dao.createUser(userB)) {
//            DBStudent studentB = new DBStudent(userB, course);
//            dao.createStudent(studentB);
//
//            // Pass Java (55%)
//            DBResult resB1 = new DBResult(modJava.getModuleID(), ModuleResult.LAB, 55, 1);
//            dao.createResult(studentB, resB1);
//
//            // Fail DB (35%) - Mark < 40 triggers Resit
//            DBResult resB2 = new DBResult(modDB.getModuleID(), ModuleResult.EXAM, 35, 1);
//            dao.createResult(studentB, resB2);
//        }
//
//        // ---------------------------------------------------------
//        // SCENARIO C: WITHDRAW PATH (Target: Withdraw)
//        // Student fails on Max Attempt (3)
//        // ---------------------------------------------------------
//        System.out.println("--- Creating Scenario C: Student must Withdraw ---");
//
//        DBUser userC = new DBUser("Charlie", "Withdraw", "pass3", "m", "charlie@test.com", "2000-01-01", true, true);
//        if(dao.createUser(userC)) {
//            DBStudent studentC = new DBStudent(userC, course);
//            dao.createStudent(studentC);
//
//            // Pass Java (60%)
//            DBResult resC1 = new DBResult(modJava.getModuleID(), ModuleResult.LAB, 60, 1);
//            dao.createResult(studentC, resC1);
//
//            // Fail DB (45%) BUT on Attempt 3 (Max Attempts = 3)
//            // Even though mark is 45 (compensatable), hitting max attempts forces withdraw logic
//            DBResult resC2 = new DBResult(modDB.getModuleID(), ModuleResult.OTHER, 45, 3);
//            dao.createResult(studentC, resC2);
//        }
//
//        System.out.println("--- Creating Scenario D: Compensation Edge Case ---");
//
////        DBUser userD = new DBUser("David", "Compensate", "pass4", "m", "david@test.com", "1999-09-09", true, true);
////        if(dao.createUser(userD)) {
////
////            DBStudent studentD = new DBStudent(userD, dao.readCourseFromCourseCode("CS2A"));
////            dao.createStudent(studentD);
////
////            // Module 1: 42% (Compensatable)
////            DBResult resD1 = new DBResult(dao.readModuleFromModuleCode("mod-java-01").getModuleID(), ModuleResult.EXAM, 42, 1);
////            dao.createResult(dao.readStudentFromEmail("david@test.com"), resD1);
////
////            // Module 2: 48% (Compensatable)
////            // Result: Student has 2 "Soft Fails"
//            DBResult resD2 = new DBResult(dao.readModuleFromModuleCode("mod-db-01").getModuleID(), ModuleResult.OTHER, 48, 1);
//            dao.createResult(dao.readStudentFromEmail("david@test.com"), resD2);
////        }

        System.out.println("--- Data Setup Complete ---");
    }
}