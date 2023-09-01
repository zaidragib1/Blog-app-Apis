package com.springproject.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

    //in response, we will get a token and user details
    private String token;

    private UserDto user;
}
