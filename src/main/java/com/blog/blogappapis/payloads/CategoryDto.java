package com.blog.blogappapis.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer id;

    @NotBlank
    @Size(min = 4, message = "La description de la category doit avoir au moins 4 caracteres")
    private String title;

    @NotBlank
    @Size(min =  5, message = "La description de la category doit avoir au moins 5 caracteres")
    private String description;


}
