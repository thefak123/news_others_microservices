package com.example.user_service.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.user_service.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(Long id);
    
}
