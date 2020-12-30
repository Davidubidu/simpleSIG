package com.example.demo.control;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.payload.response.UserResponse;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value= "/api/users")
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
	
	@GetMapping(value= "/getfiltered")
	public ResponseEntity<List<UserResponse>> getFiltered(@RequestParam(value = "filter", required = false) String filter){
				
		Map<String, String[]> data = null;
		
		try {
			if( filter != null ) {
				// convert JSON string to Map
				ObjectMapper mapper = new ObjectMapper();
				data = mapper.readValue(filter, new TypeReference<Map<String, String[]>>(){});
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
		logger.info("geting users...");
		return serv.getFiltered(data);
	}
	
	@GetMapping(value= "/getall")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAll() {
		logger.info("retrieving all users");
		return serv.listUsers();
	}

	@PostMapping(value= "/getone")
	public ResponseEntity<List<UserResponse>> getOne(@RequestBody Map<String, String[]> u) {
		logger.info("trying to log in...");
		Map<String, String[]> a = null;
		return serv.getFiltered(a);
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
