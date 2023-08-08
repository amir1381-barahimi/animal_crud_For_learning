package com.animal.animal.controller;

import com.animal.animal.AnimalApplication;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.service.AnimalService;
import com.animal.animal.shared.MyApiResponse;
import com.animal.animal.util.animalUtil.AnimalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = AnimalApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = AnimalController.class)
class AnimalControllerTest {

    private final String path = "/animals";

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    AnimalService animalService;

    @MockBean
    AnimalUtil animalUtil;


    AnimalDto animalDto;

    String publicId;

    AnimalRequestModel animalRequestModel;

    AnimalResponseModel animalResponseModel;
    ResponseEntity<MyApiResponse> excepted;


    @BeforeEach
    void init() {

        publicId = UUID.randomUUID().toString();

        animalDto = new AnimalDto();
        animalDto.setName("fil");
        animalDto.setAge(34);
        animalDto.setType("pestandar");
        animalDto.setPublicId(publicId);

        animalRequestModel = new AnimalRequestModel();
        animalRequestModel.setName("fil");
        animalRequestModel.setAge(34);
        animalRequestModel.setType("pestandar");

        animalResponseModel = new AnimalResponseModel();
        animalResponseModel.setAge(34);
        animalResponseModel.setName("fil");
        animalResponseModel.setType("pestandar");
        animalResponseModel.setPublicId(publicId);

        excepted = new ResponseEntity<>(new MyApiResponse(true, "", new Date(), animalResponseModel), HttpStatus.CREATED);

    }


    @DisplayName("test createAnimal when give valid Request model then return valid response entity")
    @Test
    void testGiveValidAnimalRequestModel_WhenCheckCreateAnimal_ThenReturnValidResponseEntity() throws Exception {
        //give
        when(animalService.createAnimal(any())).thenReturn(excepted);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(animalRequestModel))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);

        //then
        Assertions.assertThat(myApiResponse).isNotNull();
        Assertions.assertThat(myApiResponse.isAction()).isTrue();
        Assertions.assertThat(myApiResponse.getResult()).isNotNull();
        Assertions.assertThat(new ModelMapper().map(myApiResponse.getResult(), AnimalResponseModel.class)).isEqualTo(animalResponseModel);
    }

    @DisplayName("test createAnimal when give invalid AnimalRequestModel then return Exception")
    @Test
    void testGiveInvalidAnimalRequestModel_WhenCheckCreateAnimal_ThenReturnException() throws Exception {
        //give
        animalRequestModel.setName(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(animalRequestModel))).andReturn();
        //when
        String animalResponseAsString = mvcResult.getResponse().getContentAsString();


        //then
        Assertions.assertThat(animalResponseAsString).isEmpty();
    }

    @DisplayName("test getAnimal when give valid publicId then return valid ResponseEntity")
    @Test
    void testGiveValidPublicId_WhenCheckGetAnimal_ThenReturnValidResponseEntity() throws Exception {
        //give
        when(animalService.getAnimal(any())).thenReturn(excepted);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(publicId))).andReturn();
        //when
        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
        //then
        Assertions.assertThat(new ModelMapper().map(myApiResponse.getResult(), AnimalResponseModel.class)).isEqualTo(animalResponseModel);
    }

    @DisplayName("test getAnimal when give inValid publicId then return inValid ResponseEntity")
    @Test
    void testGiveInValidPublicId_WhenCheckGetAnimal_ThenReturnInValidResponseEntity() throws Exception {
        //give
        excepted.getBody().setResult("");
        excepted.getBody().setAction(false);
        excepted.getBody().setMessage("Animal not exist");
        when(animalService.getAnimal(any())).thenReturn(excepted);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(publicId))).andReturn();
        //when
        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
        //then
        Assertions.assertThat(animalResponseAsString).isNotNull();
        Assertions.assertThat(myApiResponse.getResult()).isEqualTo(excepted.getBody().getResult());
        Assertions.assertThat(myApiResponse.getMessage()).isEqualTo("Animal not exist");
        Assertions.assertThat(myApiResponse.isAction()).isFalse();
    }

    @DisplayName("test getAllAnimal then return Valid responseEntity")
    @Test
    void testGiveAny_WhenCheckGetAllAnimal_ThenReturnValidResponseEntity() throws Exception {
        //give
        List<AnimalResponseModel> animalResponseModels = new ArrayList<>();
        animalResponseModels.add(animalResponseModel);
        excepted.getBody().setResult(animalResponseModels);
        when(animalService.getAllAnimal()).thenReturn(excepted);
        excepted.getBody().setResult(animalResponseModels);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(publicId))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
//        System.out.println(myApiResponse.getResult().getClass());
        HashMap<String, String> map = (HashMap<String, String>) ((ArrayList) myApiResponse.getResult()).get(0);
//        System.out.println(((ArrayList) myApiResponse.getResult()).get(0));
//        System.out.println(Stream.of(myApiResponse.getResult()));
//        ((ArrayList) myApiResponse.getResult()).stream().map(a->new ObjectMapper().readValue(a,AnimalResponseModel.class));
//        map.get("age")

//        System.out.println(map.entrySet());
//        String json = new ObjectMapper().writeValueAsString(map);
//        System.out.println(json);
//        ArrayList<AnimalResponseModel> ar = new ArrayList<AnimalResponseModel>(map.values());
        //then
        Assertions.assertThat(myApiResponse.getResult()).isNotNull();
        Assertions.assertThat(((ArrayList<?>) myApiResponse.getResult()).size()).isEqualTo(1);
        Assertions.assertThat(map.entrySet()).isEqualTo(excepted.getBody().getResult());
//        Assertions.assertThat().isEqualTo(animalResponseModels);
        Assertions.assertThat(myApiResponse.isAction()).isTrue();
    }

    @DisplayName("test deleteAnimal when give valid publicId then return valid ResponseEntity")
    @Test
    void testGiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnValidResponseEntity() throws Exception {
        //give
        AnimalDeleteResponseModel animalDeleteResponseModel = new AnimalDeleteResponseModel();
        animalDeleteResponseModel.setPublicId(publicId);
        animalDeleteResponseModel.setStatus("animal with publicId " + publicId + " deleted");
        excepted.getBody().setResult(animalDeleteResponseModel);
        when(animalService.deleteAnimal(any())).thenReturn(excepted);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(publicId))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
        //then
        Assertions.assertThat(animalResponseAsString).isNotNull();
        Assertions.assertThat(myApiResponse.isAction()).isTrue();
        Assertions.assertThat(new ModelMapper().map(myApiResponse.getResult(), AnimalDeleteResponseModel.class)).isEqualTo(animalDeleteResponseModel);
        Assertions.assertThat(myApiResponse.getMessage()).isEmpty();
    }

    @DisplayName("test deleteAnimal when give inValid publicId then return InValid ResponseEntity")
    @Test
    void testGiveInValidPublicId_WhenCheckDeleteAnimal_ThenReturnInValidResponseEntity() throws Exception {
        //give
        excepted.getBody().setResult("");
        excepted.getBody().setAction(false);
        excepted.getBody().setMessage("not delete : animal not exist");

        when(animalService.deleteAnimal(any())).thenReturn(excepted);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(publicId))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);

        //then
        Assertions.assertThat(animalResponseAsString).isNotNull();
        Assertions.assertThat(myApiResponse.getResult()).isEqualTo(excepted.getBody().getResult());
        Assertions.assertThat(myApiResponse.getMessage()).isEqualTo("not delete : animal not exist");
        Assertions.assertThat(myApiResponse.isAction()).isFalse();
    }

    @DisplayName("test updateAnimal when give Valid publicId then return valid ResponseEntity")
    @Test
    void testGiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidResponseEntity() throws Exception {
        //give
        when(animalService.updateAnimal(any(), any())).thenReturn(excepted);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(animalRequestModel))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
        //then
        Assertions.assertThat(animalResponseAsString).isNotNull();
        Assertions.assertThat(myApiResponse.isAction()).isTrue();
        Assertions.assertThat(new ModelMapper().map(myApiResponse.getResult(), AnimalResponseModel.class)).isEqualTo(animalResponseModel);
        Assertions.assertThat(myApiResponse.getMessage()).isEmpty();
    }

    @DisplayName("test updateAnimal when give InValid publicId then return InValid ResponseEntity")
    @Test
    void testGiveInValidPublicId_WhenCheckUpdateAnimal_ThenReturnInValidResponseEntity() throws Exception {
        //give
        excepted.getBody().setMessage("animal with this publicId dont exist");
        excepted.getBody().setResult("");
        excepted.getBody().setAction(false);
        when(animalService.updateAnimal(any(), any())).thenReturn(excepted);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put(path + "/" + publicId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(animalRequestModel))).andReturn();

        String animalResponseAsString = mvcResult.getResponse().getContentAsString();
        MyApiResponse myApiResponse = new ObjectMapper().readValue(animalResponseAsString, MyApiResponse.class);
        //then
        Assertions.assertThat(animalResponseAsString).isNotNull();
        Assertions.assertThat(myApiResponse.isAction()).isFalse();
        Assertions.assertThat(myApiResponse.getResult()).isEqualTo("");
        Assertions.assertThat(myApiResponse.getMessage()).isEqualTo("animal with this publicId dont exist");
    }


}