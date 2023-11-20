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
public class ChiPhiHangNgay {
    private String maChiPhiNgay;
    private String maNhanVien;
    private double tienSuaChua;
    private double tienDiLai;
    private double chiPhiKhac;
    private Date ngayNhap;
    private String ghiChu;

    public String getMaChiPhiNgay() {
        return maChiPhiNgay;
    }

    public void setMaChiPhiNgay(String maChiPhiNgay) {
        this.maChiPhiNgay = maChiPhiNgay;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public double getTienSuaChua() {
        return tienSuaChua;
    }

    public void setTienSuaChua(double tienSuaChua) {
        this.tienSuaChua = tienSuaChua;
    }

    public double getTienDiLai() {
        return tienDiLai;
    }

    public void setTienDiLai(double tienDiLai) {
        this.tienDiLai = tienDiLai;
    }

    public double getChiPhiKhac() {
        return chiPhiKhac;
    }

    public void setChiPhiKhac(double chiPhiKhac) {
        this.chiPhiKhac = chiPhiKhac;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    
}
