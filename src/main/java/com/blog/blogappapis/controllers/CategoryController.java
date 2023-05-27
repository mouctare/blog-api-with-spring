package com.blog.blogappapis.controllers;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.CategoryDto;
import com.blog.blogappapis.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
    {
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("category is deleted successfully !!", true), HttpStatus.OK);
    }


    @GetMapping("/single/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId)
    {
      CategoryDto categoryDto =  this.categoryService.getCategory(catId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK );
    }

    @GetMapping("all")
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
      List<CategoryDto> categoryDtos =  this.categoryService.getAllCategory();

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
}
