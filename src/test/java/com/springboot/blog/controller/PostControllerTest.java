package com.springboot.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.PostServiceImpl;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

class PostControllerTest {
    /**
     * Method under test: {@link PostController#createPost(PostDto)}
     */
    @Test
    void testCreatePost() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.save(Mockito.<Post>any())).thenReturn(new Post());
        PostController postController = new PostController(new PostServiceImpl(postRepository, new ModelMapper()));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        ResponseEntity<PostDto> actualCreatePostResult = postController.createPost(postDto);
        assertTrue(actualCreatePostResult.hasBody());
        assertTrue(actualCreatePostResult.getHeaders().isEmpty());
        assertEquals(201, actualCreatePostResult.getStatusCodeValue());
        PostDto body = actualCreatePostResult.getBody();
        assertNull(body.getDescription());
        assertTrue(body.getComments().isEmpty());
        assertNull(body.getTitle());
        assertNull(body.getContent());
        assertNull(body.getId());
        verify(postRepository).save(Mockito.<Post>any());
    }


    /**
     * Method under test: {@link PostController#getAllPosts(int, int, String, String)}
     */
    @Test
    void testGetAllPosts() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        PostRepository postRepository = mock(PostRepository.class);
        ArrayList<Post> content = new ArrayList<>();
        when(postRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));
        PostResponse actualAllPosts = (new PostController(new PostServiceImpl(postRepository, new ModelMapper())))
            .getAllPosts(1, 3, "Sort By", "Sort Dir");
        assertEquals(content, actualAllPosts.getContent());
        assertTrue(actualAllPosts.isLast());
        assertEquals(1, actualAllPosts.getTotalPages());
        assertEquals(0L, actualAllPosts.getTotalElements());
        assertEquals(0, actualAllPosts.getPageSize());
        assertEquals(0, actualAllPosts.getPageNo());
        verify(postRepository).findAll(Mockito.<Pageable>any());
    }


    /**
     * Method under test: {@link PostController#getPostById(Long)}
     */
    @Test
    void testGetPostById() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        ResponseEntity<PostDto> actualPostById = (new PostController(
            new PostServiceImpl(postRepository, new ModelMapper()))).getPostById(1L);
        assertTrue(actualPostById.hasBody());
        assertTrue(actualPostById.getHeaders().isEmpty());
        assertEquals(200, actualPostById.getStatusCodeValue());
        PostDto body = actualPostById.getBody();
        assertNull(body.getDescription());
        assertTrue(body.getComments().isEmpty());
        assertNull(body.getTitle());
        assertNull(body.getContent());
        assertNull(body.getId());
        verify(postRepository).findById(Mockito.<Long>any());
    }


    /**
     * Method under test: {@link PostController#updatePost(PostDto, Long)}
     */
    @Test
    void testUpdatePost() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.save(Mockito.<Post>any())).thenReturn(new Post());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        PostController postController = new PostController(new PostServiceImpl(postRepository, new ModelMapper()));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        ResponseEntity<PostDto> actualUpdatePostResult = postController.updatePost(postDto, 1L);
        assertTrue(actualUpdatePostResult.hasBody());
        assertTrue(actualUpdatePostResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdatePostResult.getStatusCodeValue());
        PostDto body = actualUpdatePostResult.getBody();
        assertNull(body.getDescription());
        assertTrue(body.getComments().isEmpty());
        assertNull(body.getTitle());
        assertNull(body.getContent());
        assertNull(body.getId());
        verify(postRepository).save(Mockito.<Post>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }


    /**
     * Method under test: {@link PostController#deletePost(Long)}
     */
    @Test
    void testDeletePost() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        PostRepository postRepository = mock(PostRepository.class);
        doNothing().when(postRepository).delete(Mockito.<Post>any());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        ResponseEntity<String> actualDeletePostResult = (new PostController(
            new PostServiceImpl(postRepository, new ModelMapper()))).deletePost(1L);
        assertEquals("Post entity deleted successfully", actualDeletePostResult.getBody());
        assertEquals(200, actualDeletePostResult.getStatusCodeValue());
        assertTrue(actualDeletePostResult.getHeaders().isEmpty());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(postRepository).delete(Mockito.<Post>any());
    }


}

