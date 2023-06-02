package com.userapp.controller;

import com.userapp.DTO.AuthRequest;
import com.userapp.commands.Command;
import com.userapp.commands.AuthenticateCommand;
import com.userapp.entity.UserEntity;
import com.userapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public void createUser(
            @RequestBody UserEntity newUser) {
        this.userService.createUser(newUser);
        logger.info("User Created Successfully");
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
    public List<UserEntity> getAllUsers(){
        logger.info("All Users retrieved");
        return this.userService.getAllUsers();
    }

    @PostMapping("/sendAuthRequest")
    public ResponseEntity getPassword(@RequestParam(name = "id") int id){
        Optional<UserEntity> user = userService.getUser(id);
        Command c;
        if(user.isPresent()){
            c = authenticateCommand.setRequest(new AuthRequest((user.orElse(null)).getPassword()));
        }else{
            c = authenticateCommand.setRequest(new AuthRequest(""));
        }
        c.execute();
        return ResponseEntity.ok("message sent to auth successfully");
    }

}