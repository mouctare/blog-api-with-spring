package com.blog.blogappapis.services.impl;

import com.blog.blogappapis.entities.Category;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.repositories.CategoryRepo;
import com.blog.blogappapis.repositories.PostRepo;
import com.blog.blogappapis.repositories.UserRepo;
import com.blog.blogappapis.services.PosteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PosteService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "User id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setCreatedAt(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
       Post post = this.postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "Post id", postId));
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setImageName(post.getImageName());

      Post updatedPost = this.postRepo.save(post);
      return this.modelMapper.map(updatedPost, PostDto.class);


    }

    @Override
    public void deletePost(Integer postId) {
      Post post =  this.postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "Post id", postId));
    this.postRepo.delete(post);

    }

    @Override
    //Pagination
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        //int pagesSize = 5;
        //int pageNumber = 1;

        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        /**if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }
         **/

        //Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);


        Page<Post> pagePost = this.postRepo.findAll(page);

        List<Post> allPosts = pagePost.getContent();

       List<PostDto> postDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

       PostResponse postResponse = new PostResponse();
       postResponse.setContent(postDtos);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getSize());
       postResponse.setTotalElements(pagePost.getTotalElements());

       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "Post id", postId));
        return  this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> postDtos =  posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "User id", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos =  posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
      List<Post> posts = this.postRepo.findByTitleContaining(keyword);
      List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());

      return postDtos;
    }
}
