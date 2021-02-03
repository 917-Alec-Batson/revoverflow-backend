package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.revature.DTOs.RSSAccountDTO;
import com.revature.entities.User;
import com.revature.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssServiceTest {
	static User u1, u2;

	@MockBean
	private RestTemplateBuilder builder;
	
	@MockBean
	private UserRepository userRepository;
	@Autowired
	private RSSService testService;
	
	@Before
	public void setup() {
		u1 = (new User(12,26,0,true,null,"admin@rss.com","Admin","Admin", "password"));
		u2 = (new User(13,26,0,false,null,"user@rss.com","User","User", "password"));
		testService.restTemplate =mock(RestTemplate.class);

	}
	

	@Test
	public void addPointsSuccess() {
		int points = 20;
		
		RSSAccountDTO dto = new RSSAccountDTO(u1.getUserID(),points);
		when(userRepository.findById(u1.getUserID())).thenReturn(Optional.of(u1));
		testService.rssServiceUrl="test";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    Map<String, Object> map = new HashMap<>();
    	map.put("accId", u1.getRSSAccountId());
    	map.put("points", u1.getPoints()+20);
    	HttpEntity<Map<String,Object>> entity = new HttpEntity<>(map,headers);

		when(testService.restTemplate.postForEntity("test/account/points/a", entity, RSSAccountDTO.class)).thenReturn(new ResponseEntity<RSSAccountDTO>(HttpStatus.OK));
		u1.setPoints(points);
		when(testService.userRepository.save(u1)).thenReturn(u1);
		User u = testService.addPoints(dto);
		assertEquals(u.getPoints(),u1.getPoints());
		
	}
	
	@Test
	public void addPointsNull() {
		int points = 20;
		
		RSSAccountDTO dto = new RSSAccountDTO(u1.getUserID(),points);
		when(userRepository.findById(u1.getUserID())).thenReturn(Optional.of(u1));
		testService.rssServiceUrl="test";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    Map<String, Object> map = new HashMap<>();
    	map.put("accId", u1.getRSSAccountId());
    	map.put("points", u1.getPoints()+20);
    	HttpEntity<Map<String,Object>> entity = new HttpEntity<>(map,headers);

		when(testService.restTemplate.postForEntity("test/account/points/a", entity, RSSAccountDTO.class)).thenReturn(new ResponseEntity<RSSAccountDTO>(HttpStatus.BAD_GATEWAY));

		User u = testService.addPoints(dto);
		assertNull(u);
		
	}

}
