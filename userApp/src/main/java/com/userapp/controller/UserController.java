package com.userapp.controller;

import com.userapp.DTO.Request;
import com.userapp.commands.AuthenticateCommand;
import com.userapp.commands.TransactionCommand;
import com.userapp.entity.UserEntity;
import com.userapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticateCommand authenticateCommand;

    @Autowired
    private TransactionCommand transactionCommand;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public void createUser(
            @RequestBody UserEntity newUser) {
        UserEntity user = this.userService.createUser(newUser);
        logger.info("User Created Successfully");
        Request r = new Request(user.getId(), user.getName(), user.getPassword(), user.getRole());
        authenticateCommand.setRequest(r);
        authenticateCommand.execute();
        transactionCommand.setRequest(r);
        transactionCommand.execute();
    }

    @PutMapping
    public void updateUser(
            @RequestBody UserEntity newUser) {
        this.userService.updateUser(newUser);
        logger.info("User Updated Successfully");
    }

    @DeleteMapping
    public void deleteUser(
            @RequestParam(name = "id") int id) {
        this.userService.deleteUser(id);
        logger.info("User with id: (" + id + ") Deleted Successfully");
    }

    @GetMapping
    public Optional<UserEntity> getUser(
            @RequestParam(name = "id") int id) {
        logger.info("User with id: (" + id + ") retrieved");
        return this.userService.getUser(id);
    }

    @GetMapping(path = "/{name}")
    public UserEntity getUserByName(
            @PathVariable String name) {
        logger.info("User with name: (" + name + ") retrieved");
        return this.userService.getUserByName(name);
    }

    @GetMapping(path = "/all")
    public List<UserEntity> getAllUsers() {
        logger.info("All Users retrieved");
        return this.userService.getAllUsers();
    }

}