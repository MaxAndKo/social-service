package com.km.socserv.repository;

import com.km.socserv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public List<User> findAllByRole(String role);
}
