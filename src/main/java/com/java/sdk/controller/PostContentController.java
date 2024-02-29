package com.java.sdk.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.java.sdk.model.PostContentModel;
import com.java.sdk.model.PostContentResponseModel;
import com.java.sdk.model.User;
import com.java.sdk.services.HibernateExampleService;
import com.java.sdk.services.JdbcExampleService;
import com.java.sdk.services.PostContentService;

@RestController
@RequestMapping(value = "api", produces = "application/json")
@CrossOrigin
public class PostContentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostContentController.class);

	@Value("${spring.application.name}")
	private String applicationName;

	private PostContentService postContentService;
	private HibernateExampleService hibernateExampleService;
	private JdbcExampleService jdbcExampleService;
	private final RestTemplate restTemplate;

	@Autowired
	public PostContentController(PostContentService postContentService,
			HibernateExampleService hibernateExampleService,
			JdbcExampleService jdbcExampleService,
			RestTemplate restTemplate) {
		this.postContentService = postContentService;
		this.hibernateExampleService =hibernateExampleService;
		this.jdbcExampleService = jdbcExampleService;
		this.restTemplate =restTemplate;
	}
	
	@PostMapping("/createNewPost")
    public PostContentResponseModel post(@RequestBody PostContentModel postContent) {
		LOGGER.info("Incoming request at {} for request /createNewPost ", applicationName);
        return postContentService.createNewPost(postContent);
    }		
	
	@GetMapping("/hibernate/getUsers")
	public List<User> getAllUsers() {
		return hibernateExampleService.getAllUsers();
	}
	
	@PostMapping("/hibernate/createUser")
	public ResponseEntity<String> createUser(User user) {
		hibernateExampleService.createUser(user);
		return ResponseEntity.ok("Success");
	}
	
	@GetMapping("/jdbc/test")
	public Integer fetchSize() {
		return jdbcExampleService.performJdbcOperation();
	}	
	
	@GetMapping("/exception")
	public String exception() {
		throw new IllegalArgumentException("This id is invalid");
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<String> handleConflict(IllegalArgumentException ex) {
		LOGGER.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
