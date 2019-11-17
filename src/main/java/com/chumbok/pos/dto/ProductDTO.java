package com.chumbok.pos.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class ProductDTO {

    long id;

    @NotEmpty
    private String displayName;
    @NotEmpty
    private String vendor; //marca

    private String disabled; //el producto está disabled o no
    private String catagory;
    private String description;
    @Min(value = 1)
    private double salesPrice; //precio de venta -> weight
    @Min(value = 1)
    private double rentPrice; //Precio de renta -> barcode

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

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
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

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
