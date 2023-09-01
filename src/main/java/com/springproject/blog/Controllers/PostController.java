package com.springproject.blog.Controllers;

import com.springproject.blog.Config.AppConstants;
import com.springproject.blog.payloads.ApiResponse;
import com.springproject.blog.payloads.PostDto;
import com.springproject.blog.payloads.PostResponse;
import com.springproject.blog.services.FileService;
import com.springproject.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //CREATE POST
    @PostMapping("/createPost/{userId}/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){

        PostDto createPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    //UPDATE POST
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updateUser(@RequestBody PostDto postDto,@PathVariable Integer postId){

        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //DELETE POST
    @DeleteMapping("/delete/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){

        this.postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully",true);
    }

    //GETPOST BY ID
    @GetMapping("/getPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){

        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    //GET POST BY CATEGORY
    @GetMapping("/getPostByCategory/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

        List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);

    }

    //GET POST BY USER
    @GetMapping("/getPostByUser/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        List<PostDto> postByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
    }

    //GET POST BY TITLE
    @GetMapping("/searchPost/{keywords}")  // Change "keywords" to "keyword"
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keywords") String keywords){

        List<PostDto> postByTitle = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(postByTitle, HttpStatus.OK);
    }




    //GET ALL POST
    @GetMapping("/getAllposts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                   @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                   @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                   @RequestParam(name = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){

        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }


}
