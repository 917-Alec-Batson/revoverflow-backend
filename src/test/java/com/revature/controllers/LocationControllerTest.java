package com.revature.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Application;
import com.revature.entities.Location;
import com.revature.entities.User;
import com.revature.services.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class LocationControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LocationService locationService;

	static User admin, user;

	@Before
	public void setUp() {
		admin = new User(12, 26, 0, true, null, "admin@rss.com", "Admin", "Admin", "password");
		user = new User(14, 27, 0, false, null, "kelvintrinh@rss.com", "Kelvin", "Trinh", "password");
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(username = "kelvintrinh@rss.com", password = "password", authorities = "user")
	public void testAddNewLocation() throws Exception {
		Location l1 = new Location(1, "Toronto");

		when(locationService.addLocation(Mockito.any(Location.class))).thenReturn(l1);
		ObjectMapper mapper = new ObjectMapper();
		String jsonLocation = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(l1);
		System.out.println(jsonLocation);

		mvc.perform(post("/locations/add").contentType(MediaType.APPLICATION_JSON).content(jsonLocation))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.locationName", is("Toronto"))
			    );

	}
}
