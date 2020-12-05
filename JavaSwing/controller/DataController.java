package java_ptit.HW3.bai2.controller;

import java_ptit.HW3.bai2.model.MatHang;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    public void openFileToWrite(String fileName)  {

        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void closeFileAfterWrite(String fileName)  {


        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeInfoToFile(MatHang matHang, String fileName) throws IOException {
        openFileToWrite(fileName);
        printWriter.println(matHang.getMaHang() + "\n" + matHang.getTen()+ "\n" + matHang.getNhom()+
                "\n" + matHang.getGiaBan() );
        closeFileAfterWrite(fileName);
    }

    public void openFiletoRead(String fileName){
        try {
            File file = new File(fileName); // tạo mới một file nếu chưa tồn tại
            if (!file.exists()) {
                file.createNewFile();
            }
            scanner = new Scanner(Paths.get(fileName), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName){
        try{
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<MatHang> readInfofromFile(String fileName) throws IOException {
        openFiletoRead(fileName);
        ArrayList<MatHang> mhs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            int i = 1;
            String data = "";
            while (i <= 4) {
                String s = scanner.nextLine();
                s = s.trim();
                data += s;
                if (i < 4) {
                    data += "|";
                }
                i++;
            }
            data=data.trim();
            data=data.replaceAll("\\s+", " ");
            String[] datas = data.split("\\|");

            MatHang mh = new MatHang(Integer.parseInt(datas[0]),datas[1],datas[2],Double.parseDouble(datas[3]));
            mhs.add(mh);
        }
        closeFileAfterRead(fileName);
        return mhs;
    }


}
