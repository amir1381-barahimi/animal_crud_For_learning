package com.animal.animal.service;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.shared.MyApiResponse;
import com.animal.animal.util.animalUtil.AnimalUtil;
import com.animal.animal.util.generator.StringRandomGenerator;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.service.impl.AnimalServiceImpl;
import com.animal.animal.config.exception.AnimalException;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.repository.AnimalRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    AnimalRepository animalRepository;

    @InjectMocks
    AnimalServiceImpl animalService;

    @Mock
    StringRandomGenerator publicIdGenerator;

    @Mock
    AnimalUtil animalUtil;
    AnimalDto animalDto;
    AnimalEntity animalEntity;
    String publicId;

    ResponseEntity<MyApiResponse> response;

    AnimalResponseModel animalResponseModel;
    AnimalRequestModel animalRequestModel;

    AnimalDeleteResponseModel animalDeleteResponseModel;
    MyApiResponse myApiResponse;



    @BeforeEach
    void init(){
        animalEntity = new AnimalEntity();
        animalEntity.setName("fil");
        animalEntity.setAge(34);
        animalEntity.setType("pestandar");

        animalDto = new AnimalDto();
        animalDto.setName("fil");
        animalDto.setAge(34);
        animalDto.setType("pestandar");

         publicId = UUID.randomUUID().toString();


         animalResponseModel = new AnimalResponseModel();
         animalResponseModel.setPublicId(publicId);
         animalResponseModel.setType("pestandar");
         animalResponseModel.setName("fil");
         animalResponseModel.setAge(34);

         animalDeleteResponseModel = new AnimalDeleteResponseModel();
         animalDeleteResponseModel.setStatus("animal with publicId "+publicId+" deleted");
         animalDeleteResponseModel.setPublicId(publicId);

        animalRequestModel = new AnimalRequestModel();
        animalRequestModel.setType("pestandar");
        animalRequestModel.setName("fil");
        animalRequestModel.setAge(34);

        myApiResponse = new MyApiResponse();
        myApiResponse.setAction(true);
        myApiResponse.setMessage("");
        myApiResponse.setResult(animalResponseModel);
        myApiResponse.setDate(new Date());

        response = new ResponseEntity<>(myApiResponse, HttpStatus.CREATED);
    }


    @DisplayName("test create animal when give valid animal request model then return valid animal request model")
    @Test
    void test_GiveValidAnimalRequestModel_WhenCheckCreateAnimal_ThenReturnValidResponseEntity() {
        //give
        when(animalRepository.save(any())).thenReturn(animalEntity);
        animalEntity.setId(0);
        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
        when(animalUtil.createResponse(any(),any())).thenReturn(response);
        when(animalUtil.convert(any(AnimalRequestModel.class))).thenReturn(animalDto);
        when(animalUtil.convert(any(AnimalDto.class))).thenReturn(animalEntity);
        when(animalUtil.convert(any(AnimalEntity.class))).thenReturn(animalResponseModel);


        //when
        ResponseEntity<MyApiResponse> createdResponse = animalService.createAnimal(animalRequestModel);
        //then
        org.assertj.core.api.Assertions.assertThat(createdResponse).isEqualTo(response);

    }

    @DisplayName("test createAnimal when give invalid animal request model then return exception")
    @Test
    void testGiveInvalidAnimalDto_WhenCheckCreateAnimal_ThenReturnAnimalException() {
        //give
        when(animalRepository.save(any())).thenReturn(new ClassCastException());
        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
        when(animalUtil.convert(any(AnimalRequestModel.class))).thenReturn(animalDto);
        when(animalUtil.convert(any(AnimalDto.class))).thenReturn(animalEntity);

        animalDto.setName(null);

        //then
        Assertions.assertThrows(ClassCastException.class,()->{
            //when
            animalService.createAnimal(animalRequestModel);
        });

    }

    @DisplayName("test GetAnimal when give Valid exist publicId then return Valid Response entity")
    @Test
    void test_GiveValidExistPublicId_WhenCheckGetAnimal_ThenReturnValidResponseEntity() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
        animalEntity.setId(0);
        animalEntity.setPublicId(publicId);
        when(animalUtil.convert(any(AnimalEntity.class))).thenReturn(animalResponseModel);
        when(animalUtil.createResponse(any(),any())).thenReturn(response);
        //when

        ResponseEntity<MyApiResponse> getAnimal = animalService.getAnimal(publicId);
        //then
        org.assertj.core.api.Assertions.assertThat(getAnimal).isEqualTo(response);
    }

    @DisplayName("test getAnimal when give not exist publicId then return Animal Exception")
    @Test
    void test_GiveNotExistPublicId_WhenCheckGetAnimal_ThenAnimalException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(null);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.getAnimal(publicId);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
    }

    @DisplayName("test getAnimal when give null publicId then return exception")
    @Test
    void test_GiveNullPublicId_WhenCheckGetAnimal_ThenReturnException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(null);


        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.getAnimal(null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");

    }

    @DisplayName("test deleteAnimal when give valid publicId then return id")
    @Test
    void test_GiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnValidId() {
        //give
        when(animalRepository.deleteByPublicId(any())).thenReturn(10);
        when(animalUtil.createDeleteResponse(any())).thenReturn(animalDeleteResponseModel);
        when(animalUtil.createResponse(any(),any())).thenReturn(response);
        //when
        ResponseEntity<MyApiResponse> deleteResponse = animalService.deleteAnimal(publicId);

        //then
        org.assertj.core.api.Assertions.assertThat(deleteResponse).isEqualTo(response);
    }

    @DisplayName("test deleteAnimal when give null publicId then return exception")
    @Test
    void test_GiveNullPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
        //give

        when(animalRepository.deleteByPublicId(any())).thenReturn(0);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.deleteAnimal(null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
    }

    @DisplayName("test deleteAnimal when give invalid publicId then return exception")
    @Test
    void test_GiveInvalidPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
        //give

        when(animalRepository.deleteByPublicId(any())).thenReturn(0);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.deleteAnimal(publicId);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
    }

    @DisplayName("test updateAnimal when give Valid PublicId then return Valid Animal Dto")
    @Test
    void test_GiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidAnimalResponseEntity() {
        //give
        AnimalRequestModel animalUpdate = new AnimalRequestModel();
        animalUpdate.setName("zarafe");
        animalUpdate.setAge(24);
        animalUpdate.setType("pestandar");



        animalEntity.setPublicId(publicId);
        animalEntity.setId(1);

        AnimalEntity newAnimal = new ModelMapper().map(animalUpdate,AnimalEntity.class);

        newAnimal.setPublicId(publicId);
        newAnimal.setId(1);

        when(animalUtil.convert(any(AnimalEntity.class))).thenReturn(animalResponseModel);
        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
        when(animalRepository.save(any())).thenReturn(newAnimal);
        when(animalUtil.createResponse(any(),any())).thenReturn(response);

        //when

        ResponseEntity<MyApiResponse> updatedAnimal = animalService.updateAnimal(animalUpdate,publicId);
        //then

        org.assertj.core.api.Assertions.assertThat(updatedAnimal).isEqualTo(response);
    }

    @DisplayName("test updateAnimal when give Invalid publicId then return Exception")
    @Test
    void test_GiveInvalidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(null);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.updateAnimal(animalRequestModel,publicId);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");

    }

    @DisplayName("test updateAnimal when give null publicId then return Exception")
    @Test
    void test_GiveNullPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(null);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.updateAnimal(animalRequestModel,null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");

    }

    @DisplayName("test updateAnimal when give null animal dto with valid publicId then return Exception")
    @Test
    void test_GiveNullAnimalRequestModelWithValidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give


        when(animalRepository.findByPublicId(publicId)).thenReturn(animalEntity);

        //then
        Assertions.assertThrowsExactly(NullPointerException.class,()->{
            //when
            animalService.updateAnimal(null,publicId);
        });
    }

    @DisplayName("test getAllAnimal when give valid Animal Request Model then return valid response entity")
    @Test
    void testGiveValidAnimalRequestModel_WhenCheckGetAllAnimal_ThenReturnValidResponseEntity() {
        //give

        List<AnimalEntity> animalEntityList = new ArrayList<>();
        animalEntityList.add(animalEntity);
        when(animalRepository.findAll()).thenReturn(animalEntityList);
        when(animalUtil.createResponse(any(),any())).thenReturn(response);
        response.getBody().setResult(animalEntityList);
        //when

        ResponseEntity<MyApiResponse> getAllResponse = animalService.getAllAnimal();
        //then
        org.assertj.core.api.Assertions.assertThat(getAllResponse).isEqualTo(response);
    }

    @DisplayName("test getAllAnimal when dont any give then return Exception")
    @Test
    void testGiveDontAnyGive_WhenCheckGetAllAnimal_ThenReturnException() {
        //give
        when(animalRepository.findAll()).thenReturn(new ArrayList<>());

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.getAllAnimal();
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("any user not exist");
    }

}

