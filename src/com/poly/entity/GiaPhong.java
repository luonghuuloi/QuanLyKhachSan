/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.entity;



/**
 *
 * @author ADMIN
 */
public class GiaPhong {
    private String maPhong;
    private String loaiPhong;
    private float giaTheoGio;
    private float giaTheoNgay;
    private int trangThai ;

    
    @Override
     public String toString(){
        return this.maPhong;
    }
    public GiaPhong(){
        
    }
    public GiaPhong(String maPhong, String loaiPhong, float giaTheoGio, float giaTheoNgay, int trangThai) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.giaTheoGio = giaTheoGio;
        this.giaTheoNgay = giaTheoNgay;
        this.trangThai = trangThai;
    }

     
     
     
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public double getGiaTheoGio() {
        return giaTheoGio;
    }

    public void setGiaTheoGio(float giaTheoGio) {
        this.giaTheoGio = giaTheoGio;
    }

    public double getGiaTheoNgay() {
        return giaTheoNgay;
    }

    public void setGiaTheoNgay(float giaTheoNgay) {
        this.giaTheoNgay = giaTheoNgay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

   

     
   
}
