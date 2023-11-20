/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.HoaDonPhong;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HoaDonPhongDAO {
     public void insert(HoaDonPhong model) {
        String sql = "INSERT INTO HoaDonPhong (MaHoaDonPhong, MaPhong, MaNV, TenKhachHang, CMND, NgayThue, NgayTra, SoGio, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaHoaDonPhong(),                
                model.getMaPhong(),
                model.getMaNhanVien(),
                model.getTenKhachHang(),
                model.getCMND(),
                model.getNgayThue(),
                model.getNgayTra(),
                model.getSoGio(),
                model.getGhiChu());
    }

    public void update(HoaDonPhong model) {
        String sql = "UPDATE HoaDonPhong SET MaPhong=?, MaNV=?, TenKhachHang=?, CMND=?, NgayThue=?, NgayTra=?, SoGio=?, GhiChu=? WHERE MaHoaDonPhong=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaPhong(),
                model.getMaNhanVien(),
                model.getTenKhachHang(),
                model.getCMND(),
                model.getNgayThue(),
                model.getNgayTra(),
                model.getSoGio(),
                model.getGhiChu(),
                model.getMaHoaDonPhong());
    }

    public void delete(String MaHoaDonPhong) {
        String sql = "DELETE FROM HoaDonPhong WHERE MaHoaDonPhong=?";
        JDBCHelper.executeUpdate(sql, MaHoaDonPhong);
    }

    public List<HoaDonPhong> select() {
        String sql = "SELECT * FROM HoaDonPhong";
        return select(sql);
    }

    public HoaDonPhong findById(String mahoadonphong) {
        String sql = "SELECT * FROM HoaDonPhong WHERE MaHoaDonPhong=?";
        List<HoaDonPhong> list = select(sql, mahoadonphong);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDonPhong> select(String sql, Object... args) {
        List<HoaDonPhong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonPhong model = readFromResultSet(rs);
                    list.add(model);                  
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
        return list;
    }

    private HoaDonPhong readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonPhong model = new HoaDonPhong();
        model.setMaHoaDonPhong(rs.getString("MaHoaDonPhong"));
        model.setMaPhong(rs.getString("MaPhong"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTenKhachHang(rs.getString("TenKhachHang"));
        model.setCMND(rs.getString("CMND"));
        model.setNgayThue(rs.getDate("NgayThue"));
        model.setNgayTra(rs.getDate("NgayTra"));
        model.setSoGio(rs.getDouble("SoGio"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
    
//     public List<HoaDonPhong> maPhong() {
//        List<HoaDonPhong> list = new ArrayList<>();
//        try {
//            ResultSet rs = null;
//            try {
//                String sql = "SELECT MaPhong FROM HoaDonPhong";
//                rs = JDBCHelper.executeQuery(sql);
//                while (rs.next()) {
//                    HoaDonPhong model = readFromResultSetMaPhong(rs);
//                    list.add(model);
//                }
//            } finally {
//                rs.getStatement().getConnection().close();
//
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//
//        }
//        return list;
//    }
//     
//      private HoaDonPhong readFromResultSetMaPhong(ResultSet rs) throws SQLException {
//        HoaDonPhong model = new HoaDonPhong();
//        model.setMaPhong(rs.getString("MaPhong"));
//        return model;
//    }
}
