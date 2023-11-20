/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.HoaDonDichVu;
import com.poly.utils.JDBCHelper;
import com.poly.utils.XDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HoaDonDichVuDAO {
      public void insert(HoaDonDichVu model) {
        String sql = "INSERT INTO HoaDonDichVu (MaHoaDonDV, MaNV, TenKhachHang, NgayNhap) VALUES (?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaHoaDonDichVu(),
                model.getMaNhanVien(),
                model.getTenKhachHang(),
                model.getNgayNhap());
    }

    public void update(HoaDonDichVu model) {
        String sql = "UPDATE HoaDonDichVu SET MaNV=?, TenKhachHang=?, NgayNhap=? WHERE MaHoaDonDV=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),
                model.getTenKhachHang(),
                model.getNgayNhap(),
                model.getMaHoaDonDichVu());
    }

    public void delete(String MaHoaDonDV) {
        String sql = "DELETE FROM HoaDonDichVu WHERE MaHoaDonDV=?";
        JDBCHelper.executeUpdate(sql, MaHoaDonDV);
    }

    public List<HoaDonDichVu> select() {
        String sql = "SELECT * FROM HoaDonDichVu";
        return select(sql);
    }

    public HoaDonDichVu findById(String mahddv) {
        String sql = "SELECT * FROM HoaDonDichVu WHERE MaHoaDonDV=?";
        List<HoaDonDichVu> list = select(sql, mahddv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDonDichVu> select(String sql, Object... args) {
        List<HoaDonDichVu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonDichVu model = readFromResultSet(rs);
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

    private HoaDonDichVu readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonDichVu model = new HoaDonDichVu();
        model.setMaHoaDonDichVu(rs.getString("MaHoaDonDV"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTenKhachHang(rs.getString("TenKhachHang"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        return model;
    }
    
  
}
