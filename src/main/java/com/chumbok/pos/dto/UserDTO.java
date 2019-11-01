package com.chumbok.pos.dto;

import com.chumbok.pos.constraint.ValidPassword;

public class UserDTO {
    @ValidPassword
    private String password;
}
