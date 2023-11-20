/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.GiaPhongDAO;
import com.poly.dao.HangHoaDAO;
import com.poly.dao.HoaDonPhongDAO;
import com.poly.entity.GiaPhong;
import com.poly.entity.HangHoa;
import com.poly.entity.HoaDonPhong;
import com.poly.entity.XuatHang;
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
public class QuanLyHoaDonPhong extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyHoaDonPhong() {
        initComponents();
        init();
    }

    HoaDonPhongDAO hdpdao = new HoaDonPhongDAO();
    GiaPhongDAO gpdao = new GiaPhongDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyHoaDonPhong.this.DISPOSE_ON_CLOSE);
        loadCboMaPhong();
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTable();
       // fillTableThongKeHangHoa();
    }
    
    void loadCboMaPhong() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<GiaPhong> list = gpdao.layMaPhong();
        for (GiaPhong mp : list) {
            model.addElement(mp);
        }
        cboMaPhong.setModel(model);
    }

    void fillTable() {
        String header[] = {"Mã Hóa Đơn", "Mã Phòng", "Mã NV", "Tên Khách Hàng", "CMND", "Ngày Thuê", "Ngày Trả", "Số Giờ", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<HoaDonPhong> list = hdpdao.select();
            for (HoaDonPhong hdp : list) {
                model.addRow(new Object[]{
                    hdp.getMaHoaDonPhong(), hdp.getMaPhong(), hdp.getMaNhanVien(), hdp.getTenKhachHang(), hdp.getCMND(),
                    XDate.toString(hdp.getNgayThue(), "dd-MM-yyyy"), XDate.toString(hdp.getNgayTra(), "dd-MM-yyyy"),
                    hdp.getSoGio(), hdp.getGhiChu()});
            }
            tblHoaDonPhong.setModel(model);
            //Điều chỉnh độ rộng các cột
//            tblPhong.getColumnModel().getColumn(0).setPreferredWidth(30);
//            tblPhong.getColumnModel().getColumn(1).setPreferredWidth(30);
//            tblPhong.getColumnModel().getColumn(2).setPreferredWidth(120);
//            tblPhong.getColumnModel().getColumn(3).setPreferredWidth(30);
//            tblPhong.getColumnModel().getColumn(4).setPreferredWidth(30);
//            tblPhong.getColumnModel().getColumn(5).setPreferredWidth(40);
//            tblPhong.getColumnModel().getColumn(6).setPreferredWidth(40);
//
//            //Canh lề dữ liệu trong bảng
//            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
//            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//            tblPhong.getColumn("Mã Hàng").setCellRenderer(canhLe);
//            tblPhong.getColumn("Mã NV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
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
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonPhong = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        txtCMND = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaHoaDonPhong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jDateChooser_NgayThue = new com.toedter.calendar.JDateChooser();
        jDateChooser_NgayTra = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSoGio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        cboMaPhong = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKePhong = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Hóa Đơn Phòng");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("HÓA ĐƠN PHÒNG ");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Mã Hóa Đơn:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("CMND:");

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

        tblHoaDonPhong.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHoaDonPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonPhongMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonPhong);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tên Khách Hàng:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Mã Phòng:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Ngày Trả:");

        txtMaNhanVien.setEnabled(false);

        jDateChooser_NgayThue.setDateFormatString("dd-MM-yyyy");

        jDateChooser_NgayTra.setDateFormatString("dd-MM-yyyy");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Ngày Thuê:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Số Giờ:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        cboMaPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnSua)
                        .addGap(46, 46, 46)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(cboMaPhong, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(253, 253, 253)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser_NgayThue, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser_NgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(156, 156, 156)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMaHoaDonPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel4)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane1))
                .addGap(336, 336, 336))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaHoaDonPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addComponent(jDateChooser_NgayThue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(jDateChooser_NgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtSoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblThongKePhong.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblThongKePhong);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(286, Short.MAX_VALUE))
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
                .addGap(330, 330, 330)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(tabs)
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

    private void tblHoaDonPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonPhongMouseClicked
//         if (evt.getClickCount() >= 1) {
//            row = tblSapXepCaLam.getSelectedRow();
//            edit();  
//        }
    }//GEN-LAST:event_tblHoaDonPhongMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyHoaDonPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new QuanLyHoaDonPhong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboMaPhong;
    private com.toedter.calendar.JDateChooser jDateChooser_NgayThue;
    private com.toedter.calendar.JDateChooser jDateChooser_NgayTra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoaDonPhong;
    private javax.swing.JTable tblThongKePhong;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaHoaDonPhong;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSoGio;
    private javax.swing.JTextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
