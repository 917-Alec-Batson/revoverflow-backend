package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.entities.Faq;

/**
 * @authors Mahesh Kalle & Jeevan Selvagunarajah
 *
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer>{

	public List<Faq> findAll();
	
	@Query("From Faq f INNER JOIN Question q ON f.id = q.id WHERE q.revatureQuestion=true")
	public List<Faq> getRevatureBasedFaq();
	
	@Query("From Faq f INNER JOIN Question q ON f.id = q.id WHERE q.locationID = :location")
	public List<Faq> getFaqByLocation(@Param(value = "location") int location);

	@Query("From Faq f INNER JOIN Question q ON f.id = q.id WHERE q.revatureQuestion=true AND q.locationID = :location")
	public List<Faq> getRevatureAndLocationFaq(@Param(value = "location") int location);

}
