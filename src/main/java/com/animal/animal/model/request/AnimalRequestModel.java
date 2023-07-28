package com.animal.animal.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalRequestModel {
    private String name;
    private int age;
    private String type;
}
