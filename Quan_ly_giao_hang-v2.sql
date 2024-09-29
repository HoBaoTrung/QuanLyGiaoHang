DROP database IF EXISTS `quan_ly_giao_hang_springboot`;
CREATE DATABASE `quan_ly_giao_hang_springboot` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quan_ly_giao_hang_springboot`;



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


create table `role`(
 `id` int NOT NULL primary key,
 `name` varchar(10)
);
insert into `role` values 
(1,"ADMIN"), (2,"USER"), (3,"SHIPPER");



-- Tạo bảng cho các tỉnh thành
CREATE TABLE provinces (
    id INT AUTO_INCREMENT PRIMARY KEY,
    province_name VARCHAR(255) NOT NULL
);

-- Tạo bảng cho các quận huyện
CREATE TABLE districts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    district_name VARCHAR(255) NOT NULL,
    province_id INT,
    FOREIGN KEY (province_id) REFERENCES provinces(id)
);

-- Chèn dữ liệu vào bảng các tỉnh thành
INSERT INTO provinces (province_name) VALUES
('Hà Nội'), ('Hồ Chí Minh'), ('Hải Phòng'), ('Đà Nẵng'), ('Cần Thơ'), 
('Hải Dương'), ('Bắc Ninh'), ('Bắc Giang'), ('Thái Bình'), ('Nam Định'),
('Hưng Yên'), ('Vĩnh Phúc'), ('Hòa Bình'), ('Tuyên Quang'), ('Phú Thọ'), 
('Lạng Sơn'), ('Lào Cai'), ('Yên Bái'), ('Sơn La'), ('Điện Biên'),
('Lai Châu'), ('Hà Giang'), ('Cao Bằng'), ('Quảng Ninh'), ('Hà Nam'),
('Ninh Bình'), ('Thanh Hóa'), ('Nghệ An'), ('Hà Tĩnh'), ('Quảng Bình'),
('Quảng Trị'), ('Thừa Thiên Huế'), ('Quảng Nam'), ('Quảng Ngãi'), ('Bình Định'),
('Gia Lai'), ('Kon Tum'), ('Đắk Lắk'), ('Đắk Nông'), ('Lâm Đồng'),
('Bình Thuận'), ('Ninh Thuận'), ('Bình Phước'), ('Tây Ninh'), ('Long An'),
('Đồng Nai'), ('Bà Rịa - Vũng Tàu'), ('Tiền Giang'), ('Trà Vinh'), ('Sóc Trăng'),
('Bạc Liêu'), ('Cà Mau');

-- Chèn dữ liệu vào bảng các quận huyện

-- Hà Nội
INSERT INTO districts (district_name, province_id) VALUES
('Ba Đình', 1), ('Hoàn Kiếm', 1), ('Tây Hồ', 1), ('Hai Bà Trưng', 1),
('Đống Đa', 1), ('Thanh Xuân', 1), ('Hoàng Mai', 1), ('Cầu Giấy', 1),
('Long Biên', 1), ('Nam Từ Liêm', 1), ('Bắc Từ Liêm', 1), ('Hoài Đức', 1),
('Đan Phượng', 1), ('Sơn Tây', 1), ('Ba Vì', 1), ('Chương Mỹ', 1),
('Đông Anh', 1), ('Gia Lâm', 1), ('Mỹ Đức', 1), ('Thanh Oai', 1),
('Thanh Trì', 1), ('Từ Liêm', 1);

-- Hồ Chí Minh
INSERT INTO districts (district_name, province_id) VALUES
('Quận 1', 2), ('Quận 2', 2), ('Quận 3', 2), ('Quận 4', 2),
('Quận 5', 2), ('Quận 6', 2), ('Quận 7', 2), ('Quận 8', 2),
('Quận 9', 2), ('Quận 10', 2), ('Quận 11', 2), ('Quận 12', 2),
('Bình Tân', 2), ('Bình Thạnh', 2), ('Gò Vấp', 2), ('Hóc Môn', 2),
('Nhà Bè', 2), ('Phú Nhuận', 2), ('Tân Phú', 2), ('Thủ Đức', 2);

-- Hải Phòng
INSERT INTO districts (district_name, province_id) VALUES
('Hồng Bàng', 3), ('Lê Chân', 3), ('Ngô Quyền', 3), ('Kiến An', 3),
('Dương Kinh', 3), ('Hải An', 3), ('Đồ Sơn', 3), ('Cát Hải', 3),
('Bạch Long Vĩ', 3), ('An Dương', 3), ('An Lão', 3), ('Thủy Nguyên', 3),
('Vĩnh Bảo', 3), ('Tiên Lãng', 3), ('Kiến Thụy', 3);

-- Đà Nẵng
INSERT INTO districts (district_name, province_id) VALUES
('Hải Châu', 4), ('Thanh Khê', 4), ('Sơn Trà', 4), ('Ngũ Hành Sơn', 4),
('Liên Chiểu', 4);

-- Cần Thơ
INSERT INTO districts (district_name, province_id) VALUES
('Ninh Kiều', 5), ('Cái Răng', 5), ('Ô Môn', 5), ('Bình Thủy', 5),
('Thốt Nốt', 5), ('Phong Điền', 5), ('Cờ Đỏ', 5), ('Vĩnh Thạnh', 5),
('Huyện Cần Thơ', 5);

-- Hải Dương
INSERT INTO districts (district_name, province_id) VALUES
('Hải Dương', 6), ('Chí Linh', 6), ('Kim Thành', 6), ('Kinh Môn', 6),
('Nam Sách', 6), ('Ninh Giang', 6), ('Thanh Hà', 6), ('Tứ Kỳ', 6),
('Gia Lộc', 6), ('Bình Giang', 6), ('Cẩm Giàng', 6), ('Thanh Miện', 6);

-- Bắc Ninh
INSERT INTO districts (district_name, province_id) VALUES
('Bắc Ninh', 7), ('Từ Sơn', 7), ('Yên Phong', 7), ('Quế Võ', 7),
('Tiên Du', 7), ('Gia Bình', 7), ('Lương Tài', 7);

-- Bắc Giang
INSERT INTO districts (district_name, province_id) VALUES
('Bắc Giang', 8), ('Yên Dũng', 8), ('Lạng Giang', 8), ('Lục Nam', 8),
('Lục Ngạn', 8), ('Sơn Động', 8), ('Việt Yên', 8), ('Hiệp Hòa', 8),
('Tân Yên', 8);

-- Thái Bình
INSERT INTO districts (district_name, province_id) VALUES
('Thái Bình', 9), ('Hưng Hà', 9), ('Quỳnh Phụ', 9), ('Tiền Hải', 9),
('Đông Hưng', 9), ('Vũ Thư', 9), ('Kiến Xương', 9), ('Thái Thụy', 9),
('Phụ Dực', 9);

-- Nam Định
INSERT INTO districts (district_name, province_id) VALUES
('Nam Định', 10), ('Mỹ Lộc', 10), ('Vụ Bản', 10), ('Ý Yên', 10),
('Nghĩa Hưng', 10), ('Nam Trực', 10), ('Trực Ninh', 10), ('Xuân Trường', 10),
('Giao Thủy', 10), ('Hoài Đức', 10), ('Bình Lục', 10);

-- Hưng Yên
INSERT INTO districts (district_name, province_id) VALUES
('Hưng Yên', 11), ('Văn Lâm', 11), ('Văn Giang', 11), ('Yên Mỹ', 11),
('Khoái Châu', 11), ('Kim Động', 11), ('Tiên Lữ', 11), ('Mỹ Hào', 11),
('Phù Cừ', 11), ('Ân Thi', 11);

-- Vĩnh Phúc
INSERT INTO districts (district_name, province_id) VALUES
('Vĩnh Yên', 12), ('Phúc Yên', 12), ('Vĩnh Tường', 12), ('Tam Dương', 12),
('Tam Đảo', 12), ('Lập Thạch', 12), ('Yên Lạc', 12), ('Bình Xuyên', 12);

-- Hòa Bình
INSERT INTO districts (district_name, province_id) VALUES
('Hòa Bình', 13), ('Đà Bắc', 13), ('Kỳ Sơn', 13), ('Mai Châu', 13),
('Lạc Sơn', 13), ('Lạc Thủy', 13), ('Lương Sơn', 13), ('Tân Lạc', 13),
('Yên Thủy', 13);

-- Tuyên Quang
INSERT INTO districts (district_name, province_id) VALUES
('Tuyên Quang', 14), ('Chiêm Hóa', 14), ('Hàm Yên', 14), ('Na Hang', 14),
('Sơn Dương', 14), ('Yên Sơn', 14), ('Đoan Hùng', 14), ('Nà Hang', 14);

-- Phú Thọ
INSERT INTO districts (district_name, province_id) VALUES
('Việt Trì', 15), ('Phú Thọ', 15), ('Đoan Hùng', 15), ('Thanh Sơn', 15),
('Thanh Thủy', 15), ('Hạ Hòa', 15), ('Tân Sơn', 15), ('Lâm Thao', 15),
('Cẩm Khê', 15), ('Tam Nông', 15), ('Yên Lập', 15);

-- Lạng Sơn
INSERT INTO districts (district_name, province_id) VALUES
('Lạng Sơn', 16), ('Cao Lộc', 16), ('Văn Lãng', 16), ('Văn Quan', 16),
('Đồng Đăng', 16), ('Bắc Sơn', 16), ('Hữu Lũng', 16), ('Lộc Bình', 16),
('Chi Lăng', 16), ('Hữu Lũng', 16);

-- Lào Cai
INSERT INTO districts (district_name, province_id) VALUES
('Lào Cai', 17), ('Sa Pa', 17), ('Bát Xát', 17), ('Mường Khương', 17),
('Văn Bàn', 17), ('Bắc Hà', 17), ('Si Ma Cai', 17), ('Tân Uyên', 17),
('Mường Hum', 17);

-- Yên Bái
INSERT INTO districts (district_name, province_id) VALUES
('Yên Bái', 18), ('Lục Yên', 18), ('Văn Chấn', 18), ('Trấn Yên', 18),
('Mù Cang Chải', 18), ('Văn Yên', 18), ('Lâm Thao', 18), ('Yên Bình', 18),
('Tân Uyên', 18);

-- Sơn La
INSERT INTO districts (district_name, province_id) VALUES
('Sơn La', 19), ('Mộc Châu', 19), ('Mai Sơn', 19), ('Yên Châu', 19),
('Sông Mã', 19), ('Sông La', 19), ('Vân Hồ', 19), ('Phù Yên', 19),
('Quỳnh Nhai', 19);

-- Điện Biên
INSERT INTO districts (district_name, province_id) VALUES
('Điện Biên Phủ', 20), ('Mường Lay', 20), ('Điện Biên', 20), ('Mường Chà', 20),
('Mường Nhé', 20), ('Mường Ảng', 20), ('Tủa Chùa', 20), ('Nậm Pồ', 20),
('Đức Lâm', 20);

-- Lai Châu
INSERT INTO districts (district_name, province_id) VALUES
('Lai Châu', 21), ('Mường Tè', 21), ('Nậm Nhùn', 21), ('Sìn Hồ', 21),
('Tam Đường', 21), ('Phong Thổ', 21), ('Than Uyên', 21), ('Tân Uyên', 21),
('Lai Châu', 21);

-- Hà Giang
INSERT INTO districts (district_name, province_id) VALUES
('Hà Giang', 22), ('Quản Bạ', 22), ('Yên Minh', 22), ('Mèo Vạc', 22),
('Đồng Văn', 22), ('Hà Giang', 22), ('Vị Xuyên', 22), ('Bắc Mê', 22),
('Bắc Quang', 22);

-- Cao Bằng
INSERT INTO districts (district_name, province_id) VALUES
('Cao Bằng', 23), ('Bảo Lạc', 23), ('Bảo Lâm', 23), ('Hạ Lang', 23),
('Hòa An', 23), ('Nguyên Bình', 23), ('Thạch An', 23), ('Trùng Khánh', 23),
('Quảng Uyên', 23);

-- Quảng Ninh
INSERT INTO districts (district_name, province_id) VALUES
('Hạ Long', 24), ('Móng Cái', 24), ('Cẩm Phả', 24), ('Uông Bí', 24),
('Đông Triều', 24), ('Quảng Yên', 24), ('Tiên Yên', 24), ('Vân Đồn', 24),
('Ba Chẽ', 24), ('Hoành Bồ', 24);

-- Hà Nam
INSERT INTO districts (district_name, province_id) VALUES
('Phủ Lý', 25), ('Duy Tiên', 25), ('Kim Bảng', 25), ('Thanh Liêm', 25),
('Bình Lục', 25), ('Lý Nhân', 25);

-- Ninh Bình
INSERT INTO districts (district_name, province_id) VALUES
('Ninh Bình', 26), ('Tam Điệp', 26), ('Nho Quan', 26), ('Gia Viễn', 26),
('Hoa Lư', 26), ('Yên Khánh', 26), ('Yên Mô', 26), ('Kim Sơn', 26),
('Vân Giang', 26);

-- Thanh Hóa
INSERT INTO districts (district_name, province_id) VALUES
('Thanh Hóa', 27), ('Sầm Sơn', 27), ('Bỉm Sơn', 27), ('Mường Lát', 27),
('Quan Hóa', 27), ('Quan Sơn', 27), ('Lang Chánh', 27), ('Ngọc Lặc', 27),
('Cẩm Thủy', 27), ('Như Xuân', 27), ('Như Thanh', 27), ('Thọ Xuân', 27),
('Thọ Hợp', 27), ('Thiệu Hóa', 27), ('Hà Trung', 27), ('Vĩnh Lộc', 27),
('Hoằng Hóa', 27), ('Hậu Lộc', 27), ('Hoằng Hóa', 27), ('Triệu Sơn', 27),
('Tĩnh Gia', 27);

-- Nghệ An
INSERT INTO districts (district_name, province_id) VALUES
('Vinh', 28), ('Cửa Lò', 28), ('Thái Hòa', 28), ('Thanh Chương', 28),
('Tương Dương', 28), ('Kỳ Sơn', 28), ('Nghĩa Đàn', 28), ('Quế Phong', 28),
('Quỳ Châu', 28), ('Quỳ Hợp', 28), ('Con Cuông', 28), ('Anh Sơn', 28),
('Diễn Châu', 28), ('Yên Thành', 28), ('Hưng Nguyên', 28), ('Nghi Lộc', 28);

-- Hà Tĩnh
INSERT INTO districts (district_name, province_id) VALUES
('Hà Tĩnh', 29), ('Hồng Lĩnh', 29), ('Kỳ Anh', 29), ('Nghi Xuân', 29),
('Cẩm Xuyên', 29), ('Lộc Hà', 29), ('Thạch Hà', 29), ('Hương Sơn', 29),
('Hương Khê', 29), ('Vũ Quang', 29), ('Can Lộc', 29), ('Đức Thọ', 29);

-- Quảng Bình
INSERT INTO districts (district_name, province_id) VALUES
('Đồng Hới', 30), ('Ba Đồn', 30), ('Quảng Trạch', 30), ('Tuyên Hóa', 30),
('Minh Hóa', 30), ('Lệ Thủy', 30), ('Bố Trạch', 30);

-- Quảng Trị
INSERT INTO districts (district_name, province_id) VALUES
('Đông Hà', 31), ('Quảng Trị', 31), ('Hướng Hóa', 31), ('Gio Linh', 31),
('Vĩnh Linh', 31), ('Triệu Phong', 31), ('Cam Lộ', 31), ('Cồn Cỏ', 31);

-- Thừa Thiên Huế
INSERT INTO districts (district_name, province_id) VALUES
('Huế', 32), ('Hương Thủy', 32), ('Hương Trà', 32), ('Phong Điền', 32),
('Quảng Điền', 32), ('A Lưới', 32), ('Nam Đông', 32);

-- Quảng Nam
INSERT INTO districts (district_name, province_id) VALUES
('Tam Kỳ', 33), ('Hội An', 33), ('Duy Xuyên', 33), ('Điện Bàn', 33),
('Quế Sơn', 33), ('Nông Sơn', 33), ('Thăng Bình', 33), ('Hiệp Đức', 33),
('Tiên Phước', 33), ('Phú Ninh', 33), ('Nam Giang', 33), ('Phước Sơn', 33),
('Tây Giang', 33), ('Đại Lộc', 33);

-- Quảng Ngãi
INSERT INTO districts (district_name, province_id) VALUES
('Quảng Ngãi', 34), ('Lý Sơn', 34), ('Sơn Tịnh', 34), ('Tư Nghĩa', 34),
('Mộ Đức', 34), ('Nghĩa Hành', 34), ('Minh Long', 34), ('Sơn Hà', 34),
('Sơn Tây', 34), ('Trà Bồng', 34), ('Trà Mi', 34), ('Ba Tơ', 34),
('Bình Sơn', 34);

-- Bình Định
INSERT INTO districts (district_name, province_id) VALUES
('Qui Nhơn', 35), ('An Nhơn', 35), ('Hoài Nhơn', 35), ('Tuy Phước', 35),
('Vân Canh', 35), ('Vĩnh Thạnh', 35), ('Phù Cát', 35), ('Phù Mỹ', 35),
('Tuy An', 35), ('Bồng Sơn', 35), ('Tam Quan', 35);

-- Gia Lai
INSERT INTO districts (district_name, province_id) VALUES
('Pleiku', 36), ('An Khê', 36), ('Ayun Pa', 36), ('Kbang', 36),
('Kông Chro', 36), ('Kông Thanh', 36), ('Đăk Đoa', 36), ('Chư Prông', 36),
('Chư Păh', 36), ('Chư Sê', 36), ('Krông Pa', 36), ('Phú Thiện', 36),
('Đăk Pơ', 36);

-- Kon Tum
INSERT INTO districts (district_name, province_id) VALUES
('Kon Tum', 37), ('Ngọc Hồi', 37), ('Đắk Glei', 37), ('Đắk Tô', 37),
('Sa Thầy', 37), ('Kon Rẫy', 37), ('Kon Plong', 37), ('Đắk Hà', 37),
('Tu Mơ Rông', 37), ('Đắk Tô', 37);

-- Đắk Lắk
INSERT INTO districts (district_name, province_id) VALUES
('Buôn Ma Thuột', 38), ('Buôn Hồ', 38), ('Cư M gar', 38), ('Krông Ana', 38),
('Krông Búk', 38), ('Krông Bông', 38), ('Krông Năng', 38), ('Ea H leo', 38),
('Ea Súp', 38), ('M Đrắk', 38), ('Lak', 38);

-- Đắk Nông
INSERT INTO districts (district_name, province_id) VALUES
('Gia Nghĩa', 39), ('Đắk Mil', 39), ('Đắk R Lấp', 39), ('Đắk Song', 39),
('Đắk Glong', 39), ('Cư Jút', 39), ('Krông Nô', 39);

-- Lâm Đồng
INSERT INTO districts (district_name, province_id) VALUES
('Đà Lạt', 40), ('Bảo Lộc', 40), ('Lạc Dương', 40), ('Lạc Sơn', 40),
('Lâm Hà', 40), ('Di Linh', 40), ('Đức Trọng', 40), ('Đơn Dương', 40),
('Đạ Tẻh', 40), ('Đạ Huoai', 40);

-- Bình Thuận
INSERT INTO districts (district_name, province_id) VALUES
('Phan Thiết', 41), ('La Gi', 41), ('Tuy Phong', 41), ('Tuy Đức', 41),
('Hàm Thuận Bắc', 41), ('Hàm Thuận Nam', 41), ('Hàm Tân', 41),
('Đức Linh', 41), ('Bắc Bình', 41);

-- Ninh Thuận
INSERT INTO districts (district_name, province_id) VALUES
('Phan Rang-Tháp Chàm', 42), ('Ninh Sơn', 42), ('Ninh Phước', 42),
('Ninh Hải', 42), ('Ninh Hải', 42), ('Thuận Nam', 42), ('Thuận Bắc', 42);

-- Bình Phước
INSERT INTO districts (district_name, province_id) VALUES
('Đồng Xoài', 43), ('Bình Long', 43), ('Bù Gia Mập', 43), ('Bù Đăng', 43),
('Bù Đốp', 43), ('Lộc Ninh', 43), ('Phước Long', 43), ('Chơn Thành', 43),
('Hớn Quản', 43), ('Đồng Phú', 43);

-- Tây Ninh
INSERT INTO districts (district_name, province_id) VALUES
('Tây Ninh', 44), ('Hòa Thành', 44), ('Trảng Bàng', 44), ('Dương Minh Châu', 44),
('Gò Dầu', 44), ('Châu Thành', 44), ('Tân Biên', 44), ('Tân Châu', 44),
('Bến Cầu', 44);

-- Long An
INSERT INTO districts (district_name, province_id) VALUES
('Tân An', 45), ('Châu Thành', 45), ('Bến Lức', 45), ('Cần Đước', 45),
('Cần Giuộc', 45), ('Thủ Thừa', 45), ('Tân Trụ', 45), ('Tân Hưng', 45),
('Vĩnh Hưng', 45), ('Mộc Hóa', 45), ('Tân Thạnh', 45);

-- Đồng Nai
INSERT INTO districts (district_name, province_id) VALUES
('Biên Hòa', 46), ('Long Khánh', 46), ('Nhơn Trạch', 46), ('Vĩnh Cửu', 46),
('Trảng Bom', 46), ('Định Quán', 46), ('Thống Nhất', 46), ('Tân Phú', 46),
('Xuân Lộc', 46), ('Cẩm Mỹ', 46);

-- Bà Rịa - Vũng Tàu
INSERT INTO districts (district_name, province_id) VALUES
('Vũng Tàu', 47), ('Bà Rịa', 47), ('Long Điền', 47), ('Đất Đỏ', 47),
('Xuyên Mộc', 47), ('Tân Thành', 47);

-- Tiền Giang
INSERT INTO districts (district_name, province_id) VALUES
('Mỹ Tho', 48), ('Gò Công', 48), ('Cai Lậy', 48), ('Châu Thành', 48),
('Tân Phước', 48), ('Châu Thành A', 48), ('Châu Thành B', 48),
('Tân Phú Đông', 48), ('Tân Hiệp', 48);

-- Trà Vinh
INSERT INTO districts (district_name, province_id) VALUES
('Trà Vinh', 49), ('Cầu Kè', 49), ('Cầu Ngang', 49), ('Châu Thành', 49),
('Tiểu Cần', 49), ('Càng Long', 49), ('Duyên Hải', 49), ('Châu Thành A', 49);

-- Sóc Trăng
INSERT INTO districts (district_name, province_id) VALUES
('Sóc Trăng', 50), ('Ngã Năm', 50), ('Vĩnh Châu', 50), ('Kế Sách', 50),
('Cù Lao Dung', 50), ('Thạnh Trị', 50), ('Mỹ Tú', 50), ('Châu Thành', 50),
('Trần Đề', 50);

-- Bạc Liêu
INSERT INTO districts (district_name, province_id) VALUES
('Bạc Liêu', 51), ('Giá Rai', 51), ('Hồng Dân', 51), ('Phước Long', 51),
('Vĩnh Lợi', 51), ('Hòa Bình', 51), ('Đông Hải', 51), ('Duyên Hải', 51);

-- Cà Mau
INSERT INTO districts (district_name, province_id) VALUES
('Cà Mau', 52), ('Ngọc Hiển', 52), ('U Minh', 52), ('Thới Bình', 52),
('Trần Văn Thời', 52), ('Cái Nước', 52), ('Đầm Dơi', 52), ('Năm Căn', 52),
('Phú Tân', 52);


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT primary key,
  `active` tinyint(1) DEFAULT 1,
  `email` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL unique,
  `phone` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL unique,
  `username` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL unique,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'
 
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--
 ALTER TABLE `user` AUTO_INCREMENT = 1; 
INSERT INTO `user` (email, phone, username, `password`, `avatar`) VALUES
('admin@example.com', '0123456789', 'admin','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://image.spreadshirtmedia.com/image-server/v1/products/T1459A839PA3861PT28D1025034820W10000H10000/views/1,width=1200,height=630,appearanceId=839,backgroundColor=F2F2F2/admin-sticker.jpg'),
('alice.smith@example.com', '123-456-7890', 'alice_smith','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('bob.johnson@example.com', '987-654-3210', 'bob_johnson','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('charlie.brown@example.com', '555-555-5555', 'charlie_brown','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('david.wilson@example.com', '444-444-4444', 'david_wilson','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('eve.martin@example.com', '222-333-4444', 'eve_martin','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('frank.white@example.com', '666-777-8888', 'frank_white','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('grace.davis@example.com', '999-000-1111', 'grace_davis','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('heidi.clark@example.com', '333-444-5555', 'heidi_clark','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('ivan.lewis@example.com', '555-666-7777', 'ivan_lewis','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('judy.walker@example.com', '777-888-9999', 'judy_walker','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('kim.lee@example.com', '888-999-0000', 'kim_lee','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('leo.hall@example.com', '000-111-2222', 'leo_hall','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('mona.allen@example.com', '111-222-3333', 'mona_allen','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('nina.morris@example.com', '000-333-4444', 'nina_morris','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg'),
('oliver.rodgers@example.com', '000-444-5555', 'oliver_rodgers','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg');

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


create table user_role(
user_id bigint not null,
role_id int not null,
PRIMARY KEY (`user_id`, `role_id`),
foreign key(user_id) references `user`(id),
foreign key(role_id) references `role`(id)
);
insert into `user_role`(`user_id`, `role_id`) values
(1,1),(1,2),(1,3),
(2,2),(2,3),(3,2),(3,3),(4,2),(4,3),(5,2),(5,3),(6,2),(6,3),
(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2);

create table verification_token (
id bigint not null auto_increment,
token varchar(255) not null,
expiry_date DATETIME,
user_id bigint not null,
PRIMARY KEY (`id`),
foreign key(user_id) references `user`(id)
);


--
-- Table structure for table `shipper`
--

DROP TABLE IF EXISTS `shipper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipper` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `CMND` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL unique,
  `active` tinyint(1) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `shipper_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper`
--

INSERT INTO `shipper` VALUES (1,'11111111',1,1),(2,'22222222',1,2),(3,'33333333',1,3),
(4,'44444444',1,4),(5,'55555555',0,5),(6,'66666666',1,6);


CREATE TABLE `voucher`
(id bigint primary key auto_increment not null,
`name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
`value` double not null, 
`measurement` varchar(5) not null,
`expiration_date` date not null
);
insert into `voucher`(`name`, `value`,`measurement`, `expiration_date`) values
('Khuyến mãi 1', 1000,'VNĐ', '2025-1-01'),('Khuyến mãi 2', 2000,'VNĐ', '2025-11-01'),
('Khuyến mãi 3', 3000,'VNĐ', '2025-11-01'),('Khuyến mãi 4', 4000,'VNĐ', '2025-11-01'),('Khuyến mãi 5', 5000,'VNĐ', '2025-11-01'),
('Giảm 25%', 25,'%', '2025-11-01');

CREATE TABLE `user_have_voucher`
(
-- id int primary key auto_increment not null,
quantity int,
user_id bigint not null,
voucher_id bigint not null,
PRIMARY KEY (`user_id`, `voucher_id`),
foreign key(user_id) references `user`(id),
foreign key(voucher_id) references `voucher`(id)
);
insert into  `user_have_voucher`(`quantity`, `user_id`, `voucher_id`) VALUES
    (1, 6, 1), (2, 6, 2), (3, 6, 3), (1, 6, 4), (2, 6, 5),(2, 6, 6),
    (3, 7, 1), (1, 7, 2), (2, 7, 3), (3, 7, 4), (1, 7, 5),
    (2, 8, 1), (3, 8, 2), (1, 8, 3), (2, 8, 4), (3, 8, 5),
    (1, 9, 1), (2, 9, 2), (3, 9, 3), (1, 9, 4), (2, 9, 5),
    (3, 10, 1), (1, 10, 2), (2, 10, 3), (3, 10, 4), (1, 10, 5)
    ,(3, 1, 1), (1, 1, 2), (2, 1, 3), (3, 1, 4);

CREATE TABLE `service`(id int primary key auto_increment not null,
`name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
`price` double not null,
`active` tinyint(1) DEFAULT NULL
);
insert into `service`(`name`, `price`,`active`) values
('Giao thường', 30000,1), ('Giao hỏa tốc', 50000,1), ('Giao tiết kiệm', 10000,1),
('Giao đường thủy', 50000,0),('Giao đường hàng không', 80000,0),('Giao bằng xe tải', 40000,0);

CREATE TABLE `category`(id int primary key auto_increment not null,
`name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
`price` double not null
);
insert into `category`(`name`, `price`) values
('Hàng dễ vỡ', 7000), ('Dễ thấm nước', 5000), ('Nhạy cảm với nhiệt độ', 10000)
, ('Nguy hiểm', 20000), ('Khối lượng lớn', 15000), ('Có mùi', 8000)
, ('Chứa chất dễ cháy', 17000), ('Không được đặt ngược', 30000)
, ('Chứa chất độc hại', 60000), ('Yêu cầu nhẹ nhàng', 12000)
, ('Yêu cầu bảo quản đặc biệt', 11000), ('Yêu cầu đóng gói chuyên dụng', 15000)
, ('Hàng dễ bị xẹp', 11000), ('Cần lót đệm', 9000);


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `quantity` int not null,
  `sender_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiver_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `delivery_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiver_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `sender_phone` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `receiver_phone` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `note` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  
   `created_date` date not null,
  `payment_date` date DEFAULT null,
  `service_id` int not null,
  `user_id` bigint NOT NULL ,
  `from_district_id` int not null,
   `to_district_id` int not null,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
 FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`service_id`) REFERENCES `service` (`id`),
  FOREIGN KEY (`from_district_id`) REFERENCES `districts` (`id`),
  FOREIGN KEY (`to_district_id`) REFERENCES `districts` (`id`)
 
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--
-- Đặt giá trị AUTO_INCREMENT bắt đầu từ 1
ALTER TABLE `product` AUTO_INCREMENT = 1;
INSERT INTO `product` 
 (`name`, `quantity`, `sender_name`, `receiver_name`,`delivery_address`,`receiver_address`,`sender_phone`,`receiver_phone`,`note`,`image`,
 `created_date`,`payment_date`,`service_id`, `user_id`, `from_district_id`,`to_district_id`) VALUES
('Mặt hàng 1', 10,  'Nguyễn Văn An', 'Trần Thị Bích','Địa chỉ 1A', 'Địa chỉ 1B', '1234567890', '0987654321', 'Ghi chú cho mặt hàng 1', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s', '2024-08-01', '2024-08-02', 1, 6, 3, 15),
('Mặt hàng 2', 5,  'Phạm Thị Dung', 'Lê Văn Cường','Địa chỉ 2A', 'Địa chỉ 2B', '2345678901', '9876543210', 'Ghi chú cho mặt hàng 2', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s', '2024-08-03', '2024-07-04', 2, 10, 5, 10),
('Mặt hàng 3', 8,  'Đặng Thị Hồng', 'Hoàng Minh Đức','Địa chỉ 3A', 'Địa chỉ 3B', '3456789012', '8765432109', 'Ghi chú cho mặt hàng 3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s','2024-08-05', '2024-06-06', 3, 8, 7, 5),
('Mặt hàng 4', 15,  'Bùi Thị Hoa', 'Vũ Văn Hậu','Địa chỉ 4A', 'Địa chỉ 4B', '4567890123', '7654321098', 'Ghi chú cho mặt hàng 4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s', '2024-08-07', '2024-05-08', 1, 12, 1, 18),
('Mặt hàng 5', 3,  'Dương Văn Khoa', 'Nguyễn Thị Lan','Địa chỉ 5A', 'Địa chỉ 5B', '5678901234', '6543210987', 'Ghi chú cho mặt hàng 5', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s', '2024-08-09', '2024-04-10', 2, 7, 8, 12),
('Mặt hàng 6', 12,  'Lý Văn Minh', 'Hồ Thị Ngọc','Địa chỉ 6A', 'Địa chỉ 6B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 6', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-11', '2024-03-12', 3, 15, 4, 3),
('Mặt hàng 7', 6,  'Đoàn Văn Phúc', 'Cao Thị Quyên','Địa chỉ 7A', 'Địa chỉ 7B', '7890123456', '4321098765', 'Ghi chú cho mặt hàng 7', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-13', '2024-02-14', 1, 9, 10, 7),
('Mặt hàng 8', 9,  'Tạ Văn Quang', 'Nguyễn Thị Hà','Địa chỉ 8A', 'Địa chỉ 8B', '8901234567', '3210987654', 'Ghi chú cho mặt hàng 8', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s', '2024-08-15', null, 2, 13, 2, 14),
('Mặt hàng 9', 4,  'Trương Minh Hòa', 'Mai Thị Lan','Địa chỉ 9A', 'Địa chỉ 9B', '9012345678', '2109876543', 'Ghi chú cho mặt hàng 9', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-17', null, 3, 6, 9, 19),
('Mặt hàng 10', 7,  'Phan Văn Long', 'Lê Thị Mai','Địa chỉ 10A', 'Địa chỉ 10B', '0123456789', '9876543210', 'Ghi chú cho mặt hàng 10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-19', null, 1, 11, 6, 8),
('Mặt hàng 11', 11,  'Hồ Văn Nam', 'Nguyễn Thị Ngân','Địa chỉ 11A', 'Địa chỉ 11B', '2345678901', '8765432109', 'Ghi chú cho mặt hàng 11', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-21',null, 2, 14, 1, 17),
('Mặt hàng 12', 2,  'Phạm Thị Oanh', 'Trần Minh Nhật','Địa chỉ 12A', 'Địa chỉ 12B', '3456789012', '7654321098', 'Ghi chú cho mặt hàng 12', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-23', null, 3, 9, 7, 11),
('Mặt hàng 13', 13,'Bùi Thị Thanh', 'Vũ Văn Phúc', 'Địa chỉ 13A', 'Địa chỉ 13B', '4567890123', '6543210987', 'Ghi chú cho mặt hàng 13', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-25',null, 1, 12, 3, 16),
('Mặt hàng 14', 1, 'Hoàng Thị Tuyết', 'Đặng Văn Tiến','Địa chỉ 14A', 'Địa chỉ 14B', '5678901234', '4321098765', 'Ghi chú cho mặt hàng 14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-27', null, 2, 8, 9, 4),
('Mặt hàng 15', 10, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 15, 5, 13),
('Mặt hàng 16', 2, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 16', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', '2024-01-10', 3, 1, 5, 13),
('Mặt hàng 17', 3, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 17', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', '2024-09-10', 2, 1, 5, 13),
('Mặt hàng 18', 6, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 18', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 1, 1, 5, 13),
('Mặt hàng 19', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 20', 9, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 20', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', '2024-09-10', 2, 1, 5, 13),
('Mặt hàng 21', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 22', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 23', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 24', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 25', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 26', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 27', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 28', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 29', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 30', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 31', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13),
('Mặt hàng 32', 8, 'Nguyễn Thị Xuân', 'Lê Văn Vinh','Địa chỉ 15A', 'Địa chỉ 15B', '6789012345', '5432109876', 'Ghi chú cho mặt hàng 19', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6EVuB4XnRyEY_dnhnOnl7pgCqgWS1bokS-g&s',  '2024-08-29', null, 3, 1, 5, 13)
;



CREATE TABLE `product_cate`(
-- id int auto_increment primary key,
product_id bigint not null,
category_id int not null,
PRIMARY KEY (`product_id`, `category_id`),
foreign key(product_id) references `product`(id),
foreign key(category_id) references `category`(id)
) ;
INSERT INTO `product_cate` (product_id, category_id) VALUES
(1, 5),(1, 4),(2, 4),(2, 2),(3, 2),(4, 1),(5, 3),(5, 4),(6, 4),
(7, 1),(7, 2),(8, 3),(9, 2),(9, 1),(10, 5);

--
-- Table structure for table `dau_gia`
--

DROP TABLE IF EXISTS `dau_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dau_gia` (

  `product_id` bigint NOT NULL,
  `shipper_id` bigint NOT NULL,
  `price` double NOT NULL,
  `selected` tinyint(1) NOT NULL,
  PRIMARY KEY (`product_id`, `shipper_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  FOREIGN KEY (`shipper_id`) REFERENCES `shipper` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dau_gia`
--
-- Đặt giá trị AUTO_INCREMENT bắt đầu từ 1
ALTER TABLE `dau_gia` AUTO_INCREMENT = 1;

INSERT INTO `dau_gia`( `product_id`, `shipper_id`, `price`, `selected`) VALUES 
	(1, 1, 50000, 1),
    (2, 1, 100000, 1),
    (3, 2, 75000, 1),
    (4, 2, 125000, 1),
    (5, 3, 55000, 1),
    (6, 3, 110000, 1),
    (7, 4, 150000, 1),
    (8, 1, 50000, 0),
    (9, 1, 55000, 0),
    (10, 1, 60000, 0),
    (11, 1, 65000, 0),
    (12, 1, 150000, 0),
    (9, 2, 135000, 0),
    (12, 2, 70000, 0),
    (13, 2, 75000, 0),
    (14, 2, 80000, 0),
    (15, 2, 85000, 0),
    (8, 3, 90000, 0),
    (9, 3, 95000, 0),
    (10, 3, 100000, 0),
    (11, 3, 105000, 0),
    (12, 4, 110000, 0),
    (13, 4, 115000, 0),
    (14, 4, 120000, 0),
    (15, 4, 125000, 0),
    (11, 4, 145000, 0),
    (16, 2, 125000, 1),
    (17, 3, 125000, 1),
    (20, 6, 125000, 1);

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proof` (
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`product_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
INSERT INTO `proof`(`image`, `product_id`) VALUES 
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',1),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',2),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',3),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',4),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',5),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',6),
('https://vinacorp.vn/uploads/news/photo1523579750886-1523579750886413386359.jpg',7);



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_of_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `shipper_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`shipper_id`) REFERENCES `shipper` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `comment` VALUES 
(1,'tot',1,5),(2,'ok',1,1),(3,'shipper giao nhanh',2,5),
(4,'shipper giao nhanh',2,5),(5,'shipper giao nhanh',2,5),(6,'shipper giao nhanh',2,5),
(7,'shipper giao nhanh',2,5),(8,'shipper giao nhanh',2,5),(9,'shipper giao nhanh',2,5),
(10,'shipper giao nhanh',2,5),(11,'shipper giao nhanh',2,5),(12,'shipper giao nhanh',2,5)
;


create table `rate`(
-- id bigint primary key auto_increment,
`point` int not null,
user_id bigint not null,
shipper_id bigint not null,
PRIMARY KEY (`user_id`, `shipper_id`),
foreign key(user_id) references `user`(id),
foreign key(shipper_id) references `shipper`(id)
);
insert into `rate`(`point`, `user_id`,`shipper_id`) values
(5, 6, 1),(4, 7, 3),(3, 8, 3),(2, 9, 4),(5, 11, 1),(4, 12, 2),(3, 13, 3),(2, 14, 4),(4, 6, 3),
(5, 7, 2),(2, 9, 1),(1, 10, 4),(1, 1, 6),(2, 2, 6),(3, 10, 6);


