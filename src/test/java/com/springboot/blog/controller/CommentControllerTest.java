package com.springboot.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImpl;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

class CommentControllerTest {
    /**
     * Method under test: {@link CommentController#createComment(Long, CommentDto)}
     */
    @Test
    void testCreateComment() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(new Comment());
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        CommentController commentController = new CommentController(
            new CommentServiceImpl(commentRepository, postRepository, new ModelMapper()));
        CommentDto commentDto = new CommentDto();
        ResponseEntity<CommentDto> actualCreateCommentResult = commentController.createComment(1L, commentDto);
        CommentDto body = actualCreateCommentResult.getBody();
        assertEquals(commentDto, body);
        assertTrue(actualCreateCommentResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateCommentResult.getStatusCodeValue());
        assertNull(body.getId());
        assertNull(body.getName());
        assertNull(body.getEmail());
        assertNull(body.getBody());
        verify(commentRepository).save(Mockito.<Comment>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentController#createComment(Long, CommentDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateComment2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: source cannot be null
        //       at org.modelmapper.internal.util.Assert.notNull(Assert.java:53)
        //       at org.modelmapper.ModelMapper.map(ModelMapper.java:404)
        //       at com.springboot.blog.service.impl.CommentServiceImpl.mapToDTO(CommentServiceImpl.java:111)
        //       at com.springboot.blog.service.impl.CommentServiceImpl.createComment(CommentServiceImpl.java:45)
        //       at com.springboot.blog.controller.CommentController.createComment(CommentController.java:27)
        //   See https://diff.blue/R013 to resolve this issue.

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(null);
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        CommentController commentController = new CommentController(
            new CommentServiceImpl(commentRepository, postRepository, new ModelMapper()));
        commentController.createComment(1L, new CommentDto());
    }

    /**
     * Method under test: {@link CommentController#getCommentsByPostId(Long)}
     */
    @Test
    void testGetCommentsByPostId() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findByPostId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        PostRepository postRepository = mock(PostRepository.class);
        assertTrue((new CommentController(new CommentServiceImpl(commentRepository, postRepository, new ModelMapper())))
            .getCommentsByPostId(1L)
            .isEmpty());
        verify(commentRepository).findByPostId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentController#getCommentsByPostId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCommentsByPostId2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springboot.blog.service.CommentService.getCommentsByPostId(java.lang.Long)" because "this.commentService" is null
        //       at com.springboot.blog.controller.CommentController.getCommentsByPostId(CommentController.java:32)
        //   See https://diff.blue/R013 to resolve this issue.

        (new CommentController(null)).getCommentsByPostId(1L);
    }

    /**
     * Method under test: {@link CommentController#getCommentById(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCommentById() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springboot.blog.entity.Post.getId()" because the return value of "com.springboot.blog.entity.Comment.getPost()" is null
        //       at com.springboot.blog.service.impl.CommentServiceImpl.validateCommentBelongsToPost(CommentServiceImpl.java:105)
        //       at com.springboot.blog.service.impl.CommentServiceImpl.getCommentById(CommentServiceImpl.java:63)
        //       at com.springboot.blog.controller.CommentController.getCommentById(CommentController.java:37)
        //   See https://diff.blue/R013 to resolve this issue.

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Comment()));
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        (new CommentController(new CommentServiceImpl(commentRepository, postRepository, new ModelMapper())))
            .getCommentById(1L, 1L);
    }

    /**
     * Method under test: {@link CommentController#getCommentById(Long, Long)}
     */
    @Test
    void testGetCommentById2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CommentService commentService = mock(CommentService.class);
        when(commentService.getCommentById(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new CommentDto());
        ResponseEntity<CommentDto> actualCommentById = (new CommentController(commentService)).getCommentById(1L, 1L);
        assertTrue(actualCommentById.hasBody());
        assertTrue(actualCommentById.getHeaders().isEmpty());
        assertEquals(200, actualCommentById.getStatusCodeValue());
        verify(commentService).getCommentById(Mockito.<Long>any(), Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentController#updateComment(Long, Long, CommentDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateComment() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springboot.blog.entity.Post.getId()" because the return value of "com.springboot.blog.entity.Comment.getPost()" is null
        //       at com.springboot.blog.service.impl.CommentServiceImpl.validateCommentBelongsToPost(CommentServiceImpl.java:105)
        //       at com.springboot.blog.service.impl.CommentServiceImpl.updateComment(CommentServiceImpl.java:73)
        //       at com.springboot.blog.controller.CommentController.updateComment(CommentController.java:45)
        //   See https://diff.blue/R013 to resolve this issue.

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Comment()));
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        CommentController commentController = new CommentController(
            new CommentServiceImpl(commentRepository, postRepository, new ModelMapper()));
        commentController.updateComment(1L, 1L, new CommentDto());
    }

    /**
     * Method under test: {@link CommentController#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CommentService commentService = mock(CommentService.class);
        when(commentService.updateComment(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<CommentDto>any()))
            .thenReturn(new CommentDto());
        CommentController commentController = new CommentController(commentService);
        CommentDto commentDto = new CommentDto();
        ResponseEntity<CommentDto> actualUpdateCommentResult = commentController.updateComment(1L, 1L, commentDto);
        assertEquals(commentDto, actualUpdateCommentResult.getBody());
        assertTrue(actualUpdateCommentResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateCommentResult.getStatusCodeValue());
        verify(commentService).updateComment(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<CommentDto>any());
    }

    /**
     * Method under test: {@link CommentController#deleteComment(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteComment() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.springboot.blog.entity.Post.getId()" because the return value of "com.springboot.blog.entity.Comment.getPost()" is null
        //       at com.springboot.blog.service.impl.CommentServiceImpl.validateCommentBelongsToPost(CommentServiceImpl.java:105)
        //       at com.springboot.blog.service.impl.CommentServiceImpl.deleteComment(CommentServiceImpl.java:89)
        //       at com.springboot.blog.controller.CommentController.deleteComment(CommentController.java:51)
        //   See https://diff.blue/R013 to resolve this issue.

        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Comment()));
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        (new CommentController(new CommentServiceImpl(commentRepository, postRepository, new ModelMapper())))
            .deleteComment(1L, 1L);
    }

    /**
     * Method under test: {@link CommentController#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CommentService commentService = mock(CommentService.class);
        doNothing().when(commentService).deleteComment(Mockito.<Long>any(), Mockito.<Long>any());
        ResponseEntity<String> actualDeleteCommentResult = (new CommentController(commentService)).deleteComment(1L, 1L);
        assertEquals("Commment deleted successfully", actualDeleteCommentResult.getBody());
        assertEquals(200, actualDeleteCommentResult.getStatusCodeValue());
        assertTrue(actualDeleteCommentResult.getHeaders().isEmpty());
        verify(commentService).deleteComment(Mockito.<Long>any(), Mockito.<Long>any());
    }
}

