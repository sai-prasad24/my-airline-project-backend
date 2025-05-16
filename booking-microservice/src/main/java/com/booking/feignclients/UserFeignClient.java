package com.booking.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.models.User;


@FeignClient("USER-MICROSERVICE")
public interface UserFeignClient
{
	@GetMapping("user/user/{name}")
	public User getUserByUserName(@PathVariable String name);
}
