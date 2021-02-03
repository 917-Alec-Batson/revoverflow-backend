package com.revature.services;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.revature.entities.User;
import com.revature.repositories.UserRepository;

public class UserServiceTest {
	static User u1, u2;

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	

	
	@Before
	public void setup() {
		u1 = (new User(12,26,0,true,null,"admin@rss.com","Admin","Admin", "password"));
		u2 = (new User(13,26,0,false,null,"user@rss.com","User","User", "password"));
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void grantedAuthoritiesForAdminShouldReturnCorrectRoles() {
		Collection<GrantedAuthority> adminRoles = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority a= null; 
		a = new SimpleGrantedAuthority("admin");
		adminRoles.add(a);
		a = new SimpleGrantedAuthority("user");
		adminRoles.add(a);
		
		Mockito.when(userRepository.findById(u1.getUserID())).thenReturn(Optional.of(u1));
		Collection<? extends GrantedAuthority> result = userService.getAuthority(u1);
		assertEquals(adminRoles, result);
	}
	
	@Test
	public void grantedAuthoritiesUserWillReturnOnlyUserRole() {
		Collection<GrantedAuthority> userRoles = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority b= null; 
		b = new SimpleGrantedAuthority("user");
		userRoles.add(b);
		
		Mockito.when(userRepository.findById(u2.getUserID())).thenReturn(Optional.of(u2));
		Collection<? extends GrantedAuthority> result = userService.getAuthority(u2);
		assertEquals(userRoles, result);
	}
	


}
