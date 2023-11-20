/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.LuongDAO;
import com.poly.dao.NhanVienDAO;
import com.poly.entity.Luong;
import com.poly.entity.NhanVien;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyTienLuong extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLyTienLuong() {
        initComponents();
        init();
    }

    LuongDAO dao = new LuongDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyTienLuong.this.DISPOSE_ON_CLOSE);
        loadCboTenNV();
        layMaNVTheoTen();
        fillTable();
        fillTableThongKeTienLuong();
    }
    
     void loadCboTenNV() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<NhanVien> list = nvdao.select();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
        cboTenNV.setModel(model);
    }

    void layMaNVTheoTen() {
        NhanVien nv = (NhanVien) cboTenNV.getSelectedItem();
        txtMaNV.setText(String.valueOf(nv.getMaNhanVien()));       
    }

    void fillTable() {
        String header[] = {"Mã Lương", "Mã NV", "Tháng", "Năm", "Lương CB", "Phụ Cấp", "Số Ngày LV", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<Luong> list = dao.select();
            for (Luong luong : list) {
                model.addRow(new Object[]{
                    luong.getMaLuong(), luong.getMaNhanVien(), luong.getThang(),
                    luong.getNam(), luong.getLuongCoBan(), luong.getPhuCap(), 
                    luong.getSoNgayLamViec(), luong.isTrangThai() ? "Đã trả lương" : "Chưa trả lương" });
            }
            tblLuong.setModel(model);
            //Điều chỉnh độ rộng các cột
            tblLuong.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblLuong.getColumnModel().getColumn(1).setPreferredWidth(20);
            tblLuong.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblLuong.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblLuong.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblLuong.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblLuong.getColumnModel().getColumn(6).setPreferredWidth(30);
            tblLuong.getColumnModel().getColumn(7).setPreferredWidth(40);

            //Canh lề dữ liệu trong bảng
            DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
            canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            tblLuong.getColumn("Mã Lương").setCellRenderer(canhLe);
            tblLuong.getColumn("Mã NV").setCellRenderer(canhLe);
            tblLuong.getColumn("Tháng").setCellRenderer(canhLe);
            tblLuong.getColumn("Năm").setCellRenderer(canhLe);
            tblLuong.getColumn("Số Ngày LV").setCellRenderer(canhLe);

        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void insert() {
        if (check()) {
            Luong model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                this.fillTableThongKeTienLuong();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm tiền lương thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm tiền lương thất bại");
            }
        }
    }

    public boolean checkUpdate() {
        int row = tblLuong.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào để cập nhật");
            return false;
        }
        if (txtMaLuong.getText().equals("")) {
            DialogHelper.alert(this, "Mã tiền lương không được trống");
            txtMaLuong.requestFocus();
            return false;
        } else {
            try {
                if (txtThang.getText().equals("")) {
                    DialogHelper.alert(this, "Tháng không được trống");
                    txtThang.requestFocus();
                    return false;
                } else if (Integer.valueOf(txtThang.getText()) <= 0) {
                    DialogHelper.alert(this, "Tháng phải lớn hơn 0");
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
            } else if (Integer.valueOf(txtNam.getText()) <= 0) {
                DialogHelper.alert(this, "Năm phải lớn hơn 0");
                txtThang.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Năm phải là số");
            return false;
        }

        try {
            if (txtLuongCoBan.getText().equals("")) {
                DialogHelper.alert(this, "Lương cơ bản không được trống");
                txtLuongCoBan.requestFocus();
                return false;
            } else if (Double.valueOf(txtLuongCoBan.getText()) <= 0) {
                DialogHelper.alert(this, "Lương cơ bản phải lớn hơn 0");
                txtLuongCoBan.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lương cơ bản phải là số");
            return false;
        }
        
         try {
            if (txtPhuCap.getText().equals("")) {
                DialogHelper.alert(this, "Phụ cấp không được trống");
                txtPhuCap.requestFocus();
                return false;
            } else if (Double.valueOf(txtPhuCap.getText()) <= 0) {
                DialogHelper.alert(this, "Phụ cấp phải lớn hơn 0");
                txtPhuCap.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Phụ cấp phải là số");
            return false;
        }
         
         try {
            if (txtSoNgayLamViec.getText().equals("")) {
                DialogHelper.alert(this, "Số ngày làm việc không được trống");
                txtSoNgayLamViec.requestFocus();
                return false;
            } else if ((Double.valueOf(txtSoNgayLamViec.getText()) <= 0) && (Double.valueOf(txtSoNgayLamViec.getText()) > 31) ) {
                DialogHelper.alert(this, " Số ngàỳ làm việc không hợp lệ");
                txtSoNgayLamViec.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Số ngày làm việc phải là số");
            return false;
        }
        return true;
        
    }

    void update() {
        if (checkUpdate()) {
            if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                Luong model = getForm();
                try {
                    dao.update(model);
                    this.fillTable();
                    this.fillTableThongKeTienLuong();
                    this.fillTableThongKeTienLuong();
                    DialogHelper.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Cập nhật thất bại");
                }
            }
        }
    }

    boolean checkXoa() {
        if (txtMaLuong.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn dữ liệu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa hay không?")) {
                String maluong = txtMaLuong.getText();
                try {
                    dao.delete(maluong);
                    this.fillTable();
                    this.fillTableThongKeTienLuong();
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
        String maluong = (String) tblLuong.getValueAt(row, 0);       
        Luong luong = dao.findById(maluong);    
        setForm(luong);
        tabs.setSelectedIndex(0);
        updateStatus();
    }
    

    void clear() {
        Luong model = new Luong();
        row = -1;
        updateStatus();
        txtMaLuong.requestFocus();
        this.setForm(model);
    }

    void setForm(Luong model) {
        txtMaLuong.setText(model.getMaLuong());
        
       // cboTenNV.setSelectedItem(nvdao.findById(model2.getHoTenNV()));
        
        txtMaNV.setText(model.getMaNhanVien());
        txtThang.setText(String.valueOf(model.getThang()));
        txtNam.setText(String.valueOf(model.getNam()));
        txtLuongCoBan.setText(String.valueOf(model.getLuongCoBan()));
        txtPhuCap.setText(String.valueOf(model.getPhuCap()));
        txtSoNgayLamViec.setText(String.valueOf(model.getSoNgayLamViec()));
    }
    

    Luong getForm() {
        Luong luong = new Luong();
        luong.setMaLuong(txtMaLuong.getText());
        luong.setMaNhanVien(txtMaNV.getText());
        luong.setThang(Integer.valueOf(txtThang.getText()));
        luong.setNam(Integer.valueOf(txtNam.getText()));
        luong.setLuongCoBan(Double.valueOf(txtLuongCoBan.getText()));
        luong.setPhuCap(Double.valueOf(txtPhuCap.getText()));
        luong.setSoNgayLamViec(Double.valueOf(txtSoNgayLamViec.getText()));
        luong.setTrangThai(false);
        return luong;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaLuong.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (dao.findById(txtMaLuong.getText()) != null) {
            DialogHelper.alert(this, "Mã lương này đã tồn tại!!");
            txtMaLuong.requestFocus();
            return false;
        } else {
             try {
                if (txtThang.getText().equals("")) {
                    DialogHelper.alert(this, "Tháng không được trống");
                    txtThang.requestFocus();
                    return false;
                } else if (Integer.valueOf(txtThang.getText()) <= 0) {
                    DialogHelper.alert(this, "Tháng phải lớn hơn 0");
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
            } else if (Integer.valueOf(txtNam.getText()) <= 0) {
                DialogHelper.alert(this, "Năm phải lớn hơn 0");
                txtThang.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Năm phải là số");
            return false;
        }

        try {
            if (txtLuongCoBan.getText().equals("")) {
                DialogHelper.alert(this, "Lương cơ bản không được trống");
                txtLuongCoBan.requestFocus();
                return false;
            } else if (Double.valueOf(txtLuongCoBan.getText()) <= 0) {
                DialogHelper.alert(this, "Lương cơ bản phải lớn hơn 0");
                txtLuongCoBan.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lương cơ bản phải là số");
            return false;
        }
        
         try {
            if (txtPhuCap.getText().equals("")) {
                DialogHelper.alert(this, "Phụ cấp không được trống");
                txtPhuCap.requestFocus();
                return false;
            } else if (Double.valueOf(txtPhuCap.getText()) <= 0) {
                DialogHelper.alert(this, "Phụ cấp phải lớn hơn 0");
                txtPhuCap.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Phụ cấp phải là số");
            return false;
        }
         
         try {
            if (txtSoNgayLamViec.getText().equals("")) {
                DialogHelper.alert(this, "Số ngày làm việc không được trống");
                txtSoNgayLamViec.requestFocus();
                return false;
            } else if ((Double.valueOf(txtSoNgayLamViec.getText()) <= 0) && (Integer.valueOf(txtSoNgayLamViec.getText()) > 31) ) {
                DialogHelper.alert(this, " Số ngàỳ làm việc không hợp lệ");
                txtSoNgayLamViec.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Số ngày làm việc phải là số");
            return false;
        }
        return true;
    }
    
     Luong getFormTraLuong() {
        Luong luong = new Luong();
        luong.setMaLuong(txtMaLuong.getText());
        luong.setMaNhanVien(txtMaNV.getText());
        luong.setThang(Integer.valueOf(txtThang.getText()));
        luong.setNam(Integer.valueOf(txtNam.getText()));
        luong.setLuongCoBan(Double.valueOf(txtLuongCoBan.getText()));
        luong.setPhuCap(Double.valueOf(txtPhuCap.getText()));
        luong.setSoNgayLamViec(Double.valueOf(txtSoNgayLamViec.getText()));
        luong.setTrangThai(true);
        return luong;
    }
     
    boolean checkTraLuong(){
          int row = tblThongKeTienLuong.getSelectedRow();
            if (row < 0) {
                DialogHelper.alert(this, "Bạn chưa chọn dữ liệu nào trong bảng");
                return false;
            }
            return true;
    }
    
     void traLuong() {
        if(checkTraLuong()){
             
          if (DialogHelper.confirm(this, "Bạn có chắc chắn trả lương cho nhân viên này không?")) {
            Luong model = getFormTraLuong();
            try {
                dao.update(model);
                this.fillTable();
                this.fillTableThongKeTienLuong();
                DialogHelper.alert(this, "Trả lương thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Trả lương thất bại");
            }
        }
        }
      
    }
     
     void fillTableThongKeTienLuong() {
        String header[] = {"Mã Lương", "Mã NV", "Họ Tên", "Chức Vụ", "Tháng", "Năm", "Tổng Tiền Lương", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = dao.thongKeTienLuong();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblThongKeTienLuong.setModel(model);
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
        txtThang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLuongCoBan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaLuong = new javax.swing.JTextField();
        txtPhuCap = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSoNgayLamViec = new javax.swing.JTextField();
        txtNam = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLuong = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeTienLuong = new javax.swing.JTable();
        btnTraLuong = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Tiền Lương");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("QUẢN LÝ TIỀN LƯƠNG ");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Chọn Nhân Viên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("Tháng:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Năm:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Lương Cơ Bản:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Phụ Cấp:");

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
        jLabel10.setText("Số Ngày Làm Việc:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Mã Tiền Lương:");

        cboTenNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNVActionPerformed(evt);
            }
        });

        tblLuong.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLuongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLuong);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPhuCap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(txtLuongCoBan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtSoNgayLamViec, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addGap(78, 78, 78))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPhuCap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtSoNgayLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnThem))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblThongKeTienLuong.setModel(new javax.swing.table.DefaultTableModel(
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
        tblThongKeTienLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeTienLuongMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThongKeTienLuong);

        btnTraLuong.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnTraLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Refresh.png"))); // NOI18N
        btnTraLuong.setText("Trả Lương");
        btnTraLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTraLuong)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnTraLuong)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(351, Short.MAX_VALUE))
        );

        tabs.addTab("TRẢ LƯƠNG", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLuongMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblLuong.getSelectedRow();
            edit();
  
        }
    }//GEN-LAST:event_tblLuongMouseClicked

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

    private void btnTraLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraLuongActionPerformed
        traLuong();
    }//GEN-LAST:event_btnTraLuongActionPerformed

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
        layMaNVTheoTen();
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void tblThongKeTienLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeTienLuongMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblThongKeTienLuong.getSelectedRow();
            edit();  
        }
    }//GEN-LAST:event_tblThongKeTienLuongMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyTienLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyTienLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyTienLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyTienLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyTienLuong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTraLuong;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLuong;
    private javax.swing.JTable tblThongKeTienLuong;
    private javax.swing.JTextField txtLuongCoBan;
    private javax.swing.JTextField txtMaLuong;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtPhuCap;
    private javax.swing.JTextField txtSoNgayLamViec;
    private javax.swing.JTextField txtThang;
    // End of variables declaration//GEN-END:variables
}
