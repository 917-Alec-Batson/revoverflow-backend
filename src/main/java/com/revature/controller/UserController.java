package com.revature.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	UserService userService;
	
	/**
	 * @author Ryan Clayton
	 * @param id this is the user's Id
	 * @return User this returns the user entity with updated RSS points
	 */
	@GetMapping("/{id}")
	//@PreAuthorize("hasAuthority('user')")
	public User getUserById(@AuthenticationPrincipal String user, @PathVariable int id) {
		return userService.getUserById(id);
	}

	@GetMapping("/login/{token}")
	public User login(@AuthenticationPrincipal String user, @PathVariable("token") String token){
		System.out.println("hi");
		return null;
	}
}
