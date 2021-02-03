package com.revature.controller;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Question;
import com.revature.services.QuestionService;


@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	/**	 *@author ken 
	 * get all the questions*/
	@GetMapping
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestions(@AuthenticationPrincipal String user, Pageable pageable)
	{
		return questionService.getAllQuestions(pageable);
	}

	/**
	 * @param status = true/false
	 * get all the questions by the status of the question
	 */
	/**@author ken*/
	@GetMapping("/status/{status}")
	@PreAuthorize("hasAuthority('admin')")
	public Page<Question> getAllQuestionsByStatus(@AuthenticationPrincipal String user,Pageable pageable, @PathVariable boolean status)
	{
		return questionService.getAllQuestionsByStatus(pageable, status);
	}

	/**@author ken*/
	/** get all the questions by user id
	 * @param pageable
	 * @param id = the id of the user
	 * @return
	 */
	@GetMapping("/user/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestionsByUserId(@AuthenticationPrincipal String user, Pageable pageable, @PathVariable int id)
	{
		return questionService.getAllQuestionsByUserId(pageable, id);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the location based question.*/
	@GetMapping("/location")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllLocationQuestions(Pageable pageable)
	{
		return questionService.getAllLocationQuestions(pageable);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the questions based on the specific location they are related to.*/
	@GetMapping("/location/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestionsByLocationID(Pageable pageable, @PathVariable int id)
	{
		return questionService.getAllQuestionsByLocationID(pageable, id);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the questions based on the specific location they are related to
	 * and whether or not they are company based.*/
	@GetMapping("/location/{id}/{isRevature}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestionsByRevatureBasedAndLocationID(Pageable pageable, @PathVariable boolean isRevature, @PathVariable int id)
	{
		return questionService.getAllQuestionsByRevatureStatusAndLocationID(pageable, isRevature, id);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the revature and location based questions.*/
	@GetMapping("/location/revature")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllRevatureAndLocationBasedQuestions(Pageable pageable)
	{
		return questionService.getAllRevatureAndLocationBasedQuestions(pageable);
	}

	/** @Author James Walls */
	/** Adds new questions and updates existing ones. */
	@PostMapping
	@PreAuthorize("hasAuthority('user')")
	public Question saveQuestion(@AuthenticationPrincipal String user, @RequestBody Question question) {
		return questionService.save(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateQuestionAcceptedAnswerId endpoint which updates the
	 * acceptedId to the answer that is deemed the most acceptable.
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('user')")
	public Question updateQuestionAcceptedAnswerId(@AuthenticationPrincipal String user, @RequestBody Question question) {
		return questionService.updateQuestionAcceptedAnswerId(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateStatus endpoint which updates the question status and 
	 * awards 20 points to the user who answered the question.
	 */
	@PutMapping("/status")
	@PreAuthorize("hasAuthority('admin')")
	public Question updateStatus(@AuthenticationPrincipal String user, @RequestBody Question question) {
		return questionService.updateQuestionStatus(question, 20);
	}
	
	/** @Author Natasha Poser 
	 * @return the is the GetQuestionById end-point. It retrieves a question by it's ID*/
	@GetMapping("/id/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Question getQuestionById(@AuthenticationPrincipal String user, @PathVariable int id) {
		return questionService.findById(id);
	}
	
	//author: Tristan
	@GetMapping("/revature/{revature}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getQuestionsBasedOnRevature(Pageable pageable, @PathVariable boolean revature)
	{
		
		return questionService.getQuestionsBasedOnRevature(pageable, revature);
	}
	
		
}
