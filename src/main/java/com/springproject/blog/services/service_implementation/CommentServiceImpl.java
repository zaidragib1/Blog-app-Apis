package com.springproject.blog.services.service_implementation;

import com.springproject.blog.Exceptions.ResourceNotFoundException;
import com.springproject.blog.entities.Post;
import com.springproject.blog.payloads.CommentDto;
import com.springproject.blog.repositories.CommentRepo;
import com.springproject.blog.repositories.PostRepo;
import com.springproject.blog.services.CommentService;
import com.springproject.blog.entities.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.xml.stream.events.Comment;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = (Comment) commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        this.commentRepo.delete(comment);
    }
}
