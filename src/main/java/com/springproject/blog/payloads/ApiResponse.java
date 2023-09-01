package com.springproject.blog.payloads;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ApiResponse {

    private String message;

    private boolean success;
}
