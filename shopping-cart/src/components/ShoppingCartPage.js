import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Card, CardContent, Typography, Button, IconButton, Select, MenuItem, InputLabel, FormControl, TextField } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Appbar from './Appbar.js';
import { useState,useEffect } from 'react';
import AddIcon from '@mui/icons-material/Add';  
import RemoveIcon from '@mui/icons-material/Remove'; 
import DeleteIcon from '@mui/icons-material/Delete'; 
import axios from "axios";

// function createData(name, price, quantity) {
//   return { name, price, quantity };
// }

// const initialRows = [
//   createData('Product 1', 10.0, 1),
//   createData('Product 2', 20.0, 2),
//   createData('Product 3', 30.0, 1),
//   createData('Product 4', 40.0, 3),
//   createData('Product 5', 50.0, 1),
// ];

export default function ShoppingCart() {
  const [error,setError] = useState('');
  const [rows, setRows] = useState([]);
  const [address,setAddress] = useState([]);
  const [selectAddress,setSelectAddrss] = useState('');


  // const [sendData,setSendData] = useState([]);
  useEffect(()=>{
    const getShoppingCartItems =async ()=>{
      try{
        const response = await axios.get('http://localhost:8080/shoppingCart',{withCredentials:true});
        const rowsData = response.data.map(shoppingCartItem=>({
            id:shoppingCartItem.product.id,
            name:shoppingCartItem.product.name,
            price:shoppingCartItem.product.price,
            category:shoppingCartItem.product.category,
            quantity:shoppingCartItem.quantity
        }));
        setRows(rowsData);
        console.log(rowsData);
        console.log(rows);
      }catch(error){
        setError('wrong happen get shoppingCart');
      }
    }
    getShoppingCartItems();
  },[])

  useEffect(()=>{
    const getAddress = async() =>{
      try{
        const response  = await axios.get('http://localhost:8080/customer/address', {withCredentials: true});
        setAddress(response.data);
      }catch(error){
        setError('ERROR:Could not get address');
      }
    };
    getAddress();
  },[]);

  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const userData = {
    addresses:address
  };

  

  const [paymentMethod, setPaymentMethod] = useState('');
  // const [address, setAddress] = useState('');

  // Calculate total price
  const totalPrice = rows.reduce((sum, row) => sum + row.price * row.quantity, 0);

  const increaseQuantity = async (index) => {
    const newRows = [...rows];
    newRows[index].quantity += 1;
    setRows(newRows);
    const response = await axios.put("http://localhost:8080/shoppingCart/update",{product_id:newRows[index].id,quantity:1},{withCredentials:true})
  };

  const decreaseQuantity =async (index) => {
    const newRows = [...rows];
    if (newRows[index].quantity > 1) {
      newRows[index].quantity -= 1;
      setRows(newRows);
      const response = await axios.put("http://localhost:8080/shoppingCart/update",{product_id:newRows[index].id,quantity:-1},{withCredentials:true})
    }
  };

  const deleteItem = async (index) => {
    const itemId = rows[index].id;
    const newRows = rows.filter((_, i) => i !== index);
    setRows(newRows);
    const response = await axios.delete("http://localhost:8080/shoppingCart/delete", {
      params: { id: itemId },  
      withCredentials: true  
    });
    
  };

  const handlePaymentChange = (event) => {
    setPaymentMethod(event.target.value);
  };

  const handleAddressChange = (event) => {
    setAddress(event.target.value);
  };
  const getCurrentDate = ()=>{
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth()+1).padStart(2,'0');
    const day = String(today.getDate()).padStart(2,'0');

    return `${year}-${month}-${day}`;
  }
  const handleCheckout = async ()=>{
    const order_date = getCurrentDate();
    const returnData={
      products: rows,
      order_date: order_date,
      totalPrice: totalPrice,
    }; 
    console.log(returnData);
    const response = await axios.post("http://localhost:8080/shoppingCart/checkout",returnData,{withCredentials:true});
    setRows([]);
  }
  return (
    <div>
      <Grid container justifyContent="center" alignItems="flex-start" spacing={2} style={{ minHeight:'100vh', backgroundColor: '#ededed'}}>
        <Grid item xs={8}>
          <Card sx={{ padding: 2, marginTop: '32px', marginBottom: '32px' }}>
            <CardContent>
              <Typography variant='h6'>Shopping Cart</Typography>
              <TableContainer component={Paper}>
                <Table sx={{ width: 650 }} aria-label="simple table">
                  <TableHead>
                    <TableRow>
                      <TableCell>Product Name</TableCell>
                      <TableCell align="right">Product Price ($)</TableCell>
                      <TableCell align="right">Product Quantity</TableCell>
                      <TableCell align="right">Delete</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {rows.map((row, index) => (
                      <TableRow key={row.name}>
                        <TableCell component="th" scope="row">
                          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'start' }}>
                          <img 
                            src={'/media/product1.png'} 
                            alt={row.name} 
                            style={{ width: 50, height: 50, objectFit: 'cover', marginRight: 10 }}
                          />
                          {row.name}
                          </div>
                        </TableCell>
                        <TableCell align="right">{row.price.toFixed(2)}</TableCell>
                        <TableCell align="right">
                          <IconButton onClick={() => decreaseQuantity(index)}>
                            <RemoveIcon />
                          </IconButton>
                          {row.quantity}
                          <IconButton onClick={() => increaseQuantity(index)}>
                            <AddIcon />
                          </IconButton>
                        </TableCell>
                        <TableCell align="right">
                          <IconButton onClick={() => deleteItem(index)}>
                            <DeleteIcon />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={4}> {}
          <Card sx={{ padding: 2, marginTop: '32px', width:400 }}>
            <CardContent>
              <Typography variant='h6'>Order Summary</Typography>
              <Typography variant="body1" sx={{ marginBottom: 2 }}>
                Total: ${totalPrice.toFixed(2)}
              </Typography>

              {/* Payment Method */}
              <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="payment-method-label">Payment Method</InputLabel>
                <Select
                  labelId="payment-method-label"
                  id="payment-method"
                  value={paymentMethod}
                  onChange={handlePaymentChange}
                >
                  <MenuItem value="credit-card">Credit Card</MenuItem>
                  <MenuItem value="debit-card">Debit Card</MenuItem>
                  <MenuItem value="payNow">PayNow</MenuItem>
                </Select>
              </FormControl>

              {/* Address Selection */}
              <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="address-label">Address</InputLabel>
                <Select
                    labelId="address-label"
                    id="address"
                    value={selectAddress}
                    onChange={handleAddressChange}
                  >
                  {userData.addresses.map((addr, index) => (
                    <MenuItem key={index} value={addr.id}>
                      {addr.pre_address}
                    </MenuItem>
                  ))} 
                  </Select>
              </FormControl>

              <Button onClick={handleCheckout} variant="contained" fullWidth>
                Checkout
              </Button>
            </CardContent>
          </Card>
        </Grid>

      </Grid>
    </div>
  );
}
