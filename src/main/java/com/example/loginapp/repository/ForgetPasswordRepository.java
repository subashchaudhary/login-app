package com.example.loginapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loginapp.entity.ForgetPassword;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Integer>{

	
	public ForgetPassword findByEmailAndOtpGerated(String email, String otp);
}
