package com.example.loginapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.dto.Login;
import com.example.loginapp.entity.User;
import com.example.loginapp.repository.UserRepository;
import com.example.loginapp.service.MailService;
import com.example.loginapp.service.UserService;

@RestController

public class UserController {

	@Autowired
	UserService userService;

	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	MailService mailService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Login login){
		
		String username = login.getUsername();
		String password = login.getPassword();
		
		
		User user = userService.userLogin(username, password);
		if(user == null) {
			
			return	ResponseEntity.ok("Invalid username or password");
		}
		return ResponseEntity.ok("Login Success");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user){
		
		return ResponseEntity.ok(userService.saveUserDetails(user));
		 
	}
	
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestParam String email) {
		
		try {
			boolean res = mailService.sendOtp(email);
			if(res) {
				return ResponseEntity.ok("Mail send successfully");
			}
		} catch (Exception e) {
		 
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("Error while sending mail");
		 
	}
	
	@PostMapping("/opt")
	public ResponseEntity<?> confirmOtp(@RequestParam String email, @RequestParam String otp){
		
		boolean res  =mailService.verifyOtp(email, otp);
		if(res) {
			return ResponseEntity.ok(true);
		}
		
		return ResponseEntity.ok("Incorrect otp entered");
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<?> resetPassword(Integer id, String newPassword){
		User user = userRepo.findById(id).get();
		
		if(user != null) {
			user.setPassword(newPassword);
			User updateUser = userRepo.save(user);
			if(updateUser != null) {
				return ResponseEntity.ok("Successfully password updated");
			}
			
		}
		 
			return ResponseEntity.ok("User does not exits");
	}
	
	
}
