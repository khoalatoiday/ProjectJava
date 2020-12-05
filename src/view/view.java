package view;

import controller.UltilityController;
import controller.dataController;
import model.Student;
import model.StudentRegister;
import model.Subject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class view {
    public static void main(String[] args) {

        int choice = 0;

        var studentFile = "STUDENT.DAT";
        var controller = new dataController();
        var students = new ArrayList<Student>();

        var subjectFile = "SUBJECT.DAT";
        var subjects = new ArrayList<Subject>();

        var studentRegisterFile = "SR.DAT";
        var studentRegisters = new ArrayList<StudentRegister>();
        var ultilityController= new UltilityController();

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("_________________MENU_______________");
            System.out.println("1. Add a student to list: ");
            System.out.println("2. Show list of students: ");
            System.out.println("3. Add a subject to list: ");
            System.out.println("4. Show list of subjects: ");
            System.out.println("5. Do regist for student: ");
            System.out.println("0. Exit");

            choice = scanner.nextInt();
            scanner.nextLine(); // đọc bỏ dòng chứa lựa chọn

            switch (choice) {
                case 0:
                    System.out.println("Thank you for using our program!");
                    break;
                case 1:
                    System.out.println("Please fill Student's information");

                    String fullname, address, phonenumber;
                    System.out.println("Please fill name: ");
                    fullname = scanner.nextLine();

                    System.out.println("Please fill address: ");
                    address = scanner.nextLine();

                    System.out.println("Please fill Phonenumber: ");
                    String regex = "^\\d{3}-\\d{3}-\\d{4}$";
                    Pattern pattern;
                    Matcher matcher;
                    do {
                        phonenumber = scanner.nextLine();
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(phonenumber);
                        if (matcher.find()) {
                            break;
                        } else {
                            System.out.println("Please fill again!");
                        }
                    } while (true);

                    Student student = new Student(0, fullname, address, phonenumber);
                    controller.writeStudenttoFile(student, studentFile);
                    break;
                case 2:
                    students = controller.readStudentFromFile(studentFile);
                    showInfoStudent(students);
                    break;
                case 3:
                    //int subjectID, String subjectName, int totalLesson, String subjectType
                    //        printWriter.println(subject.getSubjectID() + "|" + subject.getSubjectName() + "|" +
                    //                subject.getTotalLesson() + "|" + subject.getSubjectType());
                    System.out.println("Please fill subject's information:");

                    String subjectName, subjectType;
                    int total, st;

                    System.out.println("Please fill subject's name:");
                    subjectName = scanner.nextLine();

                    System.out.println("Please fill total of lessons: ");
                    total = scanner.nextInt();

                    String[] subjectTypes = {"General", "Specializations base", "Compulsory majors",
                            "Specialization options"};
                    do {
                        System.out.println("Please fill your type of Subject: ");
                        System.out.println("1.General\n2.Specializations base\n" +
                                "3.Compulsory majors\n4.Specialization options");
                        st = scanner.nextInt();
                    } while (st < 1 || st > 4);
                    subjectType = subjectTypes[st - 1];
                    Subject subject = new Subject(0, subjectName, total, subjectType);
                    controller.writeSubjecttoFile(subject, subjectFile);
                    break;
                case 4:
                    subjects = controller.readSubjectFromFile(subjectFile);
                    showInfoSubject(subjects);
                    break;
                case 5:
                    //B0: đọc ra danh sách student,subject,thông tin quản lý đăng kí
                    students = controller.readStudentFromFile(studentFile);
                    subjects = controller.readSubjectFromFile(subjectFile);
                    studentRegisters = controller.readStudentRegisterFromFile(studentRegisterFile);
                    // B1: chọn một student từ danh sách đẻ cho phép đăng kí.Nếu đã đăng kí đủ số môn(>=8)
                    // thì không cho đăng kí nữa
                    int studentID;
                    boolean isRegister = false;

                    do {
                        showInfoStudent(students);
                        System.out.println("Please fill student's ID, put 0 to exit: ");
                        studentID = scanner.nextInt();
                        if (studentID == 0) {
                            break;
                        }
                        // hàm kiểm tra xem sinh viên đó đã vượt mức đăng kí 8 môn chưa
                        isRegister = checkRegister(studentRegisters, studentID);
                        if (isRegister) {
                            break;
                        } else {
                            System.out.println("You regist over too many subjects!");
                        }
                    } while (true);
                    //      B2: chọn một subject từ danh sách , nếu môn đó đã đăng kí rồi thì không đăng kí được,
                    //      yêu cầu đăng kí môn khác
                    showInfoSubject(subjects);
                    int subjectID;
                    boolean isAlreadyRegist = false;
                    int totalofSubject;
                    do {
                        System.out.println("Please fill subject's ID, you may put 0 to exit");
                        subjectID = scanner.nextInt();
                        totalofSubject = gettotalofSubjects(studentRegisters, studentID, subjectID);
                        if (subjectID == 0) {
                            break;
                        }
                        isAlreadyRegist = checkAlreadyRegist(studentRegisters, subjectID);
                        if (isAlreadyRegist) {
                            System.out.println("You already regist this subject! Please fill another to regist " +
                                    "other subject!");
                        } else {
                            totalofSubject++;
                            break;
                        }

                    } while (true);
                    scanner.nextLine();
                    Date date =new Date();

                    System.out.println("Please fill the state: ");
                    String state;
                    state = scanner.nextLine();


                    Student currentStudent = getStudent(students, studentID);
                    Subject currentSubject = getSubject(subjects, subjectID);
                    StudentRegister studentRegister = new StudentRegister(currentStudent, currentSubject, date,
                            totalofSubject, state);

                    // cập nhật danh sách đăng kí
                    studentRegisters= ultilityController.updateSRs(studentRegisters,studentRegister);
                    controller.updateSrFile(studentRegisters,studentRegisterFile);

                    // Hiển thị danh sách đăng kí
                    showSrInfo(studentRegisters);
                    break;
                case 6:
                    do{
                        System.out.println("Sort list of Student Regist!");
                        System.out.println("1. Sort by Student's Name: ");
                        System.out.println("2. Sort by Time: ");
                        System.out.println("0. Exit!");
                        int choices;
                        choices=scanner.nextInt();
                        if(choices==0){
                            break;
                        }
                        switch (choices){
                            case 1:
                                ultilityController.sortByStudentName(studentRegisters);
                                showSrInfo(studentRegisters);
                                break;
                            case 2:
                                ultilityController.sortByTime(studentRegisters);
                                showSrInfo(studentRegisters);
                                break;

                        }
                    }while (true);
                    break;
            }

        } while (choice != 0);

    }

    private static void showSrInfo(ArrayList<StudentRegister> studentRegisters) {
        System.out.println("Info list of registion: ");
        for(var s: studentRegisters){
            System.out.println(s);
        }
    }

    private static int gettotalofSubjects(ArrayList<StudentRegister> studentRegisters, int studentID, int subjectID) {
        for (var s : studentRegisters) {
            if (s.getStudent().getStudentID() == studentID && s.getSubject().getSubjectID() == subjectID) {
                return s.getTotalOfSubject();
            }
        }
        return 0;
    }

    private static Subject getSubject(ArrayList<Subject> subjects, int subjectID) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectID() == subjectID) {
                return subjects.get(i);
            }
        }
        return null;
    }

    private static Student getStudent(ArrayList<Student> students, int studentID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == studentID) {
                return students.get(i);
            }
        }
        return null;
    }

    private static boolean checkAlreadyRegist(ArrayList<StudentRegister> studentRegisters, int subjectID) {
        for (int i = 0; i < studentRegisters.size(); i++) {
            if (studentRegisters.get(i).getSubject().getSubjectID() == subjectID &&
                    studentRegisters.get(i).getState().toLowerCase() == "regist") {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRegister(ArrayList<StudentRegister> studentRegisters, int studentID) {
        int count = 0;
        for (var s : studentRegisters) {
            if (s.getStudent().getStudentID() == studentID) {
                count += s.getTotalOfSubject();
            }
        }
        if (count > 8) {
            return false;
        }
        return true;
    }


    private static void showInfoSubject(ArrayList<Subject> subjects) {
        System.out.println("Info of list of subjects: ");
        for (var s : subjects) {
            System.out.println(s);
        }
    }

    private static void showInfoStudent(ArrayList<Student> students) {
        System.out.println("Info of list of students: ");
        for (var s : students) {
            System.out.println(s);
        }
    }

    /*
        Viết menu thực hành chức năng thứ 5:
        B0: đọc ra danh sách student,subject,thông tin quản lý đăng kí
        B1: chọn một student từ danh sách đẻ cho phép đăng kí.Nếu đã đăng kí đủ số môn(>=8) thì không cho đăng kí nữa
        B2: chọn một subject từ danh sách , nếu môn đó đã đăng kí rồi thì không đăng kí được, yêu cầu đăng kí môn khác
        hoặc bỏ đăng kí môn đó
        B3: Nếu được đăng kí tiếp thì thực hiện đăng kí, tình trạng đăng kí
        B4: cập nhật lại file quản lý
        B5: In ra màn hình
     */
}
