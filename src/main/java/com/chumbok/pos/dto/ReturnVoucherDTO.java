package com.chumbok.pos.dto;

public class ReturnVoucherDTO {

    private long idRenta;

    private String customerName;

    private String dateOfRent;

    private String dateOfReturn;

    private String status;

    private String productName;

    private String userMakingTheReturn;

    private String userWhoMadeTheRent;

    private int quantity;

    private String comments;

    private double totalPrice;

    public ReturnVoucherDTO() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(String dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserMakingTheReturn() {
        return userMakingTheReturn;
    }

    public void setUserMakingTheReturn(String userMakingTheReturn) {
        this.userMakingTheReturn = userMakingTheReturn;
    }

    public String getUserWhoMadeTheRent() {
        return userWhoMadeTheRent;
    }

    public void setUserWhoMadeTheRent(String userWhoMadeTheRent) {
        this.userWhoMadeTheRent = userWhoMadeTheRent;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(long idRenta) {
        this.idRenta = idRenta;
    }
}
