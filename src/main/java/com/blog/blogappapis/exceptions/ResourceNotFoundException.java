package com.blog.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends  RuntimeException{

    String resourceName;
    String fileName;
    long fieldValue;
    public ResourceNotFoundException(String resourceName, String fileName, long fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fileName, fieldValue));
        this.resourceName = resourceName;
        this.fileName = fileName;
        this.fieldValue = fieldValue;
    }
}
