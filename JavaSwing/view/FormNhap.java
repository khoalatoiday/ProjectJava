package java_ptit.HW3.bai2.view;

import java_ptit.HW3.bai2.controller.DataController;
import java_ptit.HW3.bai2.model.MatHang;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FormNhap  {
    public static boolean isNumberic(String s){
        try{
            double a = Double.parseDouble(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    static JFrame f;
    public static void main(String[] args) {
        DataController controller = new DataController();
        JLabel l1,l2,l3; JTextArea ta1,ta3; JComboBox box; JButton b1;
        f = new JFrame("Nhap");
        l1 = new JLabel("Ten:"); l1.setBounds(50,50,80,20);
        ta1 = new JTextArea();    ta1.setBounds(130,50,150,20);
        l2 = new JLabel("Nhom:"); l2.setBounds(50,80,80,20);
        String[] nhoms = {"Hang tieu dung","Hang thoi trang","Dien tu- Dien Lanh"};
        box = new JComboBox(nhoms);
        box.setBounds(130,80,150,20);
        l3 = new JLabel("Gia Ban:"); l3.setBounds(50,110,80,20);
        ta3 = new JTextArea();    ta3.setBounds(130,110,150,20);
        b1 = new JButton("Luu File");   b1.setBounds(150,150,80,20);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data="";
                if(ta1.getText().equals("") || ta1.getText().equals(" ")){
                    JOptionPane.showMessageDialog(f,"Ten khong duoc de trong!Vui long nhap lai!");
                    data = "";
                }else if (!isNumberic(ta3.getText())){
                    JOptionPane.showMessageDialog(f,"Vui long nhap so!");
                    data = "";
                }else if(isNumberic(ta3.getText()) && Double.parseDouble(ta3.getText())<0.0){
                    JOptionPane.showMessageDialog(f,"Vui long nhap so nguyen duong!");
                    data = "";
                }else if(!ta1.getText().equals("") && !ta1.getText().equals(" ") &&
                        isNumberic(ta3.getText()) && Double.parseDouble(ta3.getText())>0.0){
                    data += ta1.getText() + "|" + box.getItemAt(box.getSelectedIndex()) +"|"+ ta3.getText();
                }
                data.trim();    data.replaceAll("\\s+"," ");
                String datas[] = data.split("\\|");
                try {
                    checkID(controller,"MH.TXT");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                MatHang mh = new MatHang(datas[0],datas[1],Double.parseDouble(datas[2]));
                try {
                    controller.writeInfoToFile(mh,"MH.TXT");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        f.add(l1);f.add(l2);f.add(l3); f.add(box);f.add(ta1); f.add(ta3); f.add(b1);
        f.setSize(400,400); f.setLayout(null);  f.setVisible(true);
    }
    private static void checkID(DataController controller, String filename) throws IOException {
        ArrayList<MatHang> matHangs = controller.readInfofromFile(filename);
        if(matHangs.size()!=0){
            MatHang.setCount(matHangs.get(matHangs.size()-1).getMaHang()+1);
        }
    }
}
