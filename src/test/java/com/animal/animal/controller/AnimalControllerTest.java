package com.animal.animal.controller;

import com.animal.animal.AnimalApplication;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.entity.AnimalEntity;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.service.AnimalService;
import com.animal.animal.config.exception.AnimalException;
import com.animal.animal.repository.AnimalRepository;
import com.animal.animal.shared.MyApiResponse;
import com.animal.animal.util.animalUtil.AnimalUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes= AnimalApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = AnimalController.class)
class AnimalControllerTest {

    private String path="/animals";

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    AnimalService animalService;

    @MockBean
    AnimalUtil animalUtil;

//    @Autowired
//    AnimalRepository animalRepository;
//    @Autowired
//    AnimalController animalController;
//
//    @Autowired
//    AnimalService animalService;

    //test getAnimal

//    @DisplayName("test getAnimal when give valid publicId then return valid AnimalResponseModel")
//    @Test
//    void testGiveValidPublicId_WhenCheckGetAnimal_ThenReturnValidAnimalResponseModel() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setAge(44);
//        animalDto.setType("gorbesan");
//
//        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
//        //when
//
//        AnimalResponseModel animalResponseModel = animalController.getAnimal(createdAnimal.getPublicId());
//        //then
//        Assertions.assertThat(animalResponseModel)
//                .isNotNull()
//                .isEqualTo(new ModelMapper().map(createdAnimal,AnimalResponseModel.class));
//
//
//    }
//
//    @DisplayName("test getAnimal when give invalid publicId then return Exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckGetAnimal_ThenReturnException() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setAge(44);
//        animalDto.setType("gorbesan");
//
//        animalService.createAnimal(animalDto);
//
//
//        //then
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalController.getAnimal(UUID.randomUUID().toString());
//        });
//
//        Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//
//    }
//
//    @DisplayName("test getAnimal when give null publicId then return exception")
//    @Test
//    void testGiveNullPublicId_WhenCheckGetAnimal_ThenReturnException() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setAge(44);
//        animalDto.setType("gorbesan");
//
//        animalService.createAnimal(animalDto);
//
//        //then
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalController.getAnimal(null);
//        });
//
//        Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//
//    }
//
//    //test getAnimals
//
//    @Order(2)
//    @DisplayName("test getAnimals when give two valid Animal Dto then return Two valid animals")
//    @Test
//    void testGiveTwoValidAnimal_WhenCheckGetAnimals_ThenReturnTwoValidAnimals() {
//        //give
//
//        AnimalDto animalDto1 = new AnimalDto();
//        animalDto1.setName("babr");
//        animalDto1.setAge(44);
//        animalDto1.setType("gorbesan");
//
//        AnimalDto createdAnimal1 = animalService.createAnimal(animalDto1);
//
//        AnimalDto animalDto2 = new AnimalDto();
//        animalDto2.setName("babr");
//        animalDto2.setAge(44);
//        animalDto2.setType("gorbesan");
//
//        AnimalDto createdAnimal2 = animalService.createAnimal(animalDto2);
//
//        ArrayList<AnimalResponseModel> animalResponseModels2 = new ArrayList<>();
//        animalResponseModels2.add(new ModelMapper().map(createdAnimal1,AnimalResponseModel.class));
//        animalResponseModels2.add(new ModelMapper().map(createdAnimal2,AnimalResponseModel.class));
//
//        //when
//        List<AnimalResponseModel> animalResponseModels = animalController.getAnimals();
//
//        //then
//        Assertions.assertThat(animalResponseModels).isEqualTo(animalResponseModels2);
//    }
//
//    @Order(1)
//    @DisplayName("test getAnimals when give any animal dto then return exception")
//    @Test
//    void testGiveAnyAnimalDto_WhenCheckGetAnimals_ThenReturnException() {
//        //give
//            //give_any_animal_dto
//
//        //then
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalController.getAnimals();
//        });
//        Assertions.assertThat(exception.getMessage()).isEqualTo("any user not exist");
//
//    }
//
//    //test CreateAnimal
//
//    @DisplayName("test createAnimal when give valid AnimalRequestModel then return Valid AnimalResponseAnimal")
//    @Test
//    void testGiveValidAnimalRequestModel_WhenCheckCreateAnimal_ThenReturnValidAnimalResponseModel() {
//        //give
//        AnimalRequestModel animalRequestModel = new AnimalRequestModel();
//        animalRequestModel.setName("fil");
//        animalRequestModel.setAge(23);
//        animalRequestModel.setType("pestandar");
//
//        //when
//        AnimalResponseModel animalResponseModel = animalController.createAnimal(animalRequestModel);
//        //then
//        Assertions.assertThat(animalResponseModel).isNotNull();
//        Assertions.assertThat(animalResponseModel.getPublicId()).isNotNull();
//        Assertions.assertThat(animalResponseModel.getName()).isEqualTo(animalRequestModel.getName());
//        Assertions.assertThat(animalResponseModel.getType()).isEqualTo(animalRequestModel.getType());
//        Assertions.assertThat(animalResponseModel.getAge()).isEqualTo(animalRequestModel.getAge());
//    }
//
//    @DisplayName("test createAnimal when give null AnimalRequestModel then return Exception")
//    @Test
//    void testGiveNullAnimalRequestModel_WhenCheckCreateAnimal_ThenReturnException() {
//        //give
//            //give_null
//
//        //then
//        org.junit.jupiter.api.Assertions.assertThrowsExactly(IllegalArgumentException.class,()->{
//            //when
//            animalController.createAnimal(null);
//        });
//    }
//
//    //test deleteAnimal
//
//    @DisplayName("test deleteAnimal when give valid publicId then return valid id")
//    @Test
//    void testGiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnValidId() {
//
//
//        //give
//
//        AnimalEntity animalEntity = new AnimalEntity();
//        animalEntity.setPublicId(UUID.randomUUID().toString());
//        animalEntity.setType("gorbesan");
//        animalEntity.setName("babr");
//        animalEntity.setAge(45);
//
//        AnimalEntity savedAnimal = animalRepository.save(animalEntity);
//
//        //when
//
//        AnimalDeleteResponseModel animalDeleteResponseModel = animalController.deleteAnimal(savedAnimal.getPublicId());
//        //then
//        Assertions.assertThat(animalDeleteResponseModel.getPublicId()).isEqualTo(savedAnimal.getPublicId());
//    }
//
//    @DisplayName("test delete animal when give invalid publicId then return Exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
//        //give
//        String publicId = UUID.randomUUID().toString();
//
//        //then
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalController.deleteAnimal(publicId);
//        });
//        Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
//    }
//
//    //test update
//
//    @DisplayName("test update when give valid animalRequestModel and valid publicId then return valid animalResponseModel")
//    @Test
//    void testGiveValidAnimalRequestModelAndValidPublicId_WhenCheckUpdate_ThenReturnValidAnimalResponseModel() {
//        //give
//        AnimalRequestModel animalRequestModel = new AnimalRequestModel();
//        animalRequestModel.setName("fil");
//        animalRequestModel.setType("pestandar");
//        animalRequestModel.setAge(57);
//
//        AnimalResponseModel animalResponseModel = animalController.createAnimal(animalRequestModel);
//
//        AnimalRequestModel updateAnimalModel = new AnimalRequestModel();
//        updateAnimalModel.setName("shir");
//        updateAnimalModel.setAge(55);
//        updateAnimalModel.setType("pestandar");
//
//        AnimalResponseModel updateRequestToResponse = new ModelMapper().map(updateAnimalModel,AnimalResponseModel.class);
//        updateRequestToResponse.setPublicId(animalResponseModel.getPublicId());
//        //when
//        AnimalResponseModel animalUpdate = animalController.update(updateAnimalModel,animalResponseModel.getPublicId());
//
//        //then
//        Assertions.assertThat(animalUpdate).isEqualTo(updateRequestToResponse);
//    }
//
//    @DisplayName("test update when give invalid publicId then return exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckUpdate_ThenReturnException() {
//        //give
//        AnimalRequestModel animalRequestModel = new AnimalRequestModel();
//        animalRequestModel.setName("fil");
//        animalRequestModel.setType("pestandar");
//        animalRequestModel.setAge(57);
//
//        animalController.createAnimal(animalRequestModel);
//
//        AnimalRequestModel updateAnimalModel = new AnimalRequestModel();
//        updateAnimalModel.setName("shir");
//        updateAnimalModel.setAge(55);
//        updateAnimalModel.setType("pestandar");
//
//        //then
//        Exception exception = org.junit.jupiter.api.Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalController.update(updateAnimalModel,UUID.randomUUID().toString());
//        });
//        Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");
//    }


    AnimalDto animalDto;

    String publicId;


    @BeforeEach
    void init(){

        publicId = UUID.randomUUID().toString();

        animalDto = new AnimalDto();
        animalDto.setName("fil");
        animalDto.setAge(34);
        animalDto.setType("pestandar");
        animalDto.setPublicId(publicId);
    }


    @DisplayName("")
    @Test
    void testGiven_When_Then() throws Exception {
        //give

        AnimalRequestModel a = new AnimalRequestModel();
        a.setName("fil");
        a.setType("pestandar");
        a.setAge(23);

        AnimalResponseModel animalResponseModel = new ModelMapper().map(a,AnimalResponseModel.class);
        animalResponseModel.setPublicId(publicId);

        ResponseEntity<MyApiResponse> excepted = new ResponseEntity<>(new MyApiResponse(true,"",new Date(),animalResponseModel), HttpStatus.CREATED);




        when(animalUtil.createResponse(any(),any())).thenReturn(excepted);
        when(animalService.createAnimal(any())).thenReturn(animalDto);


        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(a))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString,MyApiResponse.class);


        //then
        Assertions.assertThat(myApiResponse).isNotNull();
        Assertions.assertThat(myApiResponse.isAction()).isTrue();
        Assertions.assertThat(myApiResponse.getDate()).isEqualTo(excepted.getBody().getDate());
        Assertions.assertThat(myApiResponse.getResult()).isNotNull();
        Assertions.assertThat(new ModelMapper().map(myApiResponse.getResult(),AnimalResponseModel.class)).isEqualTo(animalResponseModel);



    }



}