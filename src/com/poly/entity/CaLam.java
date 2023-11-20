/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.entity;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class CaLam {
    private String maCaLam;
    private String maNhanVien;
    private Date ngayLam;
    private int Ca;
    private boolean nhanCa;

    public String getMaCaLam() {
        return maCaLam;
    }

    public void setMaCaLam(String maCaLam) {
        this.maCaLam = maCaLam;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(Date ngayLam) {
        this.ngayLam = ngayLam;
    }

    public int getCa() {
        return Ca;
    }

    public void setCa(int Ca) {
        this.Ca = Ca;
    }

    public boolean isNhanCa() {
        return nhanCa;
    }

    public void setNhanCa(boolean nhanCa) {
        this.nhanCa = nhanCa;
    }
    

   
}
