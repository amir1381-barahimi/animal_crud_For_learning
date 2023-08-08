package com.animal.animal.service.impl;

import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.shared.MyApiResponse;
import com.animal.animal.util.animalUtil.AnimalUtil;
import com.animal.animal.util.generator.StringRandomGenerator;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.service.AnimalService;
import com.animal.animal.config.exception.AnimalException;
import com.animal.animal.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    //injection


    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private StringRandomGenerator stringRandomGenerator;

    @Autowired
    private AnimalUtil animalUtil;



    @Override
    public ResponseEntity<MyApiResponse> createAnimal(AnimalRequestModel animalRequestModel) {

        AnimalDto animalDto = animalUtil.convert(animalRequestModel);

        String publicId= stringRandomGenerator.publicIdGenerator();
        animalDto.setPublicId(publicId);
        AnimalEntity animalEntity = animalUtil.convert(animalDto);

        AnimalEntity savedAnimal = animalRepository.save(animalEntity);
        AnimalResponseModel animalResponseModel = animalUtil.convert(savedAnimal);

        return animalUtil.createResponse(animalResponseModel,HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<MyApiResponse> getAnimal(String publicId) {
        AnimalEntity animalEntity = animalRepository.findByPublicId(publicId);
        if (animalEntity==null){
            throw new AnimalException("Animal not exist", HttpStatus.NOT_FOUND);
        }
        AnimalResponseModel animalResponseModel = animalUtil.convert(animalEntity);
        return animalUtil.createResponse(animalResponseModel,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> getAllAnimal() {
        List<AnimalEntity> animalEntities = animalRepository.findAll();
        if (animalEntities.isEmpty()){
            throw new AnimalException("any user not exist",HttpStatus.NOT_FOUND);
        }
       List<AnimalResponseModel> animalResponseModels = animalEntities.stream().map(animalEntity -> new ModelMapper().map(animalEntity,AnimalResponseModel.class)).toList();
        return animalUtil.createResponse(animalResponseModels,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> deleteAnimal(String publicId) {
        int animalDelete = animalRepository.deleteByPublicId(publicId);

        if (animalDelete==0){
            throw new AnimalException("not delete : animal not exist",HttpStatus.NOT_FOUND);
        }

        AnimalDeleteResponseModel animalDeleteResponseModel = animalUtil.createDeleteResponse(publicId);


        return animalUtil.createResponse(animalDeleteResponseModel,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyApiResponse> updateAnimal(AnimalRequestModel editAnimal,String publicId) {
        AnimalEntity findAnimal = animalRepository.findByPublicId(publicId);

        if (findAnimal==null){
            throw new AnimalException("animal with this publicId dont exist",HttpStatus.NOT_FOUND);
        }
        if(editAnimal.getName()!=null)
            findAnimal.setName(editAnimal.getName());
        if(editAnimal.getAge()!=0)
            findAnimal.setAge(editAnimal.getAge());
        if(editAnimal.getType()!=null)
            findAnimal.setType(editAnimal.getType());

        AnimalEntity updatedAnimalEntity = animalRepository.save(findAnimal);
        AnimalResponseModel animalResponseModel = animalUtil.convert(updatedAnimalEntity);

        return animalUtil.createResponse(animalResponseModel,HttpStatus.OK);
    }

}
