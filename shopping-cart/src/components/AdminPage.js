import React, { useState } from 'react';
import { Card, CardContent, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, IconButton, Paper, Dialog, DialogTitle, DialogContent, TextField, DialogActions, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Grid from '@mui/material/Grid2'

export default function AdminPage() {
    const [products, setProducts] = useState([
        { productName: 'Item 1', price: 19.99, imageUrl: '/media/product1.png', category: "Shoes" },
        { productName: 'Item 2', price: 29.99, imageUrl: '/media/product1.png', category: "Shoes" },
        { productName: 'Item 3', price: 39.99, imageUrl: '/media/product1.png', category: "Shoes" },
        { productName: 'Item 4', price: 49.99, imageUrl: '/media/product1.png', category: "Shoes" },
        { productName: 'Item 5', price: 59.99, imageUrl: '/media/product1.png', category: "Shoes" },
        { productName: 'Item 6', price: 69.99, imageUrl: '/media/product1.png', category: "Hat" },
        { productName: 'Item 7', price: 79.99, imageUrl: '/media/product1.png', category: "Hat" },
      ]);

    const [dialogOpen, setDialogOpen] = useState(false);
    const [isEditingProduct, setIsEditingProduct] = useState(false);
    const [currentIndex, setCurrentIndex] = useState(null);
    const [productName, setProductName] = useState('');
    const [productPrice, setProductPrice] = useState('');
    const [productCategory, setProductCategory] = useState('');

    const handleProductNameChange = (value) => setProductName(value);
    const handleProductPriceChange = (value) => setProductPrice(value);
    const handleProductCategoryChange = (event) => setProductCategory(event.target.value);

    const handleAddProduct = () => {
        setIsEditingProduct(false);
        setProductName('');
        setProductPrice('');
        setProductCategory('');
        setDialogOpen(true);
    };

    const handleEditProduct = (index) => {
        setIsEditingProduct(true);
        setCurrentIndex(index);
        const product = products[index];
        setProductName(product.productName);
        setProductPrice(product.price);
        setProductCategory(product.category);
        setDialogOpen(true);
    };

    const handleSaveProduct = () => {
        if (isEditingProduct) {
            // Update the product
            const updatedProducts = [...products];
            updatedProducts[currentIndex] = {
                productName,
                price: parseFloat(productPrice),
                category: productCategory,
                imageUrl: '/media/product1.png', 
            };
            setProducts(updatedProducts);
        } else {
            setProducts([...products, {
                productName,
                price: parseFloat(productPrice),
                category: productCategory,
                imageUrl: '/media/product1.png', 
            }]);
        }
        setDialogOpen(false);
    };

    const handleDeleteProduct = (index) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this product?");
        if (confirmDelete) {
            setProducts(products.filter((_, i) => i !== index));
        }
    };

    return (
        <div>
            <Grid container justifyContent="center" sx={{ backgroundColor: "#ededed" }}>
                <Card sx={{ padding: 2, margin: 5, width: '80%', marginBottom: '32px' }}>
                    <CardContent>
                        <Typography gutterBottom variant="h6" component="div">
                            Products
                        </Typography>
                        <TableContainer component={Paper}>
                            <Table aria-label="products table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Number</TableCell>
                                        <TableCell>Product</TableCell>
                                        <TableCell>Category</TableCell>
                                        <TableCell>Price</TableCell>
                                        <TableCell align="right">Actions</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {products.map((product, index) => (
                                        <TableRow key={index}>
                                            <TableCell>{index + 1}</TableCell>
                                            <TableCell>
                                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'start' }}>
                                                <img
                                                    src={product.imageUrl}
                                                    alt={product.productName}
                                                    style={{ width: 50, height: 50, objectFit: 'cover', marginRight: 10 }}
                                                />
                                                <span style={{ textAlign: 'center' }}>{product.productName}</span>
                                            </div>
                                            </TableCell>
                                            <TableCell>{product.category}</TableCell>
                                            <TableCell>{product.price.toFixed(2)}</TableCell>
                                            <TableCell align="right">
                                                <IconButton color="primary" onClick={() => handleEditProduct(index)}>
                                                    <EditIcon />
                                                </IconButton>
                                                <IconButton aria-label="delete" color="error" onClick={() => handleDeleteProduct(index)}>
                                                    <DeleteIcon />
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <Button variant="contained" color="primary" sx={{ marginTop: 2 }} onClick={handleAddProduct}>
                            <AddIcon />
                            Add New Product
                        </Button>
                    </CardContent>
                </Card>
            </Grid>
            {/* Dialog for adding/editing product */}
            <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)}>
                <DialogTitle>{isEditingProduct ? "Edit Product" : "Add New Product"}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Product Name"
                        fullWidth
                        value={productName}
                        onChange={(e) => handleProductNameChange(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        label="Product Price"
                        fullWidth
                        type="number"
                        value={productPrice}
                        onChange={(e) => handleProductPriceChange(e.target.value)}
                    />
                    <FormControl fullWidth sx={{ marginBottom: 2 }}>
                        <InputLabel id="product-category-label">Product Category</InputLabel>
                        <Select
                            labelId="product-category-label"
                            id="product-category"
                            value={productCategory}
                            onChange={handleProductCategoryChange}
                        >
                            <MenuItem value="Shoes">Shoes</MenuItem>
                            <MenuItem value="Hat">Hat</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setDialogOpen(false)} color="primary">Cancel</Button>
                    <Button onClick={handleSaveProduct} color="primary">
                        {isEditingProduct ? "Save Changes" : "Add Product"}
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );

}