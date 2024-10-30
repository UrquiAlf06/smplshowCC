package com.au.usermgmt.repository;

import com.au.usermgmt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameContaining(String firstName);
    boolean existsByEmail(String email);
}
