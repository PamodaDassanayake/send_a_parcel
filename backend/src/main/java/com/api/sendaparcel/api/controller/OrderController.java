package com.api.sendaparcel.api.controller;

import com.api.sendaparcel.api.dto.OrderDTO;
import com.api.sendaparcel.api.exceptions.RequestDataValidationException;
import com.api.sendaparcel.api.service.OrderService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(name = "order_controller", value = "/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@NotNull @RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return new ResponseEntity("Order has been placed successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/assign_driver")
    public ResponseEntity<?> assignDriver(@PathVariable("id") long order_id,
                                          @RequestParam("driver_id") long driver_id) {
        orderService.assignDriver(order_id,driver_id);
        return new ResponseEntity(String.format("Assigned a driver : %d for order : %d", order_id,driver_id),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/set_order_status")
    public ResponseEntity<?> setOrderStatus(@PathVariable("id") long order_id,
                                            @NotNull @RequestBody OrderDTO orderDTO) {
        if(orderDTO.getOrderStatus() == null) {
            throw new RequestDataValidationException("Order Status must be contained with the request");
        }
        orderService.setOrderStatus(order_id,orderDTO.getOrderStatus());
        return new ResponseEntity(String.format("Order : %d status has been changed for %s"
                ,order_id,orderDTO.getOrderStatus())
                ,HttpStatus.OK);
    }

    @PutMapping("/customer_feedback")
    public ResponseEntity<?> addCustomerFeedback(@NotNull @RequestBody OrderDTO orderDTO) {
        if(orderDTO.getCustomerFeedback() == null) {
            throw new RequestDataValidationException("Customer feedback must be contained with the request");
        }
        orderService.addFeedback(orderDTO);
        return new ResponseEntity(String.format("Add customer feedback for order : %d",orderDTO.getOrderID())
                ,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam("page_no") int pageNo
            , @RequestParam("count") int count) {
        List<OrderDTO> orderDTOS = orderService.getAllOrders(pageNo, count);
        return new ResponseEntity(orderDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        OrderDTO orderDTO = orderService.getOrderByOrderId(id);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrdersAndStatus(@RequestParam("page_no") int pageNo
            , @RequestParam("count") int count, @NotNull @RequestBody OrderDTO orderDTO) {
        if(orderDTO.getOrderStatus() == null) {
            throw new RequestDataValidationException("Order Status must be contained with the request");
        }
        List<OrderDTO> orderDTOS = orderService.getAllOrdersByOrderStatus(orderDTO.getOrderStatus(),pageNo, count);
        return new ResponseEntity(orderDTOS, HttpStatus.OK);
    }
}
