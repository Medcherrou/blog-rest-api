package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException{

    private String ressourceName;
    private String fieldName;
    private Long fieldValue;

    public RessourceNotFoundException(String ressourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : %s", ressourceName,fieldName,fieldValue)); // Post not found with id : 1
        this.ressourceName = ressourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getRessourceName() {
        return ressourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }
}
