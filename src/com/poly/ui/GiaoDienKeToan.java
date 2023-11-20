/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author ADMIN
 */
public class GiaoDienKeToan extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyThuVien
     */
    public GiaoDienKeToan() {
        initComponents();
        init();
    }

    void dongho() {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                String text = formatter.format(date);
                lblDongHo.setText(text);
            }
        }).start();
    }

    void init() {
        this.setLocationRelativeTo(null);
        dongho();
        setIconImage(XImage.getAppIcon());
       // new DangNhapJDialog(this, true).setVisible(true);
        lblTenTaiKhoan.setText("Xin chào, " +Auth.user.getTenTaiKhoan());
    }

//    void openNhanVien(int index) {
//        if (Auth.isLogin()) {
//            if (index == 1 && !Auth.isManager()) {
//                DialogHelper.alert(this, "Không có quyền xem nhân viên");
//            } else {
//                QuanLyNhanVien nv = new QuanLyNhanVien();
//                nv.setVisible(true);
//            }
//        } else {
//            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
//        }
//    }

//    void checkopenNhanVien() {
//        if (Auth.isLogin()) {
//            if (!Auth.isManager()) {
//                // mniQLNhanVien.setEnabled(false);
//
//            } else if (Auth.isManager()) {
//                // mniQLNhanVien.setEnabled(true);
//            }
//        } else {
//            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
//        }
//    }
    
     void openHangHoa() {
        if (Auth.isLogin()) {
            new QuanLyHangHoa().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openXuatHangHoa() {
        if (Auth.isLogin()) {
            new XuatHangHoa().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openTienLuong() {
        if (Auth.isLogin()) {
            new QuanLyTienLuong().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openDoanhThuNgay() {
        if (Auth.isLogin()) {
            new QuanLyDoanhThuNgay().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openDoanhThuThang() {
        if (Auth.isLogin()) {
            new QuanLyDoanhThuThang().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openXemCaLamViec() {
        if (Auth.isLogin()) {
            new XemCaLamViec().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openChiPhiNgay() {
        if (Auth.isLogin()) {
            new QuanLyChiPhiNgay().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openChiPhiThang() {
        if (Auth.isLogin()) {
            new QuanLyChiPhiThang().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
      void openThongKe() {
        if (Auth.isLogin()) {
            new ThongKe().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void dangXuat() {
        if (Auth.isLogin()) {
            Auth.dangXuat();
            new DangNhapJDialog(this, true).setVisible(true);
            lblTenTaiKhoan.setText("Xin chào, " +Auth.user.getTenTaiKhoan());
           // checkopenNhanVien();
           // checkopenThongKe();
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
            new DangNhapJDialog(this, true).setVisible(true);
        }
    }
     
     void openDoiMatKhau() {
        if (Auth.isLogin()) {
            new DoiMatKhau().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập");
        }
    }
     
//     void openDangKy(int index) {
//        if (Auth.isLogin()) {
//            if (index == 1 && !Auth.isManager()) {
//                DialogHelper.alert(this, "Bạn không có quyền đăng ký tài khoản");
//            } else {
//                DangKy dk = new DangKy();
//                dk.setVisible(true);
//            }
//        } else {
//            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
//        }
//    }
     
//    void openQLTaiKhoan(int index) {
//        if (Auth.isLogin()) {
//            if (index == 1 && !Auth.isManager()) {
//                DialogHelper.alert(this, "Bạn không có quyền quản lý tài khoản");
//            } else {
//                QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
//                qltk.setVisible(true);
//            }
//        } else {
//            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
//        }
//    }

    void ketThuc() {
        if (DialogHelper.confirm(this, "Bạn có muốn thoát khỏi ứng dụng không?")) {
            System.exit(0);
        }
    }
     
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNhanCaLam = new javax.swing.JLabel();
        lblHangHoa = new javax.swing.JLabel();
        lblLuong = new javax.swing.JLabel();
        lblDoanhThuNgay = new javax.swing.JLabel();
        lblDoanhThuThang = new javax.swing.JLabel();
        lblThongKe = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        lblTenTaiKhoan = new javax.swing.JLabel();
        lblChiPhiNgay = new javax.swing.JLabel();
        lblChiPhiThang = new javax.swing.JLabel();
        lblXuatHang = new javax.swing.JLabel();
        lblHinhNen = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniDangXuat = new javax.swing.JMenuItem();
        mniDoiMatKhau = new javax.swing.JMenuItem();
        mniDangKy = new javax.swing.JMenuItem();
        mniKetThuc = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mniTaiKhoan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mniLienHe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Khách Sạn");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNhanCaLam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNhanCaLam.setText("XEM CA LÀM VIỆC");
        lblNhanCaLam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanCaLamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNhanCaLamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNhanCaLamMouseExited(evt);
            }
        });
        getContentPane().add(lblNhanCaLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));

        lblHangHoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblHangHoa.setText("HÀNG HÓA");
        lblHangHoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHangHoaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHangHoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHangHoaMouseExited(evt);
            }
        });
        getContentPane().add(lblHangHoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, -1, -1));

        lblLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLuong.setText("TIỀN LƯƠNG");
        lblLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLuongMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLuongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLuongMouseExited(evt);
            }
        });
        getContentPane().add(lblLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, -1, -1));

        lblDoanhThuNgay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDoanhThuNgay.setText("DOANH THU NGÀY");
        lblDoanhThuNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoanhThuNgayMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDoanhThuNgayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDoanhThuNgayMouseExited(evt);
            }
        });
        getContentPane().add(lblDoanhThuNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 160, 50));

        lblDoanhThuThang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDoanhThuThang.setText("DOANH THU THÁNG");
        lblDoanhThuThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoanhThuThangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDoanhThuThangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDoanhThuThangMouseExited(evt);
            }
        });
        getContentPane().add(lblDoanhThuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, -1, -1));

        lblThongKe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblThongKe.setText("THỐNG KÊ");
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThongKeMouseExited(evt);
            }
        });
        getContentPane().add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 590, -1, -1));

        lblDongHo.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 0, 0));
        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Alarm.png"))); // NOI18N
        getContentPane().add(lblDongHo, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 700, 170, 40));

        lblTenTaiKhoan.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTenTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lblTenTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Funny.png"))); // NOI18N
        getContentPane().add(lblTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, 30));

        lblChiPhiNgay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblChiPhiNgay.setText("CHI PHÍ NGÀY");
        lblChiPhiNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblChiPhiNgayMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblChiPhiNgayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblChiPhiNgayMouseExited(evt);
            }
        });
        getContentPane().add(lblChiPhiNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 590, -1, -1));

        lblChiPhiThang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblChiPhiThang.setText("CHI PHÍ THÁNG");
        lblChiPhiThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblChiPhiThangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblChiPhiThangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblChiPhiThangMouseExited(evt);
            }
        });
        getContentPane().add(lblChiPhiThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 590, -1, -1));

        lblXuatHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblXuatHang.setText("XUẤT HÀNG");
        lblXuatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXuatHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXuatHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXuatHangMouseExited(evt);
            }
        });
        getContentPane().add(lblXuatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 190, -1, -1));

        lblHinhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/giaodienchinh.png"))); // NOI18N
        getContentPane().add(lblHinhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1150, 770));

        jMenu1.setText("Hệ thống");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Log out.png"))); // NOI18N
        mniDangXuat.setText("Đăng xuất");
        mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangXuatActionPerformed(evt);
            }
        });
        jMenu1.add(mniDangXuat);

        mniDoiMatKhau.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Refresh.png"))); // NOI18N
        mniDoiMatKhau.setText("Đổi mật khẩu");
        mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoiMatKhauActionPerformed(evt);
            }
        });
        jMenu1.add(mniDoiMatKhau);

        mniDangKy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDangKy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDangKy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Create.png"))); // NOI18N
        mniDangKy.setText("Đăng ký");
        mniDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangKyActionPerformed(evt);
            }
        });
        jMenu1.add(mniDangKy);

        mniKetThuc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        mniKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Stop.png"))); // NOI18N
        mniKetThuc.setText("Kết thúc");
        mniKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKetThucActionPerformed(evt);
            }
        });
        jMenu1.add(mniKetThuc);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tài khoản");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniTaiKhoan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Microsoft.png"))); // NOI18N
        mniTaiKhoan.setText("Quản lý tài khoản");
        mniTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTaiKhoanActionPerformed(evt);
            }
        });
        jMenu3.add(mniTaiKhoan);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Trợ giúp");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniLienHe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniLienHe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniLienHe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Brick house.png"))); // NOI18N
        mniLienHe.setText("Địa chỉ liên hệ");
        mniLienHe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLienHeActionPerformed(evt);
            }
        });
        jMenu2.add(mniLienHe);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblNhanCaLamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanCaLamMouseClicked
       // openNhanVien(1);
        openXemCaLamViec();
    }//GEN-LAST:event_lblNhanCaLamMouseClicked

    private void lblNhanCaLamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanCaLamMouseEntered
        lblNhanCaLam.setText("<html><b>XEM CA LÀM VIỆC</b></html>");
        lblNhanCaLam.setForeground(Color.red);
    }//GEN-LAST:event_lblNhanCaLamMouseEntered

    private void lblNhanCaLamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanCaLamMouseExited
        lblNhanCaLam.setText("<html>XEM CA LÀM VIỆC</html>");
        lblNhanCaLam.setForeground(Color.black);
    }//GEN-LAST:event_lblNhanCaLamMouseExited

    private void lblHangHoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHangHoaMouseClicked
        openHangHoa();
    }//GEN-LAST:event_lblHangHoaMouseClicked

    private void lblHangHoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHangHoaMouseEntered
        lblHangHoa.setText("<html><b>HÀNG HÓA</b></html>");
        lblHangHoa.setForeground(Color.red);
    }//GEN-LAST:event_lblHangHoaMouseEntered

    private void lblHangHoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHangHoaMouseExited
        lblHangHoa.setText("<html>HÀNG HÓA</html>");
        lblHangHoa.setForeground(Color.black);
    }//GEN-LAST:event_lblHangHoaMouseExited

    private void lblLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLuongMouseClicked
        openTienLuong();
    }//GEN-LAST:event_lblLuongMouseClicked

    private void lblLuongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLuongMouseEntered
        lblLuong.setText("<html><b>TIỀN LƯƠNG</b></html>");
        lblLuong.setForeground(Color.red);
    }//GEN-LAST:event_lblLuongMouseEntered

    private void lblLuongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLuongMouseExited
        lblLuong.setText("<html>TIỀN LƯƠNG</html>");
        lblLuong.setForeground(Color.black);
    }//GEN-LAST:event_lblLuongMouseExited

    private void lblDoanhThuNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuNgayMouseClicked
        openDoanhThuNgay();
    }//GEN-LAST:event_lblDoanhThuNgayMouseClicked

    private void lblDoanhThuNgayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuNgayMouseEntered
        lblDoanhThuNgay.setText("<html><b>DOANH THU NGÀY</b></html>");
        lblDoanhThuNgay.setForeground(Color.red);
    }//GEN-LAST:event_lblDoanhThuNgayMouseEntered

    private void lblDoanhThuNgayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuNgayMouseExited
        lblDoanhThuNgay.setText("<html>DOANH THU NGÀY</html>");
        lblDoanhThuNgay.setForeground(Color.black);
    }//GEN-LAST:event_lblDoanhThuNgayMouseExited

    private void lblDoanhThuThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuThangMouseClicked
        openDoanhThuThang();
    }//GEN-LAST:event_lblDoanhThuThangMouseClicked

    private void lblDoanhThuThangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuThangMouseEntered
        lblDoanhThuThang.setText("<html><b>DOANH THU THÁNG</b></html>");
        lblDoanhThuThang.setForeground(Color.red);
    }//GEN-LAST:event_lblDoanhThuThangMouseEntered

    private void lblDoanhThuThangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoanhThuThangMouseExited
        lblDoanhThuThang.setText("<html>DOANH THU THÁNG</html>");
        lblDoanhThuThang.setForeground(Color.black);
    }//GEN-LAST:event_lblDoanhThuThangMouseExited

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
        openThongKe();
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseEntered
        lblThongKe.setText("<html><b>THỐNG KÊ</b></html>");
        lblThongKe.setForeground(Color.red);
    }//GEN-LAST:event_lblThongKeMouseEntered

    private void lblThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseExited
        lblThongKe.setText("<html>THỐNG KÊ</html>");
        lblThongKe.setForeground(Color.black);
    }//GEN-LAST:event_lblThongKeMouseExited

    private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangXuatActionPerformed
        dangXuat();
    }//GEN-LAST:event_mniDangXuatActionPerformed

    private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoiMatKhauActionPerformed
        openDoiMatKhau();
    }//GEN-LAST:event_mniDoiMatKhauActionPerformed

    private void mniKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKetThucActionPerformed
        ketThuc();
    }//GEN-LAST:event_mniKetThucActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát không?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }else{
                System.exit(0);
            }
    }//GEN-LAST:event_formWindowClosing

    private void mniDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangKyActionPerformed
      //  openDangKy(1);
    }//GEN-LAST:event_mniDangKyActionPerformed

    private void mniTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTaiKhoanActionPerformed
      //  openQLTaiKhoan(1);
    }//GEN-LAST:event_mniTaiKhoanActionPerformed

    private void mniLienHeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLienHeActionPerformed
         DialogHelper.alert(this, "Mọi chi tiết gửi về Email: hoahonghotel@gmail.com");
    }//GEN-LAST:event_mniLienHeActionPerformed

    private void lblXuatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXuatHangMouseClicked
        openXuatHangHoa();
    }//GEN-LAST:event_lblXuatHangMouseClicked

    private void lblXuatHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXuatHangMouseEntered
        lblXuatHang.setText("<html><b>XUẤT HÀNG</b></html>");
        lblXuatHang.setForeground(Color.red);
    }//GEN-LAST:event_lblXuatHangMouseEntered

    private void lblXuatHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXuatHangMouseExited
        lblXuatHang.setText("<html>XUẤT HÀNG</html>");
        lblXuatHang.setForeground(Color.black);
    }//GEN-LAST:event_lblXuatHangMouseExited

    private void lblChiPhiNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiNgayMouseClicked
        openChiPhiNgay();
    }//GEN-LAST:event_lblChiPhiNgayMouseClicked

    private void lblChiPhiNgayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiNgayMouseEntered
        lblChiPhiNgay.setText("<html><b>CHI PHÍ NGÀY</b></html>");
        lblChiPhiNgay.setForeground(Color.red);
    }//GEN-LAST:event_lblChiPhiNgayMouseEntered

    private void lblChiPhiNgayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiNgayMouseExited
        lblChiPhiNgay.setText("<html>CHI PHÍ NGÀY</html>");
        lblChiPhiNgay.setForeground(Color.black);
    }//GEN-LAST:event_lblChiPhiNgayMouseExited

    private void lblChiPhiThangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiThangMouseClicked
        openChiPhiThang();
    }//GEN-LAST:event_lblChiPhiThangMouseClicked

    private void lblChiPhiThangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiThangMouseEntered
        lblChiPhiThang.setText("<html><b>CHI PHÍ THÁNG</b></html>");
        lblChiPhiThang.setForeground(Color.red);
    }//GEN-LAST:event_lblChiPhiThangMouseEntered

    private void lblChiPhiThangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChiPhiThangMouseExited
        lblChiPhiThang.setText("<html>CHI PHÍ THÁNG</html>");
        lblChiPhiThang.setForeground(Color.black);
    }//GEN-LAST:event_lblChiPhiThangMouseExited

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
            java.util.logging.Logger.getLogger(GiaoDienKeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiaoDienKeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiaoDienKeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiaoDienKeToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiaoDienKeToan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblChiPhiNgay;
    private javax.swing.JLabel lblChiPhiThang;
    private javax.swing.JLabel lblDoanhThuNgay;
    private javax.swing.JLabel lblDoanhThuThang;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblHangHoa;
    private javax.swing.JLabel lblHinhNen;
    private javax.swing.JLabel lblLuong;
    private javax.swing.JLabel lblNhanCaLam;
    private javax.swing.JLabel lblTenTaiKhoan;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblXuatHang;
    private javax.swing.JMenuItem mniDangKy;
    private javax.swing.JMenuItem mniDangXuat;
    private javax.swing.JMenuItem mniDoiMatKhau;
    private javax.swing.JMenuItem mniKetThuc;
    private javax.swing.JMenuItem mniLienHe;
    private javax.swing.JMenuItem mniTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
