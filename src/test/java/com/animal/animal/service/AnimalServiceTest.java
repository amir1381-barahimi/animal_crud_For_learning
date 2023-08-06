package com.animal.animal.service;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.service.impl.AnimalServiceImpl;
import com.animal.animal.config.exception.AnimalException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimalServiceTest {

    @Autowired
    private AnimalServiceImpl animalService;


    //test getALlAnimal

    @DisplayName("test getAllAnimal when is empty then return exception")
    @Test
    @Order(1)
    void testGiveAnyAnimalDto_WhenCheckGetAllAnimal_ThenReturnException() {
        //give
        //any animals give
        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
            //when
            animalService.getAllAnimal();
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("any user not exist");
    }

    @DisplayName("test getAllAnimal when give two valid animals then return two valid animals")
    @Test
    void testGiveTwoValidAnimalsDto_WhenCheckGetAllAnimal_ThenReturnTwoValidAnimals() {
        //give

        AnimalDto animalDto1 = new AnimalDto();
        animalDto1.setName("babr");
        animalDto1.setType("gorbesan");
        animalDto1.setAge(55);

        AnimalDto createdAnimal1 = animalService.createAnimal(animalDto1);

        AnimalDto animalDto2 = new AnimalDto();
        animalDto2.setName("fil");
        animalDto2.setType("pestandaran");
        animalDto2.setAge(76);

        AnimalDto createdAnimal2 = animalService.createAnimal(animalDto2);

        ArrayList<AnimalDto> animalDtos = new ArrayList<>();
        animalDtos.add(createdAnimal1);
        animalDtos.add(createdAnimal2);
        //when

        List<AnimalDto> animalDtosGet = animalService.getAllAnimal();

        //then

        org.assertj.core.api.Assertions.assertThat(animalDtosGet).isEqualTo(animalDtos);
    }



    //test create Animal

    @DisplayName("test create animal when give valid animal dto then assert valid animal dto return")
    @Test
    void testGiveAValidAnimalADto_WhenCheckCreateAnimal_ThenReturnValidAnimalDto() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);
        //when

        AnimalDto savedAnimal = animalService.createAnimal(animalDto);
        animalDto.setPublicId(savedAnimal.getPublicId());
        //then
        org.assertj.core.api.Assertions.assertThat(savedAnimal.getPublicId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(savedAnimal).isEqualTo(animalDto);

    }

    @DisplayName("test create animal when give invalid animal dto with null name")
    @Test
    void testGiveInvalidAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setType("gorbesan");
        animalDto.setAge(55);


        //then

        Assertions.assertThrowsExactly(DataIntegrityViolationException.class,()->{
            //when
            animalService.createAnimal(animalDto);
        });
    }

    @DisplayName("test create animal when give null animal dto")
    @Test
    void testGiveNullAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
        //give
        //then
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->{
            //when
            animalService.createAnimal(null);
        });
    }

    //test getAnimal
    @DisplayName("test get animal when give valid publicId then return valid animal dto")
    @Test
    void testGiveAValidAnimalPublicId_WhenCheckGetAnimal_ThenReturnValidAnimalDto() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
        //when

        AnimalDto getAnimal = animalService.getAnimal(createdAnimal.getPublicId());

        //then

        org.assertj.core.api.Assertions.assertThat(getAnimal).isEqualTo(createdAnimal);
    }

    @DisplayName("test get Animal when give invalid publicId then return exception")
    @Test
    void testGiveInvalidPublicId_WhenCheckGetAnimal_ThenReturnException() {
        //give

        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        animalService.createAnimal(animalDto);


        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
            //when
            animalService.getAnimal(UUID.randomUUID().toString());
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
    }

    @DisplayName("test get Animal when give null publicId then return exception")
    @Test
    void testGiveNullPublicId_WhenCheckGetAnimal_ThenReturnException() {
        //give

        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        animalService.createAnimal(animalDto);


        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
            //when
            animalService.getAnimal(null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
    }


    //test deleteAnimal

    @Transactional
    @DisplayName("test deleteAnimal when give valid publicId then return positive id")
    @Test
    void testGiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnPositiveId() {
        //give

        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        AnimalDto createdAnimal1 = animalService.createAnimal(animalDto);

        //when
        int id = animalService.deleteAnimal(createdAnimal1.getPublicId());

        //then
        org.assertj.core.api.Assertions.assertThat(id).isPositive();
    }

    @DisplayName("test deleteAnimal when give invalid publicId then return Exception")
    @Test
    void testGiveInvalidPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        animalService.createAnimal(animalDto);
        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.deleteAnimal(UUID.randomUUID().toString());
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
    }

    @DisplayName("test deleteAnimal when give null publicId then return Exception")
    @Test
    void testGiveNullPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
        //give
            //give_null


        Exception exception = Assertions.assertThrowsExactly(AnimalException.class, () -> {
            //when
            animalService.deleteAnimal(null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
    }

    //test updateAnimal

    @DisplayName("test updateAnimal when give valid publicId then return valid AnimalDto")
    @Test
    void testGiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidAnimalDto() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        AnimalDto createdAnimal = animalService.createAnimal(animalDto);

        AnimalDto animalUpdate = new AnimalDto();
        animalUpdate.setName("fil");
        animalUpdate.setAge(55);
        animalUpdate.setType("pestandar");
        animalUpdate.setPublicId(createdAnimal.getPublicId());

        //when

        AnimalDto updatedAnimal = animalService.updateAnimal(animalUpdate,createdAnimal.getPublicId());
        //then

        org.assertj.core.api.Assertions.assertThat(updatedAnimal).isEqualTo(animalUpdate);
    }

    @DisplayName("test updateAnimal when give null animalDto then return Exception")
    @Test
    void testGiveNullAnimalDto_WhenUpdateAnimal_ThenReturnException() {
        //give
            //give_null

        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        AnimalDto createdAnimal = animalService.createAnimal(animalDto);


        //then
        Assertions.assertThrowsExactly(NullPointerException.class,() ->{
            //when
            animalService.updateAnimal(null,createdAnimal.getPublicId());
        });
    }

    @DisplayName("test updateAnimal when give invalid publicId then return exception")
    @Test
    void testGiveInvalidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("babr");
        animalDto.setType("gorbesan");
        animalDto.setAge(55);

        AnimalDto createdAnimal = animalService.createAnimal(animalDto);

        AnimalDto animalUpdate = new AnimalDto();
        animalUpdate.setName("fil");
        animalUpdate.setAge(55);
        animalUpdate.setType("pestandar");
        animalUpdate.setPublicId(createdAnimal.getPublicId());

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
            //when
            animalService.updateAnimal(animalUpdate,UUID.randomUUID().toString());
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");
    }
}