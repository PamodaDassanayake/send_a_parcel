package com.api.sendaparcel.api.dto;

import com.api.sendaparcel.api.util.enums.UserStatus;
import com.api.sendaparcel.api.util.enums.UserType;

import java.util.Date;
import java.util.List;

public class UserDTO {

    private long userId;
    private String username;
    private String password;
    private String address;
    private UserType userType;
    private UserStatus userStatus;
    private Date creation;
    private List<OrderDTO> OrderDTOs;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String address, UserType userType) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.userType = userType;
    }

    public UserDTO(long userId, String username, String address, UserType userType, Date creation) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.userType = userType;
        this.creation = creation;
    }

    public UserDTO(long userId, String username, String password, String address, UserType userType, Date creation) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.address = address;
        this.userType = userType;
        this.creation = creation;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public List<OrderDTO> getOrderDTOs() {
        return OrderDTOs;
    }

    public void setOrderDTOs(List<OrderDTO> OrderDTOs) {
        this.OrderDTOs = OrderDTOs;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
