package com.belstu.thesisproject.dto.user;

import lombok.Data;

@Data
public class UserLogin {
    private final String email;
    private final String password;
}
