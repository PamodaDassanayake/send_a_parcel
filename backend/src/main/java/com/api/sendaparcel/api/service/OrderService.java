package com.api.sendaparcel.api.service;

import com.api.sendaparcel.api.dto.OrderDTO;
import com.api.sendaparcel.api.util.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDTO orderDTO);
    void assignDriver(long orderID, long driverId);
    void setOrderStatus(long orderID, OrderStatus orderStatus);
    OrderDTO getOrderByOrderId(long orderId);
    List<OrderDTO> getAllOrders(int page, int count);
    List<OrderDTO> getAllOrdersByOrderStatus(OrderStatus orderStatus,int page,int count);
    void addFeedback(OrderDTO orderDTO);
}
