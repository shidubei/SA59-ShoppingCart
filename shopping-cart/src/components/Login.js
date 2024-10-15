import React from 'react';
import { Card, CardContent, CardActions, TextField, Button, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom'

export default function Login(){
    const [formData, setFormData] = useState({
        name: '',
        password: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value
        });
    };

    const [error,setError] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();
        try{
           const response = await axios.post("http://localhost:8080/login",formData,{withCredentials:true});
           if(response.status === 200){
               //TO DO: set isLoggedIn == true
            navigate("/home");
           } 
        }catch(error){
            setError(error.response.data);
        }

        

        // if (formData.username === 'kelly' && formData.password === '123456') {
        //     navigate('/home');
        //   } else {
        //     alert('Invalid username or password!');
        //     navigate('/login'); 
        //   }
    };

    return (
        <Grid container justifyContent="center" alignItems="center" style={{ height: '100vh', backgroundColor: '#ededed'}}>
        <Card sx={{ maxWidth: 400, padding: 2 }}>
            <Typography variant="h5" component="div" gutterBottom align="center">
            Login
            </Typography>
            <CardContent>
            <form onSubmit={handleLogin}>
                <TextField
                fullWidth
                margin="normal"
                label="Username"
                variant="outlined"
                required
                value={formData.username} 
                name="name"
                onChange={handleChange}
                />
                <TextField
                fullWidth
                margin="normal"
                label="Password"
                type="password"
                variant="outlined"
                required
                value={formData.password} 
                name="password"
                onChange={handleChange}
                />
                <CardActions sx={{ justifyContent: 'center' }}>
                <Button type="submit" variant="contained" color="primary">
                    Login
                </Button>
                </CardActions>
            </form>
            <Typography align="center" sx={{ marginTop: 1 }}>
                <Link to="/signup" style={{ color: '#1976d2' }}>
                <u>Don't have an account? Register here</u>
                </Link>
            </Typography>
            </CardContent>
        </Card>
        </Grid>
    );
}

