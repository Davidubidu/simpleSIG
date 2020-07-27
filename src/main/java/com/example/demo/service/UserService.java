package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.model.User;

@Service
public interface UserService {
	
	public ResponseEntity<User> saveUser(User user);
	public ResponseEntity<User> updateUser(User user);
	ResponseEntity<Optional<User>> getUserById(String id);
	ResponseEntity<String> deleteUser(String id);
	public ResponseEntity<List<User>> listUsers();
	ResponseEntity<Optional<User>> login(String[] userData);
}
