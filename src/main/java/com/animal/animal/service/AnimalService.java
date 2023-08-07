package com.animal.animal.service;

import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.shared.MyApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnimalService {

    ResponseEntity<MyApiResponse> createAnimal(AnimalRequestModel animalRequestModel);

    ResponseEntity<MyApiResponse> getAnimal(String publicId);
    ResponseEntity<MyApiResponse> getAllAnimal();
    ResponseEntity<MyApiResponse> deleteAnimal(String publicId);
    ResponseEntity<MyApiResponse> updateAnimal(AnimalRequestModel animalRequestModel,String publicId);

}
