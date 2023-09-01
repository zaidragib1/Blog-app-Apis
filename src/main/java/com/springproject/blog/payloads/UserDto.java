package com.springproject.blog.payloads;

import com.springproject.blog.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class UserDto extends User {


    private int id;

    @Size(min=4,max=10,message = "Username must be min of 4 characters and max of 4 characters")
    private String name;

    @Email(message = "Email address is not valid!!")
    @NotEmpty(message = "Email id required!!")
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
