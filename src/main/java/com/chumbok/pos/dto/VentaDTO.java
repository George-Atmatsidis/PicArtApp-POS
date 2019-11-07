package com.chumbok.pos.dto;

import com.chumbok.pos.entity.Product;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class VentaDTO {
    @NotNull(message = "Por favor, especifique una fecha")
    Date salesDate;

    @NotEmpty(message = "Favor de especificar el usuario.")
    String email;


    long idProduct;

    @NotNull(message = "Por favor, especifique una cantidad")
    @Min(value = 0, message = "La cantidad debe ser al menos 0.")
    int quantity;

    //nombre del producto //help
    String displayName;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
