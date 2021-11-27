package com.example.loginapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.entity.User;
import com.example.loginapp.repository.UserRepository;


@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public User saveUserDetails(User user) {

		return userRepo.save(user);

	}

	@Override
	public User userLogin(String username, String password) {

		User user = userRepo.findUserByUsernameAndPassword(username, password);
		if (user == null)
			return null;
		return user;
	}

}
