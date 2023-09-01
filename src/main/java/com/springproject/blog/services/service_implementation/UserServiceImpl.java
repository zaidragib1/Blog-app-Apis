package com.springproject.blog.services.service_implementation;

import com.springproject.blog.Exceptions.ResourceNotFoundException;
import com.springproject.blog.entities.User;
import com.springproject.blog.payloads.UserDto;
import com.springproject.blog.repositories.RoleRepo;
import com.springproject.blog.repositories.UserRepo;
import com.springproject.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
/*
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        // as we had user but we require userDto so for converting user
        // to userDto we consider UserToDto here... check UserService by clicking on arrow ,ade in front
        //of registerNewUser function
        User user = this.userToDto(userDto);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

    }
    */

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);   // Convert UserDTO to User
        User savedUser = this.userRepo.save(user);  // Save the user entity

        return this.userToDto(savedUser);   // Convert the saved User back to UserDTO and return
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // Find the user with the given userId in the database or idf not find throw exception
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));

        // Update user information based on the provided UserDto
        //as we have Dto and we have to convert dto to user so we will set into User and get the data from UserDto
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        // Save the updated user entity to the database
        User updatedUser = this.userRepo.save(user);

        // Convert the updated User entity to a UserDto
        UserDto userDto1 = this.userToDto(updatedUser);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>(); //making answer list named as userDtos
        // Fetch all users from the data source
        List<User> users = this.userRepo.findAll();

        for (User x : users) {
            UserDto userDto = userToDto(x); // Convert User to UserDto
            userDtos.add(userDto);
        }

        return userDtos;

        //BELOW COMMNET STUFF IS ALSO SAME AS ABOVE , IT WILL ALSO DO SAME WORK
        /*
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
         */
    }

    @Override
    public void deleteUser(Integer userId) {
        //fetching wheteher user exists or not
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));

        this.userRepo.delete(user); //deleting user
    }

    public User dtoToUser(UserDto userDto){
        //although this can be done by model mapper but still for now we are using this thing
        //first we will convert dto to user then user to dto, to get
            User user = new User();
            user.setId(userDto.getId());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setAbout(userDto.getAbout());
            user.setPassword(userDto.getPassword());
            return user;
        }


    //WHY WE ARE CONVERTING USERDTO TO USER?
    //because we have to pass "user" as parameter in create user,but we have userDto as parameter,
    //se we will change dto to user, and then we dto is required we agian change it back to dto
    public UserDto userToDto(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAbout(user.getAbout());
        userDto.setPassword(user.getPassword());

        return userDto;
    }


}
