package com.blog.blogappapis.controllers;

import com.blog.blogappapis.config.AppConstants;
import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.PostDto;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.services.FileService;
import com.blog.blogappapis.services.PosteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PosteService posteService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
      PostDto createPost = this.posteService.createPost(postDto, userId, categoryId);
      return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> post = posteService.getPostsByUser(userId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> post = posteService.getPostsByUser(categoryId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/posts")
    //Pagination
    public ResponseEntity<PostResponse> getAllPost(
                                                  @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDirection)
    {
        PostResponse postResponse  = this.posteService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable  Integer postId){
        PostDto postDto = this.posteService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/post/{postId}")
    public ApiResponse delete(@PathVariable  Integer postId){
         this.posteService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted !!", true);
    }

    @PutMapping("/update/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable  Integer postId){
        PostDto postUpdate = this.posteService.updatePost(postDto, postId);
        return new ResponseEntity<>(postUpdate, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keyword){
      List<PostDto> posts = this.posteService.searchPosts(keyword);
      return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    //post image upload

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.posteService.getPostById(postId);
        String fileName =  this.fileService.uploadImage(path, image);

       postDto.setImageName(fileName);
       PostDto updatePost = this.posteService.updatePost(postDto, postId);
       return new ResponseEntity<>(updatePost, HttpStatus.OK);

    }

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}


