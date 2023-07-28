package com.animal.animal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalResponseModel {
    private String publicId;
    private String name;
    private int age;
    private String type;
}
