package com.animal.animal.service.impl;

import com.animal.animal.util.generator.StringRandomGenerator;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.service.AnimalService;
import com.animal.animal.config.exception.AnimalException;
import com.animal.animal.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    //injection
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private StringRandomGenerator stringRandomGenerator;



    @Override
    public AnimalDto createAnimal(AnimalDto animalDto) {
        ModelMapper modelMapper = new ModelMapper();
        AnimalEntity animalEntity = modelMapper.map(animalDto,AnimalEntity.class);
        String publicId= stringRandomGenerator.publicIdGenerator();
        animalEntity.setPublicId(publicId);
        AnimalEntity savedAnimal = animalRepository.save(animalEntity);

        return modelMapper.map(savedAnimal,AnimalDto.class);
    }


    @Override
    public AnimalDto getAnimal(String publicId) {
        AnimalEntity animalEntity = animalRepository.findByPublicId(publicId);
        if (animalEntity==null){
            throw new AnimalException("Animal not exist", HttpStatus.NOT_FOUND);
        }
        return new ModelMapper().map(animalEntity,AnimalDto.class);
    }

    @Override
    public List<AnimalDto> getAllAnimal() {
        List<AnimalEntity> animalEntities = animalRepository.findAll();
        if (animalEntities.isEmpty()){
            throw new AnimalException("any user not exist",HttpStatus.NOT_FOUND);
        }
       return animalEntities.stream().map(animalEntity -> new ModelMapper().map(animalEntity,AnimalDto.class)).toList();
    }

    @Override
    public int deleteAnimal(String publicId) {
        int animalDelete = animalRepository.deleteByPublicId(publicId);
        if (animalDelete==0){
            throw new AnimalException("not delete : animal not exist",HttpStatus.NOT_FOUND);
        }
        return animalDelete;
    }

    @Override
    public AnimalDto updateAnimal(AnimalDto animalDto,String publicId) {
        AnimalEntity findAnimal = animalRepository.findByPublicId(publicId);
        if (findAnimal==null){
            throw new AnimalException("animal with this publicId dont exist",HttpStatus.NOT_FOUND);
        }
        if(animalDto.getName()!=null)
            findAnimal.setName(animalDto.getName());
        if(animalDto.getAge()!=0)
            findAnimal.setAge(animalDto.getAge());
        if(animalDto.getType()!=null)
            findAnimal.setType(animalDto.getType());
        AnimalEntity updatedAnimalEntity = animalRepository.save(findAnimal);
        return new ModelMapper().map(updatedAnimalEntity,AnimalDto.class);
    }

}
