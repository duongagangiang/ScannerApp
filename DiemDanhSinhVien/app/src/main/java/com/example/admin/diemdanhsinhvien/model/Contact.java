package com.example.admin.diemdanhsinhvien.model;

/**
 * Created by Admin on 1/4/2018.
 */

public class Contact {
    private String mssv;
    private int trangThai_DongBo;

    public Contact(String mssv, int trangThai_DongBo) {
        this.mssv = mssv;
        this.trangThai_DongBo = trangThai_DongBo;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public int getTrangThai_DongBo() {
        return trangThai_DongBo;
    }

    public void setTrangThai_DongBo(int trangThai_DongBo) {
        this.trangThai_DongBo = trangThai_DongBo;
    }
}
