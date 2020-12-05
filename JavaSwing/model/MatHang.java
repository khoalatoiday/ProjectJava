package java_ptit.HW3.bai2.model;

import design.ID;

import java.util.Comparator;

public class MatHang {
    private static int count = 1;
    private int maHang;
    private String ten;
    private String nhom;
    private double giaBan;

    public MatHang() {
    }

    public MatHang(String ten, String nhom, double giaBan) {
        setMaHang(count++);
        this.ten = ten;
        this.nhom = nhom;
        this.giaBan = giaBan;
    }

    public MatHang(int maHang, String ten, String nhom, double giaBan) {
        if(maHang == -1){
            this.maHang = count++;
        }else{
            this.maHang = maHang;
        }
        this.ten = ten;
        this.nhom = nhom;
        this.giaBan = giaBan;
    }

    public static void setCount(int count) {
        MatHang.count = count;
    }

    public int getMaHang() {
        return maHang;
    }

    public String getTen() {
        return ten;
    }

    public String getNhom() {
        return nhom;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

//    @Override
//    public String toString() {
//        return "MatHang{" +
//                "maHang=" + maHang +
//                ", ten='" + ten + '\'' +
//                ", nhom='" + nhom + '\'' +
//                ", giaBan=" + giaBan +
//                '}';
//    }
}

