package com.blog.blog.services;

import com.blog.blog.domain.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
}
