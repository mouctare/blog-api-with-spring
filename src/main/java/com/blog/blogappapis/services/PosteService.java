package com.blog.blogappapis.services;

import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;

import java.util.List;

public interface PosteService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);
}
