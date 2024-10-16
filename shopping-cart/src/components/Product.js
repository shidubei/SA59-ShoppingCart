import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import axios from 'axios';
import {useState} from 'react'

export default function Product({ product }) {
  const [error,setError] = useState('');
    const handleClick = async () =>{
      try{
        const response = await axios.get('http://localhost:8080/shoppingCart/add',{params:{id:product.id},withCredentials:true});
        console.log(response);
      }catch(error){
        setError(error);
      }
    }

    return (
      <Card sx={{ width: 200, height: 350 }}>
        <CardMedia
          component="img"
          alt={product.name}
          height="140"
          image={product.imageUrl}
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {product.name}
          </Typography>
          <Typography variant="subtitle2" sx={{ color: 'text.secondary' }}>
            {product.category}
          </Typography>
          <Typography variant="h6" sx={{ color: 'text.primary' }}>
            ${product.price.toFixed(2)}
          </Typography>
        </CardContent>
        <CardActions style={{ justifyContent: 'center' }}>
          <Button onClick={handleClick} size="medium">Add to cart</Button>
        </CardActions>
      </Card>
    );
  }
