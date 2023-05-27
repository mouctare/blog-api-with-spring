package com.blog.blogappapis.controllers;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.UserDto;
import com.blog.blogappapis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId)
    {
       UserDto updatedUser = this.userService.updateUser(userDto, userId);
       return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/single/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
    {
       return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
