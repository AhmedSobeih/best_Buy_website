package com.userapp.service;

import com.userapp.entity.UserEntity;
import com.userapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserEntity createUser(UserEntity newUser) {
        return userRepo.save(newUser);
    }

    public void updateUser(UserEntity user) {
        UserEntity savedUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find user by ID %s", user.getId())
                ));


        if(user.getName() != null)
            savedUser.setName(user.getName());
        if(user.getPhoneNumber() != null)
            savedUser.setPhoneNumber(user.getPhoneNumber());
        if(user.getAddress() != null)
            savedUser.setAddress(user.getAddress());
        if(user.getDateOfBirth() != null)
        savedUser.setDateOfBirth(user.getDateOfBirth());
        if(user.getEmail() != null)
            savedUser.setEmail(user.getEmail());
        if(user.getPassword() != null)
            savedUser.setPassword(user.getPassword());
        userRepo.save(savedUser);
    }

    public Optional<UserEntity> getUser(int id) {
        return userRepo.findById(id);
    }

    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }
    public UserEntity getUserByName(String name){
        return userRepo.findByName(name).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find user by name %s", name)
        ));
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

}
