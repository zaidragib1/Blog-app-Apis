package com.springproject.blog.services;

import com.springproject.blog.payloads.PostDto;
import com.springproject.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get post by Id
    PostDto getPostById(Integer postId);

    //Get Post By Category
    List<PostDto>getPostByCategory(Integer CategoryId);

    //Get Post By User
    List<PostDto> getPostByUser(Integer userId);

    //Get All User
    PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);

    //search post
    List<PostDto> searchPosts(String keywords);
}
