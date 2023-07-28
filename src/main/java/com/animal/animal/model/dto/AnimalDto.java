package com.animal.animal.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalDto {
    private String publicId;
    private String name;
    private int age;
    private String type;
}
