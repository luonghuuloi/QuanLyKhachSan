/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.ChiPhiHangNgayDAO;
import com.poly.dao.ChiPhiThangDAO;
import com.poly.dao.HangHoaDAO;
import com.poly.dao.LuongDAO;
import com.poly.entity.ChiPhiThang;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XDate;
import com.poly.utils.XImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyChiPhiThang extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyChiPhiThang() {
        initComponents();
        init();
    }

    ChiPhiThangDAO dao = new ChiPhiThangDAO();
    HangHoaDAO hhdao = new HangHoaDAO();
    LuongDAO ldao = new LuongDAO();
    ChiPhiHangNgayDAO cpndao = new ChiPhiHangNgayDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyChiPhiThang.this.DISPOSE_ON_CLOSE);
         btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTable();
        fillTableTongTienNhapHangHoa();
        fillTableTongTienLuong();
        fillTableDanhSachCPN();
    }

    void fillTable() {
        String header[] = {"Mã CPT", "Mã NV", "Tháng", "Năm", "Tổng CP Ngày", "Tiền Điện", "Tiền Nước", "Tiền Lương", "Tiền Thuế", "Tiền Nhập Hàng", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<ChiPhiThang> list = dao.select();
            for (ChiPhiThang cpt : list) {
                model.addRow(new Object[]{
                    cpt.getMaChiPhiThang(), cpt.getMaNhanVien(), cpt.getThang(), cpt.getNam(),
                    cpt.getTongChiPhiNgay(), cpt.getTienDien(), cpt.getTienNuoc(), cpt.getTienLuong(),
                    cpt.getTienThue(), cpt.getTienNhapHang(), cpt.getGhiChu()});
            }
            tblChiPhiThang.setModel(model);
            //Điều chỉnh độ rộng các cột
            tblChiPhiThang.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblChiPhiThang.getColumnModel().getColumn(2).setPreferredWidth(15);
            tblChiPhiThang.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblChiPhiThang.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(7).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(8).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(9).setPreferredWidth(40);
            tblChiPhiThang.getColumnModel().getColumn(9).setPreferredWidth(30);

            //Canh lề dữ liệu trong bảng
            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            tblChiPhiThang.getColumn("Mã CPT").setCellRenderer(canhLe);
            tblChiPhiThang.getColumn("Mã NV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            ChiPhiThang model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm chi phí thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm chi phí thất bại");
            }
        }
    }

    public boolean checkUpdate() {
        int row = tblChiPhiThang.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào để cập nhật");
            return false;
        }
        if (txtMaCPT.getText().equals("")) {
            DialogHelper.alert(this, "Mã chi phí tháng không được trống");
            txtMaCPT.requestFocus();
            return false;
        } else {
            try {
                if (txtThang.getText().equals("")) {
                    DialogHelper.alert(this, "Tháng không được trống");
                    txtThang.requestFocus();
                    return false;
                } else if ((Integer.valueOf(txtThang.getText()) <= 0) && (Integer.valueOf(txtThang.getText()) > 12)) {
                    DialogHelper.alert(this, "Tháng không hợp lệ");
                    txtThang.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tháng phải là số");
                return false;
            }
        }
        try {
            if (txtNam.getText().equals("")) {
                DialogHelper.alert(this, "Năm không được trống");
                txtNam.requestFocus();
                return false;
            } else if ((Integer.valueOf(txtNam.getText()) <= 2000) && (Integer.valueOf(txtNam.getText()) >= 2100)) {
                DialogHelper.alert(this, "Năm không hợp lệ");
                txtNam.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Năm phải là số");
            return false;
        }

        try {
            if (txtTongCPN.getText().equals("")) {
                DialogHelper.alert(this, "Tổng chi phí ngày không được trống");
                txtTongCPN.requestFocus();
                return false;
            } else if (Double.valueOf(txtTongCPN.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng chi phí ngày phải lớn hơn 0");
                txtTongCPN.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tổng chi phí ngày phải là số");
            return false;
        }

        try {
            if (txtTienDien.getText().equals("")) {
                DialogHelper.alert(this, "Tiền điện không được trống");
                txtTienDien.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienDien.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền điện ngày phải lớn hơn 0");
                txtTienDien.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền điện ngày phải là số");
            return false;
        }

        try {
            if (txtTienNuoc.getText().equals("")) {
                DialogHelper.alert(this, "Tiền nước không được trống");
                txtTienNuoc.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienNuoc.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng nước phải lớn hơn 0");
                txtTienNuoc.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền nước phải là số");
            return false;
        }

        try {
            if (txtTienLuong.getText().equals("")) {
                DialogHelper.alert(this, "Tiền lương không được trống");
                txtTienLuong.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienLuong.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền lương phải lớn hơn 0");
                txtTienLuong.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền lương phải là số");
            return false;
        }

        try {
            if (txtTienThue.getText().equals("")) {
                DialogHelper.alert(this, "Tiền thuế không được trống");
                txtTienThue.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienThue.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền thuế phải lớn hơn 0");
                txtTienThue.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền thuế phải là số");
            return false;
        }

        try {
            if (txtTienNhapHang.getText().equals("")) {
                DialogHelper.alert(this, "Tiền nhập hàng không được trống");
                txtTienNhapHang.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienNhapHang.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền nhập hàng phải lớn hơn 0");
                txtTienNhapHang.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền nhập hàng phải là số");
            return false;
        }
        if (txtGhiChu.getText().equals("")) {
            DialogHelper.alert(this, "Ghi chú không được trống");
            txtGhiChu.requestFocus();
            return false;
        }
        return true;
    }

    void update() {
        if (checkUpdate()) {
            if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                ChiPhiThang model = getForm();
                try {
                    dao.update(model);
                    this.fillTable();
                    DialogHelper.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Cập nhật thất bại");
                }
            }
        }
    }

    boolean checkXoa() {
        if (txtMaCPT.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa dữ liệu này hay không?")) {
                String macpn = txtMaCPT.getText();
                try {
                    dao.delete(macpn);
                    this.fillTable();
                    this.clear();
                    btnThem.setEnabled(false);
                    DialogHelper.alert(this, "Xóa thành công");
                    fillTable();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xóa thất bại");
                }
            }
        }

    }

    void edit() {
        String macpt = (String) tblChiPhiThang.getValueAt(row, 0);
        ChiPhiThang cpt = dao.findById(macpt);
        setForm(cpt);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        ChiPhiThang model = new ChiPhiThang();
        model.setMaNhanVien(Auth.user.getMaNhanVien());

        row = -1;
        updateStatus();
        txtMaCPT.requestFocus();
        this.setForm(model);
    }

    void setForm(ChiPhiThang model) {
        txtMaCPT.setText(model.getMaChiPhiThang());
        txtMaNV.setText(model.getMaNhanVien());
        txtThang.setText(String.valueOf(model.getThang()));
        txtNam.setText(String.valueOf(model.getNam()));
        txtTongCPN.setText(String.valueOf(model.getTongChiPhiNgay()));
        txtTienDien.setText(String.valueOf(model.getTienDien()));
        txtTienNuoc.setText(String.valueOf(model.getTienNuoc()));
        txtTienLuong.setText(String.valueOf(model.getTienLuong()));
        txtTienThue.setText(String.valueOf(model.getTienThue()));
        txtTienNhapHang.setText(String.valueOf(model.getTienNhapHang()));
        txtGhiChu.setText(model.getGhiChu());
    }

    ChiPhiThang getForm() {
        ChiPhiThang cpt = new ChiPhiThang();
        cpt.setMaChiPhiThang(txtMaCPT.getText());
        cpt.setMaNhanVien(txtMaNV.getText());
        cpt.setThang(Integer.valueOf(txtThang.getText()));
        cpt.setNam(Integer.valueOf(txtNam.getText()));
        cpt.setTongChiPhiNgay(Double.valueOf(txtTongCPN.getText()));
        cpt.setTienDien(Double.valueOf(txtTienDien.getText()));
        cpt.setTienNuoc(Double.valueOf(txtTienNuoc.getText()));
        cpt.setTienLuong(Double.valueOf(txtTienLuong.getText()));
        cpt.setTienThue(Double.valueOf(txtTienThue.getText()));
        cpt.setTienNhapHang(Double.valueOf(txtTienNhapHang.getText()));
        cpt.setGhiChu(txtGhiChu.getText());
        return cpt;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaCPT.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (dao.findById(txtMaCPT.getText()) != null) {
            DialogHelper.alert(this, "Mã chi phí ngày này đã tồn tại!!");
            txtMaCPT.requestFocus();
            return false;
        } else {
            try {
                if (txtThang.getText().equals("")) {
                    DialogHelper.alert(this, "Tháng không được trống");
                    txtThang.requestFocus();
                    return false;
                } else if ((Integer.valueOf(txtThang.getText()) <= 0) && (Integer.valueOf(txtThang.getText()) > 12)) {
                    DialogHelper.alert(this, "Tháng không hợp lệ");
                    txtThang.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tháng phải là số");
                return false;
            }
        }
        try {
            if (txtNam.getText().equals("")) {
                DialogHelper.alert(this, "Năm không được trống");
                txtNam.requestFocus();
                return false;
            } else if ((Integer.valueOf(txtNam.getText()) <= 2000) && (Integer.valueOf(txtNam.getText()) >= 2100)) {
                DialogHelper.alert(this, "Năm không hợp lệ");
                txtNam.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Năm phải là số");
            return false;
        }

        try {
            if (txtTongCPN.getText().equals("")) {
                DialogHelper.alert(this, "Tổng chi phí ngày không được trống");
                txtTongCPN.requestFocus();
                return false;
            } else if (Double.valueOf(txtTongCPN.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng chi phí ngày phải lớn hơn 0");
                txtTongCPN.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tổng chi phí ngày phải là số");
            return false;
        }

        try {
            if (txtTienDien.getText().equals("")) {
                DialogHelper.alert(this, "Tiền điện không được trống");
                txtTienDien.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienDien.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền điện ngày phải lớn hơn 0");
                txtTienDien.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền điện ngày phải là số");
            return false;
        }

        try {
            if (txtTienNuoc.getText().equals("")) {
                DialogHelper.alert(this, "Tiền nước không được trống");
                txtTienNuoc.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienNuoc.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng nước phải lớn hơn 0");
                txtTienNuoc.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền nước phải là số");
            return false;
        }

        try {
            if (txtTienLuong.getText().equals("")) {
                DialogHelper.alert(this, "Tiền lương không được trống");
                txtTienLuong.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienLuong.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền lương phải lớn hơn 0");
                txtTienLuong.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền lương phải là số");
            return false;
        }

        try {
            if (txtTienThue.getText().equals("")) {
                DialogHelper.alert(this, "Tiền thuế không được trống");
                txtTienThue.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienThue.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền thuế phải lớn hơn 0");
                txtTienThue.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền thuế phải là số");
            return false;
        }

        try {
            if (txtTienNhapHang.getText().equals("")) {
                DialogHelper.alert(this, "Tiền nhập hàng không được trống");
                txtTienNhapHang.requestFocus();
                return false;
            } else if (Double.valueOf(txtTienNhapHang.getText()) <= 0) {
                DialogHelper.alert(this, "Tiền nhập hàng phải lớn hơn 0");
                txtTienNhapHang.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền nhập hàng phải là số");
            return false;
        }
        if (txtGhiChu.getText().equals("")) {
            DialogHelper.alert(this, "Ghi chú không được trống");
            txtGhiChu.requestFocus();
            return false;
        }
        return true;
    }

    void fillTableTongTienNhapHangHoa() {
        String header[] = {"Mã Hàng", "Tên Hàng", "Thành Tiền", "Ngày Nhập"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = hhdao.thongKeHangHoa();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblThongKeHangHoa.setModel(model);
    }

    void fillTableTongTienLuong() {
        String header[] = {"Mã Lương", "Mã NV", "Họ Tên", "Chức Vụ", "Tháng", "Năm", "Tổng Tiền Lương", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = ldao.thongKeTienLuong();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblThongKeLuong.setModel(model);
    }
    
     void fillTableDanhSachCPN() {
        String header[] = {"Mã CPN", "Tổng Chi Phí Trong Ngày", "Người Nhập", "Ngày Nhập"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = cpndao.danhSachChiPhiNgay();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblThongKeCPN.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTongCPN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTienNuoc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTienThue = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaCPT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtThang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNam = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTienDien = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTienLuong = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTienNhapHang = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiPhiThang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblThongKeCPN = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtThangCPN = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtNamCPN = new javax.swing.JTextField();
        btnKetQuaCPNTheoThang = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblTongCPNTheoThang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeLuong = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtThangTraLuong = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNamTraLuong = new javax.swing.JTextField();
        btnKetQuaTienLuong = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblTongTienTraLuong = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThongKeHangHoa = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtThangNhapHang = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNamNhapHang = new javax.swing.JTextField();
        btnKetQuaTienHang = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTongTienNhapHang = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Chi Phí Hằng Tháng");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ CHI PHÍ HẰNG THÁNG");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Chi Phí Tháng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tổng Chi Phí Ngày:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Tiền Nước:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Tiền Thuế:");

        btnThem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Save.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/update.png"))); // NOI18N
        btnSua.setText("Cập Nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/new.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Ghi Chú:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Tháng:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Năm:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Tiền Điện:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel12.setText("Tiền Lương:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setText("Tiền Nhập Hàng:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTienNuoc, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTongCPN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(txtTienThue))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMaCPT, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addComponent(jScrollPane4))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtNam, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txtTienDien)
                            .addComponent(txtTienLuong)
                            .addComponent(txtTienNhapHang, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(78, 78, 78))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnSua)
                        .addGap(35, 35, 35)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(117, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaCPT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTongCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtTienDien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTienNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtTienLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienThue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtTienNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(57, 57, 57))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblChiPhiThang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblChiPhiThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiPhiThangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChiPhiThang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        tblThongKeCPN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tblThongKeCPN);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel19.setText("TÍNH TỔNG CHI PHÍ NGÀY THEO THÁNG");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Tháng:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("Năm:");

        btnKetQuaCPNTheoThang.setText("Kết Quả");
        btnKetQuaCPNTheoThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetQuaCPNTheoThangActionPerformed(evt);
            }
        });

        tblTongCPNTheoThang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblTongCPNTheoThang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Tổng Chi Phí Ngày Trong Tháng"
            }
        ));
        tblTongCPNTheoThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTongCPNTheoThangMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblTongCPNTheoThang);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(txtThangCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel21)
                                .addGap(18, 18, 18)
                                .addComponent(txtNamCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(btnKetQuaCPNTheoThang))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addComponent(jLabel19))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(285, 285, 285)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 157, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel19)
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtThangCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtNamCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKetQuaCPNTheoThang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        tabs.addTab("TỔNG CP NGÀY THEO THÁNG", jPanel3);

        tblThongKeLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblThongKeLuong);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel16.setText("TÍNH TỔNG TIỀN TRẢ LƯƠNG THEO THÁNG");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Tháng:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Năm:");

        btnKetQuaTienLuong.setText("Kết Quả");
        btnKetQuaTienLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetQuaTienLuongActionPerformed(evt);
            }
        });

        tblTongTienTraLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblTongTienTraLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Tổng Tiền Trả Lương Trong Tháng"
            }
        ));
        tblTongTienTraLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTongTienTraLuongMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblTongTienTraLuong);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtThangTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(txtNamTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(btnKetQuaTienLuong))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(294, 294, 294)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16)
                                .addGap(59, 59, 59)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel16)
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtThangTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtNamTraLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKetQuaTienLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        tabs.addTab("TIỀN TRẢ LƯƠNG", jPanel4);

        tblThongKeHangHoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblThongKeHangHoa);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setText("TÍNH TỔNG TIỀN NHẬP HÀNG THEO THÁNG");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Tháng:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Năm:");

        btnKetQuaTienHang.setText("Kết Quả");
        btnKetQuaTienHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetQuaTienHangActionPerformed(evt);
            }
        });

        tblTongTienNhapHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblTongTienNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Tổng Tiền Nhập Hàng Trong Tháng"
            }
        ));
        tblTongTienNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTongTienNhapHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTongTienNhapHang);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(jLabel14))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(298, 298, 298)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(184, 184, 184)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtThangNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNamNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(btnKetQuaTienHang)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel14)
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtThangNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtNamNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKetQuaTienHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        tabs.addTab("TIỀN NHẬP HÀNG", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(44, 44, 44)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblChiPhiThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiPhiThangMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblChiPhiThang.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblChiPhiThangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnKetQuaTienHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetQuaTienHangActionPerformed
        String header[] = {"Tổng Tiền Nhập Hàng Trong Tháng"};
        DefaultTableModel tblModel = new DefaultTableModel(header, 0);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            if (txtThangNhapHang.getText().equals("")) {
                DialogHelper.alert(this, "Tháng không được trống");
                txtThangTraLuong.requestFocus();
            } else if (txtNamNhapHang.getText().equals("")) {
                DialogHelper.alert(this, "Năm không được trống");
                txtNamTraLuong.requestFocus();
            } else {
                String user = "sa";
                String pass = "123";
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSan";
                con = DriverManager.getConnection(url, user, pass);
                String sql = "SELECT SUM(SoLuong * DonGia) AS N'Tổng Tiền Nhập Hàng Trong Tháng' FROM HangHoa WHERE MONTH(NgayNhap) = " + txtThangNhapHang.getText() + " AND YEAR(NgayNhap) = " + txtNamNhapHang.getText() + "";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                Vector data = null;
                tblModel.setRowCount(0);
                while (rs.next()) {
                    data = new Vector();
                    data.add(rs.getDouble("Tổng Tiền Nhập Hàng Trong Tháng"));

                    tblModel.addRow(data);
                }
                tblTongTienNhapHang.setModel(tblModel);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tháng hoặc Năm không hợp lệ");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnKetQuaTienHangActionPerformed

    private void tblTongTienNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTongTienNhapHangMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblTongTienNhapHang.getSelectedRow();
            txtTienNhapHang.setText(tblTongTienNhapHang.getValueAt(0, 0).toString());
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblTongTienNhapHangMouseClicked

    private void tblTongTienTraLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTongTienTraLuongMouseClicked
         if (evt.getClickCount() >= 1) {
            row = tblTongTienTraLuong.getSelectedRow();
            txtTienLuong.setText(tblTongTienTraLuong.getValueAt(0, 0).toString());
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblTongTienTraLuongMouseClicked

    private void btnKetQuaTienLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetQuaTienLuongActionPerformed
        String header[] = {"Tổng Tiền Trả Lương Trong Tháng"};
        DefaultTableModel tblModel = new DefaultTableModel(header, 0);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            if (txtThangTraLuong.getText().equals("")) {
                DialogHelper.alert(this, "Tháng không được trống");
                txtThangTraLuong.requestFocus();
            } else if (txtNamTraLuong.getText().equals("")) {
                DialogHelper.alert(this, "Năm không được trống");
                txtNamTraLuong.requestFocus();
            } else {
                String user = "sa";
                String pass = "123";
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSan";
                con = DriverManager.getConnection(url, user, pass);
                String sql = "SELECT ROUND(SUM((LuongCoBan + PhuCap)/26 * SoNgayLamViec),-3) AS N'Tổng Tiền Trả Lương Trong Tháng' FROM Luong WHERE Thang = " + txtThangTraLuong.getText() + " AND Nam = " + txtNamTraLuong.getText() + "";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                Vector data = null;
                tblModel.setRowCount(0);
                while (rs.next()) {
                    data = new Vector();
                    data.add(rs.getDouble("Tổng Tiền Trả Lương Trong Tháng"));

                    tblModel.addRow(data);
                }
                tblTongTienTraLuong.setModel(tblModel);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tháng hoặc Năm không hợp lệ");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnKetQuaTienLuongActionPerformed

    private void tblTongCPNTheoThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTongCPNTheoThangMouseClicked
         if (evt.getClickCount() >= 1) {
            row = tblTongCPNTheoThang.getSelectedRow();
            txtTongCPN.setText(tblTongCPNTheoThang.getValueAt(0, 0).toString());
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblTongCPNTheoThangMouseClicked

    private void btnKetQuaCPNTheoThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetQuaCPNTheoThangActionPerformed
         String header[] = {"Tổng Chi Phí Ngày Trong Tháng"};
        DefaultTableModel tblModel = new DefaultTableModel(header, 0);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            if (txtThangCPN.getText().equals("")) {
                DialogHelper.alert(this, "Tháng không được trống");
                txtThangCPN.requestFocus();
            } else if (txtNamCPN.getText().equals("")) {
                DialogHelper.alert(this, "Năm không được trống");
                txtNamCPN.requestFocus();
            } else {
                String user = "sa";
                String pass = "123";
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSan";
                con = DriverManager.getConnection(url, user, pass);
                String sql = "SELECT SUM(TienSuaChua + TienDiLai + ChiPhiKhac) AS N'Tổng Chi Phí Ngày Trong Tháng' FROM ChiPhiHangNgay WHERE MONTH(NgayNhap) = " + txtThangCPN.getText() + " AND YEAR(NgayNhap) = " + txtNamCPN.getText() + "";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                Vector data = null;
                tblModel.setRowCount(0);
                while (rs.next()) {
                    data = new Vector();
                    data.add(rs.getDouble("Tổng Chi Phí Ngày Trong Tháng"));

                    tblModel.addRow(data);
                }
                tblTongCPNTheoThang.setModel(tblModel);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tháng hoặc Năm không hợp lệ");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnKetQuaCPNTheoThangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyChiPhiThang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKetQuaCPNTheoThang;
    private javax.swing.JButton btnKetQuaTienHang;
    private javax.swing.JButton btnKetQuaTienLuong;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiPhiThang;
    private javax.swing.JTable tblThongKeCPN;
    private javax.swing.JTable tblThongKeHangHoa;
    private javax.swing.JTable tblThongKeLuong;
    private javax.swing.JTable tblTongCPNTheoThang;
    private javax.swing.JTable tblTongTienNhapHang;
    private javax.swing.JTable tblTongTienTraLuong;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaCPT;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtNamCPN;
    private javax.swing.JTextField txtNamNhapHang;
    private javax.swing.JTextField txtNamTraLuong;
    private javax.swing.JTextField txtThang;
    private javax.swing.JTextField txtThangCPN;
    private javax.swing.JTextField txtThangNhapHang;
    private javax.swing.JTextField txtThangTraLuong;
    private javax.swing.JTextField txtTienDien;
    private javax.swing.JTextField txtTienLuong;
    private javax.swing.JTextField txtTienNhapHang;
    private javax.swing.JTextField txtTienNuoc;
    private javax.swing.JTextField txtTienThue;
    private javax.swing.JTextField txtTongCPN;
    // End of variables declaration//GEN-END:variables
}
