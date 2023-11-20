/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

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
public class ThongKeDAO {

    public List<Object[]> thongKeChiPhiThang() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaCPT, MaNV, Thang, Nam, (TongChiPhiNgay + TienDien + TienNuoc + TienLuong + TienThue + TienNhapHang) AS 'Tổng Chi Phí' FROM ChiPhiThang";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaCPT"),
                        rs.getString("MaNV"),
                        rs.getInt("Thang"),
                        rs.getInt("Nam"),
                        rs.getDouble("Tổng Chi Phí")
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
