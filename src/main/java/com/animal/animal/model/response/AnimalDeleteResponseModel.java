package com.animal.animal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalDeleteResponseModel {
    private String publicId;
    private String status;
}
