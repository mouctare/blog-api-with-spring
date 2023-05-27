package com.blog.blogappapis.controllers;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.CommentDto;
import com.blog.blogappapis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto , @PathVariable Integer postId){

        CommentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
       this.commentService.deleteComment(commentId);

       return new ResponseEntity<>(new ApiResponse("Comment deleted succesfully !!", true), HttpStatus.OK);
    }
}
