/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.Luong;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class LuongDAO {
     public void insert(Luong model) {
        String sql = "INSERT INTO Luong (MaLuong, MaNV, Thang, Nam, LuongCoBan, PhuCap, SoNgayLamViec, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaLuong(),
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getLuongCoBan(),
                model.getPhuCap(),
                model.getSoNgayLamViec(),
                model.isTrangThai());
    }

    public void update(Luong model) {
        String sql = "UPDATE Luong SET MaNV=?, Thang=?, Nam=?, LuongCoBan=?, PhuCap=?, SoNgayLamViec=?, TrangThai=? WHERE MaLuong=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),
                model.getThang(),
                model.getNam(),
                model.getLuongCoBan(),
                model.getPhuCap(),
                model.getSoNgayLamViec(),
                model.isTrangThai(),
                model.getMaLuong());
    }

    public void delete(String MaLuong) {
        String sql = "DELETE FROM Luong WHERE MaLuong=?";
        JDBCHelper.executeUpdate(sql, MaLuong);
    }

    public List<Luong> select() {
        String sql = "SELECT * FROM Luong";
        return select(sql);
    }

    public Luong findById(String maluong) {
        String sql = "SELECT * FROM Luong WHERE MaLuong=?";
        List<Luong> list = select(sql, maluong);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<Luong> select(String sql, Object... args) {
        List<Luong> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Luong model = readFromResultSet(rs);
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

    private Luong readFromResultSet(ResultSet rs) throws SQLException {
        Luong model = new Luong();
        model.setMaLuong(rs.getString("MaLuong"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setThang(rs.getInt("Thang"));
        model.setNam(rs.getInt("Nam"));
        model.setLuongCoBan(rs.getDouble("LuongCoBan"));
        model.setPhuCap(rs.getDouble("PhuCap"));
        model.setSoNgayLamViec(rs.getDouble("SoNgayLamViec"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        return model;
    }
    
     public List<Object[]> thongKeTienLuong() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT Luong.MaLuong, Luong.MaNV, NhanVien.HoTen, TaiKhoan.ChucVu, Luong.Thang, Luong.Nam, ROUND (((Luong.LuongCoBan + Luong.PhuCap)/26 * Luong.SoNgayLamViec),-3) AS N'Tổng Tiền Lương', Luong.TrangThai \n" +
                              "FROM Luong INNER JOIN NhanVien ON Luong.MaNV = NhanVien.MaNV INNER JOIN TaiKhoan ON NhanVien.MaNV = TaiKhoan.MaNV WHERE Luong.TrangThai = 0";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaLuong"),
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("ChucVu"),
                        rs.getInt("Thang"),
                        rs.getInt("Nam"),
                        rs.getInt("Tổng Tiền Lương"),
                        rs.getBoolean("TrangThai") ? "Đã trả lương" : "Chưa trả lương"
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
