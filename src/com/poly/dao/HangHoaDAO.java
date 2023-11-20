/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.HangHoa;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HangHoaDAO {
     public void insert(HangHoa model) {
        String sql = "INSERT INTO HangHoa (MaHang, MaNV, TenHang, DVT, SoLuong, DonGia, NgayNhap) VALUES (?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaHang(),
                model.getMaNhanVien(),
                model.getTenHang(),
                model.getDVT(),
                model.getSoLuong(),
                model.getDonGia(),
                model.getNgayNhap());
    }

    public void update(HangHoa model) {
        String sql = "UPDATE HangHoa SET MaNV=?, TenHang=?, DVT=?, SoLuong=?, DonGia=?, NgayNhap=? WHERE MaHang=?";
        JDBCHelper.executeUpdate(sql,
               model.getMaNhanVien(),
                model.getTenHang(),
                model.getDVT(),
                model.getSoLuong(),
                model.getDonGia(),
                model.getNgayNhap(),
                model.getMaHang());
    }

    public void delete(String MaHang) {
        String sql = "DELETE FROM HangHoa WHERE MaHang=?";
        JDBCHelper.executeUpdate(sql, MaHang);
    }

    public List<HangHoa> select() {
        String sql = "SELECT * FROM HangHoa";
        return select(sql);
    }
    
    public HangHoa findById(String mahang) {
        String sql = "SELECT * FROM HangHoa WHERE MaHang=?";
        List<HangHoa> list = select(sql, mahang);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HangHoa> select(String sql, Object... args) {
        List<HangHoa> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HangHoa model = readFromResultSet(rs);
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

    private HangHoa readFromResultSet(ResultSet rs) throws SQLException {
        HangHoa model = new HangHoa();
        model.setMaHang(rs.getString("MaHang"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTenHang(rs.getString("TenHang"));
        model.setDVT(rs.getString("DVT"));
        model.setSoLuong(rs.getDouble("SoLuong"));
        model.setDonGia(rs.getDouble("DonGia"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        return model;
    }
    
     
      public List<Object[]> thongKeHangHoa() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaHang, TenHang, SoLuong * DonGia AS N'Thành Tiền', NgayNhap FROM HangHoa";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaHang"),
                        rs.getString("TenHang"),
                        rs.getDouble("Thành Tiền"),
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
