package com.example.project_backend04.repository.IRepository;

import com.example.project_backend04.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
