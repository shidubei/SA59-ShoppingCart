package sg.nus.iss.shoppingCart.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.OrderDetailsRepository;
import sg.nus.iss.shoppingCart.repository.OrderRepository;

// change: 
// 1.OrderService Implements OrderInterfacemethods
// 2.@Override the getAllOrders methods

@Service
public class OrderService implements OrderInterfacemethods{
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private OrderDetailsRepository orderDetailRepo;
	
	//View Order History - retrieves all orders with a specific customer 
	@Override
	@Transactional
	public List<Order> findOrdersByCustomer(Customer customer) {
		return orderRepo.findByCustomer(customer);
	}

	@Override
	public Optional<Order> findOrderDetailsForCustomer(Customer customer, int orderId) {
		return orderRepo.findByCustomerAndId(customer, orderId); 
	}
	@Override
	public BigDecimal findOrderProductUnits(int orderId,int productId) {
		return orderDetailRepo.findUnitsByOrderAndProduct(orderId, productId);
	}
	@Override
	public void CreateOrder(Order order,OrderDetails orderDetails) {
		orderRepo.save(order);
		orderDetailRepo.save(orderDetails);
	}
	@Override
	public int getOrderItemCount(int id) {
		return orderDetailRepo.countItem(id);
	}
	@Override
	public List<Product> getProductsInOrder(int id){
		return orderDetailRepo.findProductsByOrderId(id);
	}
	
}
