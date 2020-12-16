package com.example.SpringTutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String home() {
		return "home";
	}
}
