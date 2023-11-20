/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.CaLam;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class CaLamDAO {
     public void insert(CaLam model) {
        String sql = "INSERT INTO CaLam (MaCaLam, MaNV, NgayLam, Ca, NhanCa) VALUES (?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaCaLam(),
                model.getMaNhanVien(),
                model.getNgayLam(),
                model.getCa(),
                model.isNhanCa());
    }

    public void update(CaLam model) {
        String sql = "UPDATE CaLam SET MaNV=?, NgayLam=?, Ca=?, NhanCa=? WHERE MaCaLam=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),
                model.getNgayLam(),
                model.getCa(),      
                model.isNhanCa(),
                model.getMaCaLam());
    }

    public void delete(String MaCaLam) {
        String sql = "DELETE FROM CaLam WHERE MaCaLam=?";
        JDBCHelper.executeUpdate(sql, MaCaLam);
    }

    public List<CaLam> select() {
        String sql = "SELECT * FROM CaLam";
        return select(sql);
    }

    public CaLam findById(String macl) {
        String sql = "SELECT * FROM CaLam WHERE MaCaLam=?";
        List<CaLam> list = select(sql, macl);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<CaLam> select(String sql, Object... args) {
        List<CaLam> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    CaLam model = readFromResultSet(rs);
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

    private CaLam readFromResultSet(ResultSet rs) throws SQLException {
        CaLam model = new CaLam();
        model.setMaCaLam(rs.getString("MaCaLam"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setNgayLam(rs.getDate("NgayLam"));
        model.setCa(rs.getInt("Ca"));
        model.setNhanCa(rs.getBoolean("NhanCa"));
        return model;
    }
    
    
}
