package com.alesta.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alesta.response.ApiResponse;
import com.alesta.user.exception.UserException;
import com.alesta.user.model.User;
import com.alesta.user.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ApiResponse<User> saveUser(@RequestBody User user) {
		try {
			return userService.saveUser(user);
		} catch (UserException e) {
			return new ApiResponse<>(false, "User registered failed : " + e.getMessage(), user);
		}
	}

	@GetMapping("/login")
	public ApiResponse<User> loginUser(@RequestBody User user) {
		try {
			return userService.loginUser(user);
		} catch (UserException e) {
			return new ApiResponse<>(false, "User login failed : " + e.getMessage(), user);
		}
	}
}
