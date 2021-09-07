package com.myapp.courier.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateOrderStatusDTO {
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;

    public UpdateOrderStatusDTO(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
