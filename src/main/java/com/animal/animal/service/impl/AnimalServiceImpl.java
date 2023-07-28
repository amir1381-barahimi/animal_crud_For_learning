package com.animal.animal.service.impl;

import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.service.AnimalService;
import com.animal.animal.exception.AnimalException;
import com.animal.animal.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalServiceImpl implements AnimalService {

    //injection
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalDto createAnimal(AnimalDto animalDto) {
        ModelMapper modelMapper = new ModelMapper();
        AnimalEntity animalEntity = modelMapper.map(animalDto,AnimalEntity.class);
        String publicId= String.valueOf(UUID.randomUUID());
        animalEntity.setPublicId(publicId);
        AnimalEntity savedAnimal = animalRepository.save(animalEntity);

        return modelMapper.map(savedAnimal,AnimalDto.class);
    }

    @Override
    public AnimalDto getAnimal(String publicId) {
        AnimalEntity animalEntity = animalRepository.findByPublicId(publicId);
        if (animalEntity==null){
            throw new AnimalException("Animal not exist");
        }
        return new ModelMapper().map(animalEntity,AnimalDto.class);
    }

    @Override
    public List<AnimalDto> getAllAnimal() {
        List<AnimalEntity> animalEntities = animalRepository.findAll();
        if (animalEntities.isEmpty()){
            throw new AnimalException("any user not exist");
        }
       return animalEntities.stream().map(animalEntity -> new ModelMapper().map(animalEntity,AnimalDto.class)).toList();
    }

    @Override
    public int deleteAnimal(String publicId) {
        int animalDelete = animalRepository.deleteByPublicId(publicId);
        if (animalDelete==0){
            throw new AnimalException("not delete : animal not exist");
        }
        return animalDelete;
    }

    @Override
    public AnimalDto updateAnimal(AnimalDto animalDto,String publicId) {
        AnimalEntity findAnimal = animalRepository.findByPublicId(publicId);
        if (findAnimal==null){
            throw new AnimalException("animal with this publicId dont exist");
        }
        findAnimal.setName(animalDto.getName());
        findAnimal.setAge(animalDto.getAge());
        findAnimal.setType(animalDto.getType());
        AnimalEntity updatedAnimalEntity = animalRepository.save(findAnimal);
        return new ModelMapper().map(updatedAnimalEntity,AnimalDto.class);
    }

}
