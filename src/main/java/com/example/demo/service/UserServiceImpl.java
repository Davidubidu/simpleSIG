package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

@Service
@Component
public class UserServiceImpl implements UserService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	public ResponseEntity<Optional<User>> login(String[] userData) {
		HttpStatus response;
		Optional<User> u;
		try {
			u = dao.findByUserNameAndPassword(userData[0], userData[1]);
			response = (u != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		} catch (Exception e) {
			logger.error(e.toString());
			u = null;
			response = HttpStatus.INTERNAL_SERVER_ERROR; //500
		}
		
		return new ResponseEntity<Optional<User>>(u, response);
	}


}
