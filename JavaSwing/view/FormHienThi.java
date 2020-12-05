package java_ptit.HW3.bai2.view;

import java_ptit.HW3.bai2.controller.DataController;
import java_ptit.HW3.bai2.model.MatHang;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FormHienThi {



    public static void main(String[] args) throws IOException {
        DataController controller = new DataController();
        JFrame f = new JFrame("Hien Thi");
        ArrayList<MatHang> mhs = new ArrayList<>();
        mhs = controller.readInfofromFile("MH.TXT");
        String[] strings = {"ID","Ten","Nhom","Gia Ban"};
        String[][] inputs = new String[mhs.size()][4];
        for(int i=0;i<mhs.size();i++){
            inputs[i][0]= String.valueOf(mhs.get(i).getMaHang());
            inputs[i][1]=  String.valueOf(mhs.get(i).getTen());
            inputs[i][2]= String.valueOf(mhs.get(i).getNhom());
            inputs[i][3] = String.valueOf(mhs.get(i).getGiaBan());
        }
        JTable table = new JTable(inputs,strings);
        table.setBounds(100,100,300,200);
        JScrollPane sp = new JScrollPane(table);
        f.add(sp);
        f.setSize(400, 400);
        f.setVisible(true);
    }
}
