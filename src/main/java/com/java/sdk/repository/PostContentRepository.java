package com.java.sdk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.sdk.model.PostContentModel;

@Repository
public interface PostContentRepository extends JpaRepository<PostContentModel, Integer> {

}
