import React from 'react';
import { Card, CardContent, CardActions, TextField, Button, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';

export default function SignUp(){
  const [formData, setFormData] = useState({
    username: '',
    password1: '',
    password2: '',
    email: '',
    contactNumber: '',
    // address: '',
  });

  const [error,setError]= useState("");
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();

  const handleSignUp = async (event) => {
      event.preventDefault();
      try{
        const response = await axios.post("http://localhost:8080/signup",formData,{withCredentials:true});
        console.log("signUp success")
        navigate('/login')
      }catch(error){
        setError(error.response.data)
      }
    };
    
  return (
    <Grid container justifyContent="center" alignItems="center" style={{backgroundColor: '#1976d2'}}>
      <Card sx={{ maxWidth: 400, padding: 2, marginTop:'64px', marginBottom:'64px' }}>
        <Typography variant="h5" component="div" gutterBottom align="center">
          Sign up
        </Typography>
        <CardContent>
          <form onSubmit={handleSignUp}>
            <TextField
              fullWidth
              margin="normal"
              label="Username"
              variant="outlined"
              name="username"
              onChange={handleChange}
              required
            />
            <TextField
              fullWidth
              margin="normal"
              label="Password"
              type="password"
              variant="outlined"
              name="password1"
              onChange={handleChange}
              required
            />
            <TextField
              fullWidth
              margin="normal"
              label="Password"
              type="password"
              variant="outlined"
              name="password2"
              onChange={handleChange}
              required
            />
            <TextField
              fullWidth
              margin="normal"
              label="Email"
              type="email"
              variant="outlined"
              name="email"
              onChange={handleChange}
              required
            />
            <TextField
              fullWidth
              margin="normal"
              label="Phone"
              type="tel"
              variant="outlined"
              name="contactNumber"
              onChange={handleChange}
              required
            />
            {/* <TextField
              fullWidth
              margin="normal"
              label="Address"
              type="text"
              variant="outlined"
              name="address"
              onChange={handleChange}
              required
            /> */}
            <CardActions sx={{ justifyContent: 'center' }}>
              <Button  type="submit" variant="contained" color="primary">
                Sign Up
              </Button>
            </CardActions>
          </form>
        </CardContent>
      </Card>
    </Grid>
  );
}