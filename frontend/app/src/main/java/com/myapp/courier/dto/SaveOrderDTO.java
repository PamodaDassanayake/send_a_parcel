package com.myapp.courier.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveOrderDTO {
    @SerializedName("receiverName")
    @Expose
    private String receiverName;

    @SerializedName("receiverAddress")
    @Expose
    private String receiverAddress;

    @SerializedName("receiverPhoneNumber")
    @Expose
    private String receiverPhoneNumber;

    @SerializedName("orderPickedAddress")
    @Expose
    private String orderPickedAddress;

    @SerializedName("description")
    @Expose
    private String description;

    public SaveOrderDTO(String receiverName, String receiverAddress, String receiverPhoneNumber, String orderPickedAddress, String description) {
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.orderPickedAddress = orderPickedAddress;
        this.description = description;
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
}
