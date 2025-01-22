package com.myproject.myproject.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.myproject.model.User;
import com.myproject.myproject.service.UserService;

@RestController
public class UserController {
	
	@Autowired UserService service;
	
	@PostMapping("/register")
	public User register(@RequestBody User user)
	{
		return service.saveUser(user);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody User user)
	{
		User dbUser = service.findUserByName(user);
		if(Objects.nonNull(dbUser))
		{
			return "success";
		}
		return "login failed";
	}
}
