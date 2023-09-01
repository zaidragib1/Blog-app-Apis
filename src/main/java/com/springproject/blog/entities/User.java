package com.springproject.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.springframework.security.core.userdetails.UserDetails;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.InverseJoinColumn;

@Entity
@Table(name="users")
//below annotations is used for genertaing the getter and setter of the values.
//if we use this annotation, then no need to generate the g&s individullly
//that why we put comment on the g&s below
@NoArgsConstructor
@Getter
@Setter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="user_name" ,nullable = false,length=100)
    private String name;

    private String about;

    private String password;

    @Column(unique = true)
    private String email;

    //cascade indicates that operations on a user (like deletion) should also be applied to related posts.
    //FetchType.LAZY, meaning posts are fetched from the database only when needed.
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role" , referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public void setRolesEntity(Set<Role> roles) {
        this.roles = roles;
    }


}
