package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String Name);
}
