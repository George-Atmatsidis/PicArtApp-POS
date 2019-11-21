package com.chumbok.pos.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "returnVoucher")
public class ReturnVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date dateWhenTheReturnWasMade;

    private String comentary;

    private String wasItMadeOnTime;

    @OneToOne
    private Renta renta; //se ancla a la tabla de ventas

    public ReturnVoucher() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateWhenTheReturnWasMade() {
        return dateWhenTheReturnWasMade;
    }

    public void setDateWhenTheReturnWasMade(Date dateWhenTheReturnWasMade) {
        this.dateWhenTheReturnWasMade = dateWhenTheReturnWasMade;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public Renta getRenta() {
        return renta;
    }

    public void setRenta(Renta renta) {
        this.renta = renta;
    }

    public String getWasItMadeOnTime() {
        return wasItMadeOnTime;
    }

    public void setWasItMadeOnTime(String wasItMadeOnTime) {
        this.wasItMadeOnTime = wasItMadeOnTime;
    }
}
