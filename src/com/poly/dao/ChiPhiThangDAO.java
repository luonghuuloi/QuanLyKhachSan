/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.ChiPhiThang;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChiPhiThangDAO {
     public void insert(ChiPhiThang model) {
        String sql = "INSERT INTO ChiPhiThang (MaCPT, MaNV, Thang, Nam, TongChiPhiNgay, TienDien, TienNuoc, TienLuong, TienThue, TienNhapHang, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaChiPhiThang(),
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getTongChiPhiNgay(),
                model.getTienDien(),
                model.getTienNuoc(),
                model.getTienLuong(),
                model.getTienThue(),
                model.getTienNhapHang(),
                model.getGhiChu());
    }

    public void update(ChiPhiThang model) {
        String sql = "UPDATE ChiPhiThang SET MaNV=?, Thang=?, Nam=?, TongChiPhiNgay=?, TienDien=?, TienNuoc=?, TienLuong=?, TienThue=?, TienNhapHang=?, GhiChu=? WHERE MaCPT=?";
        JDBCHelper.executeUpdate(sql,              
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getTongChiPhiNgay(),
                model.getTienDien(),
                model.getTienNuoc(),
                model.getTienLuong(),
                model.getTienThue(),
                model.getTienNhapHang(),
                model.getGhiChu(),
                model.getMaChiPhiThang());
    }

    public void delete(String MaCPT) {
        String sql = "DELETE FROM ChiPhiThang WHERE MaCPT=?";
        JDBCHelper.executeUpdate(sql, MaCPT);
    }

    public List<ChiPhiThang> select() {
        String sql = "SELECT * FROM ChiPhiThang";
        return select(sql);
    }
    
    public ChiPhiThang findById(String macpt) {
        String sql = "SELECT * FROM ChiPhiThang WHERE MaCPT=?";
        List<ChiPhiThang> list = select(sql, macpt);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChiPhiThang> select(String sql, Object... args) {
        List<ChiPhiThang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiPhiThang model = readFromResultSet(rs);
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

    private ChiPhiThang readFromResultSet(ResultSet rs) throws SQLException {
        ChiPhiThang model = new ChiPhiThang();
        model.setMaChiPhiThang(rs.getString("MaCPT"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setThang(rs.getInt("Thang"));
        model.setNam(rs.getInt("Nam"));
        model.setTongChiPhiNgay(rs.getDouble("TongChiPhiNgay"));
        model.setTienDien(rs.getDouble("TienDien"));
        model.setTienNuoc(rs.getDouble("TienNuoc"));
        model.setTienLuong(rs.getDouble("TienLuong"));
        model.setTienThue(rs.getDouble("TienThue"));
        model.setTienNhapHang(rs.getDouble("TienNhapHang"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
