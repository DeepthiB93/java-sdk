package com.java.sdk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.sdk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}