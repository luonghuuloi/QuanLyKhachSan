/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.ChiPhiHangNgay;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChiPhiHangNgayDAO {
     public void insert(ChiPhiHangNgay model) {
        String sql = "INSERT INTO ChiPhiHangNgay (MaCPN, MaNV, TienSuaChua, TienDiLai, ChiPhiKhac, NgayNhap, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaChiPhiNgay(),
                model.getMaNhanVien(),
                model.getTienSuaChua(),
                model.getTienDiLai(),
                model.getChiPhiKhac(),
                model.getNgayNhap(),
                model.getGhiChu());
    }

    public void update(ChiPhiHangNgay model) {
        String sql = "UPDATE ChiPhiHangNgay SET MaNV=?, TienSuaChua=?, TienDiLai=?, ChiPhiKhac=?, NgayNhap=?, GhiChu=? WHERE MaCPN=?";
        JDBCHelper.executeUpdate(sql,              
                model.getMaNhanVien(),
                model.getTienSuaChua(),
                model.getTienDiLai(),
                model.getChiPhiKhac(),
                model.getNgayNhap(),
                model.getGhiChu(),
                model.getMaChiPhiNgay());
    }

    public void delete(String MaCPN) {
        String sql = "DELETE FROM ChiPhiHangNgay WHERE MaCPN=?";
        JDBCHelper.executeUpdate(sql, MaCPN);
    }

    public List<ChiPhiHangNgay> select() {
        String sql = "SELECT * FROM ChiPhiHangNgay";
        return select(sql);
    }
    
    public ChiPhiHangNgay findById(String macpn) {
        String sql = "SELECT * FROM ChiPhiHangNgay WHERE MaCPN=?";
        List<ChiPhiHangNgay> list = select(sql, macpn);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChiPhiHangNgay> select(String sql, Object... args) {
        List<ChiPhiHangNgay> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiPhiHangNgay model = readFromResultSet(rs);
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

    private ChiPhiHangNgay readFromResultSet(ResultSet rs) throws SQLException {
        ChiPhiHangNgay model = new ChiPhiHangNgay();
        model.setMaChiPhiNgay(rs.getString("MaCPN"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTienSuaChua(rs.getDouble("TienSuaChua"));
        model.setTienDiLai(rs.getDouble("TienDiLai"));
        model.setChiPhiKhac(rs.getDouble("ChiPhiKhac"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
    
     public List<Object[]> danhSachChiPhiNgay() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT ChiPhiHangNgay.MaCPN, ChiPhiHangNgay.TienSuaChua + ChiPhiHangNgay.TienDiLai + ChiPhiHangNgay.ChiPhiKhac AS N'Tổng Chi Phí Trong Ngày', NhanVien.HoTen, ChiPhiHangNgay.NgayNhap \n" +
                "FROM ChiPhiHangNgay INNER JOIN NhanVien ON ChiPhiHangNgay.MaNV = NhanVien.MaNV";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaCPN"),
                        rs.getDouble("Tổng Chi Phí Trong Ngày"),
                        rs.getString("HoTen"),
                        rs.getDate("NgayNhap")
                    };
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
}
