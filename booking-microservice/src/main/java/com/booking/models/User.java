package com.booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User 
{
	private int userId;
	private String userName;
	private String email;
	private String phoneNumber;
	private String password;
	
	private String role="user";
}
