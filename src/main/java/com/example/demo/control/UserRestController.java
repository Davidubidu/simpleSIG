package com.example.demo.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;

@RestController
@RequestMapping(value= "/api/Users")
@Component
public class UserRestController {
	
	@Autowired
	UserServiceImpl serv;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	@Autowired
	private BCryptPasswordEncoder encoder;
	*/
	@PostMapping(value= "/insert")
	public ResponseEntity<User> insert(@RequestBody User u) {
		logger.info("saving user...");
		//String encodedPassword = encoder.encode(u.getPassword());
		//u.setPassword(encodedPassword);
		return serv.saveUser(u);
	}
	
	@PostMapping(value= "/login")
	public ResponseEntity<Optional<User>> login(@RequestBody User u){
		logger.info("trying to log in...");
		//String encodedPassword = encoder.encode(u.getPassword());
		//u.setPassword(encodedPassword);
		String[] userData = {u.getUserName(), u.getPassword()};
		return serv.login(userData);
	}
	
	@GetMapping(value= "/getall")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAll() {
		logger.info("retrieving all users");
		return serv.listUsers();
	}

	@PostMapping(value= "/getone")
	public ResponseEntity<Optional<User>> getOne(@RequestBody Map<String, String[]> u) {
		logger.info("trying to log in...");
		return serv.login(u.get("credentials"));
	}
	
	@PutMapping(value= "/update/{User-id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> update(@PathVariable(value="User-id") String id, @RequestBody User u) {
		logger.info("Updating user "+ id +"...");
		u.setId(id);
		logger.info("user "+id+" updated");
		return serv.updateUser(u);
	}
	
	@DeleteMapping(value= "/deleteuser/{User-id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable(value= "User-id") String id) {
		logger.info("Deleting user with id: "+ id +"...");
		return serv.deleteUser(id);
	}
}
