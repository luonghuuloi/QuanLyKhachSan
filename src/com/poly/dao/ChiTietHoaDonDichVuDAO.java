/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.ChiTietHoaDonDichVu;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChiTietHoaDonDichVuDAO {
      public void insert(ChiTietHoaDonDichVu model) {
        String sql = "INSERT INTO ChiTietHoaDonDichVu (MaHoaDonDV, MaDichVu, SoLuong, GhiChu) VALUES (?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaHoaDonDichVu(),
                model.getMaDichVu(),
                model.getSoLuong(),
                model.getGhiChu());
    }

    public List<ChiTietHoaDonDichVu> select() {
        String sql = "SELECT * FROM ChiTietHoaDonDichVu";
        return select(sql);
    }

    private List<ChiTietHoaDonDichVu> select(String sql, Object... args) {
        List<ChiTietHoaDonDichVu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietHoaDonDichVu model = readFromResultSet(rs);
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

    private ChiTietHoaDonDichVu readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietHoaDonDichVu model = new ChiTietHoaDonDichVu();
        model.setMaHoaDonDichVu(rs.getString("MaHoaDonDV"));
        model.setMaDichVu(rs.getString("MaDichVu"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
