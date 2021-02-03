package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Location;
import com.revature.services.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
	
	@Autowired
	LocationService locationService;
	
	/**
	 * @author Kelvin Trinh
	 * @apiNote get all locations
	 * @return list of locations
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('user')")
	public List<Location> getAllLocation() {
		return locationService.getAllLocations();
	}
		
	/**
	 * @author Kelvin Trinh
	 * @apiNote add a new location
	 * @return location
	 */
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('admin')")
	public Location addNewLocation(@RequestBody Location location) {
		return locationService.addLocation(location);
	}
}
