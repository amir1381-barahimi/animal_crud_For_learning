package com.animal.animal.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class AnimalException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    public AnimalException(String message,HttpStatus httpStatus) {
        super(message);
        this.message=message;
        this.httpStatus=httpStatus;
    }
}
