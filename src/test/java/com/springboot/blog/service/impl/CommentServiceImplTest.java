package com.springboot.blog.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.RessourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PostRepository postRepository;

    /**
     * Method under test: {@link CommentServiceImpl#createComment(long, CommentDto)}
     */
    @Test
    void testCreateComment() {
        when(commentRepository.save(Mockito.<Comment>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Comment>>any())).thenReturn(new Comment());
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.createComment(1L, new CommentDto()));
        verify(commentRepository).save(Mockito.<Comment>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Comment>>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#createComment(long, CommentDto)}
     */
    @Test
    void testCreateComment2() {
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Comment>>any())).thenReturn(new Comment());
        assertThrows(RessourceNotFoundException.class, () -> commentServiceImpl.createComment(1L, new CommentDto()));
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Comment>>any());
    }


    /**
     * Method under test: {@link CommentServiceImpl#getCommentsByPostId(Long)}
     */
    @Test
    void testGetCommentsByPostId() {
        when(commentRepository.findByPostId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        assertTrue(commentServiceImpl.getCommentsByPostId(1L).isEmpty());
        verify(commentRepository).findByPostId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#getCommentsByPostId(Long)}
     */
    @Test
    void testGetCommentsByPostId2() {
        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        when(commentRepository.findByPostId(Mockito.<Long>any())).thenReturn(commentList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any())).thenReturn(new CommentDto());
        assertEquals(1, commentServiceImpl.getCommentsByPostId(1L).size());
        verify(commentRepository).findByPostId(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#getCommentsByPostId(Long)}
     */
    @Test
    void testGetCommentsByPostId3() {
        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        commentList.add(new Comment());
        when(commentRepository.findByPostId(Mockito.<Long>any())).thenReturn(commentList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any())).thenReturn(new CommentDto());
        assertEquals(2, commentServiceImpl.getCommentsByPostId(1L).size());
        verify(commentRepository).findByPostId(Mockito.<Long>any());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#getCommentsByPostId(Long)}
     */
    @Test
    void testGetCommentsByPostId4() {
        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment());
        when(commentRepository.findByPostId(Mockito.<Long>any())).thenReturn(commentList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.getCommentsByPostId(1L));
        verify(commentRepository).findByPostId(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }



    /**
     * Method under test: {@link CommentServiceImpl#getCommentById(Long, Long)}
     */
    @Test
    void testGetCommentById2() {
        when(commentRepository.findById(Mockito.<Long>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.getCommentById(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }



    /**
     * Method under test: {@link CommentServiceImpl#getCommentById(Long, Long)}
     */
    @Test
    void testGetCommentById4() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.getCommentById(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#getCommentById(Long, Long)}
     */
    @Test
    void testGetCommentById5() {
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(RessourceNotFoundException.class, () -> commentServiceImpl.getCommentById(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#getCommentById(Long, Long)}
     */
    @Test
    void testGetCommentById6() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post2 = new Post();
        post2.setId(1L);
        Optional<Post> ofResult2 = Optional.of(post2);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CommentDto commentDto = new CommentDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any())).thenReturn(commentDto);
        assertSame(commentDto, commentServiceImpl.getCommentById(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }




    /**
     * Method under test: {@link CommentServiceImpl#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment2() {
        when(commentRepository.findById(Mockito.<Long>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.updateComment(1L, 1L, new CommentDto()));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }



    /**
     * Method under test: {@link CommentServiceImpl#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment4() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.updateComment(1L, 1L, new CommentDto()));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment5() {
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(RessourceNotFoundException.class, () -> commentServiceImpl.updateComment(1L, 1L, new CommentDto()));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment6() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(new Comment());
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post2 = new Post();
        post2.setId(1L);
        Optional<Post> ofResult2 = Optional.of(post2);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        CommentDto commentDto = new CommentDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any())).thenReturn(commentDto);
        assertSame(commentDto, commentServiceImpl.updateComment(1L, 1L, new CommentDto()));
        verify(commentRepository).save(Mockito.<Comment>any());
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#updateComment(Long, Long, CommentDto)}
     */
    @Test
    void testUpdateComment7() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(new Comment());
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post2 = new Post();
        post2.setId(1L);
        Optional<Post> ofResult2 = Optional.of(post2);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.updateComment(1L, 1L, new CommentDto()));
        verify(commentRepository).save(Mockito.<Comment>any());
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<CommentDto>>any());
    }



    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment2() {
        when(commentRepository.findById(Mockito.<Long>any()))
            .thenThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred"));
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.deleteComment(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment4() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.deleteComment(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment5() {
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(RessourceNotFoundException.class, () -> commentServiceImpl.deleteComment(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment6() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        doNothing().when(commentRepository).delete(Mockito.<Comment>any());
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post2 = new Post();
        post2.setId(1L);
        Optional<Post> ofResult2 = Optional.of(post2);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        commentServiceImpl.deleteComment(1L, 1L);
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(commentRepository).delete(Mockito.<Comment>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment7() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(RessourceNotFoundException.class, () -> commentServiceImpl.deleteComment(1L, 1L));
        verify(postRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CommentServiceImpl#deleteComment(Long, Long)}
     */
    @Test
    void testDeleteComment8() {
        Post post = new Post();
        post.setId(1L);

        Comment comment = new Comment();
        comment.setPost(post);
        Optional<Comment> ofResult = Optional.of(comment);
        doThrow(new BlogAPIException(HttpStatus.CONTINUE, "An error occurred")).when(commentRepository)
            .delete(Mockito.<Comment>any());
        when(commentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post2 = new Post();
        post2.setId(1L);
        Optional<Post> ofResult2 = Optional.of(post2);
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(BlogAPIException.class, () -> commentServiceImpl.deleteComment(1L, 1L));
        verify(commentRepository).findById(Mockito.<Long>any());
        verify(commentRepository).delete(Mockito.<Comment>any());
        verify(postRepository).findById(Mockito.<Long>any());
    }
}

