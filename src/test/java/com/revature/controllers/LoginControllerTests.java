package com.revature.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Application;
import com.revature.DTOs.RSSUserDTO;
import com.revature.services.RSSService;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class)
@AutoConfigureMockMvc
public class LoginControllerTests {
	static RSSUserDTO u1, u2, u3;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired 
	private ObjectMapper mapper;
	
	@Mock
	RSSService rssService;
	
	@Autowired                           
    private MockMvc mockMvc;  
	
	@Before                          
    public void setUp() {  
	   u1 = new RSSUserDTO("admin@rss.com","Password123!");
       u2 = new RSSUserDTO("keirss",null);
       u3 = new RSSUserDTO("user@rss.com", "Password123!");
   	   mockMvc = MockMvcBuilders
   				.webAppContextSetup(context)
   				.apply(springSecurity())
   				.build();
    }
	
	@Test
    public void will_return_user_with_a_status_of_200_and_them_to_our_database() throws Exception {
		String user = mapper.writeValueAsString(u1);
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
    			.contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(user)
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    	       // System.out.println(result);
    			String content = result.getResponse().getContentAsString();
    			assertEquals(200, result.getResponse().getStatus());
    			//assertTrue("This return object conains the string", content.contains("jwt"));
    			//assertNotEquals(null, content);
    }
	@Test
    public void will_not_return_no_user_with_a_status_of_200_and_no_jwtToken() throws Exception {
		String user1 = mapper.writeValueAsString(u2);
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
    			.contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(user1)
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			System.out.println(content+ "\n" +  result.getResponse().getStatus());
    			assertEquals(401, result.getResponse().getStatus());
//   			assertFalse("This return object conains no token", content.contains("jwt"));
//    			assertEquals("", content);
    }
	@Test
    public void will_return_user_with_a_status_of_200() throws Exception {
		String user = mapper.writeValueAsString(u3);
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
    			.contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(user)
    			.accept(MediaType.APPLICATION_JSON_UTF8)
    			).andReturn();
    			String content = result.getResponse().getContentAsString();
    			assertEquals(200, result.getResponse().getStatus());
    			//assertTrue("This return object conains the string", content.contains("jwt"));
    			//assertNotEquals(null, content);
    }
}
