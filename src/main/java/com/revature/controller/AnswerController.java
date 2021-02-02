package com.revature.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.revature.entities.Answer;
import com.revature.services.AnswerService;

@RestController
@RequestMapping("/answers")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
/** @Author Natasha Poser 
 * 	@return This is the GetAnswers end-point. It retrieves all Answers in the database */
	@GetMapping
	@PreAuthorize("hasAuthority('user')")
	public Page<Answer>getAnswers(@AuthenticationPrincipal String user, Pageable pageable){
			return answerService.getAnswers(pageable);
	}

	/** @author Natasha Poser 
	 * @param questionId = question_id
	 * @return This is the GetAnswerByQuestionId end-point. It retrieves all answers associated with a specific Question ID */
	@GetMapping("/{questionId}") 
	@PreAuthorize("hasAuthority('user')")
	public Page<Answer> getAnswersByQuestionId(@AuthenticationPrincipal String user, Pageable pageable, @PathVariable int questionId){
		return answerService.getAnswerByQuestionId(pageable, questionId);
	}
	

	/** @Author James Walls */
	/** Adds new answers and updates existing ones. */
	@PostMapping
	@PreAuthorize("hasAuthority('user')")
	public Answer saveAnswer( @AuthenticationPrincipal String user, @RequestBody Answer answer) {
		return answerService.save(answer);
	}
	
	/**@author ken*/
	/**@param id = the id of the user
	 * get all the Answers by the user id */
	@GetMapping("/user/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Answer> getAllAnswersByUserID(@AuthenticationPrincipal String user, Pageable pageable,@PathVariable int id){
		return answerService.getAllAnswersByUserID(pageable, id);
	} 
	
	/** @author Natasha Poser 
	 * @param acceptedId = accepted_id
	 * @return This is the GetAcceptedAnswerByQuestionId end-point. */
	@GetMapping("/acceptedAnswers/{acceptedId}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Answer> getAcceptedAnswerByQuestionId(@AuthenticationPrincipal String user, Pageable pageable, @PathVariable int acceptedId){
		return answerService.getAcceptedAnswerByQuestionId(pageable, acceptedId);
	}
	
	/** @author Natasha Poser 
	 * @param id = id
	 * @return This is the GetAnswerById end-point. It retrieves the answer by it's own unique ID*/
	@GetMapping("/id/{id}")
	public Answer getAnswerById(@AuthenticationPrincipal String user, @PathVariable int id){
		return answerService.getAnswerById(id);
	}
}
 