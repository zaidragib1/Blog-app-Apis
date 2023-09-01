package com.springproject.blog.services;

import com.springproject.blog.payloads.CommentDto;

public interface CommentService {

    //do comment
    CommentDto createComment(CommentDto commentDto, Integer postId);

    //delete comment
    void deleteComment(Integer commentId);
}
