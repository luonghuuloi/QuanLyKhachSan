/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;


import com.poly.entity.DoanhThuThang;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DoanhThuThangDAO {
     public void insert(DoanhThuThang model) {
        String sql = "INSERT INTO DoanhThuThang (MaDTT, MaNV, Thang, Nam, TongDoanhThuNgay, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaDoanhThuThang(),
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getTongDoanhThuNgay(),
                model.getGhiChu());
    }

    public void update(DoanhThuThang model) {
        String sql = "UPDATE DoanhThuThang SET MaNV=?, Thang=?, Nam=?, TongDoanhThuNgay=?, GhiChu=? WHERE MaDTT=?";
        JDBCHelper.executeUpdate(sql,              
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getTongDoanhThuNgay(),
                model.getGhiChu(),
                model.getMaDoanhThuThang());
    }

    public void delete(String MaDTT) {
        String sql = "DELETE FROM DoanhThuThang WHERE MaDTT=?";
        JDBCHelper.executeUpdate(sql, MaDTT);
    }

    public List<DoanhThuThang> select() {
        String sql = "SELECT * FROM DoanhThuThang";
        return select(sql);
    }
    
    public DoanhThuThang findById(String madtt) {
        String sql = "SELECT * FROM DoanhThuThang WHERE MaDTT=?";
        List<DoanhThuThang> list = select(sql, madtt);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<DoanhThuThang> select(String sql, Object... args) {
        List<DoanhThuThang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DoanhThuThang model = readFromResultSet(rs);
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

    private DoanhThuThang readFromResultSet(ResultSet rs) throws SQLException {
        DoanhThuThang model = new DoanhThuThang();
        model.setMaDoanhThuThang(rs.getString("MaDTT"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setThang(rs.getInt("Thang"));
        model.setNam(rs.getInt("Nam"));
        model.setTongDoanhThuNgay(rs.getDouble("TongDoanhThuNgay"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
