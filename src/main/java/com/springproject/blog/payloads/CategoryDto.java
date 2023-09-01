package com.springproject.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {


    private Integer categoryId;

    // @NotBlank annotation is stricter than @NotEmpty in that it requires the string to have at least one non-whitespace character.
    @NotBlank
    @Size(min =4, message = "min Size of category title is 4")
    private String CategoryTitle;

    @NotBlank
    @Size(min=10 , message= "min size of description is 10")
    private String CategoryDescription;
}
