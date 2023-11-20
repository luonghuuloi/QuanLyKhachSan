/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.DoanhThuThangDAO;
import com.poly.entity.DoanhThuThang;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyDoanhThuThang extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyDoanhThuThang() {
        initComponents();
        init();
    }

    DoanhThuThangDAO dao = new DoanhThuThangDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyDoanhThuThang.this.DISPOSE_ON_CLOSE);
         btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTable();
    }

    void fillTable() {
        String header[] = {"Mã DTT", "Mã NV", "Tháng", "Năm", "Tổng Doanh Thu Ngày", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<DoanhThuThang> list = dao.select();
            for (DoanhThuThang dtt : list) {
                model.addRow(new Object[]{
                    dtt.getMaDoanhThuThang(), dtt.getMaNhanVien(), dtt.getThang(), dtt.getNam(),
                    dtt.getTongDoanhThuNgay(), dtt.getGhiChu()});
            }
            tblDoanhThuThang.setModel(model);
            //Điều chỉnh độ rộng các cột
            tblDoanhThuThang.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblDoanhThuThang.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblDoanhThuThang.getColumnModel().getColumn(2).setPreferredWidth(15);
            tblDoanhThuThang.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblDoanhThuThang.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblDoanhThuThang.getColumnModel().getColumn(5).setPreferredWidth(80);
           
            //Canh lề dữ liệu trong bảng
            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            tblDoanhThuThang.getColumn("Mã DTT").setCellRenderer(canhLe);
            tblDoanhThuThang.getColumn("Mã NV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            DoanhThuThang model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm doanh thu thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm doanh thu thất bại");
            }
        }
    }

    public boolean checkUpdate() {
        int row = tblDoanhThuThang.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào để cập nhật");
            return false;
        }
        if (txtMaDTT.getText().equals("")) {
            DialogHelper.alert(this, "Mã doanh thu tháng không được trống");
            txtMaDTT.requestFocus();
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
            if (txtTongDTN.getText().equals("")) {
                DialogHelper.alert(this, "Tổng doanh thu ngày không được trống");
                txtTongDTN.requestFocus();
                return false;
            } else if (Double.valueOf(txtTongDTN.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng doanh thu ngày phải lớn hơn 0");
                txtTongDTN.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tổng doanh thu ngày phải là số");
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
                DoanhThuThang model = getForm();
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
        if (txtMaDTT.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa dữ liệu này hay không?")) {
                String macpn = txtMaDTT.getText();
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
        String madtt = (String) tblDoanhThuThang.getValueAt(row, 0);
        DoanhThuThang dtt = dao.findById(madtt);
        setForm(dtt);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        DoanhThuThang model = new DoanhThuThang();
        model.setMaNhanVien(Auth.user.getMaNhanVien());

        row = -1;
        updateStatus();
        txtMaDTT.requestFocus();
        this.setForm(model);
    }

    void setForm(DoanhThuThang model) {
        txtMaDTT.setText(model.getMaDoanhThuThang());
        txtMaNV.setText(model.getMaNhanVien());
        txtThang.setText(String.valueOf(model.getThang()));
        txtNam.setText(String.valueOf(model.getNam()));
        txtTongDTN.setText(String.valueOf(model.getTongDoanhThuNgay()));
        txtGhiChu.setText(model.getGhiChu());
    }

    DoanhThuThang getForm() {
        DoanhThuThang dtt = new DoanhThuThang();
        dtt.setMaDoanhThuThang(txtMaDTT.getText());
        dtt.setMaNhanVien(txtMaNV.getText());
        dtt.setThang(Integer.valueOf(txtThang.getText()));
        dtt.setNam(Integer.valueOf(txtNam.getText()));
        dtt.setTongDoanhThuNgay(Double.valueOf(txtTongDTN.getText()));
        dtt.setGhiChu(txtGhiChu.getText());
        return dtt;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaDTT.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (dao.findById(txtMaDTT.getText()) != null) {
            DialogHelper.alert(this, "Mã doanh thu này đã tồn tại!!");
            txtMaDTT.requestFocus();
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
            if (txtTongDTN.getText().equals("")) {
                DialogHelper.alert(this, "Tổng doanh thu ngày không được trống");
                txtTongDTN.requestFocus();
                return false;
            } else if (Double.valueOf(txtTongDTN.getText()) <= 0) {
                DialogHelper.alert(this, "Tổng doanh thu ngày phải lớn hơn 0");
                txtTongDTN.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tổng doanh thu ngày phải là số");
            return false;
        }
        if (txtGhiChu.getText().equals("")) {
            DialogHelper.alert(this, "Ghi chú không được trống");
            txtGhiChu.requestFocus();
            return false;
        }
        return true;
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
        txtTongDTN = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaDTT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtThang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNam = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThuThang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Doanh Thu Hằng Tháng");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ DOANH THU HẰNG THÁNG");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Doanh Thu Tháng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tổng Doanh Thu Ngày:");

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

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(btnThem))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtThang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaDTT)
                            .addComponent(txtMaNV)
                            .addComponent(txtTongDTN)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE)))
                .addGap(15, 15, 15)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaDTT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtTongDTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tblDoanhThuThang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDoanhThuThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoanhThuThangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDoanhThuThang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
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

        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblThongKe);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        tabs.addTab("THỐNG KÊ", jPanel3);

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

    private void tblDoanhThuThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoanhThuThangMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblDoanhThuThang.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblDoanhThuThangMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyDoanhThuThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new QuanLyDoanhThuThang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDoanhThuThang;
    private javax.swing.JTable tblThongKe;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaDTT;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtThang;
    private javax.swing.JTextField txtTongDTN;
    // End of variables declaration//GEN-END:variables
}
