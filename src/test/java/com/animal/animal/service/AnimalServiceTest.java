package com.animal.animal.service;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

//    @Autowired
//    private AnimalServiceImpl animalService;

    @Mock
    AnimalRepository animalRepository;

    @InjectMocks
    AnimalServiceImpl animalService;

    @Mock
    StringRandomGenerator publicIdGenerator;

    AnimalDto animalDto;
    AnimalEntity animalEntity;
    String publicId;






    //test getALlAnimal

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
    }

//    @DisplayName("test getAllAnimal when is empty then return exception")
//    @Test
//    @Order(1)
//    void testGiveAnyAnimalDto_WhenCheckGetAllAnimal_ThenReturnException() {
//        //give
//        //any animals give
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
//            //when
//            animalService.getAllAnimal();
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("any user not exist");
//    }
//
//    @DisplayName("test getAllAnimal when give two valid animals then return two valid animals")
//    @Test
//    void testGiveTwoValidAnimalsDto_WhenCheckGetAllAnimal_ThenReturnTwoValidAnimals() {
//        //give
//
//        AnimalDto animalDto1 = new AnimalDto();
//        animalDto1.setName("babr");
//        animalDto1.setType("gorbesan");
//        animalDto1.setAge(55);
//
//        AnimalDto createdAnimal1 = animalService.createAnimal(animalDto1);
//
//        AnimalDto animalDto2 = new AnimalDto();
//        animalDto2.setName("fil");
//        animalDto2.setType("pestandaran");
//        animalDto2.setAge(76);
//
//        AnimalDto createdAnimal2 = animalService.createAnimal(animalDto2);
//
//        ArrayList<AnimalDto> animalDtos = new ArrayList<>();
//        animalDtos.add(createdAnimal1);
//        animalDtos.add(createdAnimal2);
//        //when
//
//        List<AnimalDto> animalDtosGet = animalService.getAllAnimal();
//
//        //then
//
//        org.assertj.core.api.Assertions.assertThat(animalDtosGet).isEqualTo(animalDtos);
//    }
//
//
//
//    //test create Animal
//
//    @DisplayName("test create animal when give valid animal dto then assert valid animal dto return")
//    @Test
//    void testGiveAValidAnimalADto_WhenCheckCreateAnimal_ThenReturnValidAnimalDto() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//        //when
//
//        AnimalDto savedAnimal = animalService.createAnimal(animalDto);
//        animalDto.setPublicId(savedAnimal.getPublicId());
//        //then
//        org.assertj.core.api.Assertions.assertThat(savedAnimal.getPublicId()).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(savedAnimal).isEqualTo(animalDto);
//
//    }
//
//    @DisplayName("test create animal when give invalid animal dto with null name")
//    @Test
//    void testGiveInvalidAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//
//        //then
//
//        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->{
//            //when
//            animalService.createAnimal(animalDto);
//        });
//    }
//
//    @DisplayName("test create animal when give null animal dto")
//    @Test
//    void testGiveNullAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
//        //give
//        //then
//        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->{
//            //when
//            animalService.createAnimal(null);
//        });
//    }
//
//    //test getAnimal
//    @DisplayName("test get animal when give valid publicId then return valid animal dto")
//    @Test
//    void testGiveAValidAnimalPublicId_WhenCheckGetAnimal_ThenReturnValidAnimalDto() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
//        //when
//
//        AnimalDto getAnimal = animalService.getAnimal(createdAnimal.getPublicId());
//
//        //then
//
//        org.assertj.core.api.Assertions.assertThat(getAnimal).isEqualTo(createdAnimal);
//    }
//
//    @DisplayName("test get Animal when give invalid publicId then return exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckGetAnimal_ThenReturnException() {
//        //give
//
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        animalService.createAnimal(animalDto);
//
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
//            //when
//            animalService.getAnimal(UUID.randomUUID().toString());
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//    }
//
//    @DisplayName("test get Animal when give null publicId then return exception")
//    @Test
//    void testGiveNullPublicId_WhenCheckGetAnimal_ThenReturnException() {
//        //give
//
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        animalService.createAnimal(animalDto);
//
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
//            //when
//            animalService.getAnimal(null);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//    }
//
//
//    //test deleteAnimal
//
//    @Transactional
//    @DisplayName("test deleteAnimal when give valid publicId then return positive id")
//    @Test
//    void testGiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnPositiveId() {
//        //give
//
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        AnimalDto createdAnimal1 = animalService.createAnimal(animalDto);
//
//        //when
//        int id = animalService.deleteAnimal(createdAnimal1.getPublicId());
//
//        //then
//        org.assertj.core.api.Assertions.assertThat(id).isPositive();
//    }
//
//    @DisplayName("test deleteAnimal when give invalid publicId then return Exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        animalService.createAnimal(animalDto);
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.deleteAnimal(UUID.randomUUID().toString());
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
//    }
//
//    @DisplayName("test deleteAnimal when give null publicId then return Exception")
//    @Test
//    void testGiveNullPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
//        //give
//            //give_null
//
//
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class, () -> {
//            //when
//            animalService.deleteAnimal(null);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
//    }
//
//    //test updateAnimal
//
//    @DisplayName("test updateAnimal when give valid publicId then return valid AnimalDto")
//    @Test
//    void testGiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidAnimalDto() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
//
//        AnimalDto animalUpdate = new AnimalDto();
//        animalUpdate.setName("fil");
//        animalUpdate.setAge(55);
//        animalUpdate.setType("pestandar");
//        animalUpdate.setPublicId(createdAnimal.getPublicId());
//
//        //when
//
//        AnimalDto updatedAnimal = animalService.updateAnimal(animalUpdate,createdAnimal.getPublicId());
//        //then
//
//        org.assertj.core.api.Assertions.assertThat(updatedAnimal).isEqualTo(animalUpdate);
//    }
//
//    @DisplayName("test updateAnimal when give null animalDto then return Exception")
//    @Test
//    void testGiveNullAnimalDto_WhenUpdateAnimal_ThenReturnException() {
//        //give
//            //give_null
//
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
//
//
//        //then
//        Assertions.assertThrowsExactly(NullPointerException.class,() ->{
//            //when
//            animalService.updateAnimal(null,createdAnimal.getPublicId());
//        });
//    }
//
//    @DisplayName("test updateAnimal when give invalid publicId then return exception")
//    @Test
//    void testGiveInvalidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
//        //give
//        AnimalDto animalDto = new AnimalDto();
//        animalDto.setName("babr");
//        animalDto.setType("gorbesan");
//        animalDto.setAge(55);
//
//        AnimalDto createdAnimal = animalService.createAnimal(animalDto);
//
//        AnimalDto animalUpdate = new AnimalDto();
//        animalUpdate.setName("fil");
//        animalUpdate.setAge(55);
//        animalUpdate.setType("pestandar");
//        animalUpdate.setPublicId(createdAnimal.getPublicId());
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,() ->{
//            //when
//            animalService.updateAnimal(animalUpdate,UUID.randomUUID().toString());
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");
//    }


    //______________________________________________________________________________________________________________

//    @DisplayName("kk")
//    @Test
//    void testGiven_When_Then() {
//        //give
//        String publicId = UUID.randomUUID().toString();
//
//        AnimalEntity animalEntity = new AnimalEntity();
//        animalEntity.setAge(3);
//        animalEntity.setName("in");
//        animalEntity.setType("gf");
//        animalEntity.setPublicId(publicId);
//        animalEntity.setId(0);
//
//        AnimalDto animalDto = new ModelMapper().map(animalEntity,AnimalDto.class);
//        animalDto.setPublicId(null);
//
//        //when
//
//        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
//        when(animalRepository.save(animalEntity)).thenReturn(animalEntity);
//        AnimalDto savedDto = animalService.createAnimal(animalDto);
//
//        //then
//        org.assertj.core.api.Assertions.assertThat(savedDto)
//                .isEqualTo(new ModelMapper().map(animalEntity,AnimalDto.class));
//
//    }

    @DisplayName("test create animal when give valid animal dto then return valid animal dto")
    @Test
    void test_GiveValidAnimalDto_WhenCheckCreateAnimal_ThenReturnValidAnimalDto() {
        //give
        when(animalRepository.save(any())).thenReturn(animalEntity);
        animalEntity.setId(0);
        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
        //when

        AnimalDto savedAnimalDto = animalService.createAnimal(animalDto);
        //then
        org.assertj.core.api.Assertions.assertThat(savedAnimalDto).isEqualTo(animalDto);

    }

    @DisplayName("test createAnimal when give invalid animal dto then return exception")
    @Test
    void testGiveInvalidAnimalDto_WhenCheckCreateAnimal_ThenReturnAnimalException() {
        //give
        when(animalRepository.save(any())).thenReturn(new ClassCastException());
        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
        animalDto.setName(null);

        //then
        Assertions.assertThrows(ClassCastException.class,()->{
            //when
            animalService.createAnimal(animalDto);
        });

    }

    @DisplayName("test GetAnimal when give Valid exist publicId then return Valid animal dto")
    @Test
    void test_GiveNullAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
        animalEntity.setId(0);
        animalEntity.setPublicId(publicId);
       animalDto.setPublicId(publicId);

        //when

        AnimalDto savedAnimalDto = animalService.getAnimal(publicId);
        //then
        org.assertj.core.api.Assertions.assertThat(savedAnimalDto).isEqualTo(animalDto);
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
        //when
        int id = animalService.deleteAnimal(publicId);

        //then
        org.assertj.core.api.Assertions.assertThat(id).isPositive().isEqualTo(10);
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
    void test_GiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidAnimalDto() {
        //give
        AnimalDto animal = new AnimalDto();
        animal.setName("zarafe");
        animal.setAge(24);
        animal.setType("pestandar");

        animalEntity.setPublicId(publicId);
        animalEntity.setId(1);

        AnimalEntity animal1 = new ModelMapper().map(animal,AnimalEntity.class);
        animal1.setId(animalEntity.getId());
        animal1.setPublicId(animalEntity.getPublicId());

        animal.setPublicId(animalEntity.getPublicId());

        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
        when(animalRepository.save(any())).thenReturn(animal1);


        //when

        AnimalDto updatedAnimal = animalService.updateAnimal(animal,publicId);
        //then

        org.assertj.core.api.Assertions.assertThat(updatedAnimal).isEqualTo(animal);
    }

    @DisplayName("test updateAnimal when give Invalid publicId then return Exception")
    @Test
    void test_GiveInvalidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give
        when(animalRepository.findByPublicId(any())).thenReturn(null);

        //then
        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
            //when
            animalService.updateAnimal(animalDto,publicId);
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
            animalService.updateAnimal(animalDto,null);
        });
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");

    }

    @DisplayName("test updateAnimal when give null animal dto with valid publicId then return Exception")
    @Test
    void test_GiveNullAnimalDtoWithValidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
        //give

        animalEntity.setPublicId(publicId);
        animalEntity.setId(1);

        animalDto.setPublicId(publicId);

        when(animalRepository.findByPublicId(publicId)).thenReturn(animalEntity);

        //then
        Assertions.assertThrowsExactly(NullPointerException.class,()->{
            //when
            animalService.updateAnimal(null,publicId);
        });
    }
}

