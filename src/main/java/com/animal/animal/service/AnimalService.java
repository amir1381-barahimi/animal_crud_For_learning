package com.animal.animal.service;

import com.animal.animal.model.dto.AnimalDto;

import java.util.List;

public interface AnimalService {
    AnimalDto createAnimal(AnimalDto animalDto);
    AnimalDto getAnimal(String publicId);
    List<AnimalDto> getAllAnimal();
    int deleteAnimal(String publicId);
    AnimalDto updateAnimal(AnimalDto animalDto,String publicId);

}
