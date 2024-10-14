import React, { useEffect,useState } from 'react';
import Appbar from './Appbar.js'; 
import Product from './Product.js';
import Grid from '@mui/material/Grid2';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import FormControl from '@mui/material/FormControl';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import axios from 'axios';

export default function ProductPage() {
    const [isLoggedIn, setIsLoggedIn] = useState(true); 
    const userData = {
      shoppingCartItems: 3, 
    };
    const [products,setProducts] = useState([]);

    const [error,setError] = useState('');

    useEffect(() =>{
      const listProducts =  async()=>{
        try{
          const response = await axios.get('http://localhost:8080/products',{withCredentials:true});
          const updateProductsWithImg = response.data.map(product => {
            return{
              ...product,
              imageUrl:'/media/product1.png'
            };
          });
          setProducts(updateProductsWithImg);
        }catch(error){
          setError('wrong happen');
        }
      }
      listProducts();
    },[])
  
    // Dummy list of products
    // const products = [
    //   { productName: 'Item 1', price: 19.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 2', price: 29.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 3', price: 39.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 4', price: 49.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 5', price: 59.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 6', price: 69.99, imageUrl: '/media/product1.png', category: "Shoes" },
    //   { productName: 'Item 7', price: 79.99, imageUrl: '/media/product1.png', category: "Shoes" },
    // ];

    const [searchInput, setSearchInput] = useState('');
    const [searchCategory, setSearchCategory] = useState('All');

    const handleSearchInputChange = (event) => {
      setSearchInput(event.target.value);
    };
  
    const handleSearchCategoryChange = (event) => {
      setSearchCategory(event.target.value);
    };
  
    return (
      <div>
        <Appbar isLoggedIn={isLoggedIn} userData={userData}/>
        <Grid 
          container 
          spacing={3}
          sx={{ 
            marginTop: '32px', 
            marginLeft: 10, 
            marginRight: 5,
            marginBottom: 5,
          }}
        >
          <Grid item>
            <Typography variant="h6">Search:</Typography>
          </Grid>
          <Grid item>
            <TextField 
              value={searchInput}
              sx={{height:'100%', width:'300px'}}
              id="outlined-search" 
              label="Input product name" 
              type="search" 
              size="small"
              onChange={handleSearchInputChange}
            />
          </Grid>
          <Grid item>
            <FormControl>
              <InputLabel id="category-label">Category</InputLabel>
              <Select
                labelId="category-label"
                id="category"
                value={searchCategory}
                onChange={handleSearchCategoryChange}
                label="Select category"
                sx={{width:'200px', height: '40px'}}
              >
                <MenuItem value="All">All</MenuItem>
                <MenuItem value="Category_1">Category 1</MenuItem>
                <MenuItem value="Category_2">Category 2</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item>
            <Button variant="contained" sx={{height:'40px'}}>Search</Button>
          </Grid>
        </Grid>
        <Grid 
          container 
          spacing={5}
          sx={{ 
            marginTop: '32px', 
            marginLeft: 10, 
            marginBottom: 5,
            marginRight: 0,
          }}
        >
          {products.map((product, index) => (
            <Grid item key={index}>
              <Product product={product} />
            </Grid>
          ))}
        </Grid>
      </div> 
    );
  }