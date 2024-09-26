import React from 'react';
import {Navigate, Outlet } from 'react-router-dom';


const PrivateRoute = ({ userRole }) => {
    return userRole === 'admin' ? <Outlet /> : <Navigate to="/unauthorized" />;
  };

export default PrivateRoute;
