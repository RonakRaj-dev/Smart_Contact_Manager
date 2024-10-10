package com.my_first_project.SCM.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my_first_project.SCM.entities.User;

@Repository
public interface UserRepositories extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
