package com.spyair.blog;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//这个注解是指 最后跳到404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    //自定义的一个抛出异常
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
