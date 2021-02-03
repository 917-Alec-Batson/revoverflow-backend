package com.revature.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Application;
import com.revature.entities.Answer;
import com.revature.entities.Faq;
import com.revature.entities.Location;
import com.revature.entities.Question;
import com.revature.entities.User;
import com.revature.services.FaqService;

/**
 * @authors Mahesh Kalle & Jeevan Selvagunarajah
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class)
@AutoConfigureMockMvc
public class FaqControllerTest {
	
	@Autowired 
	private ObjectMapper mapper;
	
	@Autowired
	private WebApplicationContext context;

	static User u1;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FaqService faqService;
	
	@Before                          
    public void setUp() {  
       u1 = new User(12,26,0,true,null,"admin@rss.com","Admin","Admin", "password");
   	   mvc = MockMvcBuilders
   				.webAppContextSetup(context)
   				.apply(springSecurity())
   				.build();
    }
	
	@Test
    @WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void testGetAllFaq() throws Exception {
		
		List<Faq> faqList = new ArrayList<>();
		
		Question q = new Question();
		q.setId(1);
		
		Answer a = new Answer();
		a.setId(1);
		
		
		faqList.add(new Faq(q,a));

		// Stub getAllQuestions to return page of data
		when(faqService.getAllFaq()).thenReturn(faqList);
		
		// Call API end point and assert result
		mvc.perform(get("/faq")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
			.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id", is(0)));
	}
	
	@Test
    @WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void testGetFaqById() throws Exception {
		
		List<Faq> faqList = new ArrayList<>();
		
		Question q = new Question();
		q.setId(1);
		
		Answer a = new Answer();
		a.setId(1);
		
		Faq f = new Faq(q,a);
		f.setId(4);
		
		
		
		faqList.add(f);

		// Stub getAllQuestions to return page of data
		when(faqService.findFaqById(4)).thenReturn(f);
		
		// Call API end point and assert result
		MvcResult res = mvc.perform(get("/faq/4")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
			.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(4))).andReturn();
		System.out.println("result = "+res);
	}
	
	@Test
    @WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "user")
	public void testSaveFaq() throws Exception {
		
		Question q = new Question();
		q.setId(1);
		q.setAcceptedId(1);
		q.setContent("quest content");
		q.setCreationDate(LocalDateTime.MIN);
		q.setEditDate(LocalDateTime.MIN);
		q.setStatus(true);
		q.setUserID(13);
		
		Answer a = new Answer();
		a.setId(1);
		a.setContent("quest content");
		a.setCreationDate(LocalDateTime.MIN);
		a.setEditDate(LocalDateTime.MIN);
		a.setUserId(13);
		
		Faq faq = new Faq(q, a);

		when(faqService.newFaqQuestion(Mockito.any(Faq.class))).thenReturn(faq);
		
        String toUpdate = mapper.writeValueAsString(faq);
         mvc.perform(MockMvcRequestBuilders.post("/faq")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toUpdate)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                );
	}
}


