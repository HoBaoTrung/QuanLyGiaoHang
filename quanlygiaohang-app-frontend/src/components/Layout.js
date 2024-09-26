
import React from 'react';
import { Outlet } from 'react-router-dom';


const Layout = () => {
  return (
    <div style={{marginTop:100}}>
      
      <main className="main-content">
        <Outlet /> 
      </main>
      
    </div>
  );
};

export default Layout;
