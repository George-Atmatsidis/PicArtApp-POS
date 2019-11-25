package com.chumbok.pos.dto;

import java.util.Date;

public class VentaDTO {
    private Date salesDate;

    private String email;

    private long productId;

    private long quantity;

    private long maxQuantity; //I hope we can limit the sale to the products on stock

    //nombre del producto //help
    private String displayName;

    private double unitaryPrice;

    public VentaDTO() {
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getUnitaryPrice() {
        return unitaryPrice;
    }

    public void setUnitaryPrice(double unitaryPrice) {
        this.unitaryPrice = unitaryPrice;
    }
}
