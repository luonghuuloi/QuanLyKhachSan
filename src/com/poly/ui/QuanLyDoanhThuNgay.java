/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.DoanhThuHangNgayDAO;
import com.poly.entity.DoanhThuHangNgay;
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
public class QuanLyDoanhThuNgay extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyDoanhThuNgay() {
        initComponents();
        init();
    }

    DoanhThuHangNgayDAO dao = new DoanhThuHangNgayDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyDoanhThuNgay.this.DISPOSE_ON_CLOSE);
         btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTable();
    }

    void fillTable() {
        String header[] = {"Mã DTN", "Mã NV", "Tổng Tiền Phòng", "Tổng Tiền DV", "Ngày Nhập", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<DoanhThuHangNgay> list = dao.select();
            for (DoanhThuHangNgay dtn : list) {
                model.addRow(new Object[]{
                    dtn.getMaDoanhThuNgay(), dtn.getMaNhanVien(), dtn.getTongTienPhong(),
                    dtn.getTongTienDichVu(), dtn.getNgayNhap(), dtn.getGhiChu()});
            }
            tblDoanhThuNgay.setModel(model);
            //Điều chỉnh độ rộng các cột
            tblDoanhThuNgay.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblDoanhThuNgay.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblDoanhThuNgay.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblDoanhThuNgay.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblDoanhThuNgay.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblDoanhThuNgay.getColumnModel().getColumn(5).setPreferredWidth(50);

            //Canh lề dữ liệu trong bảng
            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            tblDoanhThuNgay.getColumn("Mã DTN").setCellRenderer(canhLe);
            tblDoanhThuNgay.getColumn("Mã NV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            DoanhThuHangNgay model = getForm();
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
        int row = tblDoanhThuNgay.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào để cập nhật");
            return false;
        }
        if (txtMaDTN.getText().equals("")) {
            DialogHelper.alert(this, "Mã doanh thu ngày không được trống");
            txtMaDTN.requestFocus();
            return false;
        } else {
            try {
                if (txtTongTienPhong.getText().equals("")) {
                    DialogHelper.alert(this, "Tổng tiền phòng không được trống");
                    txtTongTienPhong.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTongTienPhong.getText()) <= 0) {
                    DialogHelper.alert(this, "Tổng tiền phòng phải lớn hơn 0");
                    txtTongTienPhong.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tổng tiền phòng phải là số");
                return false;
            }
        }
         try {
                if (txtTongTienDV.getText().equals("")) {
                    DialogHelper.alert(this, "Tổng tiền dịch vụ không được trống");
                    txtTongTienDV.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTongTienDV.getText()) <= 0) {
                    DialogHelper.alert(this, "Tổng tiền dịch vụ phải lớn hơn 0");
                    txtTongTienDV.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tổng tiền dịch vụ phải là số");
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
                DoanhThuHangNgay model = getForm();
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
        if (txtMaDTN.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa dữ liệu này hay không?")) {
                String macpn = txtMaDTN.getText();
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
        String madtn = (String) tblDoanhThuNgay.getValueAt(row, 0);
        DoanhThuHangNgay cpn = dao.findById(madtn);
        setForm(cpn);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        DoanhThuHangNgay model = new DoanhThuHangNgay();
        model.setMaNhanVien(Auth.user.getMaNhanVien());
        model.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
        row = -1;
        updateStatus();
        txtMaDTN.requestFocus();
        this.setForm(model);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    void setForm(DoanhThuHangNgay model) {
        txtMaDTN.setText(model.getMaDoanhThuNgay());
        txtMaNV.setText(model.getMaNhanVien());
        txtTongTienPhong.setText(String.valueOf(model.getTongTienPhong()));
        txtTongTienDV.setText(String.valueOf(model.getTongTienDichVu()));
        jDateChooser_NgayNhap.setDate(model.getNgayNhap());
        txtGhiChu.setText(model.getGhiChu());
    }

    DoanhThuHangNgay getForm() {
        DoanhThuHangNgay dtn = new DoanhThuHangNgay();
        dtn.setMaDoanhThuNgay(txtMaDTN.getText());
        dtn.setMaNhanVien(txtMaNV.getText());
        dtn.setTongTienPhong(Double.valueOf(txtTongTienPhong.getText()));
        dtn.setTongTienDichVu(Double.valueOf(txtTongTienDV.getText()));
        dtn.setNgayNhap(jDateChooser_NgayNhap.getDate());
        dtn.setGhiChu(txtGhiChu.getText());
        return dtn;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaDTN.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (dao.findById(txtMaDTN.getText()) != null) {
            DialogHelper.alert(this, "Mã doanh thu ngày này đã tồn tại!!");
            txtMaDTN.requestFocus();
            return false;
        } else {
             try {
                if (txtTongTienPhong.getText().equals("")) {
                    DialogHelper.alert(this, "Tổng tiền phòng không được trống");
                    txtTongTienPhong.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTongTienPhong.getText()) <= 0) {
                    DialogHelper.alert(this, "Tổng tiền phòng phải lớn hơn 0");
                    txtTongTienPhong.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tổng tiền phòng phải là số");
                return false;
            }
        }
         try {
                if (txtTongTienDV.getText().equals("")) {
                    DialogHelper.alert(this, "Tổng tiền dịch vụ không được trống");
                    txtTongTienDV.requestFocus();
                    return false;
                } else if (Double.valueOf(txtTongTienDV.getText()) <= 0) {
                    DialogHelper.alert(this, "Tổng tiền dịch vụ phải lớn hơn 0");
                    txtTongTienDV.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Tổng tiền dịch vụ phải là số");
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
        txtTongTienPhong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTongTienDV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaDTN = new javax.swing.JTextField();
        jDateChooser_NgayNhap = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThuNgay = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Doanh Thu Hằng Ngày");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ DOANH THU HẰNG NGÀY");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Doanh Thu Ngày:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tổng Tiền Phòng:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Tổng Tiền Dịch Vụ:");

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

        jDateChooser_NgayNhap.setDateFormatString("dd-MM-yyyy");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        tblDoanhThuNgay.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDoanhThuNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoanhThuNgayMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDoanhThuNgay);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTongTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMaDTN, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooser_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addGap(29, 29, 29)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTongTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnSua)
                        .addGap(29, 29, 29)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaDTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTongTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jDateChooser_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
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

    private void tblDoanhThuNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoanhThuNgayMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblDoanhThuNgay.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblDoanhThuNgayMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyDoanhThuNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyDoanhThuNgay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new QuanLyDoanhThuNgay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private com.toedter.calendar.JDateChooser jDateChooser_NgayNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDoanhThuNgay;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaDTN;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtTongTienDV;
    private javax.swing.JTextField txtTongTienPhong;
    // End of variables declaration//GEN-END:variables
}
