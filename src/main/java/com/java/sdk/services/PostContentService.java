package com.java.sdk.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.java.sdk.model.PostContentModel;
import com.java.sdk.model.PostContentResponseModel;
import com.java.sdk.repository.PostContentRepository;

@Service
public class PostContentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostContentService.class);

	@Value("${spring.application.name}")
	private String applicationName;

	private final RestTemplate restTemplate;
	private PostContentRepository postContentRepository;

	@Autowired
    public PostContentService( PostContentRepository postContentRepository,
    		RestTemplate restTemplate) {
        this.postContentRepository = postContentRepository;
        this.restTemplate = restTemplate;
        
    }

	public PostContentResponseModel createNewPost(PostContentModel postContent) {
		Object getContentObj = null;
		PostContentResponseModel response = new PostContentResponseModel();
		PostContentModel contentModel = postContentRepository.save(postContent);
		response.setDbPost(contentModel.getId());
		
		getContentObj = restTemplate.getForObject(
				"http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);
		response.setHttpOutbound(getContentObj);
		LOGGER.info("Completed creating a new post.",response.toString());
		return response;
	}		
}
