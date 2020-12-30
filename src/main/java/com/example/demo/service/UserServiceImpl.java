package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.security.services.UserDetailsImpl;

@Service
@Component
public class UserServiceImpl implements UserService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public UserServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Autowired
	UserDAO dao;
	
	@Override
	public ResponseEntity<User>  saveUser(User user) {
		HttpStatus response;
		if (dao.findByUserName(user.getUserName()) != null) {
			logger.info("That user name is already taken");
			response = HttpStatus.FORBIDDEN; // 403
		} else {
			try {
				dao.save(user);
				logger.info("User created");
				response = HttpStatus.OK; // 200
			} catch (Exception e) {
				logger.error(e.toString());
				response = HttpStatus.INTERNAL_SERVER_ERROR; // 500
			}			
		}

		return new ResponseEntity<User>(user, response);
	}

	@Override
	public ResponseEntity<User> updateUser(User user) {		
		HttpStatus response;
		try {
			dao.save(user);
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}		
		return new ResponseEntity<User>(user, response);
	}

	@Override
	public ResponseEntity<Optional<User>> getUserById(String id) {
		HttpStatus response;
		Optional<User> u;
		try {
			u = dao.findById(id);
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			u = null;
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		
		return new ResponseEntity<Optional<User>>(u, response);
	}

	@Override
	public ResponseEntity<String> deleteUser(String id) {
		HttpStatus response;
		try{
			dao.deleteById(id);
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		return new ResponseEntity<String>("user deleted correctly", response);
	}

	@Override
	public ResponseEntity<List<User>> listUsers() {
		HttpStatus response;
		List<User> us;
		try {
			us = dao.findAll();
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			us = new ArrayList<User>();
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		return new ResponseEntity<List<User>>(us, response);
	}

	@Override
	public ResponseEntity<List<UserResponse>> getFiltered(Map<String, String[]> data) {
		HttpStatus response;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	            .getPrincipal();
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		List<User> users = null;
		List<UserResponse> usersResponse = null;
		
		Boolean check = ( data != null ) ? true : false;
		String[] usernames;
		String[] emails;
		
		if (check) {
			
			usernames = (data.get("usernames") != null) ? data.get("usernames") : null;
			if (usernames != null) Arrays.asList(usernames).forEach((username) -> criteria.add(Criteria.where("usernames").regex(username)));
			
			emails = (data.get("emails") != null) ? data.get("emails") : null;
			if (emails != null) Arrays.asList(emails).forEach((email) -> criteria.add(Criteria.where("emails").regex(email)));
			
		}
		
		if(!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		}
		
		try {
			logger.info("query: " + query.toString());
			users = mongoTemplate.find(query, User.class);
			for (User u: users) {
				usersResponse.add(new UserResponse(u));
			}
			response = HttpStatus.OK; //200
		} catch (Exception e) {
			logger.error(e.toString());
			usersResponse = null;
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		
		return new ResponseEntity<List<UserResponse>>(usersResponse, response);
	}
	/**
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User us = dao.findByUserName(username);
		UserDetails ud = (UserDetails) new User(us.getUserName(), us.getPassword());
		return ud;
	}*/


}
