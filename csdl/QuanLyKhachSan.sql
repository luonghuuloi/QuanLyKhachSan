CREATE DATABASE QuanLyKhachSan
GO

USE QuanLyKhachSan
GO

CREATE TABLE NhanVien(
    MaNV VARCHAR(10) NOT NULL PRIMARY KEY,
	HoTen NVARCHAR(50) NOT NULL,
    NgaySinh DATE NOT NULL,
	CMND VARCHAR(20) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    SDT VARCHAR(15) NOT NULL,
	GioiTinh BIT NOT NULL,
	DiaChi NVARCHAR(100) NOT NULL,
	Hinh NVARCHAR(50) NOT NULL,
);

CREATE TABLE TaiKhoan(
    TenTaiKhoan VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	MatKhau VARCHAR(20) NOT NULL,
	ChucVu NVARCHAR(20) NOT NULL,
	VaiTro BIT NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE CaLam(
    MaCaLam VARCHAR(15) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	NgayLam DATE NOT NULL,
	Ca INT NOT NULL,
	NhanCa BIT NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE Luong(
    MaLuong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	Thang INT NOT NULL,
	Nam INT NOT NULL,
	LuongCoBan FLOAT NOT NULL,
	PhuCap FLOAT NULL,
	SoNgayLamViec FLOAT NOT NULL,
	TrangThai BIT NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE HangHoa(
    MaHang VARCHAR(10) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	TenHang NVARCHAR(50) NOT NULL,
	DVT NVARCHAR(10) NOT NULL,
	SoLuong FLOAT NOT NULL,
	DonGia FLOAT NOT NULL,
	NgayNhap DATE NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE ChiPhiHangNgay(
    MaCPN VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	TienSuaChua FLOAT NULL,
	TienDiLai FLOAT NULL,
	ChiPhiKhac FLOAT NULL,
	NgayNhap DATE NOT NULL,
	GhiChu NVARCHAR(100)
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE ChiPhiThang(
    MaCPT VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	Thang INT NOT NULL,
	Nam INT NOT NULL,
	TongChiPhiNgay FLOAT NOT NULL,
	TienDien FLOAT NOT NULL,
	TienNuoc FLOAT NOT NULL,
	TienLuong FLOAT NOT NULL,
	TienThue FLOAT NULL,
	TienNhapHang FLOAT NOT NULL,
	GhiChu NVARCHAR(100)
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE GiaPhong(
    MaPhong VARCHAR(10) NOT NULL PRIMARY KEY,
	LoaiPhong NVARCHAR(20) NOT NULL,
    GiaTheoGio FLOAT NOT NULL,
	GiaTheoNgay FLOAT NOT NULL,
	TrangThai INT NOT NULL,
);

CREATE TABLE HoaDonPhong(
    MaHoaDonPhong VARCHAR(20) NOT NULL PRIMARY KEY,
    MaPhong VARCHAR(10) NOT NULL,
	MaNV VARCHAR(10) NOT NULL,
	TenKhachHang NVARCHAR(50) NOT NULL,
	CMND VARCHAR(20) NOT NULL,
	NgayThue DATE NOT NULL,
	NgayTra DATE NULL,
	CHECK (NgayTra >= NgayThue OR NgayTra IS NULL),
	SoGio FLOAT NULL,
	TongTienPhong FLOAT NULL,
	GhiChu NVARCHAR(100)
	FOREIGN KEY(MaPhong) REFERENCES GiaPhong(MaPhong),
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE DichVu(
    MaDichVu VARCHAR(10) NOT NULL PRIMARY KEY,
	TenDichVu NVARCHAR(50) NOT NULL,
    DonGia FLOAT NOT NULL,
);

CREATE TABLE HoaDonDichVu(
    MaHoaDonDV VARCHAR(10) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	TenKhachHang NVARCHAR(50) NOT NULL,
	NgayNhap DATE NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE ChiTietHoaDonDichVu(
	MaHoaDonDV VARCHAR(10) NOT NULL REFERENCES HoaDonDichVu(MaHoaDonDV),
	MaDichVu VARCHAR(10) NOT NULL REFERENCES DichVu(MaDichVu),
	SoLuong INT NOT NULL,
    GhiChu NVARCHAR(100),
	PRIMARY KEY (MaHoaDonDV, MaDichVu)
);

CREATE TABLE DoanhThuHangNgay(
    MaDTN VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	TongTienPhong FLOAT NOT NULL,
	TongTienDV FLOAT NOT NULL,
	NgayNhap DATE NOT NULL,
	GhiChu NVARCHAR(100)
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
	
);

CREATE TABLE DoanhThuThang(
    MaDTT VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	Thang INT NOT NULL,
	Nam INT NOT NULL,
	TongDoanhThuNgay FLOAT NOT NULL,
	GhiChu NVARCHAR(100)
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE XuatHang(
    MaXuatHang VARCHAR(20) NOT NULL PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
	NgayXuat DATE NOT NULL
	FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE ChiTietPhieuXuatHang(
	MaXuatHang VARCHAR(20) NOT NULL REFERENCES XuatHang(MaXuatHang),
	MaHang VARCHAR(10) NOT NULL REFERENCES HangHoa(MaHang),
	SoLuong FLOAT NOT NULL,
	PRIMARY KEY (MaXuatHang, MaHang)
);

--Nhập dữ liệu vào bảng
--NHÂN VIÊN
INSERT INTO NhanVien(MaNV, HoTen, NgaySinh, CMND, Email, SDT, GioiTinh, DiaChi, Hinh)
    VALUES ('QL01', N'Nguyễn Văn An','1980-02-10', '364895221','annv@gmail.com', '0903456789' ,1, N'Cần Thơ', N'1'),
           ('LT01', N'Huỳnh Thị Hoa','1982-05-18', '396554788','hoaht@gmail.com', '0983489669' ,0, N'An Giang', N'2'),
		   ('KT01', N'Lương Hữu Lợi','1979-10-20', '356774225','cuongtc@gmail.com', '0944454777' ,1, N'Cà Mau', N'3')

--TÀI KHOẢN
INSERT INTO TaiKhoan(TenTaiKhoan, MaNV, MatKhau, ChucVu, VaiTro)
    VALUES ('Annv01', 'QL01','123', N'Quản Lý', 1),
		   ('Hoaht01', 'LT01','123', N'Lễ Tân', 0),
		   ('Loilh01', 'KT01','123', N'Kế Toán', 0)

--CA LÀM
INSERT INTO CaLam(MaCaLam, MaNV, NgayLam, Ca, NhanCa)
    VALUES ('QL011', 'QL01','2021-11-15', 1,0),
		   ('LT012', 'LT01','2021-11-15', 2,0),
		   ('KT013', 'KT01','2021-11-15', 3,1)

--LƯƠNG
INSERT INTO Luong(MaLuong, MaNV, Thang, Nam, LuongCoBan, PhuCap, SoNgayLamViec, TrangThai)
    VALUES ('QL011121', 'QL01', 11, 2021, 10000000, 200000, 30, 0),
		   ('LT011121', 'LT01',11, 2021,8000000, 100000, 30, 0),
		   ('KT011121', 'KT01',11, 2021,8000000, 150000, 30, 0)
		   
-- HÀNG HÓA
INSERT INTO HangHoa(MaHang, MaNV, TenHang, DVT, SoLuong, DonGia, NgayNhap)
    VALUES ('HH01', 'KT01',N'Mì Hảo Hảo', N'Thùng',10,80000, '2021-11-01'),
		   ('HH02', 'KT01',N'Coffee Trung Nguyên', N'Gói',20,20000, '2021-11-01'),
		   ('HH03', 'KT01',N'Nước uống Lavie', N'Thùng',15,30000, '2021-11-01'),
		   ('HH04', 'KT01',N'Thịt bò', 'Kg',10,220000, '2021-12-01')

--CHI PHÍ HÀNG NGÀY
INSERT INTO ChiPhiHangNgay(MaCPN, MaNV, TienSuaChua, TienDiLai, ChiPhiKhac, NgayNhap, GhiChu)
    VALUES ('CPN011121', 'KT01', 1000000, 30000, 10000, '2021-11-01', N'Sửa tủ lạnh 1000000 và Tiền in 10000'),
		   ('CPN021121', 'KT01', 0, 20000, 5000, '2021-11-02', N'Photocopy 5000'),
		   ('CPN031121', 'KT01', 500000, 0, 0, '2021-11-03', N'Sửa máy lạnh 500000')

--CHI PHÍ THÁNG
INSERT INTO ChiPhiThang(MaCPT, MaNV, Thang, Nam, TongChiPhiNgay, TienDien, TienNuoc, TienLuong, TienThue, TienNhapHang, GhiChu)
    VALUES ('CPT1121', 'KT01', 11, 2021, 1565000, 5000000, 2000000, 26000000, 0, 1650000, N'Tháng này chi phí ổn')

--GIÁ PHÒNG		0: phòng trống		1: đã có người ở	2:đã đặt trước và trả tiền		3: đã đặt trước chưa trả tiền		4: phòng đang sửa chữa
INSERT INTO GiaPhong(MaPhong, LoaiPhong, GiaTheoGio, GiaTheoNgay, TrangThai)
    VALUES ('101', N'Giường Đơn', 80000, 300000,1),
		   ('102', N'Giường Đơn', 80000, 300000,1),
		   ('103', N'Giường Đơn', 80000, 300000,0),
		   ('104', N'Giường Đơn', 80000, 300000,2),
		   ('105', N'Giường Đơn', 80000, 300000,0),
		   ('201', N'Giường Đôi', 120000, 500000,1),
		   ('202', N'Giường Đôi', 120000, 500000,0),
		   ('203', N'Giường Đôi', 120000, 500000,0),
		   ('204', N'Giường Đôi', 120000, 500000,3),
		   ('205', N'Giường Đôi', 120000, 500000,4),
		   ('301', N'Phòng VIP', 150000, 800000,1),
		   ('302', N'Phòng VIP', 150000, 800000,2),
		   ('303', N'Phòng VIP', 150000, 800000,2),
		   ('304', N'Phòng VIP', 150000, 800000,0),
		   ('305', N'Phòng VIP', 150000, 800000,4)

--HÓA ĐƠN PHÒNG
INSERT INTO HoaDonPhong(MaHoaDonPhong, MaPhong, MaNV, TenKhachHang, CMND, NgayThue, NgayTra, SoGio, GhiChu)
    VALUES ('103KH01', '103','LT01', N'Lê Văn Tuấn', '364116784', '2021-11-11', '2021-12-21', 2, N'Cảm ơn quý khách'),
		   ('304KH01', '304','LT01', N'Nguyễn Thị Mai', '363945552', '2021-11-11', '2021-12-21', NULL, N'Cảm ơn quý khách')

--DỊCH VỤ
INSERT INTO DichVu(MaDichVu, TenDichVu, DonGia)
    VALUES ('MX01', N'Mì xào bò', 45000),
		   ('MX02', N'Mì xào trứng', 30000),
		   ('COM01', N'Cơm chiên hải sản', 70000),
		   ('CF01', N'Cà phê đen', 20000),
		   ('CF02', N'Cà phê sữa', 25000)

--HÓA ĐƠN DỊCH VỤ
INSERT INTO HoaDonDichVu(MaHoaDonDV, MaNV, TenKhachHang, NgayNhap)
    VALUES ('HD01', 'LT01', N'Lê Văn Tuấn', '2021-11-11'),
		   ('HD02', 'LT01', N'Nguyễn Thị Mai', '2021-11-11')

--CHI TIẾT HÓA ĐƠN DỊCH VỤ
INSERT INTO ChiTietHoaDonDichVu(MaHoaDonDV, MaDichVu, SoLuong, GhiChu)
    VALUES ('HD01', 'MX01', 1, N'Mì ít hành'),
		   ('HD01', 'CF01', 1, N'Cà phê ít đường'),
		   ('HD02', 'CF02', 1, N'Cà phê nhiều sữa')

--DOANH THU HẰNG NGÀY
INSERT INTO DoanhThuHangNgay(MaDTN, MaNV, TongTienPhong, TongTienDV, NgayNhap, GhiChu)
    VALUES ('111121', 'KT01', 160000, 90000, '2021-11-11', N'Doanh thu hôm nay ổn')

--DOANH THU THÁNG
INSERT INTO DoanhThuThang(MaDTT, MaNV, Thang, Nam, TongDoanhThuNgay, GhiChu)
    VALUES ('DT1121', 'KT01', 11, 2021, 1800000, N'Doanh thu tháng này ổn')

--XUẤT HÀNG
INSERT INTO XuatHang(MaXuatHang, MaNV, NgayXuat)
    VALUES ('XH01', 'KT01', '2021-11-28'),
			('XH02', 'KT01', '2021-11-29')

--CHI TIET PHIEU XUAT HANG
INSERT INTO ChiTietPhieuXuatHang(MaXuatHang, MaHang, SoLuong)
    VALUES ('XH01', 'HH01', 1),
		   ('XH01', 'HH02', 1),
		   ('XH02', 'HH01', 1)

--TÍNH TOÁN BẢNG HÀNG HÓA		   
SELECT * FROM HangHoa
	
SELECT MaHang, MaNV, TenHang, SoLuong, DonGia, NgayNhap, SoLuong * DonGia AS N'Thành Tiền' FROM HangHoa	   

SELECT SUM(SoLuong * DonGia) AS N'Tổng tiền hàng trong tháng' FROM HangHoa WHERE MONTH(NgayNhap) = 11 AND YEAR(NgayNhap) = 2021;

select MONTH(NgayNhap) From HangHoa group by NgayNhap

--TÍNH TOÁN BẢNG LƯƠNG
SELECT Luong.MaLuong, Luong.MaNV, NhanVien.HoTen, TaiKhoan.ChucVu, Luong.Thang, Luong.Nam, ROUND (((Luong.LuongCoBan + Luong.PhuCap)/26 * Luong.SoNgayLamViec),-3) AS N'Tổng Lương', Luong.TrangThai 
FROM Luong INNER JOIN NhanVien ON Luong.MaNV = NhanVien.MaNV INNER JOIN TaiKhoan ON NhanVien.MaNV = TaiKhoan.MaNV WHERE Luong.TrangThai = 0

SELECT ROUND(SUM((LuongCoBan + PhuCap)/26 * SoNgayLamViec),-3) AS N'Tổng tiền lương trong tháng' FROM Luong WHERE Thang = 11 AND Nam = 2021;

--TÍNH TOÁN BẢNG CHI PHÍ NGÀY
SELECT ChiPhiHangNgay.MaCPN, ChiPhiHangNgay.TienSuaChua + ChiPhiHangNgay.TienDiLai + ChiPhiHangNgay.ChiPhiKhac AS N'Tổng Chi Phí Trong Ngày', NhanVien.HoTen, ChiPhiHangNgay.NgayNhap 
FROM ChiPhiHangNgay INNER JOIN NhanVien ON ChiPhiHangNgay.MaNV = NhanVien.MaNV

SELECT SUM(TienSuaChua + TienDiLai + ChiPhiKhac) AS N'Tổng Chi Phí Ngày Trong Tháng' FROM ChiPhiHangNgay WHERE MONTH(NgayNhap) = 11 AND YEAR(NgayNhap) = 2021;

--TRUY VẤN BẢNG CA LÀM
SELECT * FROM CaLam WHERE MaNV = 'KT01'

--THỐNG KÊ DOANH THU NĂM
SELECT * FROM DoanhThuThang

SELECT SUM(TongDoanhThuNgay) AS N'Doanh Thu Trong Năm' FROM DoanhThuThang WHERE Nam = 2021


--THỐNG KÊ CHI PHÍ NĂM
SELECT * FROM ChiPhiThang
SELECT MaCPT, MaNV, Thang, Nam, (TongChiPhiNgay + TienDien + TienNuoc + TienLuong + TienThue + TienNhapHang) AS 'Tổng Chi Phí Tháng' FROM ChiPhiThang

SELECT SUM(TongChiPhiNgay + TienDien + TienNuoc + TienLuong + TienThue + TienNhapHang) AS N'Chi Phí Trong Năm' FROM ChiPhiThang WHERE Nam = 2021

--THỐNG KÊ LỢI NHUẬN NĂM
SELECT SUM(DoanhThuThang.TongDoanhThuNgay) - SUM(ChiPhiThang.TongChiPhiNgay + ChiPhiThang.TienDien + ChiPhiThang.TienNuoc + ChiPhiThang.TienLuong + ChiPhiThang.TienThue + ChiPhiThang.TienNhapHang) AS N'Lợi Nhuận' 
FROM DoanhThuThang INNER JOIN ChiPhiThang ON DoanhThuThang.MaNV = ChiPhiThang.MaNV Where DoanhThuThang.Thang = 11 AND DoanhThuThang.Nam = 2021

--THỐNG KÊ XUẤT HÀNG
SELECT HangHoa.MaHang, HangHoa.TenHang, HangHoa.DonGia,HangHoa.DVT,HangHoa.SoLuong - ChiTietPhieuXuatHang.SoLuong AS N'Số Lượng Còn Lại' 
FROM HangHoa INNER JOIN ChiTietPhieuXuatHang ON HangHoa.MaHang = ChiTietPhieuXuatHang.MaHang

--TÍNH TIỀN PHÒNG THEO GIỜ
SELECT HoaDonPhong.MaHoaDonPhong, HoaDonPhong.MaPhong, HoaDonPhong.TenKhachHang, HoaDonPhong.NgayThue, HoaDonPhong.NgayTra, HoaDonPhong.SoGio,HoaDonPhong.SoGio * GiaPhong.GiaTheoGio AS N'Tổng Tiền'
FROM HoaDonPhong INNER JOIN GiaPhong ON HoaDonPhong.MaPhong = GiaPhong.MaPhong WHERE HoaDonPhong.NgayThue = HoaDonPhong.NgayTra

--TÍNH TIỀN PHÒNG THEO NGÀY
SELECT HoaDonPhong.MaHoaDonPhong, HoaDonPhong.MaPhong, HoaDonPhong.TenKhachHang, HoaDonPhong.NgayThue, HoaDonPhong.NgayTra, DATEDIFF(DAY,HoaDonPhong.NgayThue,HoaDonPhong.NgayTra) * GiaPhong.GiaTheoNgay AS N'Tổng Tiền'
FROM HoaDonPhong INNER JOIN GiaPhong ON HoaDonPhong.MaPhong = GiaPhong.MaPhong WHERE HoaDonPhong.NgayThue < HoaDonPhong.NgayTra

-- TÍNH TỔNG DOANH THU TIỀN PHÒNG
SELECT SUM(HoaDonPhong.SoGio * GiaPhong.GiaTheoGio) AS 'Tổng Tiền Thu Được' FROM HoaDonPhong INNER JOIN GiaPhong ON HoaDonPhong.MaPhong = GiaPhong.MaPhong 
WHERE HoaDonPhong.NgayThue = HoaDonPhong.NgayTra AND HoaDonPhong.NgayTra = '2021-11-11'
SELECT SUM(TongTienPhong) AS 'Tổng Tiền Phòng Thu Được' FROM HoaDonPhong WHERE NgayTra = '2021-12-11'
SELECT SUM(DATEDIFF(DAY,HoaDonPhong.NgayThue,HoaDonPhong.NgayTra) * GiaPhong.GiaTheoNgay)
FROM HoaDonPhong INNER JOIN GiaPhong ON HoaDonPhong.MaPhong = GiaPhong.MaPhong WHERE HoaDonPhong.NgayThue < HoaDonPhong.NgayTra AND HoaDonPhong.NgayTra = '2021-11-15'

-- TRUY VẤN HÓA ĐƠN DỊCH VỤ
SELECT ChiTietHoaDonDichVu.MaHoaDonDV, ChiTietHoaDonDichVu.MaDichVu, DichVu.TenDichVu, DichVu.DonGia, ChiTietHoaDonDichVu.SoLuong, HoaDonDichVu.TenKhachHang, HoaDonDichVu.NgayNhap
FROM HoaDonDichVu INNER JOIN ChiTietHoaDonDichVu ON HoaDonDichVu.MaHoaDonDV = ChiTietHoaDonDichVu.MaHoaDonDV INNER JOIN DichVu ON ChiTietHoaDonDichVu.MaDichVu = DichVu.MaDichVu

--TRUY VẤN TÍNH TIỀN DV
SELECT ChiTietHoaDonDichVu.MaHoaDonDV, ChiTietHoaDonDichVu.MaDichVu, DichVu.TenDichVu, DichVu.DonGia, ChiTietHoaDonDichVu.SoLuong,  DichVu.DonGia * ChiTietHoaDonDichVu.SoLuong AS 'Tong Tien' ,HoaDonDichVu.TenKhachHang, HoaDonDichVu.NgayNhap
FROM HoaDonDichVu INNER JOIN ChiTietHoaDonDichVu ON HoaDonDichVu.MaHoaDonDV = ChiTietHoaDonDichVu.MaHoaDonDV INNER JOIN DichVu ON ChiTietHoaDonDichVu.MaDichVu = DichVu.MaDichVu

--TRUY VẤN TÍNH TỔNG TIỀN DV
SELECT SUM(DichVu.DonGia * ChiTietHoaDonDichVu.SoLuong) 
FROM HoaDonDichVu INNER JOIN ChiTietHoaDonDichVu ON HoaDonDichVu.MaHoaDonDV = ChiTietHoaDonDichVu.MaHoaDonDV INNER JOIN DichVu ON ChiTietHoaDonDichVu.MaDichVu = DichVu.MaDichVu
WHERE HoaDonDichVu.NgayNhap = '2021-11-11'

--TRUY VẤN TỔNG DOANH THU TRONG THÁNG
SELECT SUM(TongTienPhong + TongTienDV) AS 'Tổng Doanh Thu Tháng' FROM DoanhThuHangNgay WHERE MONTH(NgayNhap) = 11 AND YEAR(NgayNhap) = 2021


