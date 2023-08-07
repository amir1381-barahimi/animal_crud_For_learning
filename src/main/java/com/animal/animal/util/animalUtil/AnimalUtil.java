package com.animal.animal.util.animalUtil;

import com.animal.animal.shared.MyApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AnimalUtil {


    public ResponseEntity<MyApiResponse>createResponse(Object animalResponse , HttpStatus httpStatus){

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
