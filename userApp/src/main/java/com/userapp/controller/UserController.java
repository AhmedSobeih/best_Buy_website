package com.userapp.controller;

import com.userapp.entity.UserEntity;
import com.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void createUser(
            @RequestBody UserEntity newUser) {
        this.userService.createUser(newUser);
    }

    @PutMapping
    public void updateUser(
            @RequestBody UserEntity newUser) {
        this.userService.updateUser(newUser);
    }

    @DeleteMapping
    public void deleteUser(
            @RequestParam(name = "id") int id) {
        this.userService.deleteUser(id);
    }

    @GetMapping
    public Optional<UserEntity> getUser(
            @RequestParam(name = "id") int id) {
        return this.userService.getUser(id);
    }

    @GetMapping(path = "/{name}")
    public UserEntity getUserByName(
            @PathVariable String name) {
        return this.userService.getUserByName(name);
    }

    @GetMapping(path = "/all")
    public List<UserEntity> getAllUsers(){
        return this.userService.getAllUsers();
    }

}