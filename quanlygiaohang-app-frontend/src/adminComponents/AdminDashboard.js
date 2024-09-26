import React from 'react';
import { Outlet, Link } from 'react-router-dom';
import '../static/css/AdminDashboard.css';
import { FaBox, FaChartBar, FaTags, FaTruck, FaUser } from 'react-icons/fa';
import { MdBuild } from 'react-icons/md';
const AdminDashboard = () => {
  return (
    <div className="admin-dashboard">
      <div className="sidebar">

        <ul className="menu-list">
          <li><Link to="/admin/customers"><FaUser /> CUSTOMER</Link></li>
          <li><Link to="/admin/shippers?active=true"><FaTruck /> SHIPPER</Link></li>
          <li><Link to="/admin/orders"><FaBox /> ORDER</Link></li>
          <li><Link to="/admin/categories"><FaTags /> CATEGORY</Link></li>
          <li><Link to="/admin/services"><MdBuild /> SERVICE</Link></li>
          <li><Link to="/admin/stats"><FaChartBar /> STATS</Link></li>
        </ul>
      </div>

      <div className='content' >
        <Outlet />
      </div>
    </div>
  );
};

export default AdminDashboard;
