package com.au.usermgmt.repository;

import com.au.usermgmt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsById(Long id);
}
