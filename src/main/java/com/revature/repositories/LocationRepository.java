package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	public Location findByLocationName(String location);
	
}
