package com.chumbok.pos.dto;

import com.chumbok.pos.utility.ValidPassword;

public class UserDTO {
    @ValidPassword
    private String password;
}
