/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.GiaPhong;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class GiaPhongDAO {
     public void insert(GiaPhong model) {
        String sql = "INSERT INTO GiaPhong (MaPhong, LoaiPhong, GiaTheoGio, GiaTheoNgay, TrangThai) VALUES (?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaPhong(),                
                model.getLoaiPhong(),
                model.getGiaTheoGio(),
                model.getGiaTheoNgay(),       
                model.getTrangThai());
    }

    public void update(GiaPhong model) {
        String sql = "UPDATE GiaPhong SET LoaiPhong=?, GiaTheoGio=?, GiaTheoNgay=?, TrangThai=? WHERE MaPhong=?";
        JDBCHelper.executeUpdate(sql,
                model.getLoaiPhong(),
                model.getGiaTheoGio(),
                model.getGiaTheoNgay(),       
                model.getTrangThai(),
                model.getMaPhong());
    }

    public void delete(String MaPhong) {
        String sql = "DELETE FROM GiaPhong WHERE MaPhong=?";
        JDBCHelper.executeUpdate(sql, MaPhong);
    }

    public List<GiaPhong> select() {
        String sql = "SELECT * FROM GiaPhong";
        return select(sql);
    }

    public GiaPhong findById(String maphong) {
        String sql = "SELECT * FROM GiaPhong WHERE MaPhong=?";
        List<GiaPhong> list = select(sql, maphong);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<GiaPhong> select(String sql, Object... args) {
        List<GiaPhong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    GiaPhong model = readFromResultSet(rs);
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

    private GiaPhong readFromResultSet(ResultSet rs) throws SQLException {
        GiaPhong model = new GiaPhong();
        model.setMaPhong(rs.getString("MaPhong"));
        model.setLoaiPhong(rs.getString("LoaiPhong"));
        model.setGiaTheoGio(rs.getFloat("GiaTheoGio"));
        model.setGiaTheoNgay(rs.getFloat("GiaTheoNgay"));
        model.setTrangThai(rs.getInt("TrangThai"));
        return model;
    }
    
     public List<GiaPhong> layMaPhong() {
        List<GiaPhong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaPhong FROM GiaPhong";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    GiaPhong model = readFromResultSetMaPhong(rs);
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
     
      private GiaPhong readFromResultSetMaPhong(ResultSet rs) throws SQLException {
        GiaPhong model = new GiaPhong();
        model.setMaPhong(rs.getString("MaPhong"));
        return model;
    }
      
     public List<GiaPhong> layTrangThai() {
        List<GiaPhong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT TrangThai FROM GiaPhong";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    GiaPhong model = readFromResultSetTrangThai(rs);
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
     
      private GiaPhong readFromResultSetTrangThai(ResultSet rs) throws SQLException {
        GiaPhong model = new GiaPhong();
        model.setTrangThai(rs.getInt("TrangThai"));
        return model;
    }
}
