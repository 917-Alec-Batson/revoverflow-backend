package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Answer;
import com.revature.entities.Question;
import com.revature.entities.User;
import com.revature.DTOs.RSSAccountDTO;
import com.revature.repositories.AnswerRepository;
import com.revature.repositories.QuestionRepository;

@Service
public class QuestionService {

	@Autowired 
	RSSService rssService;	
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	
	/**@author ken*/
	public Page<Question> getAllQuestions(Pageable pageable){
		return questionRepository.findAll(pageable);
	}

	/**@author ken*/
	public Page<Question> getAllQuestionsByUserId(Pageable pageable, int id){
		return questionRepository.getAllQuestionsByUserID(pageable, id);		
	}
	
	/** @Author James Walls */
	/** Adds new questions and updates existing ones. */
	public Question save(Question question) {
		return questionRepository.save(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This updates the AcceptedAnswerId on the Questions table.
	 * This indicates that the user who posted the question has accepted an
	 * answer as the best one. The comments below indicate how the rest of the
	 * implementation can be written out.
	 */
	public Question updateQuestionAcceptedAnswerId(Question question) {
		if(question.getId() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		/*
		 * Here's how you can finish the implementation of this method
		 * Very similar to the updateStatus method below, minus the RSSService logic
			- Get question from database
			- Ensure that only the answerId is updated
	 		- Question q = optQuestion.get()
			- If q.getAnswerId is null, set the answerId
			- q.setAnswer(question.getAnswer)
			- You will be saving q, and NOT question 
		*/

		return save(question);
	}
	
	/**
	 * @author Hugh Thornhill, Ryan Clayton
	 * @return This method is for updating the status of the question, indicating
	 * an admin finds the answer chosen for the question acceptable. The points are
	 * then added to the user who's answer was chosen.
	 * */
	public Question updateQuestionStatus(Question question, int points) {

		// check the question accepted answer id is there
		if(question.getId() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		// ensures someone isn't maliciously updating the question
		Optional<Question> optQuestion = questionRepository.findById(question.getId());
		if(optQuestion.isPresent()) {
			// This overwrites the question from the parameter above, and replaces it with the one from the database
			question = optQuestion.get();
		}
		// If the status is already true OR the accepted id is 0/null then it will throw an error
		if(question.isStatus() || question.getAcceptedId() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		// Sets the status as true3
		question.setStatus(true);
		
		Optional<Answer> optAnswer = answerRepository.findById(question.getAcceptedId());
		if(optAnswer.isPresent()) {
			Answer a = optAnswer.get();
			RSSAccountDTO dto = new RSSAccountDTO(a.getUserId(), points);
			User user = rssService.addPoints(dto);
			if (user == null) {
				throw new NullPointerException("Null value");
			}
		}
		return save(question);
	}
	
  /** @Author Natasha Poser
   * @return retrieves a specific question by using it's specific ID */ 
	public Question findById(int id) {
		return questionRepository.findById(id)
				// If no question is found by the particular ID then HTTP Status is provided. 
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	/**@author ken*/
	public Page<Question> getAllQuestionsByStatus(Pageable pageable, boolean status){
		return questionRepository.getQuestionsByStatus(pageable, status);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the questions based on the specific location they are related to.*/
	public Page<Question> getAllQuestionsByLocationID(Pageable pageable, int locationID){
		return questionRepository.findByLocationID(pageable, locationID);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the questions based on the specific location they are related to
	 * and whether or not they are company based.*/
	public Page<Question> getAllQuestionsByRevatureStatusAndLocationID(Pageable pageable, boolean isRevature, int locationID){
		return questionRepository.findByRevatureQuestionAndLocationID(pageable, isRevature, locationID);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the questions based on the specific location they are related to
	 * and whether or not they are company based.*/
	public Page<Question> getAllRevatureAndLocationBasedQuestions(Pageable pageable){
		return questionRepository.findByRevatureAndLocation(pageable);
	}
	
	//author Tristan
	public Page<Question> getQuestionsBasedOnRevature(Pageable pageable, boolean revature){
		return questionRepository.getQuestionsBasedOnRevature(pageable, revature);
	}
	
	/**@author Hammad
	 * @return This method retrieves all the location based question.*/
	public Page<Question> getAllLocationQuestions(Pageable pageable){
		return questionRepository.findByLocation(pageable);
	}
	
}
