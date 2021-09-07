package com.api.sendaparcel.api.entity;

import com.api.sendaparcel.api.util.enums.UserStatus;
import com.api.sendaparcel.api.util.enums.UserType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    private Date creation;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Parcel> customerOrders;

    @OneToMany(
            mappedBy = "assignedDriver",
            fetch = FetchType.LAZY
    )
    private List<Parcel> driverOrders;

    public User() {
    }

    public User(String username, String password, String address, UserType userType) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.userType = userType;
    }

    public User(long id, String username, String password, String address, UserType userType, Date creation) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.userType = userType;
        this.creation = creation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public List<Parcel> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<Parcel> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public List<Parcel> getDriverOrders() {
        return driverOrders;
    }

    public void setDriverOrders(List<Parcel> driverOrders) {
        this.driverOrders = driverOrders;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
