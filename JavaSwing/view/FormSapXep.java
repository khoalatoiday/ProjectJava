package java_ptit.HW3.bai2.view;

import java_ptit.HW3.bai2.controller.DataController;
import java_ptit.HW3.bai2.model.MatHang;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FormSapXep {
    public static void main(String[] args) throws IOException {
        DataController controller = new DataController();
        ArrayList<MatHang> mhs = controller.readInfofromFile("MH.TXT");
        JFrame f = new JFrame("Sort");
        String[] strings= {"Hang tieu dung","Hang thoi trang","Dien tu- Dien Lanh"};
        JComboBox box = new JComboBox(strings);
        box.setBounds(150,300,200,30);
        String[] datas = {"ID","Ten","Nhom","Gia Ban"};

        String[][] inputs = new String[mhs.size()][4];
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<MatHang> matHangs = new ArrayList<>();
                String s = String.valueOf(box.getItemAt(box.getSelectedIndex()));
                for(int i = 0; i<mhs.size();i++){
                    if(mhs.get(i).getNhom().equals(s)){
                        matHangs.add(mhs.get(i));
                    }
                }
                Collections.sort(matHangs, new Comparator<MatHang>() {
                    @Override
                    public int compare(MatHang o1, MatHang o2) {
                        return (int) (-o1.getGiaBan() + o2.getGiaBan());
                    }
                });
                for(int i=0;i<matHangs.size();i++){
                    inputs[i][0]= String.valueOf(matHangs.get(i).getMaHang());
                    inputs[i][1]=  String.valueOf(matHangs.get(i).getTen());
                    inputs[i][2]= String.valueOf(matHangs.get(i).getNhom());
                    inputs[i][3] = String.valueOf(matHangs.get(i).getGiaBan());
                }

            }
        });


        JTable table = new JTable(inputs,datas);
        JScrollPane sp = new JScrollPane(table);
        f.add(box); f.add(sp);
        f.setSize(400,400); f.setVisible(true);
    }
}
