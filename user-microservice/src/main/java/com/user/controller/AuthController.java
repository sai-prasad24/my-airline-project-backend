package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.models.User;
import com.user.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public User addUsers(@RequestBody User user) 
	{
		return userService.addUser(user);
	}
	
	@PostMapping("/login")
	public String loginUsers(@RequestBody User user)
	{

//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//		if(authentication.isAuthenticated())
//			return jwtService.generateToken(""+user.getUserName()+","+user.get);
//		else
//			return "Login Failed";
		return userService.findUserToken(user);

	}
	
}
