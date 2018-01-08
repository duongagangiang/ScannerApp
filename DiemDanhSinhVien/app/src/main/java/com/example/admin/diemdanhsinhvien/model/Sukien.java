package com.example.admin.diemdanhsinhvien.model;

import java.io.Serializable;

/**
 * Created by Admin on 12/19/2017.
 */

public class Sukien implements Serializable{
    private int id;
    private String tensukien;
    private String thoigian;
    private String diadiem;
    private int soluong;

    public Sukien() {
    }

    public Sukien(int id, String tensukien, String thoigian, String diadiem, int soluong) {
        this.id=id;
        this.tensukien = tensukien;
        this.thoigian = thoigian;
        this.diadiem = diadiem;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensukien() {
        return tensukien;
    }

    public void setTensukien(String tensukien) {
        this.tensukien = tensukien;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    @Override
    public String toString() {
        return "Sukien{" +
                "id=" + id +
                ", tensukien='" + tensukien + '\'' +
                ", thoigian=" + thoigian +
                ", diadiem='" + diadiem + '\'' +
                ", soluong='" +  + '\'' +
                '}';
    }
}
