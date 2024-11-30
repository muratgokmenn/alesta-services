package com.alesta.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alesta.order.exception.OrderException;
import com.alesta.order.exception.OrderExceptionConstants;
import com.alesta.order.model.Order;
import com.alesta.order.repository.OrderRepository;
import com.alesta.response.ApiResponse;
import com.alesta.utility.ValidationHelper;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	//TODO siparişi olmaayan bir müşteri için istek yapıldığında ne döner
	public List<Order> getOrdersByCustomerId(Long customerId) throws OrderException {
		List<Order> orders = orderRepository.findAllById(customerId);
		if(orders.isEmpty())
			throw new OrderException("Customer has no any order");
		
		return orderRepository.findAllById(customerId);
	}
	
	public ApiResponse<Order> saveOrder(Order order) throws OrderException {
	//	validationOrderRequest(order);
		orderRepository.save(order);
		return new ApiResponse<>(true, "Order registry is succesfully", order);
	}

	/*private void validationOrderRequest(Order order) throws OrderException {
		if(ValidationHelper.isValid(order.getCustomer().getId()))
			throw new OrderException(OrderExceptionConstants.CUSTOMER_ID_IS_NOT_VALID);
		if(ValidationHelper.isValid(order.getProduct().getId()))
			throw new OrderException(OrderExceptionConstants.PRODUCT_ID_IS_NOT_VALID);
		if(ValidationHelper.isValid(order.getDeliveryDate()))
			throw new OrderException(OrderExceptionConstants.DELIVERY_DATE_IS_NOT_VALID);
	}*/
	
	//TODO updateOrder
	
	//TODO deleteOrder

}
