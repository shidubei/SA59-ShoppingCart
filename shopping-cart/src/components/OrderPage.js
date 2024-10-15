import * as React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import Grid from '@mui/material/Grid2';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

import Appbar from './Appbar.js';
import { useState,useEffect } from 'react';
import TablePagination from '@mui/material/TablePagination';
import axios from 'axios';

// Create order data with products, total prices, payment method, and shipping address
// function createData(orderDate, totalItems, totalPrice, paymentMethod, shippingAddress, items) {
//   return {
//     orderDate,
//     totalItems,
//     totalPrice,
//     paymentMethod,
//     shippingAddress,
//     items, // Array of products in the order
//   };
// }

// // Sample data with payment method and shipping address
// const rows = [
//   createData('2023-10-01', 3, 100.00, 'Credit Card', '123 Main St, Cityville', [
//     { productName: 'Product 1', quantity: 1, price: 30.00 },
//     { productName: 'Product 2', quantity: 2, price: 40.00 },
//     { productName: 'Product 3', quantity: 1, price: 30.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ]),
//   createData('2023-10-02', 2, 60.00, 'PayPal', '456 Oak St, Townsville', [
//     { productName: 'Product 4', quantity: 1, price: 40.00 },
//     { productName: 'Product 5', quantity: 1, price: 20.00 },
//   ])
// ];


// Row component with collapsible product list, payment method, and shipping address
function Row(props) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.orderDate}
        </TableCell>
        <TableCell align="right">{row.totalItems}</TableCell>
        <TableCell align="right">${row.totalPrice.toFixed(2)}</TableCell>
      </TableRow>

      {/* Collapsible section for showing purchased items, payment method, and shipping address */}
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Purchased Items
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Product Name</TableCell>
                    <TableCell align="right">Quantity</TableCell>
                    <TableCell align="right">Price ($)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.items.map((item, index) => (
                    <TableRow key={index}>
                      <TableCell component="th" scope="row">
                        {item.name}
                      </TableCell>
                      <TableCell align="right">{item.quantity}</TableCell>
                      <TableCell align="right">${item.price.toFixed(2)}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>

              {/* Additional information: Payment Method and Shipping Address */}
              {/* <Typography variant="subtitle1" sx={{ marginTop: 2 }}>
                Payment Method: {row.paymentMethod}
              </Typography>
              <Typography variant="subtitle1">
                Shipping Address: {row.shippingAddress}
              </Typography> */}
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

Row.propTypes = {
  row: PropTypes.shape({
    orderDate: PropTypes.string.isRequired,
    totalItems: PropTypes.number.isRequired,
    totalPrice: PropTypes.number.isRequired,
    // paymentMethod: PropTypes.string.isRequired,
    // shippingAddress: PropTypes.string.isRequired,
    items: PropTypes.arrayOf(
      PropTypes.shape({
        name: PropTypes.string.isRequired,
        quantity: PropTypes.number.isRequired,
        price: PropTypes.number.isRequired,
      })
    ).isRequired,
  }).isRequired,
};

// Main component to display the table
export default function OrderPage() {
  const [rows,setRows] = useState([]);

  useEffect(()=>{const getOrder = async ()=>{
      const response = await axios.get("http://localhost:8080/customer/order/vieworder",{withCredentials:true});
      console.log(response.data);
      const reverseData = response.data.reverse();
      console.log(reverseData.data);
      setRows(reverseData.data); //shows the most recent data
    };getOrder()},

    []);
  const [isLoggedIn, setIsLoggedIn] = useState(true);

  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [page, setPage] = React.useState(0);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div>
      <Grid container justifyContent="center" alignItems="flex-start" spacing={2} style={{ minHeight: '100vh', backgroundColor: '#ededed' }}>
        <Card sx={{ padding: 2, marginTop: '32px', marginBottom: '32px', width: '90%' }}>
          <CardContent>
            <Typography variant='h6'>Order History</Typography>
            <TableContainer component={Paper} sx={{ marginTop: '32px', marginBottom: '32px' }}>
              <Table aria-label="collapsible table">
                <TableHead>
                  <TableRow>
                    <TableCell sx={{ backgroundColor: 'primary.main', color: 'white' }}/>
                    <TableCell sx={{ backgroundColor: 'primary.main', color: 'white' }}>Order Date</TableCell>
                    <TableCell align="right" sx={{ backgroundColor: 'primary.main', color: 'white' }}>Items Purchased</TableCell>
                    <TableCell align="right" sx={{ backgroundColor: 'primary.main', color: 'white' }}>Total Price ($)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row, index) => (
                    <Row key={index} row={row} />
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={rows.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </CardContent>
        </Card>
      </Grid>
    </div>
  );
}
