package com.api.sendaparcel.api.service.impl;

import com.api.sendaparcel.api.dto.OrderDTO;
import com.api.sendaparcel.api.dto.UserDTO;
import com.api.sendaparcel.api.entity.Parcel;
import com.api.sendaparcel.api.entity.User;
import com.api.sendaparcel.api.exceptions.*;
import com.api.sendaparcel.api.repository.OrderRepository;
import com.api.sendaparcel.api.repository.UserRepository;
import com.api.sendaparcel.api.security.model.SpringSecurityUser;
import com.api.sendaparcel.api.service.OrderService;
import com.api.sendaparcel.api.util.enums.OrderStatus;
import com.api.sendaparcel.api.util.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private SpringSecurityUser requestedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringSecurityUser requestedUser = (SpringSecurityUser) authentication.getPrincipal();
        return requestedUser;
    }

    private User isUserExists(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if(!userOptional.isPresent())
            throw new UserNotFoundException(String.format("User : %d not found",username));
        return userOptional.get();
    }

    private User isUserExists(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
            throw new UserNotFoundException(String.format("User : %d not found",userId));
        return userOptional.get();
    }

    private Parcel isOrderExists(long orderId) {
        Optional<Parcel> orderOptional = orderRepository.findById(orderId);
        if(!orderOptional.isPresent())
            throw new OrderNotFoundException(String.format("Order : %d not found",orderId));
        return orderOptional.get();
    }

    private void checkPrivilege(long orderId) {
        SpringSecurityUser requestedUser = requestedUser();
        String userType = requestedUser.getRole();
        if(!userType.equals(UserType.ADMIN.name())) {
            Optional<Parcel> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent()) {
                Parcel order = orderOptional.get();
                if(userType.equals(UserType.CUSTOMER.name())) {
                    if(order.getCustomer() != null && order.getCustomer().getUsername()
                            .equals(requestedUser.getUsername())) {
                        return;
                    }
                    throw new NotAllowedResourceException("you are not able to access this resource");
                } else if(userType.equals(UserType.DRIVER.name())) {
                    if(order.getAssignedDriver() != null && order.getAssignedDriver().getUsername()
                            .equals(requestedUser.getUsername())) {
                        return;
                    }
                    throw new NotAllowedResourceException("you are not able to access this resource");
                }
                throw new NotAllowedResourceException("Unknown user type requested");
            }
            throw new OrderNotFoundException(String.format("Order : %d not found",orderId));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createOrder(OrderDTO orderDTO) {
        SpringSecurityUser requestedUser = requestedUser();
        if(!requestedUser.getRole().equals(UserType.CUSTOMER.name())) {
            throw new NotAllowedResourceException("Only a customer can request to create an order");
        }

        if(orderDTO.getReceiverName().trim().isEmpty() || orderDTO.getReceiverAddress().trim().isEmpty() ||
        orderDTO.getOrderPickedAddress().trim().isEmpty() || orderDTO.getReceiverPhoneNumber().trim().isEmpty()) {
            throw new RequestDataValidationException("Receiver name, Receiver address, Picking address and Telephone" +
                    " number is required");
        }

        Optional<User> userOptional = userRepository.findUserByUsername(requestedUser.getUsername());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Parcel parcel = new Parcel(orderDTO.getReceiverName().trim(), orderDTO.getReceiverAddress().trim(),
                    orderDTO.getReceiverPhoneNumber().trim(), orderDTO.getOrderPickedAddress().trim(),
                    orderDTO.getDescription().trim(), user);
            parcel.setOrderPlacedDate(new Date());
            parcel.setOrderStatus(OrderStatus.REQUESTED);
            orderRepository.save(parcel);
            return;
        }
        throw new ConflictException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void assignDriver(long orderID, long driverId) {
        SpringSecurityUser requestedUser = requestedUser();
        if(!requestedUser.getRole().equals(UserType.ADMIN.name())) {
            throw new NotAllowedResourceException("Only a admin can request to assign driver for a given order");
        }

        User user = isUserExists(driverId);
        if(!user.getUserType().name().equals(UserType.DRIVER.name()))
            throw new ConflictException("User must be a driver");
        Parcel parcel = isOrderExists(orderID);
        parcel.setAssignedDriver(user);
        parcel.setOrderStatus(OrderStatus.ACCEPTED);
        orderRepository.save(parcel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void setOrderStatus(long orderID, OrderStatus orderStatus) {
        Parcel parcel = isOrderExists(orderID);
        parcel.setOrderStatus(orderStatus);
        orderRepository.save(parcel);
    }

    private UserDTO getUserDetails(User user) {
        UserDTO userDTO = new UserDTO(user.getId(),user.getUsername(),user.getAddress(),
                user.getUserType(),user.getCreation());
        return userDTO;
    }

    private OrderDTO getOrder(Parcel parcel) {
        OrderDTO orderDTO = new OrderDTO(parcel.getId(),parcel.getReceiverName(),parcel.getReceiverAddress(),
                parcel.getReceiverPhoneNumber(),parcel.getOrderPickedAddress(),parcel.getDescription(),
                parcel.getOrderPlacedDate(),getUserDetails(parcel.getCustomer()),
                parcel.getAssignedDriver() != null ? getUserDetails(parcel.getAssignedDriver()) : null);
        orderDTO.setOrderStatus(parcel.getOrderStatus());
        orderDTO.setCustomerFeedback(parcel.getCustomerFeedBack());
        return orderDTO;
    }

    @Override
    public OrderDTO getOrderByOrderId(long orderId) {
        checkPrivilege(orderId);
        Optional<Parcel> parcelOptional = orderRepository.findById(orderId);
        if(parcelOptional.isPresent()) {
            return getOrder(parcelOptional.get());
        }
        throw new OrderNotFoundException(String.format("Order : %d not found ", orderId));
    }

    private List<OrderDTO> ordersToDtoList(Page<Parcel> parcels) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Parcel parcel : parcels) {
            orderDTOS.add(getOrder(parcel));
        }
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrders(int page, int count) {
        Page<Parcel> parcels;
        Pageable pageable = PageRequest.of(page,count);
        SpringSecurityUser springSecurityUser = requestedUser();
        User user = isUserExists(springSecurityUser.getUsername());
        if(user.getUserType().name().equals(UserType.CUSTOMER.name())) {
            parcels = orderRepository.findParcelsByCustomer(user,pageable);
            return ordersToDtoList(parcels);
        } else if(user.getUserType().name().equals(UserType.DRIVER.name())) {
            parcels = orderRepository.findParcelsByAssignedDriver(user,pageable);
            return ordersToDtoList(parcels);
        } else if(user.getUserType().name().equals(UserType.ADMIN.name())) {
            parcels = orderRepository.findAll(pageable);
            return ordersToDtoList(parcels);
        }
        throw new NotAllowedResourceException("Unknown user type");
    }

    @Override
    public List<OrderDTO> getAllOrdersByOrderStatus(OrderStatus orderStatus, int page,
                                                             int count) {
        Page<Parcel> parcels;
        Pageable pageable = PageRequest.of(page,count);
        SpringSecurityUser springSecurityUser = requestedUser();
        User user = isUserExists(springSecurityUser.getUsername());
        if(user.getUserType().name().equals(UserType.CUSTOMER.name())) {
            parcels = orderRepository.findParcelsByCustomerAndAndOrderStatus(user, orderStatus ,pageable);
            return ordersToDtoList(parcels);
        } else if(user.getUserType().name().equals(UserType.DRIVER.name())) {
            parcels = orderRepository.findParcelsByAssignedDriverAndOrderStatus(user, orderStatus ,pageable);
            return ordersToDtoList(parcels);
        } else if(user.getUserType().name().equals(UserType.ADMIN.name())) {
            parcels = orderRepository.findParcelsByOrderStatus(orderStatus,pageable);
            return ordersToDtoList(parcels);
        }
        throw new NotAllowedResourceException("Unknown user type");
    }

    @Override
    public void addFeedback(OrderDTO orderDTO) {
        SpringSecurityUser requestedUser = requestedUser();
        if(!requestedUser.getRole().equals(UserType.CUSTOMER.name())) {
            throw new NotAllowedResourceException("Only a customer can request to add an feedback");
        }
        Optional<Parcel> parcelOptional = orderRepository.findById(orderDTO.getOrderID());
        if(parcelOptional.isPresent()) {
            Parcel parcel = parcelOptional.get();
            if(parcel.getCustomer().getUsername().equals(requestedUser.getUsername())) {
                if(orderDTO.getCustomerFeedback() != null) {
                    parcel.setCustomerFeedBack(orderDTO.getCustomerFeedback());
                    orderRepository.save(parcel);
                    return;
                }
                throw new RequestDataValidationException("Feedback must be present with the request");
            }
            throw new NotAllowedResourceException("You are not allowed to add feedback for this order");
        }
        throw new OrderNotFoundException(String.format("Order : %d does not exists",orderDTO.getOrderID()));
    }
}
