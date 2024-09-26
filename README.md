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
![Database_QuanLyGiaoHang](https://github.com/user-attachments/assets/1e7f78bf-e959-478a-a727-f9da4dd7b783)


---
## 🖼️ Some pictures of the website

**Sign up**
![signup](https://github.com/user-attachments/assets/9aa0c5b8-5335-4326-a6ec-a22ce8a291db)


**Home**
![home](https://github.com/user-attachments/assets/02409cdc-a3a4-4562-89a4-18d468ae37fa)


**Create order**
![create_order](https://github.com/user-attachments/assets/3bf8bbc7-d576-4216-8b47-c4d168873a5c)


**Owner order detail**
![owner_order_detail](https://github.com/user-attachments/assets/213343c4-dc76-4275-a81f-e57572db2412)


**Shipper info**
![shipper_info](https://github.com/user-attachments/assets/45bb16e1-de35-4936-b3df-52ec8d167070)



**List owner voucher**
![owner_voucher](https://github.com/user-attachments/assets/3c57ce10-5663-443e-8af6-ae285f37be7d)


**Bid on deliveries (shipper)**
![Bid_on_deliveries](https://github.com/user-attachments/assets/24e9ffc2-ed6e-4a34-aaa6-451c14c57179)



**Order detail for shipper (after shipper is selected)**
![order_detail](https://github.com/user-attachments/assets/094dbcb4-7c50-44fb-8b3f-2667b21bdcb4)


**Administration interface**
![category_manage](https://github.com/user-attachments/assets/f97c3fb9-0d80-4398-ae66-5cffe22dd805)
![service_manage](https://github.com/user-attachments/assets/685fbb7d-29ab-4413-8924-9cacd8d1923f)
![statistic](https://github.com/user-attachments/assets/03e0a853-aa42-46a5-b28a-ac9f7098fac3)
![statistic_by_service](https://github.com/user-attachments/assets/8e87da4a-4448-43a2-a484-a52a3e5ab869)




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
