package com.authenticationApp.authentication.repository;

import com.authenticationApp.authentication.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthenticationRepo extends JpaRepository<AuthenticationEntity, Integer> {
}
