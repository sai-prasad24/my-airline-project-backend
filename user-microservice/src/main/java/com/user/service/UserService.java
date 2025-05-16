package com.user.service;

import java.util.List;

import com.user.models.User;

public interface UserService 
{
	public User addUser(User user);
	public List<User> findAllUsers();
	public User findUser(String name);
	public String findUserToken(User name);
}
