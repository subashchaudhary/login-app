package com.example.loginapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loginapp.entity.User;

@Repository	
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findUserByUsernameAndPassword(String username, String password);
	
	
	public User findByEmail(String email);
	

}
