/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.ChiTietPhieuXuatHang;
import com.poly.entity.XuatHang;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChiTietPhieuXuatHangDAO {
     public void insert(ChiTietPhieuXuatHang model) {
        String sql = "INSERT INTO ChiTietPhieuXuatHang (MaXuatHang, MaHang, SoLuong) VALUES (?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaXuatHang(),              
                model.getMaHang(),                            
                model.getSoLuong());
    }


    public List<ChiTietPhieuXuatHang> select() {
        String sql = "SELECT * FROM ChiTietPhieuXuatHang";
        return select(sql);
    }
    
    private List<ChiTietPhieuXuatHang> select(String sql, Object... args) {
        List<ChiTietPhieuXuatHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietPhieuXuatHang model = readFromResultSet(rs);
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

    private ChiTietPhieuXuatHang readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietPhieuXuatHang model = new ChiTietPhieuXuatHang();
        model.setMaXuatHang(rs.getString("MaXuatHang"));
        model.setMaHang(rs.getString("MaHang"));     
        model.setSoLuong(rs.getDouble("SoLuong"));
        return model;
    }
    
     
      public List<Object[]> thongKeXuatHangHoa() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT HangHoa.MaHang, HangHoa.TenHang, HangHoa.DonGia,HangHoa.DVT,HangHoa.SoLuong - XuatHang.SoLuong AS N'Số Lượng Còn Lại' FROM HangHoa INNER JOIN XuatHang ON HangHoa.MaHang = XuatHang.MaHang";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaHang"),
                        rs.getString("TenHang"),
                        rs.getDouble("DonGia"),
                        rs.getString("DVT"),
                        rs.getDouble("Số Lượng Còn Lại")
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
