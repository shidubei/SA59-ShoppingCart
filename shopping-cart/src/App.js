import React, { useState } from 'react';
import ProductPage from './components/ProductPage.js'
import Login from './components/Login.js'
import SignUp from './components/SignUp.js'
import ShoppingCart from './components/ShoppingCartPage.js'
import OrderPage from './components/OrderPage.js'
import ProfilePage from './components/ProfilePage.js'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import axios from 'axios';
import Appbar from './components/Appbar.js'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState({ shoppingCartItems: 0 });
  
  useEffect(()=>{
    if(isLoggedIn){
      const fetchUserData = async() => {
        try{
          const response = await axios.get('http://localhost:8080/shoppingCart', { withCredentials: true });
          const shoppingCartItems = response.data.reduce((acc, item) => acc + item.quantity, 0);
          setUserData({ shoppingCartItems });
        }
        catch(error){
          console.error('Error fetching shopping cart data:', error);

        }
      };   
      fetchUserData(); 
    }
  }, [isLoggedIn]);

  return (
    <BrowserRouter>
      <Appbar isLoggedIn={isLoggedIn} userData={userData} />
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/shoppingCart" element={<ShoppingCart />}/>
        <Route path="/order" element={<OrderPage />}/>
        <Route path="/profile" element={<ProfilePage />}/>
        <Route path="/home" element={<ProductPage />} />
    
      </Routes>
    </BrowserRouter>
  );
}

export default App;
