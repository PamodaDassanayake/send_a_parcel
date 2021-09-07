package com.api.sendaparcel.api.dto;

import com.api.sendaparcel.api.util.enums.OrderStatus;

import java.util.Date;

public class OrderDTO {

    private long orderID;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String orderPickedAddress;
    private String description;
    private Date orderPlacedDate;
    private UserDTO customer;
    private UserDTO assignedDriver;
    private OrderStatus orderStatus;
    private String customerFeedback;

    public OrderDTO() {
    }

    public OrderDTO(String receiverName, String receiverAddress, String receiverPhoneNumber,
                    String orderPickedAddress, String description) {
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
    }

    public OrderDTO(long orderID, String receiverName, String receiverAddress,
                    String receiverPhoneNumber, String orderPickedAddress, String description, Date orderPlacedDate,
                    UserDTO customer) {
        this.orderID = orderID;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
        this.orderPlacedDate = orderPlacedDate;
        this.customer = customer;
    }

    public OrderDTO(long orderID, String receiverName, String receiverAddress, String receiverPhoneNumber,
                    String orderPickedAddress, String description, Date orderPlacedDate, UserDTO customer,
                    UserDTO assignedDriver) {
        this.orderID = orderID;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
        this.orderPlacedDate = orderPlacedDate;
        this.customer = customer;
        this.assignedDriver = assignedDriver;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getOrderPickedAddress() {
        return orderPickedAddress;
    }

    public void setOrderPickedAddress(String orderPickedAddress) {
        this.orderPickedAddress = orderPickedAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Date orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
        this.customer = customer;
    }

    public UserDTO getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(UserDTO assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerFeedback() {
        return customerFeedback;
    }

    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }
}
