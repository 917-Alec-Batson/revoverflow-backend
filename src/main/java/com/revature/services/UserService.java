package com.revature.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.revature.entities.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RSSService rssService;
	
	/**
	 * @author Ryan Clayton
	 * @param id this is the user's Id
	 * @return User this returns the user entity with updated RSS points
	 */

	public User getUserById(int id) {
			try {
				Optional<User> optUser = userRepository.findById(id);
				if(optUser.isPresent()) {
					User user = optUser.get();
				
				user.setPoints(rssService.getPoints(id));
				return userRepository.save(user);
				}
			}catch(Exception e) {

				Logger logger = Logger.getLogger(UserService.class);
				logger.error("error in UserService getUserById", e);
				return null;
			}
			return null;
		
		
	}
	public Collection<GrantedAuthority> getAuthority(User u){
		Collection<GrantedAuthority>auths = new ArrayList<>();
		Optional<User> optUser = userRepository.findById(u.getUserID());
		SimpleGrantedAuthority a= null; 
		
		if (optUser.isPresent()) {
			User user = optUser.get();
			if(user.isAdmin()) {
				a = new SimpleGrantedAuthority("admin");
				auths.add(a);
				a = new SimpleGrantedAuthority("user");
				auths.add(a);
			}else {
				a = new SimpleGrantedAuthority("user");
				auths.add(a);
				
			}
		}
		return auths;
	}
	
	 public User getByEmail(String email){
	        User user = userRepository.findByEmail(email);

	        if(user!=null){
	            return user;
	        }else{
	            return new User();
	        }
	    }
	    
	    public User getUserByEmail(String email){
	        try {
	            User user = userRepository.findByEmail(email);
	            if(user != null) {
	                return userRepository.save(user);
	            }
	        }catch(Exception e) {
	            Logger logger = Logger.getLogger(UserService.class);
	            logger.error("error in UserService getUserByEmail", e);
	            return null;
	        }
	        return null;
	    } 

}
