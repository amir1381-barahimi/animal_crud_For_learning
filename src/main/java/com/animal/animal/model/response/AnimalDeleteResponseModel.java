package com.animal.animal.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "animal", description = "animal delete response Model")
public class AnimalDeleteResponseModel {
    @Schema(description = "The unique id that is random String and is public", example = "ksdf973bjvkjbwiucbalgi2029ghvjvkfj")
    private String publicId;

    @Schema(description = "status of delete request", example = "not found")
    private String status;
}
