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
public class HoaDonDichVu {
    private String maHoaDonDichVu;
    private String maNhanVien;
    private String tenKhachHang;
    private Date ngayNhap;


    @Override
    public String toString(){
        return this.maHoaDonDichVu;
    }

    public String getMaHoaDonDichVu() {
        return maHoaDonDichVu;
    }

    public void setMaHoaDonDichVu(String maHoaDonDichVu) {
        this.maHoaDonDichVu = maHoaDonDichVu;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
   
    
}
