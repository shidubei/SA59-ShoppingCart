import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Card, CardContent, Typography, IconButton, TextField, Dialog, DialogActions, DialogContent, DialogTitle, Button } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Appbar from './Appbar.js';
import { useState,useEffect } from 'react';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import axios from 'axios';

export default function Profile() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [shoppingCartItemCount, setShoppingCartItemCount] = useState(0);
  const [customer, setCustomer] = useState({});
  const [shippingAddresses, setShippingAddresses] = useState([]);
  const [userData, setUserData] = useState({
    shoppingCartItems: 0,
    customerId:0,
    username: "",
    email: "",
    contactNumber: "",
  });
  useEffect(()=>{
    const getInfo = async ()=>{
      const response1 = await axios.get('http://localhost:8080/shoppingCart/count',{withCredentials:true});
      setShoppingCartItemCount(response1.data);
      const response2 = await axios.get('http://localhost:8080/customer',{withCredentials:true});
      setCustomer(response2.data);
      const response3 = await axios.get('http://localhost:8080/customer/address',{withCredentials:true});
      const addresses = response3.data.map(item => ({ id: item.id, pre_address: item.pre_address }));
      setShippingAddresses(addresses);
      // console.log(addresses);
    }
    getInfo();
  },[])

  useEffect(() => {
    setUserData({
      shoppingCartItems: shoppingCartItemCount,
      customerId:customer.id,
      username: customer.name || "Unkown",  
      email: customer.email || "Unkown",
      contactNumber: customer.contactNumber || "Unkown",
    });
  }, [customer, shoppingCartItemCount]);



  const [dialogOpen, setDialogOpen] = useState(false);
  const [currentAddress, setCurrentAddress] = useState('');
  const [isEditingAddress, setIsEditingAddress] = useState(false);
  const [editingIndex, setEditingIndex] = useState(null);
  
  const [profileDialogOpen, setProfileDialogOpen] = useState(false);
  const [editedUserData, setEditedUserData] = useState(userData);


  useEffect(() => {
    const {customerId,username,email,contactNumber} = userData;
    setEditedUserData({customerId,username,email,contactNumber});
  }, [userData]);  

  // Function to handle adding a new address
  const handleAddAddress = () => {
    setCurrentAddress('');
    setIsEditingAddress(false);
    setDialogOpen(true);
  };

  // Function to open the dialog for editing an existing address
  const handleEditAddress = async (index) => {
    setCurrentAddress(shippingAddresses[index].pre_address);
    setEditingIndex(index);
    setIsEditingAddress(true);
    setDialogOpen(true);
  };

  // Function to delete a shipping address
  const handleDeleteAddress = async (index) => {
    const itemId = shippingAddresses[index].id;
    const updatedAddresses = shippingAddresses.filter((_, i) => i !== index);
    const response =  await axios.delete('http://localhost:8080/customer/address/delete', {
      params: { id: itemId },  
      withCredentials: true
    });
    setShippingAddresses(updatedAddresses);
  };

  // Function to handle saving or editing the address
  const handleSaveAddress = async () => {
    if (isEditingAddress) {
      const updatedAddresses = shippingAddresses.map((address, index) =>
        index === editingIndex 
          ? { ...address, pre_address: currentAddress }  // 只更新 pre_address，保留其他属性
          : address
      );
      await axios.get('http://localhost:8080/customer/address/update',{params:{id:updatedAddresses[editingIndex].id},withCredentials:true});
      const respone =await axios.put('http://localhost:8080/customer/address/update',{address:currentAddress},{withCredentials:true})
      setShippingAddresses(updatedAddresses);
    } else {
      const newAddress = {address:currentAddress};
      const response =await axios.post('http://localhost:8080/customer/address/add',newAddress,{withCredentials:true})
      setShippingAddresses([...shippingAddresses, {id:response.data.id,pre_address:response.data.pre_address}]);
    }
    setDialogOpen(false);
  };

  // Function to open the profile edit dialog
  const handleEditProfile = () => {
    setProfileDialogOpen(true);
  };

  // Function to handle saving profile edits
  const handleSaveProfile = async () => {
    setEditedUserData(editedUserData);
    try{
      // const response1 = await axios.get('http://localhost:8080/updatedetails',{withCredentials:true});
      const response2 = await axios.put('http://localhost:8080/updatedetails',editedUserData,{withCredentials:true,headers:{'Content-Type': 'application/json'}});
      console.log(response2);
    }catch(error){
      if(error.response2){
        console.log(error.response2)
      }else if(error.request){
        console.log(error.request2);
      }
      
    }
    
    setProfileDialogOpen(false);
  };

  return (
    <div>
      <Appbar isLoggedIn={isLoggedIn} userData={userData} />
      <Grid container justifyContent="center" alignItems="flex-start" spacing={1} style={{ backgroundColor: '#ededed'}}>

        {/* Profile Card */}
        <Card sx={{ padding: 2, marginTop: '32px', width: '80%', marginBottom: '32px'}}>
          <CardContent>
            <Typography gutterBottom variant="h6" component="div">
              Profile
            </Typography>
            <Typography variant="body1" sx={{marginTop:'5px'}}>
              <strong>Username:</strong> {editedUserData.username}
            </Typography>
            <Typography variant="body1" sx={{marginTop:'5px'}}>
              <strong>Email:</strong> {editedUserData.email}
            </Typography>
            <Typography variant="body1" sx={{marginTop:'5px'}}>
              <strong>Contact Number:</strong> {editedUserData.contactNumber}
            </Typography>
            <Button variant="contained" onClick={handleEditProfile} sx={{ marginTop: '16px' }}>
              <EditIcon /> Edit profile
            </Button>
          </CardContent>
        </Card>

        {/* Shipping Address Card */}
        <Card sx={{ padding: 2, width: '80%', marginBottom: '32px'}}>
          <CardContent>
            <Typography gutterBottom variant="h6" component="div">
              Shipping Address
            </Typography>
            <TableContainer component={Paper}>
              <Table aria-label="shipping address table">
                <TableHead>
                  <TableRow>
                    <TableCell>Number</TableCell>
                    <TableCell>Shipping Address</TableCell>
                    <TableCell align="right">Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {shippingAddresses.map((address, index) => (
                    <TableRow key={index}>
                      <TableCell>{index + 1}</TableCell>
                      <TableCell>{address.pre_address}</TableCell>
                      <TableCell align="right">
                        <IconButton color="primary" onClick={() => handleEditAddress(index)}>
                          <EditIcon />
                        </IconButton>
                        <IconButton aria-label="delete" color="error" onClick={() => handleDeleteAddress(index)}>
                          <DeleteIcon />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <Button variant="contained" color="primary" sx={{ marginTop: 2 }} onClick={handleAddAddress}>
              <AddIcon />
              Add New Shipping Address
            </Button>
          </CardContent>
        </Card>
      </Grid>

      {/* Dialog for adding/editing address */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)}>
        <DialogTitle>{isEditingAddress ? "Edit Shipping Address" : "Add New Shipping Address"}</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Shipping Address"
            fullWidth
            value={currentAddress}
            onChange={(e) => setCurrentAddress(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)} color="primary">Cancel</Button>
          <Button onClick={handleSaveAddress} color="primary">
            {isEditingAddress ? "Save Changes" : "Add Address"}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Dialog for editing profile */}
      <Dialog open={profileDialogOpen} onClose={() => setProfileDialogOpen(false)}>
        <DialogTitle>Edit Profile</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Username"
            fullWidth
            value={editedUserData.username}
            onChange={(e) => setEditedUserData({ ...editedUserData, username: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Email"
            fullWidth
            value={editedUserData.email}
            onChange={(e) => setEditedUserData({ ...editedUserData, email: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Contact Number"
            fullWidth
            value={editedUserData.contactNumber}
            onChange={(e) => setEditedUserData({ ...editedUserData, contactNumber: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setProfileDialogOpen(false)} color="primary">Cancel</Button>
          <Button onClick={handleSaveProfile} color="primary">Save Changes</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
