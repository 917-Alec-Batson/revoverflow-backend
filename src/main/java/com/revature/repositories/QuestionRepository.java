package com.revature.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	/**@author ken*/
	Page<Question> getAllQuestionsByUserID(Pageable pageable, int id);

	/**@author ken*/
	@Query("FROM Question s WHERE :status = s.status")
	Page<Question> getQuestionsByStatus(Pageable pageable, boolean status);
	
	/**@author Hammad*/
	@Query("From Question l WHERE l.locationID is not null")
	Page<Question> findByLocation(Pageable pageable);
	
	/**@author Hammad*/
	Page<Question> findByLocationID(Pageable pageable, int locationID);
	
	/**@author Hammad*/
	@Query("From Question l WHERE l.revatureQuestion = true and l.locationID is not null")
	Page<Question> findByRevatureAndLocation(Pageable pageable);
	
	/**@author Hammad*/
	Page<Question> findByRevatureQuestionAndLocationID(Pageable pageable, boolean isRevature, int locationID);
	
	///**@author Arjun*/
	//@Query("FROM Question s WHERE :status = s.status and :revature = s.revaturequestion")
	//Page<Question> getQuestionsBasedOnRevature(Pageable pageable, boolean status, boolean revature);
//	/**@author Arjun*/
//	@Query("FROM Question s WHERE :status = s.status and :revature = s.revaturequestion")
//	Page<Question> getQuestionsBasedOnRevatureWithStatusTest(Pageable pageable, boolean status, boolean revature);
	
	/**@author Arjun+Tristan*/
	@Query("FROM Question s WHERE s.revatureQuestion = :revature")
	Page<Question> getQuestionsBasedOnRevature(Pageable pageable, boolean revature);
	
}
