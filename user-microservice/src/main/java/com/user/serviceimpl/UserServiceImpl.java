package com.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.jwt.JwtService;
import com.user.models.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	public User addUser(User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
	return userRepository.save(user) ;
	}

	@Override
	public List<User> findAllUsers()
	{
		
		return userRepository.findAll();
	}

	public String findUserToken(User name)
	{
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name.getUserName(), name.getPassword()));
		User user=userRepository.findByUserName(name.getUserName());
		
		if(authentication.isAuthenticated())
			return jwtService.generateToken(user);
		else
			return "Login Failed";
	}

	@Override
	public User findUser(String name)
	{
		  
		return userRepository.findByUserName(name);
	}

}
