package com.animal.animal.repository;
import com.animal.animal.model.entity.AnimalEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;



    //test findByPublicId

    @DisplayName("test finding exist valid entity by  publicId in db")
    @Test
    void testGiveAPublicIdValid_WhenCheckFindByPublicId_ThenReturnSavedEntity() {
        //give

        AnimalEntity animal = new AnimalEntity();
        animal.setPublicId(UUID.randomUUID().toString());
        animal.setAge(13);
        animal.setType("gorbe");
        animal.setName("babr");

        AnimalEntity savedAnimal = animalRepository.save(animal);


        //when
        AnimalEntity animalEntity = animalRepository.findByPublicId(savedAnimal.getPublicId());


        //then
        Assertions.assertThat(animalEntity).isNotNull();
        Assertions.assertThat(animalEntity.getId()).isPositive();
    }

    @DisplayName("test finding not exist valid entity by publicId in db")
    @Test
    void testGiveNotExistPublicId_WhenCheckFindByPublicId_ThenReturnNull() {
        //give
        AnimalEntity animal = new AnimalEntity();
        animal.setPublicId(UUID.randomUUID().toString());
        animal.setAge(13);
        animal.setType("gorbe");
        animal.setName("babr");

        animalRepository.save(animal);

        //when
//
        AnimalEntity animalEntity = animalRepository.findByPublicId(UUID.randomUUID().toString());

        //then

        Assertions.assertThat(animalEntity).isNull();
    }


    //test findAll

    @DisplayName("test finding all valid entity in db")
    @Test
    void testGiveTwoValidEntity_WhenCheckFindAll_ThenReturnAllEntity() {
        //give
        AnimalEntity animal1 = new AnimalEntity();
        animal1.setPublicId(UUID.randomUUID().toString());
        animal1.setAge(13);
        animal1.setType("gorbe");
        animal1.setName("babr");
        animalRepository.save(animal1);

        AnimalEntity animal2 = new AnimalEntity();
        animal2.setPublicId(UUID.randomUUID().toString());
        animal2.setAge(14);
        animal2.setType("gorbe");
        animal2.setName("shir");

        animalRepository.save(animal2);

        List<AnimalEntity> animalEntities2 = new ArrayList<>();
        animalEntities2.add(animal1);
        animalEntities2.add(animal2);


        //when

        List<AnimalEntity> animalEntities = animalRepository.findAll();

        //then

        Assertions.assertThat(animalEntities).isNotNull();
        Assertions.assertThat(animalEntities).isEqualTo(animalEntities2);
    }


    @DisplayName("test findAll when not animal Entity Exist")
    @Test
    void testGiveAnyEntity_WhenCheckFindAll_ThenReturnNull() {
        //give

        //when
        ArrayList<AnimalEntity> animalEntities = (ArrayList<AnimalEntity>) animalRepository.findAll();
        //then
        Assertions.assertThat(animalEntities).isEmpty();

    }



    //test save

    @DisplayName("test saving invalid entity with throw exception in db")
    @Test
    void testGiveInvalidEntity_WhenCheckSave_ThenReturnInValidEntity() {
        //give

        AnimalEntity animal2 = new AnimalEntity();
        animal2.setPublicId(UUID.randomUUID().toString());
        animal2.setAge(13);
        animal2.setType("sag");




        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(DataIntegrityViolationException.class,() -> {
            //when
            animalRepository.save(animal2);
        });

    }


    @DisplayName("test save valid entity then assert to return equals value")
    @Test
    void testGiveAValidEntity_WhenCheckSave_ThenReturnValidValue() {
        //give
        AnimalEntity animal1 = new AnimalEntity();
        animal1.setPublicId(UUID.randomUUID().toString());
        animal1.setAge(13);
        animal1.setType("gorbe");
        animal1.setName("babr");

        //when
        AnimalEntity savedAnimal = animalRepository.save(animal1);
        //then
        Assertions.assertThat(savedAnimal).isEqualTo(animal1);
    }


    //test delete

    @DisplayName("test delete valid entity with publicId")
    @Test
    void testGiveAValidEntity_WhenCheckDeleteByPublicId_ThenReturnEntityId() {
        //give
        AnimalEntity animal1 = new AnimalEntity();
        animal1.setPublicId(UUID.randomUUID().toString());
        animal1.setAge(13);
        animal1.setType("gorbe");
        animal1.setName("babr");
        AnimalEntity savedAnimal = animalRepository.save(animal1);


        //when
        int id = animalRepository.deleteByPublicId(savedAnimal.getPublicId());
        AnimalEntity animalEntity = animalRepository.findByPublicId(savedAnimal.getPublicId());
        //then
        Assertions.assertThat(id).isPositive();
        Assertions.assertThat(animalEntity).isNull();
    }

    @DisplayName("test delete with publicId that not exist")
    @Test
    void testGiveNotExistPublicId_WhenCheckDelete_ThenReturnZero() {
        //give
        String publicId = String.valueOf(UUID.randomUUID());

        //when
        int id = animalRepository.deleteByPublicId(publicId);

        //then
        Assertions.assertThat(id).isZero();
    }

}