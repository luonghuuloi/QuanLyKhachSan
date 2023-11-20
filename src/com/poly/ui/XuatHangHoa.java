/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.ChiTietPhieuXuatHangDAO;
import com.poly.dao.HangHoaDAO;
import com.poly.dao.XuatHangDAO;
import com.poly.entity.ChiTietPhieuXuatHang;
import com.poly.entity.HangHoa;
import com.poly.entity.NhanVien;
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
public class XuatHangHoa extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public XuatHangHoa() {
        initComponents();
        init();
    }

    HangHoaDAO hhdao = new HangHoaDAO();
    XuatHangDAO xhdao = new XuatHangDAO();
    ChiTietPhieuXuatHangDAO ctpxdao = new ChiTietPhieuXuatHangDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(XuatHangHoa.this.DISPOSE_ON_CLOSE);
        loadCboMaPhieuXuatHang();
        loadCboTenHang();
        layMaHangTheoTen();
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        fillTableMaPhieuXuatHang();
        fillTableChiTietPhieuXuatHang();
        fillTableThongKeHangHoa();
    }

    void loadCboTenHang() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<HangHoa> list = hhdao.select();
        for (HangHoa hh : list) {
            model.addElement(hh);
        }
        cboTenHang.setModel(model);
    }

    void layMaHangTheoTen() {
        HangHoa hh = (HangHoa) cboTenHang.getSelectedItem();
        txtMaHang.setText(String.valueOf(hh.getMaHang()));
    }

    void fillTableMaPhieuXuatHang() {
        String header[] = {"Mã Phiếu Xuất Hàng", "Mã NV", "Ngày Xuất"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<XuatHang> list = xhdao.select();
            for (XuatHang xh : list) {
                model.addRow(new Object[]{
                    xh.getMaXuatHang(), xh.getMaNhanVien(),
                    XDate.toString(xh.getNgayXuat(), "dd-MM-yyyy")});
            }
            tblPhieuXuatHang.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            XuatHang model = getForm();
            try {
                xhdao.insert(model);
                this.fillTableMaPhieuXuatHang();
                this.fillTableThongKeHangHoa();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Tạo phiếu xuất hàng thành công");
                loadCboMaPhieuXuatHang();
            } catch (Exception e) {
                DialogHelper.alert(this, "Tạo phiếu xuất hàng thất bại");
            }
        }
    }

    public boolean checkUpdate() {
        int row = tblPhieuXuatHang.getSelectedRow();
        if (row < 0) {
            DialogHelper.alert(this, "Bạn chưa chọn mặt hàng để cập nhật");
            return false;
        }
        if (txtMaPhieuXuatHang.getText().equals("")) {
            DialogHelper.alert(this, "Mã phiếu xuất hàng không được trống");
            txtMaPhieuXuatHang.requestFocus();
            return false;
        }
        return true;
    }

    void update() {
        if (checkUpdate()) {
            if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                XuatHang model = getForm();
                try {
                    xhdao.update(model);
                    this.fillTableMaPhieuXuatHang();
                    this.fillTableThongKeHangHoa();
                    DialogHelper.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Cập nhật thất bại");
                }
            }
        }
    }

    boolean checkXoa() {
        if (txtMaPhieuXuatHang.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn phiếu để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa hay không?")) {
                String maxuathang = txtMaPhieuXuatHang.getText();
                try {
                    xhdao.delete(maxuathang);
                    this.fillTableMaPhieuXuatHang();
                    this.fillTableThongKeHangHoa();
                    this.clear();
                    btnThem.setEnabled(false);
                    DialogHelper.alert(this, "Xóa thành công");
                    // fillTable();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xóa thất bại");
                }
            }
        }

    }

    void edit() {
        String maxuathang = (String) tblPhieuXuatHang.getValueAt(row, 0);
        XuatHang xh = xhdao.findById(maxuathang);
        setForm(xh);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        XuatHang model = new XuatHang();
        model.setMaNhanVien(Auth.user.getMaNhanVien());
        model.setNgayXuat(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
        row = -1;
        updateStatus();
        txtMaPhieuXuatHang.requestFocus();
        this.setForm(model);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    void setForm(XuatHang model) {
        txtMaPhieuXuatHang.setText(model.getMaXuatHang());
        txtMaNV.setText(model.getMaNhanVien());
        jDateChooser_NgayXuat.setDate(model.getNgayXuat());
    }

    XuatHang getForm() {
        XuatHang xh = new XuatHang();
        xh.setMaXuatHang(txtMaPhieuXuatHang.getText());
        xh.setMaNhanVien(txtMaNV.getText());
        xh.setNgayXuat(jDateChooser_NgayXuat.getDate());
        return xh;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaPhieuXuatHang.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public boolean check() {
        if (xhdao.findById(txtMaPhieuXuatHang.getText()) != null) {
            DialogHelper.alert(this, "Mã phiếu xuất hàng này đã tồn tại!!");
            txtMaPhieuXuatHang.requestFocus();
            return false;
        } else {
            if (txtMaPhieuXuatHang.getText().equals("")) {
                DialogHelper.alert(this, "Mã phiếu xuất hàng không được trống");
                txtMaPhieuXuatHang.requestFocus();
                return false;
            }
        }

        return true;
    }

    void loadCboMaPhieuXuatHang() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<XuatHang> list = xhdao.phieuChuaXuat();
        for (XuatHang xh : list) {
            model.addElement(xh);
        }
        cboMaPhieuXuatHang.setModel(model);
    }

    void setFormChiTietPX(ChiTietPhieuXuatHang model) {
        cboMaPhieuXuatHang.getModel().setSelectedItem(model.getMaXuatHang());
        cboTenHang.getModel().setSelectedItem(model.getMaHang());
    }

    ChiTietPhieuXuatHang getFormChiTietPX() {
        ChiTietPhieuXuatHang ctpx = new ChiTietPhieuXuatHang();
        ctpx.setMaXuatHang(cboMaPhieuXuatHang.getSelectedItem().toString());
        ctpx.setMaHang(txtMaHang.getText());
        ctpx.setSoLuong(Double.valueOf(txtSoLuong.getText()));
        return ctpx;
    }

    void fillTableChiTietPhieuXuatHang() {
        String header[] = {"Mã Phiếu Xuất Hàng", "Mã Hàng", "Số Lượng"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            List<ChiTietPhieuXuatHang> list = ctpxdao.select();
            for (ChiTietPhieuXuatHang ctpx : list) {
                model.addRow(new Object[]{
                    ctpx.getMaXuatHang(), ctpx.getMaHang(), ctpx.getSoLuong()});
            }
            tblChiTietPhieuXuatHang.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    boolean checkChiTietPX() {
        try {
            if (txtSoLuong.getText().equals("")) {
                DialogHelper.alert(this, "Số lượng không được trống");
                txtSoLuong.requestFocus();
                return false;
            } else if (Double.valueOf(txtSoLuong.getText()) <= 0) {
                DialogHelper.alert(this, "Số lượng phải lớn hơn 0");
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Số lượng phải là số" + e);
            return false;
        }
        return true;
    }

    void insertChiTietPX() {
        if (checkChiTietPX()) {
            ChiTietPhieuXuatHang model = getFormChiTietPX();
            try {
                ctpxdao.insert(model);
                this.fillTableChiTietPhieuXuatHang();               
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Err" + e);
            }
        }
    }

    void fillTableThongKeHangHoa() {
        String header[] = {"Mã Hàng", "Tên Hàng", "Đơn Giá", "Đơn Vị Tính", "Số Lượng Còn Lại"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        List<Object[]> list = xhdao.thongKeXuatHangHoa();
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        tblThongKeHangHoa.setModel(model);

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
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jDateChooser_NgayXuat = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPhieuXuatHang = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtMaPhieuXuatHang = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboMaPhieuXuatHang = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cboTenHang = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMaHang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnDongY = new javax.swing.JButton();
        btnHuyBo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiTietPhieuXuatHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeHangHoa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Xuất Hàng");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("XUẤT HÀNG");

        tabs.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Ngày Xuất:");

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

        jDateChooser_NgayXuat.setDateFormatString("dd-MM-yyyy");

        tblPhieuXuatHang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPhieuXuatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuXuatHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPhieuXuatHang);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Mã Phiếu Xuất Hàng:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(74, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser_NgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)))
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaPhieuXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaPhieuXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jDateChooser_NgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("PHIẾU XUẤT HÀNG", jPanel1);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Tên Hàng:");

        cboMaPhieuXuatHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaPhieuXuatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaPhieuXuatHangActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Mã Phiếu Xuất Hàng:");

        cboTenHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenHangActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Số Lượng:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Mã Hàng:");

        btnDongY.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDongY.setText("Đồng Ý");
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });

        btnHuyBo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHuyBo.setText("Hủy Bỏ");
        btnHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyBoActionPerformed(evt);
            }
        });

        tblChiTietPhieuXuatHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblChiTietPhieuXuatHang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboMaPhieuXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTenHang, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addComponent(txtMaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(477, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGap(220, 220, 220)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboMaPhieuXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTenHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(jLabel11)
                    .addContainerGap(470, Short.MAX_VALUE)))
        );

        tabs.addTab("CHI TIẾT PHIẾU XUẤT HÀNG", jPanel2);

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
        jScrollPane2.setViewportView(tblThongKeHangHoa);

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
                .addContainerGap(79, Short.MAX_VALUE))
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
                .addGap(291, 291, 291)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblPhieuXuatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuXuatHangMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblPhieuXuatHang.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblPhieuXuatHangMouseClicked

    private void cboMaPhieuXuatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaPhieuXuatHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaPhieuXuatHangActionPerformed

    private void cboTenHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenHangActionPerformed
        layMaHangTheoTen();
    }//GEN-LAST:event_cboTenHangActionPerformed

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        insertChiTietPX();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyBoActionPerformed
         this.dispose();
    }//GEN-LAST:event_btnHuyBoActionPerformed

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
            java.util.logging.Logger.getLogger(XuatHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XuatHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XuatHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XuatHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new XuatHangHoa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDongY;
    private javax.swing.JButton btnHuyBo;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboMaPhieuXuatHang;
    private javax.swing.JComboBox<String> cboTenHang;
    private com.toedter.calendar.JDateChooser jDateChooser_NgayXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietPhieuXuatHang;
    private javax.swing.JTable tblPhieuXuatHang;
    private javax.swing.JTable tblThongKeHangHoa;
    private javax.swing.JTextField txtMaHang;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaPhieuXuatHang;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
