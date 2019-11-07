package com.chumbok.pos.dto;

import com.chumbok.pos.constraint.ValidPassword;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;

public class UserDTO {

    @Column(unique = true)
    @Email(message = "*Asegúrese de escribir correctamente su email.")
    @NotEmpty(message = "*Favor de proveer un email.")
    private String email;

    @ValidPassword
    private String password;

    @NotEmpty(message = "*Especificar nombre.")
    private String firstName;

    @NotEmpty(message = "*Especificar apellido.")
    private String lastName;

    private int active;
}
