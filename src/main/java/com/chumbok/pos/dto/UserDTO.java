package com.chumbok.pos.dto;

import com.chumbok.pos.constraint.ValidPassword;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.Valid;

public class UserDTO {

    private long id;

    @Column(unique = true)
    @Email(message = "*Asegúrese de escribir correctamente su email.")
    @NotEmpty(message = "*Favor de proveer un email.")
    private String email;

    @ValidPassword
    @NotEmpty(message = "*La contraseña no puede estar vacía.")
    private String password;

    private String confirmPassword;

    @NotEmpty(message = "*Especificar nombre.")
    private String firstName;

    @NotEmpty(message = "*Especificar apellido.")
    private String lastName;

    private int active;

    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
