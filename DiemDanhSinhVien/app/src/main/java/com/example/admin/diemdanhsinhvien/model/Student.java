package com.example.admin.diemdanhsinhvien.model;

/**
 * Created by Admin on 12/19/2017.
 */

public class Student {
    private int id;
    private String mssv;
    private String hoten;
    private String lop;
    private int diemdanh;

    public Student(int id, String mssv, String hoten, String lop, int diemdanh) {
        this.id = id;
        this.mssv = mssv;
        this.hoten = hoten;
        this.lop = lop;
        this.diemdanh = diemdanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getDiemdanh() {
        return diemdanh;
    }

    public void setDiemdanh(int diemdanh) {
        this.diemdanh = diemdanh;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", mssv='" + mssv + '\'' +
                ", hoten=" + hoten +
                ", lop='" + lop + '\'' +
                ", diemdanh=" + diemdanh+
                '}';
    }
}
