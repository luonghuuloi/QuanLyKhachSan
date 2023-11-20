/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;


import com.poly.entity.DoanhThuHangNgay;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DoanhThuHangNgayDAO {
     public void insert(DoanhThuHangNgay model) {
        String sql = "INSERT INTO DoanhThuHangNgay (MaDTN, MaNV, TongTienPhong, TongTienDV, NgayNhap, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaDoanhThuNgay(),
                model.getMaNhanVien(),
                model.getTongTienPhong(),
                model.getTongTienDichVu(),
                model.getNgayNhap(),
                model.getGhiChu());
    }

    public void update(DoanhThuHangNgay model) {
        String sql = "UPDATE DoanhThuHangNgay SET MaNV=?, TongTienPhong=?, TongTienDV=?, NgayNhap=?, GhiChu=? WHERE MaDTN=?";
        JDBCHelper.executeUpdate(sql,              
                model.getMaNhanVien(),
                model.getTongTienPhong(),
                model.getTongTienDichVu(),
                model.getNgayNhap(),
                model.getGhiChu(),
                model.getMaDoanhThuNgay());
    }

    public void delete(String MaDTN) {
        String sql = "DELETE FROM DoanhThuHangNgay WHERE MaDTN=?";
        JDBCHelper.executeUpdate(sql, MaDTN);
    }

    public List<DoanhThuHangNgay> select() {
        String sql = "SELECT * FROM DoanhThuHangNgay";
        return select(sql);
    }
    
    public DoanhThuHangNgay findById(String madtn) {
        String sql = "SELECT * FROM DoanhThuHangNgay WHERE MaDTN=?";
        List<DoanhThuHangNgay> list = select(sql, madtn);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<DoanhThuHangNgay> select(String sql, Object... args) {
        List<DoanhThuHangNgay> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DoanhThuHangNgay model = readFromResultSet(rs);
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

    private DoanhThuHangNgay readFromResultSet(ResultSet rs) throws SQLException {
        DoanhThuHangNgay model = new DoanhThuHangNgay();
        model.setMaDoanhThuNgay(rs.getString("MaDTN"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTongTienPhong(rs.getDouble("TongTienPhong"));
        model.setTongTienDichVu(rs.getDouble("TongTienDV"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
