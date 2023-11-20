/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.ChiPhiHangNgayDAO;
import com.poly.entity.ChiPhiHangNgay;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XDate;
import com.poly.utils.XImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyChiPhiNgay extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyChiPhiNgay() {
        initComponents();
        init();
    }

    ChiPhiHangNgayDAO dao = new ChiPhiHangNgayDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyChiPhiNgay.this.DISPOSE_ON_CLOSE);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTable();
        fillTableDanhSachCPN();
    }

    void fillTable() {
        String header[] = {"Mã CPN", "Mã NV", "Tiền Sửa Chữa", "Tiền Đi Lại", "Chi Phí Khác", "Ngày Nhập", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<ChiPhiHangNgay> list = dao.select();
            for (ChiPhiHangNgay cpn : list) {
                model.addRow(new Object[]{
                    cpn.getMaChiPhiNgay(), cpn.getMaNhanVien(), cpn.getTienSuaChua(),
                    cpn.getTienDiLai(), cpn.getChiPhiKhac(), cpn.getNgayNhap(), cpn.getGhiChu()});
            }
            tblChiPhiNgay.setModel(model);
            //Điều chỉnh độ rộng các cột
            tblChiPhiNgay.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblChiPhiNgay.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblChiPhiNgay.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblChiPhiNgay.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblChiPhiNgay.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblChiPhiNgay.getColumnModel().getColumn(5).setPreferredWidth(40);

            //Canh lề dữ liệu trong bảng
            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            tblChiPhiNgay.getColumn("Mã CPN").setCellRenderer(canhLe);
            tblChiPhiNgay.getColumn("Mã NV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            ChiPhiHangNgay model = getForm();
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
        int row = tblChiPhiNgay.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào để cập nhật");
            return false;
        }
        if (txtMaCPN.getText().equals("")) {
            DialogHelper.alert(this, "Mã chi phí ngày không được trống");
            txtMaCPN.requestFocus();
            return false;
        } else {
            try {
                if (txtTienSuaChua.getText().equals("")) {
                    DialogHelper.alert(this, "Tiền sửa chữa không được trống");
                    txtTienSuaChua.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTienSuaChua.getText()) <= 0) {
                    DialogHelper.alert(this, "Tiền sửa chữa phải lớn hơn 0");
                    txtTienSuaChua.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tiền sửa chữa phải là số");
                return false;
            }
        }
         try {
                if (txtTienDiLai.getText().equals("")) {
                    DialogHelper.alert(this, "Tiền đi lại không được trống");
                    txtTienDiLai.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTienDiLai.getText()) <= 0) {
                    DialogHelper.alert(this, "Tiền đi lại phải lớn hơn 0");
                    txtTienDiLai.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tiền đi lại phải là số");
                return false;
            }
        
        try {
            if (txtChiPhiKhac.getText().equals("")) {
                DialogHelper.alert(this, "Chi phí khác không được trống");
                txtChiPhiKhac.requestFocus();
                return false;
            } else if (Double.valueOf(txtChiPhiKhac.getText()) <= 0) {
                DialogHelper.alert(this, "Chi phí khác phải lớn hơn 0");
                txtChiPhiKhac.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Chi phí khác phải là số");
            return false;
        }
         if (txtGhiChu.getText().equals("")) {
                DialogHelper.alert(this, "Bạn phải ghi chi tiết các chi phí khác ở đây");
                txtGhiChu.requestFocus();
                return false;
            }
        return true;
    }

    void update() {
        if (checkUpdate()) {
            if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                ChiPhiHangNgay model = getForm();
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
        if (txtMaCPN.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa dữ liệu này hay không?")) {
                String macpn = txtMaCPN.getText();
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
        String macpn = (String) tblChiPhiNgay.getValueAt(row, 0);
        ChiPhiHangNgay cpn = dao.findById(macpn);
        setForm(cpn);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        ChiPhiHangNgay model = new ChiPhiHangNgay();
        model.setMaNhanVien(Auth.user.getMaNhanVien());
        model.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
        row = -1;
        updateStatus();
        txtMaCPN.requestFocus();
        this.setForm(model);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    void setForm(ChiPhiHangNgay model) {
        txtMaCPN.setText(model.getMaChiPhiNgay());
        txtMaNV.setText(model.getMaNhanVien());
        txtTienSuaChua.setText(String.valueOf(model.getTienSuaChua()));
        txtTienDiLai.setText(String.valueOf(model.getTienDiLai()));
        txtChiPhiKhac.setText(String.valueOf(model.getChiPhiKhac()));
        Datechooser.setDate(model.getNgayNhap());
        txtGhiChu.setText(model.getGhiChu());
    }

    ChiPhiHangNgay getForm() {
        ChiPhiHangNgay cpn = new ChiPhiHangNgay();
        cpn.setMaChiPhiNgay(txtMaCPN.getText());
        cpn.setMaNhanVien(txtMaNV.getText());
        cpn.setTienSuaChua(Double.valueOf(txtTienSuaChua.getText()));
        cpn.setTienDiLai(Double.valueOf(txtTienDiLai.getText()));
        cpn.setChiPhiKhac(Double.valueOf(txtChiPhiKhac.getText()));
        cpn.setNgayNhap(Datechooser.getDate());
        cpn.setGhiChu(txtGhiChu.getText());
        return cpn;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaCPN.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (dao.findById(txtMaCPN.getText()) != null) {
            DialogHelper.alert(this, "Mã chi phí ngày này đã tồn tại!!");
            txtMaCPN.requestFocus();
            return false;
        } else {
             try {
                if (txtTienSuaChua.getText().equals("")) {
                    DialogHelper.alert(this, "Tiền sửa chữa không được trống");
                    txtTienSuaChua.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTienSuaChua.getText()) <= 0) {
                    DialogHelper.alert(this, "Tiền sửa chữa phải lớn hơn 0");
                    txtTienSuaChua.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tiền sửa chữa phải là số");
                return false;
            }
        }
         try {
                if (txtTienDiLai.getText().equals("")) {
                    DialogHelper.alert(this, "Tiền đi lại không được trống");
                    txtTienDiLai.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTienDiLai.getText()) <= 0) {
                    DialogHelper.alert(this, "Tiền đi lại phải lớn hơn 0");
                    txtTienDiLai.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tiền đi lại phải là số");
                return false;
            }
        
        try {
            if (txtChiPhiKhac.getText().equals("")) {
                DialogHelper.alert(this, "Chi phí khác không được trống");
                txtChiPhiKhac.requestFocus();
                return false;
            } else if (Double.valueOf(txtChiPhiKhac.getText()) <= 0) {
                DialogHelper.alert(this, "Chi phí khác phải lớn hơn 0");
                txtChiPhiKhac.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Chi phí khác phải là số");
            return false;
        }
         if (txtGhiChu.getText().equals("")) {
                DialogHelper.alert(this, "Bạn phải ghi chi tiết các chi phí ở đây");
                txtGhiChu.requestFocus();
                return false;
            }
        return true;
    }
    
     void fillTableDanhSachCPN() {
        String header[] = {"Mã CPN", "Tổng Chi Phí Trong Ngày", "Người Nhập", "Ngày Nhập"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = dao.danhSachChiPhiNgay();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblDanhSachCPN.setModel(model);
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
        txtTienSuaChua = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTienDiLai = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtChiPhiKhac = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaCPN = new javax.swing.JTextField();
        Datechooser = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiPhiNgay = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachCPN = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Chi Phí Hằng Ngày");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ CHI PHÍ HẰNG NGÀY");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Chi Phí Ngày:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tiền Sửa Chữa:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Tiền Đi Lại:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Chi Phí Khác:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Ngày Nhập:");

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

        Datechooser.setDateFormatString("dd-MM-yyyy");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        tblChiPhiNgay.setModel(new javax.swing.table.DefaultTableModel(
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
        tblChiPhiNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiPhiNgayMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChiPhiNgay);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(btnThem))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtChiPhiKhac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(txtTienSuaChua, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaCPN, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jScrollPane3))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienDiLai, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnSua)
                        .addGap(27, 27, 27)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaCPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTienSuaChua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienDiLai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtChiPhiKhac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(Datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblDanhSachCPN.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDanhSachCPN);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(43, 43, 43)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblChiPhiNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiPhiNgayMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblChiPhiNgay.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblChiPhiNgayMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyChiPhiNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyChiPhiNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyChiPhiNgay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Datechooser;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiPhiNgay;
    private javax.swing.JTable tblDanhSachCPN;
    private javax.swing.JTextField txtChiPhiKhac;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaCPN;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtTienDiLai;
    private javax.swing.JTextField txtTienSuaChua;
    // End of variables declaration//GEN-END:variables
}
