package model;

import java.util.Date;

public class StudentRegister {
    private Student student;
    private Subject subject;
    private Date time;
    private int totalOfSubject;
    private String state;

    public StudentRegister() {
    }


    public StudentRegister(Student student, Subject subject, Date time, int totalOfSubject, String state) {
        this.student = student;
        this.subject = subject;
        this.time = time;
        this.totalOfSubject = totalOfSubject;
        this.state = state;
    }

    public Student getStudent() {
        return student;
    }


    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTotalOfSubject() {
        return totalOfSubject;
    }

    public void setTotalOfSubject(int totalOfSubject) {
        this.totalOfSubject = totalOfSubject;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Override
//    public String toString() {
//        return "StudentRegister{" +
//                "student=" + student.getFullname() + " " + student.getStudentID()+" "+ student.getPhonenumber()
//                + " "+ student.getAddress()+
//                ", subject=" + subject.getSubjectID() + " "+ subject.getSubjectName()+ " "+ subject.getSubjectType()+
//                " " + subject.getTotalLesson()+
//                ", time=" + time +
//                ", totalOfSubject=" + totalOfSubject +
//                ", state='" + state + '\'' +
//                '}';
//    }
}
