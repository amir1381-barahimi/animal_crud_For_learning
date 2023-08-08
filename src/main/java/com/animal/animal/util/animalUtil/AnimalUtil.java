package com.animal.animal.util.animalUtil;

import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.shared.MyApiResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AnimalUtil {

    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<MyApiResponse>createResponse(Object animalResponse , HttpStatus httpStatus){

        if(animalResponse == null || httpStatus == null){
            return null;
        }
        MyApiResponse myApiResponse = new MyApiResponse();
        myApiResponse.setAction(true);
        myApiResponse.setDate(new Date());
        myApiResponse.setMessage("");
        myApiResponse.setResult(animalResponse);

        return new ResponseEntity<>(myApiResponse,httpStatus);
    }

    public AnimalDto convert(AnimalRequestModel animalRequestModel){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(animalRequestModel,AnimalDto.class);
    }

    public AnimalResponseModel convert(AnimalEntity animalEntity){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(animalEntity,AnimalResponseModel.class);
    }

    public AnimalEntity convert(AnimalDto animalDto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(animalDto,AnimalEntity.class);
    }

    public AnimalDeleteResponseModel createDeleteResponse(String publicId){
        AnimalDeleteResponseModel animalDeleteResponseModel = new AnimalDeleteResponseModel();
        animalDeleteResponseModel.setPublicId(publicId);
        animalDeleteResponseModel.setStatus("animal with publicId "+publicId+" deleted");
        return animalDeleteResponseModel;
    }
}
