/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.HangHoaDAO;
import com.poly.entity.HangHoa;
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
public class QuanLyTaiKhoan extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyTaiKhoan() {
        initComponents();
        init();
    }

    HangHoaDAO dao = new HangHoaDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyTaiKhoan.this.DISPOSE_ON_CLOSE);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
      //  fillTable();
       // fillTableThongKeHangHoa();
    }

//    void fillTable() {
//        String header[] = {"Mã Hàng", "Mã NV", "Tên Hàng", "ĐVT", "Số lượng", "Đơn Giá", "Ngày Nhập"};
//        DefaultTableModel model = new DefaultTableModel(header, 0);
//        try {
//            List<HangHoa> list = dao.select();
//            for (HangHoa hh : list) {
//                model.addRow(new Object[]{
//                    hh.getMaHang(), hh.getMaNhanVien(), hh.getTenHang(), hh.getDVT(),
//                    hh.getSoLuong(),hh.getDonGia(), XDate.toString(hh.getNgayNhap(), "dd-MM-yyyy")});
//            }
//            tblSapXepCaLam.setModel(model);
//            //Điều chỉnh độ rộng các cột
//            tblSapXepCaLam.getColumnModel().getColumn(0).setPreferredWidth(30);
//            tblSapXepCaLam.getColumnModel().getColumn(1).setPreferredWidth(30);
//            tblSapXepCaLam.getColumnModel().getColumn(2).setPreferredWidth(120);
//            tblSapXepCaLam.getColumnModel().getColumn(3).setPreferredWidth(30);
//            tblSapXepCaLam.getColumnModel().getColumn(4).setPreferredWidth(30);
//            tblSapXepCaLam.getColumnModel().getColumn(5).setPreferredWidth(40);
//            tblSapXepCaLam.getColumnModel().getColumn(6).setPreferredWidth(40);
//
//            //Canh lề dữ liệu trong bảng
//            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
//            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//            tblSapXepCaLam.getColumn("Mã Hàng").setCellRenderer(canhLe);
//            tblSapXepCaLam.getColumn("Mã NV").setCellRenderer(canhLe);
//
//        } catch (Exception e) {
//            System.out.println(e);
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }
    
//    void insert() {
//        if (check()) {
//            HangHoa model = getForm();
//            try {
//                dao.insert(model);
//                this.fillTable();
//                this.fillTableThongKeHangHoa();
//                btnThem.setEnabled(false);
//                DialogHelper.alert(this, "Thêm mặt hàng thành công");
//            } catch (Exception e) {
//                DialogHelper.alert(this, "Thêm mặt hàng thất bại");
//            }
//        }
//    }
//     public boolean checkUpdate() {
//        int row = tblSapXepCaLam.getSelectedRow();
//        if (row < 0) {
//                DialogHelper.alert(this, "Bạn chưa chọn mặt hàng để cập nhật");
//                return false;
//            }
//        if (txtMaHang.getText().equals("")) {
//            DialogHelper.alert(this, "Mã hàng không được trống");
//            txtMaHang.requestFocus();
//            return false;
//        } else {
//            if (txtTenHang.getText().equals("")) {
//                DialogHelper.alert(this, "Tên hàng không được trống");
//                txtTenHang.requestFocus();
//                return false;
//            } else if(txtDVT.getText().equals("")){
//                DialogHelper.alert(this, "Đơn vị tính không được trống");
//                txtDVT.requestFocus();
//            }            
//            else {
//               {
//                    try {
//                        if (txtSoLuong.getText().equals("")) {
//                            DialogHelper.alert(this, "Số lượng không được trống");
//                            txtSoLuong.requestFocus();
//                            return false;
//                        } else if (Double.valueOf(txtSoLuong.getText()) <= 0) {
//                            DialogHelper.alert(this, "Số lượng phải lớn hơn 0");
//                            txtSoLuong.requestFocus();
//                            return false;
//                        }
//                    } catch (Exception e) {
//                        DialogHelper.alert(this, "Số lượng phải là số" + e);
//                        return false;
//                    }
//                }
//            }
//        }
//        try {
//            if (txtDonGia.getText().equals("")) {
//                DialogHelper.alert(this, "Đơn giá không được trống");
//                txtDonGia.requestFocus();
//                return false;
//            } else if (Double.valueOf(txtDonGia.getText()) <= 0) {
//                DialogHelper.alert(this, "Đơn giá phải lớn hơn 0");
//                txtDonGia.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            DialogHelper.alert(this, "Đơn giá phải là số");
//            return false;
//        }
//        return true;
//    }
//    void update() {
//        if (checkUpdate()) {
//            if(DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")){
//              HangHoa model = getForm();
//            try {
//                dao.update(model);
//                this.fillTable();
//                this.fillTableThongKeHangHoa();
//                DialogHelper.alert(this, "Cập nhật thành công");
//            } catch (Exception e) {
//                DialogHelper.alert(this, "Cập nhật thất bại");
//            }
//            }
//        }
//    }
//    boolean checkXoa() {
//        if (txtMaHang.getText().equals("")) {
//            DialogHelper.alert(this, "Bạn chưa chọn mặt hàng để xóa");
//            return false;
//        }
//        return true;
//    }
//    void delete() {
//        if (checkXoa()) {
//            if (DialogHelper.confirm(this, "Bạn có muốn xóa mặt hàng này hay không?")) {
//                String masach = txtMaHang.getText();
//                try {
//                    dao.delete(masach);
//                    this.fillTable();
//                    this.fillTableThongKeHangHoa();
//                    this.clear();
//                    btnThem.setEnabled(false);
//                    DialogHelper.alert(this, "Xóa mặt hàng thành công");
//                    fillTable();
//                } catch (Exception e) {
//                    DialogHelper.alert(this, "Xóa mặt hàng thất bại");
//                }
//            }
//        }
//
//    }
//    void edit() {
//        String mahang = (String) tblSapXepCaLam.getValueAt(row, 0);
//        HangHoa hh = dao.findById(mahang);
//        setForm(hh);
//        tabs.setSelectedIndex(0);
//        updateStatus();
//    }
//
//    void clear() {
//        HangHoa model = new HangHoa();
//        model.setMaNhanVien(Auth.user.getMaNhanVien());
//        model.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
//        row = -1;
//        updateStatus();
//        txtMaHang.requestFocus();
//        this.setForm(model);
//    }
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//    void setForm(HangHoa model) {
//        txtMaHang.setText(model.getMaHang());
//        txtMaNV.setText(model.getMaNhanVien());
//        txtTenHang.setText(model.getTenHang());
//        txtDVT.setText(String.valueOf(model.getDVT()));
//        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
//        txtDonGia.setText(String.valueOf(model.getDonGia()));
//        //txtNgayNhap.setText(XDate.toString(model.getNgayNhap(), "dd/MM/yyyy"));
//        jDateChooser_NgayNhap.setDate(model.getNgayNhap());
//    }
//
//    HangHoa getForm() {
//        HangHoa hh = new HangHoa();
//        hh.setMaHang(txtMaHang.getText());
//        hh.setMaNhanVien(txtMaNV.getText());
//        hh.setTenHang(txtTenHang.getText());
//        hh.setDVT(txtDVT.getText());
//        hh.setSoLuong(Double.valueOf(txtSoLuong.getText()));
//        hh.setDonGia(Double.valueOf(txtDonGia.getText()));
//        //sach.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
//        hh.setNgayNhap(jDateChooser_NgayNhap.getDate());
//        return hh;
//    }
//
//    void updateStatus() {
//        boolean edit = row >= 0;
//        txtMaHang.setEditable(!edit);
//        btnThem.setEnabled(!edit);
//        btnSua.setEnabled(edit);
//        btnXoa.setEnabled(edit);
//    }
//
//    public boolean check() {
//        if (dao.findById(txtMaHang.getText()) != null) {
//            DialogHelper.alert(this, "Mã hàng này đã tồn tại!!");
//            txtMaHang.requestFocus();
//            return false;
//        } else {
//            if (txtTenHang.getText().equals("")) {
//                DialogHelper.alert(this, "Tên hàng không được trống");
//                txtTenHang.requestFocus();
//                return false;
//            } else if(txtDVT.getText().equals("")){
//                DialogHelper.alert(this, "Đơn vị tính không được trống");
//                txtDVT.requestFocus();
//            }        
//            else {
//                {
//                    try {
//                        if (txtSoLuong.getText().equals("")) {
//                            DialogHelper.alert(this, "Số lượng không được trống");
//                            txtSoLuong.requestFocus();
//                            return false;
//                        } else if (Double.valueOf(txtSoLuong.getText()) <= 0) {
//                            DialogHelper.alert(this, "Số lượng phải lớn hơn 0");
//                            txtSoLuong.requestFocus();
//                            return false;
//                        }
//                    } catch (Exception e) {
//                        DialogHelper.alert(this, "Số lượng phải là số");
//                        return false;
//                    }
//                }
//            }
//        }
//        try {
//            if (txtDonGia.getText().equals("")) {
//                DialogHelper.alert(this, "Đơn giá không được trống");
//                txtDonGia.requestFocus();
//                return false;
//            } else if (Double.valueOf(txtDonGia.getText()) <= 0) {
//                DialogHelper.alert(this, "Giá tiền phải lớn hơn 0");
//                txtDonGia.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            DialogHelper.alert(this, "Đơn giá phải là số");
//            return false;
//        }
//        return true;
//    }
//    
//     void fillTableThongKeHangHoa() {
//        String header[] = {"Mã Hàng", "Tên Hàng", "Thành Tiền", "Ngày Nhập"};
//        DefaultTableModel model = new DefaultTableModel(header, 0);
//        List<Object[]> list = dao.thongKeHangHoa();
//        for (Object[] ob : list) {
//            model.addRow(ob);
//        }
//        tblThongKeHangHoa.setModel(model);
//        
//    }
     

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
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaHang = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtMaHang1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMaHang2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Ca Làm");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("TÀI KHOẢN");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Tên Tài Khoản:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Mã Nhân Viên:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Vai Trò:");

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

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jRadioButton1.setText("ADMIN");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jRadioButton2.setText("STAFF");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Mật Khẩu:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Chọn Nhân Viên:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Chức Vụ:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(433, 433, 433))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHang1)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaHang)
                            .addComponent(jPasswordField1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jRadioButton2))
                            .addComponent(txtMaHang2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnSua)
                        .addGap(41, 41, 41)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(82, 82, 82))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblTaiKhoan);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
       // clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
      //  delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
     //   update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
      //  insert();
    }//GEN-LAST:event_btnThemActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new QuanLyTaiKhoan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTextField txtMaHang;
    private javax.swing.JTextField txtMaHang1;
    private javax.swing.JTextField txtMaHang2;
    // End of variables declaration//GEN-END:variables
}
