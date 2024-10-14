import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import Badge from '@mui/material/Badge';
import MenuIcon from '@mui/icons-material/Menu';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import LogoutIcon from '@mui/icons-material/Logout';
import HistoryIcon from '@mui/icons-material/History';
import SearchIcon from '@mui/icons-material/Search';

import { Link } from 'react-router-dom';
import axios from 'axios';

export default function Appbar({ isLoggedIn, userData }) {
  // State to control the Drawer
  const [open, setOpen] = React.useState(false);

  // Function to toggle Drawer
  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  const handleLogout = async ()=>{
    const response = await axios.get("http://localhost:8080/logout",{withCredentials:true});
  }

  // Drawer content
  const DrawerList = (
    <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
      <List>
        <ListItem>
          <ListItemButton component={Link} to={'/profile'}>
            <ListItemIcon>
              <AccountCircleIcon/>
            </ListItemIcon>
            <ListItemText>Account</ListItemText>
          </ListItemButton>
        </ListItem>
        <Divider/>
        <ListItem>
          <ListItemButton component={Link} to={'/home'}>
            <ListItemIcon>
              <SearchIcon/>
            </ListItemIcon>
            <ListItemText>Search Product</ListItemText>
          </ListItemButton>
        </ListItem>
        <ListItem>
          <ListItemButton component={Link} to={'/order'}>
            <ListItemIcon>
              <HistoryIcon/>
            </ListItemIcon>
            <ListItemText>Past Orders</ListItemText>
          </ListItemButton>
        </ListItem>
        <ListItem>
          <ListItemButton onClick={handleLogout} component={Link} to={'/login'}>
            <ListItemIcon>
              <LogoutIcon/>
            </ListItemIcon>
            <ListItemText>Logout</ListItemText>
          </ListItemButton>
        </ListItem>
      </List>
    </Box>
  );

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          {/* When logged in */}
          {isLoggedIn ? (
            <>
              {/* Menu Icon with Drawer Toggle */}
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={toggleDrawer(true)}
                sx={{ mr: 2 }}
              >
                <MenuIcon />
              </IconButton>
              <Drawer open={open} onClose={toggleDrawer(false)}>
                {DrawerList}
              </Drawer>

              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="h6" component="div">
                  Online Shop
                </Typography>
              </Box>
              
              <Box sx={{ flexGrow: 1 }} />
              
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="cart"
                sx={{ ml: 2 }}
              >
                <Badge badgeContent={userData.shoppingCartItems} color="error">
                  <Box component={Link} to="/shoppingCart" sx={{ display: 'flex', alignItems: 'center', color: 'inherit', textDecoration: 'none' }}>
                    <ShoppingCartIcon />
                  </Box>
                </Badge>
              </IconButton>
            </>
          ) : (
            <>
              {/* When not logged in */}
              <Button color="inherit">Login</Button>
              <Box sx={{ display: { flexGrow: 1 } }}>
                <Typography variant="h6" component="div" sx={{ ml: 2 }}>
                  Online Shop
                </Typography>
              </Box>
              
              <Box sx={{ flexGrow: 1 }} />
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
