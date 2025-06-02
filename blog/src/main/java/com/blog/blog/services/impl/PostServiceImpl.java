package com.blog.blog.services.impl;

import com.blog.blog.domain.PostStatus;
import com.blog.blog.domain.entities.Category;
import com.blog.blog.domain.entities.Post;
import com.blog.blog.domain.entities.Tag;
import com.blog.blog.repositories.PostRepository;
import com.blog.blog.services.CategoryService;
import com.blog.blog.services.PostService;
import com.blog.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository; //injected via rewuired args constructor
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
           Category category =  categoryService.getCategoryById(categoryId);
           Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContains(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if(categoryId != null){
            Category category =  categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if(tagId != null){
            Tag tag =  tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }
}
