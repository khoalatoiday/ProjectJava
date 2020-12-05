package model;

public class Student {
    private static int id = 10000000;
    private int studentID;
    private String fullname;
    private String address;
    private String phonenumber;

    public Student() {
    }

    public Student(int studentID, String fullname, String address, String phonenumber) {
        if (studentID == 0) { // yêu cầu mã số sinh viên có 8 chữ số tăng dần
            this.studentID = id++;
        } else {
            this.studentID = studentID;
        }
        this.fullname = fullname;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Student.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
