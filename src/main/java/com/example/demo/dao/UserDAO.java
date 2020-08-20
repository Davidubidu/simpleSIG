package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
@Component
public interface UserDAO extends MongoRepository<User, String> {
	// FIXME: Delete if works
	//User findByUserName(String username);
	//Optional<User> findByUserNameAndPassword(String username, String password);

	Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
