package com.shoppingcart.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    
    private Long clientId;
    private String name;
    private String lastName;
    private String email;
    private String username;
    private String role;
}
