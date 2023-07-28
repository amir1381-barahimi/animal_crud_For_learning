package com.animal.animal.repository;
import com.animal.animal.model.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity,Long> {
    AnimalEntity findByPublicId(String publicId);
    List<AnimalEntity> findAll();
    int deleteByPublicId(String publicId);

}
