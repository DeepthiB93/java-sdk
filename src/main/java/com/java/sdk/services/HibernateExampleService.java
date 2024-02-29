package com.java.sdk.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.sdk.model.User;
import com.java.sdk.repository.UserRepository;

import io.opentelemetry.api.OpenTelemetry;

@Service
public class HibernateExampleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateExampleService.class);
	private UserRepository userRepository;

	@Autowired
	public HibernateExampleService(UserRepository userRepository, OpenTelemetry openTelemetry) {
		this.userRepository = userRepository;
	}

	@Transactional
	public List<User> getAllUsers() {
		userRepository.findAll();		
		return userRepository.findAll();
	}

	@Transactional
	public void createUser(User user) {
		LOGGER.info("Saving the user data in the DB");
		userRepository.save(user);
	}	
}
