/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.CaLamDAO;
import com.poly.dao.HangHoaDAO;
import com.poly.dao.NhanVienDAO;
import com.poly.entity.CaLam;
import com.poly.entity.HangHoa;
import com.poly.entity.NhanVien;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XDate;
import com.poly.utils.XImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyCaLam extends javax.swing.JFrame {
    CaLamDAO dao = new CaLamDAO();
    NhanVienDAO nvd = new NhanVienDAO();
    int row = -1;
    /**
     * Creates new form QuanLySach
     */
    public QuanLyCaLam() {
        initComponents();
        init();
    }
    
    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyCaLam.this.DISPOSE_ON_CLOSE);
        fillTable();
    }
    
    void fillTable() {
        String header[] = {"Mã Ca Làm", "Mã Nhân Viên", "Ngày Làm", "Ca", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<CaLam> list = dao.select();
            for (CaLam cal : list) {
                Object[] row = {
                    cal.getMaCaLam(),
                    cal.getMaNhanVien(),
                    cal.getNgayLam(),
                    cal.getCa(),
                    cal.isNhanCa() ? "Đang Làm" : "Chưa Làm"
                };
                model.addRow(row);
            }
            tblSapXepCaLam.setModel(model);
            tblSapXepCaLam.getColumnModel().getColumn(0);
            tblSapXepCaLam.getColumnModel().getColumn(1);
            tblSapXepCaLam.getColumnModel().getColumn(2);
            tblSapXepCaLam.getColumnModel().getColumn(3);
            tblSapXepCaLam.getColumnModel().getColumn(4);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void insert() {
        if (check()) {
            CaLam model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm ca làm thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm ca làm thất bại");
            }
        }
    }
    
    void update() {
        if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                CaLam model = getForm();
                try {
                    dao.update(model);
                    this.fillTable();
                    DialogHelper.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Cập nhật thất bại");
                }
            }
    }
        
    void delete() {
        if (DialogHelper.confirm(this, "Bạn có muốn xóa nhân viên này hay không?")) {
                String macd = txtMaNV.getText();
                try {
                    dao.delete(macd);
                    this.fillTable();
                    this.clear();
                    btnThem.setEnabled(false);
                    DialogHelper.alert(this, "Xóa nhân viên thành công");
                    fillTable();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Không thể xóa nhân viên đã có tài khoản");
                }
            }
    }
    
    void clear() {
        CaLam model = new CaLam();
        row = -1;
        updateStatus();
        txtMaCaLam.requestFocus();
        this.setForm(model);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    void updateStatus() {
        boolean edit = row >= 0;
        txtMaCaLam.setEditable(edit);
        btnThem.setEnabled(edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }
    
    public boolean check() {
        if (dao.findById(txtMaCaLam.getText()) != null) {
            DialogHelper.alert(this, "Mã ca làm này đã tồn tại!!");
            txtMaCaLam.requestFocus();
            return false;
        } if (txtMaCaLam.getText().equals("")) {
            DialogHelper.alert(this, "Mã ca làm không được trống");
            txtMaCaLam.requestFocus();
            return false;
        } if (txtCa.getText().equals("")) {
            DialogHelper.alert(this, "Ca không được trống");
            txtCa.requestFocus();
            return false;
        }
        return true;
    }
    
    CaLam getForm() {
        CaLam cal = new CaLam();
        cal.setMaCaLam(txtMaCaLam.getText());
        cal.setMaNhanVien(txtMaNV.getText());
        cal.setNgayLam(Date_Chooser.getDate());
        cal.setCa(0);
        cal.setNhanCa(rdoDangLam.isSelected());
        return cal;
    }
    
    void loadTenNV() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<NhanVien> list = nvd.select();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
        cboChonNhanVien.setModel(model);
    }
    
    void MaNV() {
        NhanVien nv = (NhanVien) cboChonNhanVien.getSelectedItem();
        txtMaNV.setText(String.valueOf(nv.getMaNhanVien()));       
    }
    
    void edit() {
        String macalam = (String) tblSapXepCaLam.getValueAt(row, 0);       
        CaLam gp = dao.findById(macalam);    
        setForm(gp);
        tabs.setSelectedIndex(0);
        updateStatus();
    }
    
    void setForm(CaLam model) {
        txtMaCaLam.setText(model.getMaCaLam());
        txtMaNV.setText(model.getMaNhanVien());
        Date_Chooser.setDate(model.getNgayLam());
        txtCa.setText(String.valueOf(model.getCa()));
        rdoDangLam.setSelected(model.isNhanCa());
        rdoChuaLam.setSelected(model.isNhanCa());
    }
    

    
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaCaLam = new javax.swing.JTextField();
        txtCa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSapXepCaLam = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        rdoDangLam = new javax.swing.JRadioButton();
        rdoChuaLam = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        cboChonNhanVien = new javax.swing.JComboBox<>();
        Date_Chooser = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThongKeCaLam = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Ca Làm");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ CA LÀM ");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Ca Làm:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Ca:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Trạng Thái:");

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

        tblSapXepCaLam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Ca Làm", "Mã Nhân Viên", "Ngày Làm", "Ca", "Trạng Thái"
            }
        ));
        tblSapXepCaLam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSapXepCaLamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSapXepCaLam);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Ngày Làm:");

        buttonGroup1.add(rdoDangLam);
        rdoDangLam.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdoDangLam.setText("Đang Làm");

        buttonGroup1.add(rdoChuaLam);
        rdoChuaLam.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rdoChuaLam.setText("Chưa làm");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Chọn Nhân Viên:");

        cboChonNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChonNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChonNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cboChonNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCa, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMaCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(Date_Chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoDangLam)
                                .addGap(32, 32, 32)
                                .addComponent(rdoChuaLam))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboChonNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel5))
                    .addComponent(Date_Chooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtCa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoDangLam)
                        .addComponent(rdoChuaLam)))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnMoi)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblThongKeCaLam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Ca Làm", "Mã Nhân Viên", "Ngày Làm", "Ca", "Nhận Ca"
            }
        ));
        tblThongKeCaLam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeCaLamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblThongKeCaLam);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(382, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(260, 260, 260))
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
        
    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblSapXepCaLamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSapXepCaLamMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 1) {
            row = tblSapXepCaLam.getSelectedRow();
            edit();
  
        }
    }//GEN-LAST:event_tblSapXepCaLamMouseClicked

    private void tblThongKeCaLamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeCaLamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblThongKeCaLamMouseClicked

    private void cboChonNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChonNhanVienActionPerformed
        // TODO add your handling code here:
        MaNV();
    }//GEN-LAST:event_cboChonNhanVienActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyCaLam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyCaLam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyCaLam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyCaLam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyCaLam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_Chooser;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChonNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rdoChuaLam;
    private javax.swing.JRadioButton rdoDangLam;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblSapXepCaLam;
    private javax.swing.JTable tblThongKeCaLam;
    private javax.swing.JTextField txtCa;
    private javax.swing.JTextField txtMaCaLam;
    private javax.swing.JTextField txtMaNV;
    // End of variables declaration//GEN-END:variables
}
