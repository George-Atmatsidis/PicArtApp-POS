package com.chumbok.pos.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "renta")
public class Renta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idRenta;

    @ManyToOne
    private Product product;

    @OneToOne
    private User user;

    @OneToOne
    private Customer customer;

    private int quantity;

    private int price;

    private Date dateOfRent;

    private Date dateOfReturn;

    public Renta() {
    }

    public long getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(long idRenta) {
        this.idRenta = idRenta;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(Date dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
