package com.enviro.practice.grad001.kwanelentshele.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.practice.grad001.kwanelentshele.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
}
