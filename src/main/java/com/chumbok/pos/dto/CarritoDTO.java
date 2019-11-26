package com.chumbok.pos.dto;

import com.chumbok.pos.entity.Product;

import java.util.List;

public class CarritoDTO {
    private List<Product> productosEnElCarrito;
    private long cantidadDeObjetos;
    private double totalPrice;

    public CarritoDTO() {
    }

    public List<Product> getProductosEnElCarrito() {
        return productosEnElCarrito;
    }

    public void setProductosEnElCarrito(List<Product> productosEnElCarrito) {
        this.productosEnElCarrito = productosEnElCarrito;
    }

    public long getCantidadDeObjetos() {
        return cantidadDeObjetos;
    }

    public void setCantidadDeObjetos(long cantidadDeObjetos) {
        this.cantidadDeObjetos = cantidadDeObjetos;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
