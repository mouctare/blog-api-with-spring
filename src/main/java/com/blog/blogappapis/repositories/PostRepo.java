package com.blog.blogappapis.repositories;

import com.blog.blogappapis.entities.Category;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
