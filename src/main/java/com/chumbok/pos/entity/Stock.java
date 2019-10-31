package com.chumbok.pos.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date stockEntryDate; //fecha en que se genera la alta / baja
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private String stockExpireDate; // este campo es una descripción, creo

    private BigDecimal purchasePrice;
    private BigDecimal salePrice;

    private Long quantiy;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(Long quantiy) {
        this.quantiy = quantiy;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockEntryDate=" + stockEntryDate +
                ", stockExpireDate=" + stockExpireDate +
                ", purchasePrice=" + purchasePrice +
                ", salePrice=" + salePrice +
                ", quantiy=" + quantiy +
                ", product=" + product +
                '}';
    }
}