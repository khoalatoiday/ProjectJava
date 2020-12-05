package controller;

import model.Student;
import model.StudentRegister;

import java.util.ArrayList;

public class UltilityController {
    public ArrayList<StudentRegister> updateSRs(ArrayList<StudentRegister>list,StudentRegister studentRegister){
        boolean isUpdate=false;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getStudent().getStudentID()==studentRegister.getStudent().getStudentID()
            && list.get(i).getSubject().getSubjectID()== studentRegister.getSubject().getSubjectID()){
                list.set(i,studentRegister);
                isUpdate=true;
                break;
            }
        }
        if(!isUpdate){
            list.add(studentRegister);
        }
        return list;
    }

    public ArrayList<StudentRegister> sortByStudentName(ArrayList<StudentRegister> studentRegisters){
        for(int i=0;i<studentRegisters.size();i++){
            for(int j=studentRegisters.size()-1;j>i;j--){
                StudentRegister st1=studentRegisters.get(j);
                StudentRegister st2= studentRegisters.get(j-1);
                String[] name1= st1.getStudent().getFullname().split("\\s");
                String[] name2= st2.getStudent().getFullname().split("\\s");
                if(name1[name1.length-1].compareTo(name2[name2.length-1])<0){
                    studentRegisters.set(j,st2);
                    studentRegisters.set(j-1,st1);
                }
            }
        }
        return studentRegisters;
    }

    public ArrayList<StudentRegister> sortByTime(ArrayList<StudentRegister> studentRegisters){
        for(int i=0;i<studentRegisters.size();i++){
            for(int j=studentRegisters.size()-1;j>i;j--){
                StudentRegister st2= studentRegisters.get(j);
                StudentRegister st1= studentRegisters.get(j-1);
                if(st1.getTime().compareTo(st2.getTime())>0){
                    studentRegisters.set(j,st1);
                    studentRegisters.set(j-1,st2);
                }
            }
        }
        return studentRegisters;
    }


}
