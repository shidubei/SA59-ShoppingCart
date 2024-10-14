import React, { useState } from 'react';
import ProductPage from './components/ProductPage.js'
import Login from './components/Login.js'
import SignUp from './components/SignUp.js'
import ShoppingCart from './components/ShoppingCartPage.js'
import OrderPage from './components/OrderPage.js'
import ProfilePage from './components/ProfilePage.js'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<SignUp />} />
        <Route path="/shoppingCart" element={<ShoppingCart />}/>
        <Route path="/order" element={<OrderPage />}/>
        <Route path="/profile" element={<ProfilePage />}/>
        <Route path="/home" element={<ProductPage />} />
    
      </Routes>
    </BrowserRouter>
  );
}

export default App;
