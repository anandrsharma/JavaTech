package com.aws.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/")
	public String getMessage() {
		return "Hello World, I am first program on AWS....";
	}
}
