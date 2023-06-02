package com.authenticationApp.authentication.repository;

import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthenticationRepo extends JpaRepository<AuthenticationEntity, Integer> {

    Optional<AuthenticationEntity> findByEmail(String email);
    Optional<Integer> findIdByEmail(String email);
    Optional<Role> findRoleByEmail(String email);
}
