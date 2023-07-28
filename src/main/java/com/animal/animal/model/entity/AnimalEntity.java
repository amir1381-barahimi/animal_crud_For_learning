package com.animal.animal.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AnimalEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String publicId;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50)
    private int age;

    @Column(length = 50)
    private String type;
}
