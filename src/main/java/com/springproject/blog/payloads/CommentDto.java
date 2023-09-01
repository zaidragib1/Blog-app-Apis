package com.springproject.blog.payloads;

import lombok.Getter;
import lombok.Setter;

//@NoArgsConstructor
@Setter
@Getter

public class CommentDto {

    private Integer id;

    private String content;
}
