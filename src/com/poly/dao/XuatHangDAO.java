/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

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
public class XuatHangDAO {
     public void insert(XuatHang model) {
        String sql = "INSERT INTO XuatHang (MaXuatHang, MaNV, NgayXuat) VALUES (?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaXuatHang(),              
                model.getMaNhanVien(),                            
                model.getNgayXuat());
    }

    public void update(XuatHang model) {
        String sql = "UPDATE XuatHang SET MaNV=?, NgayXuat=? WHERE MaXuatHang=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),                          
                model.getNgayXuat(),
                model.getMaXuatHang());
    }

    public void delete(String MaXuatHang) {
        String sql = "DELETE FROM XuatHang WHERE MaXuatHang=?";
        JDBCHelper.executeUpdate(sql, MaXuatHang);
    }

    public List<XuatHang> select() {
        String sql = "SELECT * FROM XuatHang";
        return select(sql);
    }
    
    public XuatHang findById(String maxuathang) {
        String sql = "SELECT * FROM XuatHang WHERE MaXuatHang=?";
        List<XuatHang> list = select(sql, maxuathang);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<XuatHang> select(String sql, Object... args) {
        List<XuatHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    XuatHang model = readFromResultSet(rs);
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

    private XuatHang readFromResultSet(ResultSet rs) throws SQLException {
        XuatHang model = new XuatHang();
        model.setMaXuatHang(rs.getString("MaXuatHang"));
        model.setMaNhanVien(rs.getString("MaNV"));     
        model.setNgayXuat(rs.getDate("NgayXuat"));
        return model;
    }
    
    
     public List<XuatHang> phieuChuaXuat() {
        List<XuatHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaXuatHang FROM XuatHang";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    XuatHang model = readFromResultSetMaPX(rs);
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
     
      private XuatHang readFromResultSetMaPX(ResultSet rs) throws SQLException {
        XuatHang model = new XuatHang();
        model.setMaXuatHang(rs.getString("MaXuatHang"));
        return model;
    }
    
     
      public List<Object[]> thongKeXuatHangHoa() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT HangHoa.MaHang, HangHoa.TenHang, HangHoa.DonGia,HangHoa.DVT,HangHoa.SoLuong - ChiTietPhieuXuatHang.SoLuong AS N'Số Lượng Còn Lại' \n" +
                "FROM HangHoa INNER JOIN ChiTietPhieuXuatHang ON HangHoa.MaHang = ChiTietPhieuXuatHang.MaHang";
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
