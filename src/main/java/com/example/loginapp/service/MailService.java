package com.example.loginapp.service;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.loginapp.entity.ForgetPassword;
import com.example.loginapp.entity.User;
import com.example.loginapp.repository.ForgetPasswordRepository;
import com.example.loginapp.repository.UserRepository;

@Component
public class MailService {

	@Autowired

	private UserRepository userRepo;

	@Autowired
	private ForgetPasswordRepository forgetPassRepo;

	public boolean sendOtp(String toEmail) throws Exception{
	
		String from = "example@gmail.com";
		String host = "smtp.gmail.com";
		
		
		//check if this email is valid register user 
		User user = userRepo.findByEmail(toEmail);
		if(user != null) {
			
			//send otp to this email
 
			//generate random number
			String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
			
			System.out.println("otp generated "+ otp);
			ForgetPassword forgetPassword = new ForgetPassword();
			forgetPassword.setEmail(toEmail);
			forgetPassword.setOtpGerated(otp);
			
			 
			//sending opt to mail
	
			//getting system properties
			Properties properties = System.getProperties();
			
			//setting up properties for mail
			
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enabled", "true");
			
			
			//for sending mail
			//step1: create session object
			Session session = Session.getInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					 
					return new PasswordAuthentication(from, "<password here>");
				}
				
			});
			
			//for debug
			session.setDebug(true);
			
			//step2: prepare message
			try {
			MimeMessage msg = new MimeMessage(session);
			
			msg.setFrom(from);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject("Reset Password");
			msg.setText("This is 6 digit opt for password rest. OTP :"+ otp);
			
			
			//send mail
			Transport.send(msg);
			
			System.out.println("Mail Successfully sent");
			
			return true;
			}catch(Exception e) {
				e.printStackTrace();
			}
			 
		}
		return false;
	}

	public boolean verifyOtp(String email, String otp) {

		ForgetPassword forgetPass = forgetPassRepo.findByEmailAndOtpGerated(email, otp);

		if (forgetPass != null) {
			return true;
		}
		return false;
	}

}
