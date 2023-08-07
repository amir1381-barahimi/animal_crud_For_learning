//package com.animal.animal.service;
//import com.animal.animal.util.generator.StringRandomGenerator;
//import com.animal.animal.model.dto.AnimalDto;
//import com.animal.animal.service.impl.AnimalServiceImpl;
//import com.animal.animal.config.exception.AnimalException;
//import com.animal.animal.model.entity.AnimalEntity;
//import com.animal.animal.repository.AnimalRepository;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(MockitoExtension.class)
//class AnimalServiceTest {
//
//    @Mock
//    AnimalRepository animalRepository;
//
//    @InjectMocks
//    AnimalServiceImpl animalService;
//
//    @Mock
//    StringRandomGenerator publicIdGenerator;
//
//    AnimalDto animalDto;
//    AnimalEntity animalEntity;
//    String publicId;
//
//
//    @BeforeEach
//    void init(){
//        animalEntity = new AnimalEntity();
//        animalEntity.setName("fil");
//        animalEntity.setAge(34);
//        animalEntity.setType("pestandar");
//
//        animalDto = new AnimalDto();
//        animalDto.setName("fil");
//        animalDto.setAge(34);
//        animalDto.setType("pestandar");
//
//         publicId = UUID.randomUUID().toString();
//    }
//
//
//    @DisplayName("test create animal when give valid animal dto then return valid animal dto")
//    @Test
//    void test_GiveValidAnimalDto_WhenCheckCreateAnimal_ThenReturnValidAnimalDto() {
//        //give
//        when(animalRepository.save(any())).thenReturn(animalEntity);
//        animalEntity.setId(0);
//        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
//        //when
//
//        AnimalDto savedAnimalDto = animalService.createAnimal(animalDto);
//        //then
//        org.assertj.core.api.Assertions.assertThat(savedAnimalDto).isEqualTo(animalDto);
//
//    }
//
//    @DisplayName("test createAnimal when give invalid animal dto then return exception")
//    @Test
//    void testGiveInvalidAnimalDto_WhenCheckCreateAnimal_ThenReturnAnimalException() {
//        //give
//        when(animalRepository.save(any())).thenReturn(new ClassCastException());
//        when(publicIdGenerator.publicIdGenerator()).thenReturn(publicId);
//        animalDto.setName(null);
//
//        //then
//        Assertions.assertThrows(ClassCastException.class,()->{
//            //when
//            animalService.createAnimal(animalDto);
//        });
//
//    }
//
//    @DisplayName("test GetAnimal when give Valid exist publicId then return Valid animal dto")
//    @Test
//    void test_GiveNullAnimalDto_WhenCheckCreateAnimal_ThenReturnException() {
//        //give
//        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
//        animalEntity.setId(0);
//        animalEntity.setPublicId(publicId);
//       animalDto.setPublicId(publicId);
//
//        //when
//
//        AnimalDto savedAnimalDto = animalService.getAnimal(publicId);
//        //then
//        org.assertj.core.api.Assertions.assertThat(savedAnimalDto).isEqualTo(animalDto);
//    }
//
//    @DisplayName("test getAnimal when give not exist publicId then return Animal Exception")
//    @Test
//    void test_GiveNotExistPublicId_WhenCheckGetAnimal_ThenAnimalException() {
//        //give
//        when(animalRepository.findByPublicId(any())).thenReturn(null);
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.getAnimal(publicId);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//    }
//
//    @DisplayName("test getAnimal when give null publicId then return exception")
//    @Test
//    void test_GiveNullPublicId_WhenCheckGetAnimal_ThenReturnException() {
//        //give
//        when(animalRepository.findByPublicId(any())).thenReturn(null);
//
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.getAnimal(null);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("Animal not exist");
//
//    }
//
//    @DisplayName("test deleteAnimal when give valid publicId then return id")
//    @Test
//    void test_GiveValidPublicId_WhenCheckDeleteAnimal_ThenReturnValidId() {
//        //give
//        when(animalRepository.deleteByPublicId(any())).thenReturn(10);
//        //when
//        int id = animalService.deleteAnimal(publicId);
//
//        //then
//        org.assertj.core.api.Assertions.assertThat(id).isPositive().isEqualTo(10);
//    }
//
//    @DisplayName("test deleteAnimal when give null publicId then return exception")
//    @Test
//    void test_GiveNullPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
//        //give
//
//        when(animalRepository.deleteByPublicId(any())).thenReturn(0);
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.deleteAnimal(null);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
//    }
//
//    @DisplayName("test deleteAnimal when give invalid publicId then return exception")
//    @Test
//    void test_GiveInvalidPublicId_WhenCheckDeleteAnimal_ThenReturnException() {
//        //give
//
//        when(animalRepository.deleteByPublicId(any())).thenReturn(0);
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.deleteAnimal(publicId);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("not delete : animal not exist");
//    }
//
//    @DisplayName("test updateAnimal when give Valid PublicId then return Valid Animal Dto")
//    @Test
//    void test_GiveValidPublicId_WhenCheckUpdateAnimal_ThenReturnValidAnimalDto() {
//        //give
//        AnimalDto animal = new AnimalDto();
//        animal.setName("zarafe");
//        animal.setAge(24);
//        animal.setType("pestandar");
//
//        animalEntity.setPublicId(publicId);
//        animalEntity.setId(1);
//
//        AnimalEntity animal1 = new ModelMapper().map(animal,AnimalEntity.class);
//        animal1.setId(animalEntity.getId());
//        animal1.setPublicId(animalEntity.getPublicId());
//
//        animal.setPublicId(animalEntity.getPublicId());
//
//        when(animalRepository.findByPublicId(any())).thenReturn(animalEntity);
//        when(animalRepository.save(any())).thenReturn(animal1);
//
//
//        //when
//
//        AnimalDto updatedAnimal = animalService.updateAnimal(animal,publicId);
//        //then
//
//        org.assertj.core.api.Assertions.assertThat(updatedAnimal).isEqualTo(animal);
//    }
//
//    @DisplayName("test updateAnimal when give Invalid publicId then return Exception")
//    @Test
//    void test_GiveInvalidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
//        //give
//        when(animalRepository.findByPublicId(any())).thenReturn(null);
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.updateAnimal(animalDto,publicId);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");
//
//    }
//
//    @DisplayName("test updateAnimal when give null publicId then return Exception")
//    @Test
//    void test_GiveNullPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
//        //give
//        when(animalRepository.findByPublicId(any())).thenReturn(null);
//
//        //then
//        Exception exception = Assertions.assertThrowsExactly(AnimalException.class,()->{
//            //when
//            animalService.updateAnimal(animalDto,null);
//        });
//        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).isEqualTo("animal with this publicId dont exist");
//
//    }
//
//    @DisplayName("test updateAnimal when give null animal dto with valid publicId then return Exception")
//    @Test
//    void test_GiveNullAnimalDtoWithValidPublicId_WhenCheckUpdateAnimal_ThenReturnException() {
//        //give
//
//        animalEntity.setPublicId(publicId);
//        animalEntity.setId(1);
//
//        animalDto.setPublicId(publicId);
//
//        when(animalRepository.findByPublicId(publicId)).thenReturn(animalEntity);
//
//        //then
//        Assertions.assertThrowsExactly(NullPointerException.class,()->{
//            //when
//            animalService.updateAnimal(null,publicId);
//        });
//    }
//}
//
