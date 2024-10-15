import React, { useState } from 'react';
import ProductPage from './components/ProductPage.js'
import Login from './components/Login.js'
import SignUp from './components/SignUp.js'
import ShoppingCart from './components/ShoppingCartPage.js'
import OrderPage from './components/OrderPage.js'
import ProfilePage from './components/ProfilePage.js'
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import Appbar from './components/Appbar.js'
import AdminPage from './components/AdminPage.js'

function App() {
  return (
    <BrowserRouter>
      <MainLayout />
    </BrowserRouter>
  );
}

function MainLayout() {
  const location = useLocation();
  
  const hideAppbar = location.pathname === '/admin';

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState({ shoppingCartItems: 0 });

  return (
    <>
      {!hideAppbar && <Appbar isLoggedIn={isLoggedIn} userData={userData} />}
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/shoppingCart" element={<ShoppingCart />} />
        <Route path="/order" element={<OrderPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/home" element={<ProductPage />} />
        <Route path="/admin" element={<AdminPage />} />
      </Routes>
    </>
  );
}

export default App;
