package com.animal.animal.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "animal", description = "animal response Model")
public class AnimalResponseModel {
    @Schema(description = "The unique id that is random String and is public", example = "ksdf973bjvkjbwiucbalgi2029ghvjvkfj")
    private String publicId;

    @Schema(description = "The name of animal", example = "tiger")
    private String name;

    @Schema(description = "The age of animal", example = "15")
    private int age;

    @Schema(description = "type means that the animal belongs to which category of animals?", example = "Insects")
    private String type;
}
