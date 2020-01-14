package com.gul.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gul.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndPassword(String email, String password);
}
