package com.myproject.myproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.myproject.model.Configuration;
import com.myproject.myproject.model.Limits;

@RestController
public class LimitsController {
	
	@Autowired
	private Configuration config;
	
	
	
	@GetMapping("/limits")
	public Limits getLimits()
	{
		return new Limits(config.getMinimum(),config.getMaximum());
	}

}
