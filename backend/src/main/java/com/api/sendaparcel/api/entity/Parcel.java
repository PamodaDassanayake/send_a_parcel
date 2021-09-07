package com.api.sendaparcel.api.entity;

import com.api.sendaparcel.api.util.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverAddress;
    @Column(nullable = false)
    private String receiverPhoneNumber;
    @Column(nullable = false)
    private String orderPickedAddress;
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String customerFeedBack;
    @Column(nullable = false)
    private Date orderPlacedDate;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            targetEntity = User.class
    )
    @JoinColumn(
            name = "customer",
            nullable = false,
            referencedColumnName = "user_id"
    )
    private User customer;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = User.class
    )
    @JoinColumn(
            name = "assignedDriver",
            referencedColumnName = "user_id"
    )
    private User assignedDriver;

    public Parcel() {
    }

    public Parcel(String receiverName, String receiverAddress, String receiverPhoneNumber, String orderPickedAddress,
                 String description, User customer) {
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
        this.customer = customer;
    }

    public Parcel(long id, String receiverName, String receiverAddress, String receiverPhoneNumber,
                  String orderPickedAddress, String description, Date orderPlacedDate, User customer,
                  User assignedDriver) {
        this.id = id;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
        this.orderPlacedDate = orderPlacedDate;
        this.customer = customer;
        this.assignedDriver = assignedDriver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(User assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerFeedBack() {
        return customerFeedBack;
    }

    public void setCustomerFeedBack(String customerFeedBack) {
        this.customerFeedBack = customerFeedBack;
    }
}
