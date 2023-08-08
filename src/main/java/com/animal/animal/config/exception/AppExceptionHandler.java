package com.animal.animal.config.exception;


import com.animal.animal.shared.MyApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = {AnimalException.class})
    public ResponseEntity<Object> handleAnimalException(AnimalException animalException){
        MyApiResponse response = new MyApiResponse(false,
                animalException.getMessage(),
                new Date(),
                new ArrayList<>());
        return new ResponseEntity<>(response,animalException.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handlerException(Exception exception){

        logger.info("exception had just happened : "+exception.getMessage());

        MyApiResponse response = new MyApiResponse();
        response.setAction(false);
        response.setMessage(exception.getMessage());
        response.setDate(new Date());
        response.setResult(new ArrayList<>());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
