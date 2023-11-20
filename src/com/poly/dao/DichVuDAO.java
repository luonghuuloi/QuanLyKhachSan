/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.DichVu;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DichVuDAO {
     public void insert(DichVu model) {
        String sql = "INSERT INTO DichVu (MaDichVu, TenDichVu, DonGia) VALUES (?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaDichVu(),
                model.getTenDichVu(),
                model.getDonGia());
    }

    public void update(DichVu model) {
        String sql = "UPDATE DichVu SET TenDichVu=?, DonGia=? WHERE MaDichVu=?";
        JDBCHelper.executeUpdate(sql,               
                model.getTenDichVu(),
                model.getDonGia(),
                model.getMaDichVu());
    }

    public void delete(String MaDichVu) {
        String sql = "DELETE FROM DichVu WHERE MaDichVu=?";
        JDBCHelper.executeUpdate(sql, MaDichVu);
    }

    public List<DichVu> select() {
        String sql = "SELECT * FROM DichVu";
        return select(sql);
    }

    public DichVu findById(String macl) {
        String sql = "SELECT * FROM DichVu WHERE MaDichVu=?";
        List<DichVu> list = select(sql, macl);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<DichVu> select(String sql, Object... args) {
        List<DichVu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DichVu model = readFromResultSet(rs);
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

    private DichVu readFromResultSet(ResultSet rs) throws SQLException {
        DichVu model = new DichVu();
        model.setMaDichVu(rs.getString("MaDichVu"));
        model.setTenDichVu(rs.getString("TenDichVu"));
        model.setDonGia(rs.getDouble("DonGia"));
        return model;
    }
}
