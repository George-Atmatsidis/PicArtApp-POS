package com.chumbok.pos.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


public class StockDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date stockEntryDate;
    private String stockExpireDate; //this is not a date, reason for the update here.
    private Long quantiy;
    private Long productId;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String user;
    private String productName;

    public Date getStockEntryDate() {
        return stockEntryDate;
    }

    public void setStockEntryDate(Date stockEntryDate) {
        this.stockEntryDate = stockEntryDate;
    }

    public String getStockExpireDate() {
        return stockExpireDate;
    }

    public void setStockExpireDate(String stockExpireDate) {
        this.stockExpireDate = stockExpireDate;
    }

    public Long getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(Long quantiy) {
        this.quantiy = quantiy;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}