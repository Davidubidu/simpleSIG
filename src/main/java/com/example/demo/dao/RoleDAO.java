package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.ERole;
import com.example.demo.model.Role;

public interface RoleDAO extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}