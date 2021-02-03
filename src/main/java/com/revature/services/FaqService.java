package com.revature.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.entities.Answer;
import com.revature.entities.Faq;
import com.revature.entities.Location;
import com.revature.entities.Question;
import com.revature.repositories.AnswerRepository;
import com.revature.repositories.FaqRepository;
import com.revature.repositories.LocationRepository;
import com.revature.repositories.QuestionRepository;

/**
 * @authors Mahesh Kalle & Jeevan Selvagunarajah
 *
 */
@Service
public class FaqService {

	@Autowired
	FaqRepository faqRepository;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	LocationRepository locationRepository;

	/**This method returns all the Faqs
	 * @return all frequently asked questions
	 */
	public List<Faq> getAllFaq() {
		return faqRepository.findAll();
	}


	/**This method is used to get the questions and answers based on location 
	 * @param takes in the location name
	 * @return returns all frequently asked questions 
	 * filtered by location name 
	 */
	public List<Faq> getFaqByLocation(String location) {
		Location faqLocation = this.getLocation(location);
		if(faqLocation != null) {
			return faqRepository.getFaqByLocation(faqLocation.getId());
		}
		return null;		
	}

	/**This method is used to get all the Revature based questions and answers by location
	 * @param location takes in the name of the location
	 * @return returns all frequently asked questions that are 
	 * Revature based filtered by the location name 
	 */
	public List<Faq> getRevatureAndLocationFaq(String location) {
		Location faqLocation = this.getLocation(location);
		if(faqLocation != null) {
			return faqRepository.getRevatureAndLocationFaq(faqLocation.getId());
		}
		return null;
	}
		
	/**This method is used to get all the Revature based questions 
	 * @return returns all frequently asked questions and answers
	 */
	public List<Faq> getRevatureBasedFaq() {
		return faqRepository.getRevatureBasedFaq();
	}

	/**This method is used to post a new Faq question and answer
	 * @param takes in Faq object
	 * @return returns the Faq object stored in the database
	 */
	@Transactional
	public Faq newFaqQuestion(Faq faq) {
		Question question = this.saveFaqQuestion(faq.getQuestion());
		Answer answer = faq.getAnswer();
		answer.setQuestionId(question.getId());
		Answer savedAnswer = this.saveFaqAnswer(answer);
		Faq submitfaq = new Faq(question,savedAnswer);
		Faq newFaq = faqRepository.save(submitfaq);
		return newFaq;
	}

	/**This method is used to get the questions and answers based on location 
	 * @param takes in the location name
	 * @return returns all frequently asked questions 
	 * filtered by location name 
	 */
	public Location getLocation(String location) {
		Location faqLocation = locationRepository.findByLocationName(location);
		return faqLocation;
	}

	/**This method is used to save a new Faq question
	 * @param takes in Faq object
	 * @return returns the Faq object stored in the database
	 */
	public Question saveFaqQuestion(Question question) {
		question.setCreationDate(LocalDateTime.now());
		question.setEditDate(LocalDateTime.now());
		Question faqQuestion = questionRepository.save(question);
		return faqQuestion;
	}

	/**This method is used to save a new Faq answer
	 * @param takes in Faq object
	 * @return returns the Faq object stored in the database
	 */
	public Answer saveFaqAnswer(Answer answer) {
		answer.setCreationDate(LocalDateTime.now());
		answer.setEditDate(LocalDateTime.now());
		Answer faqAnswer = answerRepository.save(answer);
		return faqAnswer;
	}
	
	public void deleteFaqById(int id) {
		faqRepository.deleteById(id);
	}
	
	/**This method is used to return Faq object associated with the id
	 * @param takes in the faq id
	 * @return returns the faq object
	 */
	public Faq findFaqById(int id) {
		Optional<Faq> faq = faqRepository.findById(id);
		if(faq.isPresent()) {
			return faq.get();
		}
		else return null;
		}
		
	/**This method is used to update the Faq object
	 * @param takes in the updated Faq object 
	 * @return returns the object that is stored in the database
	 */
	public Faq updateFaq(Faq faq) {
		faq.getAnswer().setEditDate(LocalDateTime.now());
		faq.getQuestion().setEditDate(LocalDateTime.now());
		Faq updatedFaq = faqRepository.save(faq);
			return updatedFaq;	
	}
	
}
