# 🚚 Delivery Management System

**Delivery Management System** is a web application designed to streamline the process of managing deliveries, connecting **customers**, **shippers**, and **administrators** in a seamless and efficient way. This system allows users to post and bid on delivery jobs, handle payments, and manage services, providing a comprehensive solution for package deliveries.

---

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Database Structure](#database-structure)
- [Some Pictures Of The Website](#picture)
- [API Endpoints](#api-endpoints)


---

## ✨ Features

### 1. **Customer Role**
- Post delivery requests with details (quantity, service type, item attributes).
- Select a shipper from bids and complete payment through **VNPAY**.
- Chat with shippers and leave ratings/reviews after delivery.

### 2. **Shipper Role**
- Browse available delivery orders and place bids.
- Filter orders based on conditions (area, base price).
- Communicate with customers for delivery arrangements.

### 3. **Admin Role**
- Full CRUD operations for services, categories, and item attributes.
- View reports for sales and order quantities by month or quarter.
- Manage and verify shipper accounts, activate permissions, and handle unpaid orders.

---

## 🛠 Tech Stack
- **Frontend**: ReactJS, Bootstrap
- **Backend**: Spring Boot, Hibernate
- **Database**: MySQL
- **Payments**: VNPAY Integration
- **Messaging**: Firebase Realtime Database

---

## 🚀 Installation

### Prerequisites:
- **Oracle OpenJDK 21.0.4**
- **MySQL**
- **Node.js & npm**
- **Maven**

---

## 📖 Usage

Once the application is running, you can access the following roles:

1. **Customer**:
   - Sign up, log in, and start posting delivery requests.
   - Select a shipper and make payments.

2. **Shipper**:
   - Browse available jobs and place bids.
   - Communicate directly with customers.

3. **Admin**:
   - Log in with admin credentials to manage services, shippers, and orders.
   - Access reports and handle user verification.

---

## 📂 Database Structure
![Database_QuanLyGiaoHang](https://github.com/user-attachments/assets/820be9be-6781-48de-aa41-a343413d5fe9)


---
## 🖼️ Some pictures of the website

**Sign up**
![register](https://github.com/user-attachments/assets/3c734bce-aa42-449a-9262-322392745301)

**Header**
![header](https://github.com/user-attachments/assets/eab96151-a8a3-44c8-b9e5-aea873117bac)

**Create order**
![create_order](https://github.com/user-attachments/assets/5c8fe5e5-cf2e-46b4-8186-677301e6d202)

**Owner order detail**
![view_owner_product](https://github.com/user-attachments/assets/288d0005-3997-4b27-9961-ccf6d2eee12c)

**Shipper info**
![view_shipper_info](https://github.com/user-attachments/assets/2889f990-832c-43db-b4e8-95533c36bc30)


**List owner voucher**
![owner_voucher](https://github.com/user-attachments/assets/055c8a60-832a-4382-85fd-bcc6042a3388)

**Bid on deliveries (shipper)**
![Bid_on_Deliveries](https://github.com/user-attachments/assets/a5d18542-2aa5-4d2e-a62f-ca44ae239b99)


**Order detail for shipper (after shipper is selected)**
![order_detail(view_of_shipper)](https://github.com/user-attachments/assets/f4f1ae1f-edbd-4419-9112-81ba8f8cda03)

**Administration interface**
![category_manage](https://github.com/user-attachments/assets/3146928b-2cc3-4db6-88c5-37a82fd4b74c)
![service_manage](https://github.com/user-attachments/assets/836e3d2f-3e73-425e-b906-19a3caf73640)
![statistic](https://github.com/user-attachments/assets/28a728a4-bcc0-4c48-aa59-365a7f9f69fa)
![statistic_by_service](https://github.com/user-attachments/assets/bed69153-7cf2-43dd-b23f-bb175f95fc74)



---
## 🔗 API Endpoints

| Method | Endpoint                               | Description                         |
|--------|----------------------------------------|-------------------------------------|
| GET   | `/api/public/getProvince/{name}/`      | Get provinces by location      |
| GET    | `/api/public/getDistricts/{id}/`                | Get districts by privince ID          |
| GET   | `/api/public/getAllCate/`                   | Get all category of product        |
| GET    | `/api/public/getRateOfShipper/{shipperId}/`                | Get average point of shipper by ID      |
| POST   | `/api/public/getAllService/`                          | Get all service (have conditions so use POST method)   |
| POST   | `/api/auth/login/`                          | Login with username and password   |
| POST    | `/api/auth/register/`                     | Sign up a new account |
| POST    | `/api/customer/orders/`                | Get orders of this user (have conditions so use POST method)          |
| GET    | `/api/customer/getOrder/{orderId}/`                     | Get order detail |
| POST   | `/api/customer/addOrder/`                   | Post delivery request      |
| POST    | `/api/customer/pay/`                | Return information for payment           |
| POST   | `/api/customer/vnpay-return/`                          | Processing payment results   |
| GET   | `/api/customer/getShipper/{userId}/`                          | Get info of shipper by ID   |
| GET    | `/api/customer/getComments/{shipperId}`                     | Get comments by shipperID |
| POST   | `/api/customer/addComments/`                   | Get all category of product        |
| POST   | `/api/customer/getRate/`      | Get owner customer point for shipper     |
| DELETE    | `/api/customer/postRate/`                | Add or update point for shipper         |
| GET   | `/api/customer/firebase-custom-token/`                   | Get custom token to authenticate into Firebase       |
| GET   | `/api/customer/getUser/{userId}/`                          | Get user detail   |
| GET    | `/api/customer/vouchers/`                     | Get list voucher of current user |
| POST   | `/api/shipper/addDauGia/`      | (SHIPPER ROLE) shipper can bid on orders      |
| GET   | `/api/shipper/getOrder/{orderId}/`      |(SHIPPER ROLE)  Information about product for shipper when they bid on deliveries      |
| POST   | `/api/shipper/addProof/{productId}/`       |(SHIPPER ROLE) Post proof for complete order        |
| POST   | `/api/admin/addOrUpdateCategory/`      | (AMIN ROLE) add or update a category      |
| DELETE    | `/api/admin/deleteCategory/{id}/`                | (AMIN ROLE)  delete a category by ID          |
| POST    | `/api/admin/addOrUpdateService/`                     | (ADMIN ROLE) add or update a service |
| GET   | `/api/admin/allOrders/`                          | (ADMIN ROLE) get all orders for admin view   |
| POST    | `/api/admin/addOrUpdateService/`                     | (ADMIN ROLE) add or update a service |
| GET   | `/api/admin/getAllShipper/`      | (ADMIN ROLE) get all shipper info      |
| POST   | `/api/admin/{id}/toggle/`                          | (ADMIN ROLE) update status for shipper   |
| POST    | `/api/admin/price-by-period/`                     | (ADMIN ROLE) statistics price by period|
| POST   | `/api/admin/count-by-period/`                   | (ADMIN ROLE) statistics quantity by period  |
| POST   | `/api/admin/count-by-service/`      | (ADMIN ROLE) statistics quantity by service    |
| GET    | `/api/admin/getAllUsers/`                | (ADMIN ROLE) Get all user info          |


---
