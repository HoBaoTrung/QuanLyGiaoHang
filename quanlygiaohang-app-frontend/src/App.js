import { createContext, useEffect, useReducer, useState } from 'react';
import './App.css';
import cookies from 'react-cookies'
import Header from './layout/Header';
import Footer from './layout/Footer';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Layout from './components/Layout';
import About from './components/About';
import Register from './components/Register';
import Login from './components/Login';
import MyUserReducer from './reducers/MyUserReducer';
import Orders from './components/Orders';
import Product from './components/Product';
import CreateOrder from './components/CreateOrder';
import ProductViewForShipper from './components/ProductViewForShipper';
import Profile from './components/Profile';
import Voucher from './components/Voucher';
import ShipperDetail from './components/ShipperDetail';
import Unauthorized from './configs/Unauthorized';
import AdminDashboard from './adminComponents/AdminDashboard';
import PrivateRoute from './configs/PrivateRoute';
import ManagerUser from './adminComponents/ManageUser';
import ManagerShipper from './adminComponents/ManageShipper';
import ManagerShipperDetail from './adminComponents/ManagerShipperDetail';
import ManagerCategory from './adminComponents/ManageCategory';
import ManagerService from './adminComponents/ManageService';
import Stats from './adminComponents/Stats';
export const MyUserContext = createContext()
function App() {
  const [user, dispatch] = useReducer(MyUserReducer, cookies.load("user") || null)
  const userRole = user !== null ?
    user.data.roles.some(item => item.name === 'ADMIN') ?
      "admin" : "notAdmin"
    : "null"
  return (
    <>
      <MyUserContext.Provider value={[user, dispatch]}>

        <BrowserRouter>

          <Header />
          <Routes>
            <Route path="/" element={<Layout />}>
              <Route index element={<Home />} />
              <Route path="about" element={<About />} />
              <Route path="myprofile" element={<Profile />} />
              <Route path="shipperDetail/:shipperId" element={<ShipperDetail />} />
              <Route path="vouchers" element={<Voucher />} />
              <Route path="register" element={<Register />} />
              <Route path="login" element={<Login />} />
              <Route path="orders" element={<Orders />} />
              <Route path="orders/:orderStatus" element={<Orders />} />
              <Route path="createorder/:locationName" element={<CreateOrder />} />
              <Route path="createorder/" element={<CreateOrder />} />
              <Route path="product/:productId" element={<Product />} />
              <Route path="viewselectorderforshipper/:productId" element={<Product />} />
              <Route path="viewproductforshipper/:productId" element={<ProductViewForShipper />} />

              {/* phần này không có footer */}
              <Route element={<PrivateRoute userRole={userRole} />}>
                <Route path="/admin" element={<AdminDashboard />}>
                  <Route path="customers" element={<ManagerUser />} />
                  <Route path="shippers" element={<ManagerShipper />} />
                  <Route path="adminShippersDetail/:shipperId" element={<ManagerShipperDetail />} />
                  <Route path="orders" element={<ManagerUser />} />
                  <Route path="services" element={<ManagerService />} />
                  <Route path="categories" element={<ManagerCategory />} />
                  <Route path="stats" element={<Stats />} />
                </Route>
              </Route>


              <Route path="unauthorized" element={<Unauthorized />} />
            </Route>

          </Routes>

          {!window.location.pathname.startsWith('/admin') && <Footer />}

        </BrowserRouter>
      </MyUserContext.Provider>

    </>
  );
}

export default App;
