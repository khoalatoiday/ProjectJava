package controller;

import model.Student;
import model.StudentRegister;
import model.Subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class dataController {
    /*
        Ghi thông tin Subject vào file
        Ghi thông tin Student vào file
        Ghi thông tin StudentRegister vào file
     */

    FileWriter fileWriter; // 3 cái này để ghi file
    BufferedWriter bufferedWriter;
    PrintWriter printWriter;
    Scanner scanner; // dùng để đọc file


    public void openFiletoWrite(String filename) {
        try {
            fileWriter = new FileWriter(filename, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileafterWrite(String filename) {
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeStudenttoFile(Student student, String filename) {
        openFiletoWrite(filename);
        // sử dụng printWriter để viết
        //int studentID, String fullname, String address, String phonenumber
        printWriter.println(student.getStudentID() + "|" + student.getFullname() + "|" +
                student.getAddress() + "|" + student.getPhonenumber());
        closeFileafterWrite(filename);
    }

    public void writeSubjecttoFile(Subject subject, String filename) {
        openFiletoWrite(filename);
        // sử dụng printWriter để viết
        //int subjectID, String subjectName, int totalLesson, String subjectType
        printWriter.println(subject.getSubjectID() + "|" + subject.getSubjectName() + "|" +
                subject.getTotalLesson() + "|" + subject.getSubjectType());
        closeFileafterWrite(filename);
    }

    public void writeStudentRegistertoFile(StudentRegister studentRegister, String filename) {
        openFiletoWrite(filename);
        // sử dụng printWriter để viết
        //Student student, Subject subject, Date time, int totalOfSubject, String state
        printWriter.println(studentRegister.getStudent().getStudentID() + "|" +
                studentRegister.getSubject().getSubjectID() + "|" + studentRegister.getTime() +"|"+
                studentRegister.getTotalOfSubject() + "|" + studentRegister.getState());
        closeFileafterWrite(filename);
    }

    //đọc file: dùng Scanner
    public void openFiletoRead(String filename) {
        try {
            File file = new File(filename); // tạo file
            if (!file.exists()) {
                file.createNewFile();
            }
            scanner = new Scanner(Paths.get(filename), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String filename) {
        try {
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* đọc thông tin Student từ file:
    1.chuyển dữ liệu thành đối tượng Student
    2.Thêm đối  tượng vào danh sách Student
    3.Trả về danh sách Student
     */

    public ArrayList<Student> readStudentFromFile(String filename) {
        openFiletoRead(filename);
        ArrayList<Student> students = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            Student student = createStudentFromData(data);
            students.add(student);
        }
        closeFileAfterRead(filename);
        return students;
    }

    private Student createStudentFromData(String data) {
        String[] datas = data.split("\\|");
        //int studentID, String fullname, String address, String phonenumber
        //student.getStudentID()+"|"+student.getFullname()+"|"+student.getAddress()+"|"+ student.getPhonenumber()
        Student student = new Student(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3]);
        return student;
    }

    public ArrayList<Subject> readSubjectFromFile(String filename) {
        openFiletoRead(filename);
        ArrayList<Subject> subjects = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            Subject subject = createSubjectFromData(data);
            subjects.add(subject);
        }
        closeFileAfterRead(filename);
        return subjects;
    }

    private Subject createSubjectFromData(String data) {
        String[] datas = data.split("\\|");
        //int subjectID, String subjectName, int totalLesson, String subjectType
        //        printWriter.println(subject.getSubjectID()+"|"+subject.getSubjectName()+"|"+
        //                subject.getTotalLesson()+"|"+ subject.getSubjectType());
        Subject subject = new Subject(Integer.parseInt(datas[0]), datas[1], Integer.parseInt(datas[2]), datas[3]);
        return subject;
    }

    public ArrayList<StudentRegister> readStudentRegisterFromFile(String filename) {
        var students = readStudentFromFile("STUDENT.DATA");
        var subjects = readSubjectFromFile("SUBJECT.DATA");
        openFiletoRead(filename);
        ArrayList<StudentRegister> studentRegisters = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            StudentRegister studentRegister = createStudentRegisterFromData(data, students, subjects);
            studentRegisters.add(studentRegister);
        }
        closeFileAfterRead(filename);
        return studentRegisters;
    }

    private StudentRegister createStudentRegisterFromData(String data, ArrayList<Student> students,
                                                          ArrayList<Subject> subjects) {
        //Student student, Subject subject, Date time, int totalOfSubject, String state
        // studentRegister.getStudent().getStudentID() + "|" +
        // studentRegister.getSubject().getSubjectID() + "|" + studentRegister.getTime()
        // studentRegister.getTotalOfSubject()+"|" +studentRegister.getState())
        String[] datas = data.split("\\|");
        StudentRegister studentRegister = new StudentRegister(getStudent(students, Integer.parseInt(datas[0])),
                getSubject(subjects, Integer.parseInt(datas[1])), Date.valueOf(datas[2])
                , Integer.parseInt(datas[3]), datas[4]);

        return studentRegister;
    }

    private Subject getSubject(ArrayList<Subject> subjects, int subjectID) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectID() == subjectID) {
                return subjects.get(i);
            }
        }
        return null;
    }

    private Student getStudent(ArrayList<Student> students, int studentID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID() == studentID) {
                return students.get(i);
            }
        }
        return null;
    }

    public void updateSrFile(ArrayList<StudentRegister> studentRegisters, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            file.delete();
        }
       openFiletoWrite(filename);
        for(var s: studentRegisters){
            printWriter.println(s.getStudent().getStudentID() + "|" +
                    s.getSubject().getSubjectID() + "|" + s.getTime() +
                    s.getTotalOfSubject() + "|" + s.getState());
        }
        closeFileafterWrite(filename);
    }
}
