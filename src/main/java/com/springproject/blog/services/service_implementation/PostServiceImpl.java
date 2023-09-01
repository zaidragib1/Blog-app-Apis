package com.springproject.blog.services.service_implementation;

import com.springproject.blog.Exceptions.ResourceNotFoundException;
import com.springproject.blog.entities.Category;
import com.springproject.blog.entities.Post;
import com.springproject.blog.entities.User;
import com.springproject.blog.payloads.PostDto;
import com.springproject.blog.payloads.PostResponse;
import com.springproject.blog.repositories.CategoryRepo;
import com.springproject.blog.repositories.PostRepo;
import com.springproject.blog.repositories.UserRepo;
import com.springproject.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import static jogamp.graph.font.typecast.ot.table.Table.post;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        // Fetch the User and Category entities based on their IDs
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","User Id",userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","Category Id",categoryId));

        // Map the PostDto to a Post entity
        Post post = this.modelMapper.map(postDto,Post.class);

        // Set the User and Category for the Post entity
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        // Save the Post entity
        Post savedPost = this.postRepo.save(post);

        // Map the saved Post entity back to a PostDto and retruning
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","Post Id",postId));

        Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);

        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post checkPost = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post Id",postId));

        return this.modelMapper.map(checkPost, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
       Category checkcategory = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));

       List<Post> posts = this.postRepo.findByCategory(checkcategory);

       List<PostDto> postListCategorywise = new ArrayList<>();

       for(Post x: posts){

           PostDto postDto = this.modelMapper.map(x, PostDto.class);
           postListCategorywise.add(postDto);
       }
       return postListCategorywise;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
         User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user Id",userId));

         List<Post> posts = this.postRepo.findByUser(user);

         List<PostDto> UsersPostList = new ArrayList<>();

         for(Post x:posts){

             PostDto postDto = this.modelMapper.map(x,PostDto.class);
             UsersPostList.add(postDto);
         }
         return UsersPostList;

    }
//CKECK IT ONCE AGAIN
    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Validate pageNumber and pageSize to prevent negative values or other invalid inputs
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must be at least 1");
        }

        // Validate sort direction
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDir)) {
            direction = Sort.Direction.DESC;
        }

        // Create a Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortBy);

        // Retrieve the page of posts from the repository
        Page<Post> postPage = postRepo.findAll(pageable);

        // Map the Post entities to PostDto
        List<PostDto> postDtoList = postPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        // Create a PostResponse with the PostDto list and pagination info
        PostResponse postResponse = new PostResponse();

        postResponse.setPosts(postDtoList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPages((long) postPage.getTotalPages());
        postResponse.setTotalPosts(postPage.getTotalElements());

        return postResponse;
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitle("%" + keyword + "%");

        List<PostDto> postsList = new ArrayList<>();

        for(Post x:posts){

            PostDto postDto = this.modelMapper.map(x,PostDto.class);
            postsList.add(postDto);
        }
        return postsList;
}
}
