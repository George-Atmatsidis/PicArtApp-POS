package com.chumbok.pos.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CustomerDTO {
    @NotEmpty(message = "Inserte correctamente el nombre.")
    @NotNull
    private String firstName;

    @NotEmpty(message = "Inserte correctamente el apellido.")
    @NotNull
    private String lastName;

    @NumberFormat
    private long phoneNumber;

    @Email(message = "Favor de verificar el email.")
    @NotEmpty(message = "El email no puede estar vacío.")
    @NotNull
    private String email;

    @NotEmpty(message = "La CURP no puede estar vacía.")
    @NotNull
    private String curp;

    private String nameAval;

    private long avalPhone;

    public CustomerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNameAval() {
        return nameAval;
    }

    public void setNameAval(String nameAval) {
        this.nameAval = nameAval;
    }

    public long getAvalPhone() {
        return avalPhone;
    }

    public void setAvalPhone(long avalPhone) {
        this.avalPhone = avalPhone;
    }
}
