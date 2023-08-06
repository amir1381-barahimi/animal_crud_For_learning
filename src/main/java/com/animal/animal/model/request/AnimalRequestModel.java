package com.animal.animal.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "animal", description = "animal request Model")
public class AnimalRequestModel {
    @Schema(description = "The name of animal", example = "tiger")
    private String name;

    @Schema(description = "The age of animal", example = "14")
    private int age;

    @Schema(description = "type means that the animal belongs to which category of animals?", example = "Insects")
    private String type;
}
