package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.models.User;
import com.user.service.UserService;


@RestController
@RequestMapping("user")
public class UserController
{
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/user/{name}")
	public User getUserByUserName(@PathVariable String name)
	{
		return userService.findUser(name);
	}
}
