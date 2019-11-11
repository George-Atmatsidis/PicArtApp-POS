package com.chumbok.pos.entity;

import javax.persistence.*;

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


    //TODO add the rest of this renta
}
