package com.animal.animal.util.animalUtil;

import com.animal.animal.shared.MyApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class AnimalUtil {


    public static ResponseEntity<MyApiResponse>createResponse(Object animalResponse , HttpStatus httpStatus){

        if(animalResponse == null || httpStatus == null){
            return null;
        }
        MyApiResponse myApiResponse = new MyApiResponse();
        myApiResponse.setAction(true);
        myApiResponse.setDate(new Date());
        myApiResponse.setMessage("");
        myApiResponse.setResult(animalResponse);

        return new ResponseEntity<MyApiResponse>(myApiResponse,httpStatus);
    }
}
