package com.blog.blogappapis.services.impl;

import com.blog.blogappapis.entities.Comment;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.CommentDto;
import com.blog.blogappapis.repositories.CommentRepo;
import com.blog.blogappapis.repositories.PostRepo;
import com.blog.blogappapis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id" , postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return  this.modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id" , commentId));
        this.commentRepo.delete(comment);


    }
}
