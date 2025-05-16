package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.models.User;
import com.user.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController
{
	
	@Autowired
	private UserService userService;


	@GetMapping("/getAllUsers")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> getUsers()
	{
		return userService.findAllUsers();
	}
}
