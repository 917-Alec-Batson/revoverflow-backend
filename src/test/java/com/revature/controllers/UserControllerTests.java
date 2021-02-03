package com.revature.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.revature.Application;
import com.revature.entities.User;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerTests {
	static int id = 12;
	static User u1;
	
	@Autowired
	private WebApplicationContext context;
	
	@Mock                           
	private UserService userService; 
	
	@Autowired                           
    private MockMvc mockMvc;  
                                         
    
    @Before                          
    public void setUp() {  
       u1 = new User(12,26,0,true,null,"admin@rss.com","Admin","Admin", "password");
   	   mockMvc = MockMvcBuilders
   				.webAppContextSetup(context)
   				.apply(springSecurity())
   				.build();
    }
    
    @Test
    @WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
    public void willReturnUserWith200Status() throws Exception {
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/12")
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			System.out.println(content);
    			assertEquals(200, result.getResponse().getStatus());
    			//System.out.println(content);
    			assertFalse("This return object conains the string", content.contains("admin@rss.com"));
    }
    
    @Test
    @WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
    public void willNotReturnUserWith200Status() throws Exception {
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/15")
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			assertEquals(200, result.getResponse().getStatus());
    			assertEquals("", content);
    }
    @Test
    @WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
    public void willReturnNothingWith400Status() throws Exception {
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/alatreon")
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			assertEquals(400, result.getResponse().getStatus());
    			assertEquals("", content);
    }
    
    @Test
    @WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
    public void willReturnNothingWith404Status() throws Exception {
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/20/alatreon")
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			assertEquals(404, result.getResponse().getStatus());
    			assertEquals("", content);
    }
    
}