package com.example.loginapp.service;

import com.example.loginapp.entity.User;

public interface UserService {

	
	public User saveUserDetails(User user);
	
	public User userLogin(String username, String password);
	
}
