package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.entities.Location;
import com.revature.repositories.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	LocationRepository locationRepository;
	
	
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	/**
	 * @author Kelvin Trinh
	 * @return all available locations in the database
	 */
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}
	
	/**
	 * @author Kelvin Trinh
	 * @param location
	 * @description add a new location
	 * @return location object
	 */
	@Transactional
	public Location addLocation(Location location) {
		//location.setId(0);
		return locationRepository.save(location);
	}

}
