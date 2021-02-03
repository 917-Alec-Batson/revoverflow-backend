package com.revature.services;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.entities.Location;
import com.revature.repositories.LocationRepository;

public class LocationServiceTest {
	
	@Mock
	LocationRepository locationRepository;
	
	@InjectMocks
	LocationService locationService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addLocationWillReturnLocation() {
		Location l1 = new Location(1,"Toronto");
		
		Location l2 = new Location();
		
		//expected result for l2
		Mockito.when(locationRepository.save(l1)).thenReturn(l2);
		
		//actual result
		Location l3 = locationService.addLocation(l1);
		assertEquals(l2,l3);
		
	}
	
	@Test
	public void getAllLocationWillReturnAllLocation() {
		List<Location> expectedResult = new ArrayList<Location>();
		locationRepository.save(new Location(1,"Toronto"));
		locationRepository.save(new Location(2,"Ottawa"));

		Mockito.when(locationRepository.findAll()).thenReturn(expectedResult);
		
		List<Location> actualResult = locationService.getAllLocations();
		assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
	}
	
}
