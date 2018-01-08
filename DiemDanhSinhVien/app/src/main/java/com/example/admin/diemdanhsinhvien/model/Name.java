package com.example.admin.diemdanhsinhvien.model;

/**
 * Created by HuyTran on 1/4/2018.
 */

public class Name {
    private String mssv;
    private String idSuKien;
    private int status;

    public Name(String mssv, String idSuKien, int status) {
        this.mssv = mssv;
        this.idSuKien = idSuKien;
        this.status = status;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getIdSuKien() {
        return idSuKien;
    }

    public void setIdSuKien(String idSuKien) {
        this.idSuKien = idSuKien;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
