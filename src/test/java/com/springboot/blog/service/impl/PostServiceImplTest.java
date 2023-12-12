package com.springboot.blog.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.RessourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PostServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postServiceImpl;

    /**
     * Method under test: {@link PostServiceImpl#createPost(PostDto)}
     */
    @Test
    void testCreatePost() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
            .thenThrow(new RessourceNotFoundException("Ressource Name", "Field Name", 42L));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        assertThrows(RessourceNotFoundException.class, () -> postServiceImpl.createPost(postDto));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Post>>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#createPost(PostDto)}
     */
    @Test
    void testCreatePost2() {
        when(postRepository.save(Mockito.<Post>any())).thenReturn(new Post());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        assertNull(postServiceImpl.createPost(postDto));
        verify(postRepository).save(Mockito.<Post>any());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#getAllPosts(int, int, String, String)}
     */
    @Test
    void testGetAllPosts() {
        ArrayList<Post> content = new ArrayList<>();
        when(postRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));
        PostResponse actualAllPosts = postServiceImpl.getAllPosts(1, 3, "Sort By", "Sort Dir");
        assertEquals(content, actualAllPosts.getContent());
        assertTrue(actualAllPosts.isLast());
        assertEquals(1, actualAllPosts.getTotalPages());
        assertEquals(0L, actualAllPosts.getTotalElements());
        assertEquals(0, actualAllPosts.getPageSize());
        assertEquals(0, actualAllPosts.getPageNo());
        verify(postRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#getAllPosts(int, int, String, String)}
     */
    @Test
    void testGetAllPosts2() {
        ArrayList<Post> content = new ArrayList<>();
        content.add(new Post());
        PageImpl<Post> pageImpl = new PageImpl<>(content);
        when(postRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any())).thenReturn(postDto);
        PostResponse actualAllPosts = postServiceImpl.getAllPosts(1, 3, "Sort By", "Sort Dir");
        assertEquals(1, actualAllPosts.getContent().size());
        assertTrue(actualAllPosts.isLast());
        assertEquals(0, actualAllPosts.getPageNo());
        assertEquals(1, actualAllPosts.getTotalPages());
        assertEquals(1L, actualAllPosts.getTotalElements());
        assertEquals(1, actualAllPosts.getPageSize());
        verify(postRepository).findAll(Mockito.<Pageable>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#getAllPosts(int, int, String, String)}
     */
    @Test
    void testGetAllPosts3() {
        ArrayList<Post> content = new ArrayList<>();
        content.add(new Post());
        content.add(new Post());
        PageImpl<Post> pageImpl = new PageImpl<>(content);
        when(postRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any())).thenReturn(postDto);
        PostResponse actualAllPosts = postServiceImpl.getAllPosts(1, 3, "Sort By", "Sort Dir");
        assertEquals(2, actualAllPosts.getContent().size());
        assertTrue(actualAllPosts.isLast());
        assertEquals(0, actualAllPosts.getPageNo());
        assertEquals(2L, actualAllPosts.getTotalElements());
        assertEquals(1, actualAllPosts.getTotalPages());
        assertEquals(2, actualAllPosts.getPageSize());
        verify(postRepository).findAll(Mockito.<Pageable>any());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }


    /**
     * Method under test: {@link PostServiceImpl#getPostById(Long)}
     */
    @Test
    void testGetPostById() {
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any())).thenReturn(postDto);
        assertSame(postDto, postServiceImpl.getPostById(1L));
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#getPostById(Long)}
     */
    @Test
    void testGetPostById2() {
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any()))
            .thenThrow(new RessourceNotFoundException("Ressource Name", "Field Name", 42L));
        assertThrows(RessourceNotFoundException.class, () -> postServiceImpl.getPostById(1L));
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }


    /**
     * Method under test: {@link PostServiceImpl#updatePost(PostDto, Long)}
     */
    @Test
    void testUpdatePost() {
        when(postRepository.save(Mockito.<Post>any())).thenReturn(new Post());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any())).thenReturn(postDto);

        PostDto postDto2 = new PostDto();
        postDto2.setComments(new HashSet<>());
        postDto2.setContent("Not all who wander are lost");
        postDto2.setDescription("The characteristics of someone or something");
        postDto2.setId(1L);
        postDto2.setTitle("Dr");
        assertSame(postDto, postServiceImpl.updatePost(postDto2, 1L));
        verify(postRepository).save(Mockito.<Post>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#updatePost(PostDto, Long)}
     */
    @Test
    void testUpdatePost2() {
        when(postRepository.save(Mockito.<Post>any())).thenReturn(new Post());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any()))
            .thenThrow(new RessourceNotFoundException("Ressource Name", "Field Name", 42L));

        PostDto postDto = new PostDto();
        postDto.setComments(new HashSet<>());
        postDto.setContent("Not all who wander are lost");
        postDto.setDescription("The characteristics of someone or something");
        postDto.setId(1L);
        postDto.setTitle("Dr");
        assertThrows(RessourceNotFoundException.class, () -> postServiceImpl.updatePost(postDto, 1L));
        verify(postRepository).save(Mockito.<Post>any());
        verify(postRepository).findById(Mockito.<Long>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<PostDto>>any());
    }


    /**
     * Method under test: {@link PostServiceImpl#deletePostById(Long)}
     */
    @Test
    void testDeletePostById() {
        doNothing().when(postRepository).delete(Mockito.<Post>any());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        postServiceImpl.deletePostById(1L);
        verify(postRepository).findById(Mockito.<Long>any());
        verify(postRepository).delete(Mockito.<Post>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#deletePostById(Long)}
     */
    @Test
    void testDeletePostById2() {
        doThrow(new RessourceNotFoundException("Ressource Name", "Field Name", 42L)).when(postRepository)
            .delete(Mockito.<Post>any());
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Post()));
        assertThrows(RessourceNotFoundException.class, () -> postServiceImpl.deletePostById(1L));
        verify(postRepository).findById(Mockito.<Long>any());
        verify(postRepository).delete(Mockito.<Post>any());
    }

    /**
     * Method under test: {@link PostServiceImpl#deletePostById(Long)}
     */
    @Test
    void testDeletePostById3() {
        when(postRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(RessourceNotFoundException.class, () -> postServiceImpl.deletePostById(1L));
        verify(postRepository).findById(Mockito.<Long>any());
    }
}

