package com.chumbok.pos.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class VentaDTO {
    private Date salesDate;

    private String email;

    private long idProduct;

    @NotNull(message = "Por favor, especifique una cantidad")
    @Min(value = 0, message = "La cantidad debe ser al menos 0.")
    private long quantity;

    private long maxQuantity; //I hope we can limit the sale to the products on stock

    //nombre del producto //help
    private String displayName;

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

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
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
}
