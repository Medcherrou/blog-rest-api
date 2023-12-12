package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
    name = "CRUD REST APIs for Post Ressource"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }



    @Operation(
        summary = "Create Post Rest Api",
        description = "Create post Rest API is used to save post into database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 Created"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


    @Operation(
        summary = "Get All Posts REST API",
        description = "Get All Posts REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
        @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
        @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
        ){

        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @Operation(
        summary = "Get Post By Id REST API",
        description = "Get Post By Id REST API is used to get single post from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post by id rest api
    @Operation(
        summary = "update Post REST API",
        description = "Update Post REST API is used to update a particular post in the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    // delete post rest api
    @Operation(
        summary = "Delete Post REST API",
        description = "Delete Post REST API is used to delete a particular post from the database"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
        name = "Bear Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }




}
