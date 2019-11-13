package com.chumbok.pos.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class ProductDTO {

    @NotEmpty
    private String displayName;
    @NotEmpty
    private String vendor; //marca

    private String disabled; //el producto está disabled o no
    private String catagory;
    private String description;
    @Min(value = 0)
    private BigDecimal salesPrice; //precio de venta -> weight
    @Min(value = 0)
    private BigDecimal rentPrice; //Precio de renta -> barcode

    public ProductDTO() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }
}
