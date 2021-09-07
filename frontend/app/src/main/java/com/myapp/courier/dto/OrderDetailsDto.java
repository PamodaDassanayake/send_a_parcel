package com.myapp.courier.dto;

public class OrderDetailsDto {
    String name,address,phone,desc;

    public OrderDetailsDto(String name, String address, String phone, String desc) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
