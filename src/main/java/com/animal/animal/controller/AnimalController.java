package com.animal.animal.controller;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.service.AnimalService;
import com.animal.animal.shared.MyApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/animals")
@Tag(name = "Animals",description = "Animal endpoints")
public class AnimalController {
    Logger logger = LoggerFactory.getLogger(AnimalController.class);

    //injection

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    //get
    @Operation(summary = "getting a animal from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a stored animal with valid publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return Animal exception that not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "animal Exception (AE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    @GetMapping("/{publicId}")
    public ResponseEntity<MyApiResponse> getAnimal(@PathVariable String publicId){
        logger.info("get a animal with publicId : "+publicId);
        return animalService.getAnimal(publicId);
    }

    @Operation(summary = "getting all animals from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all stored animal",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return Animal exception that any animal not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "animal Exception (AE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    @GetMapping
    public ResponseEntity<MyApiResponse> getAnimals(){
        logger.info("get all animals");
        return animalService.getAllAnimal();
    }


    //post
    @Operation(summary = "Adding a new animal into database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return a stored animal with valid publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "animal Exception (AE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    @PostMapping
    public ResponseEntity<MyApiResponse> createAnimal(@RequestBody AnimalRequestModel animalRequestModel){
        logger.info("create a new animal");
        return animalService.createAnimal(animalRequestModel);
    }
    //delete
    @Operation(summary = "deleting a animal from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully has deleted",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return Animal exception that animal not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "animal Exception (AE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    @DeleteMapping("/{publicId}")
    @Transactional
    public ResponseEntity<MyApiResponse> deleteAnimal(@PathVariable String publicId){
        logger.info("delete a animal with publicId : "+publicId);
        return animalService.deleteAnimal(publicId);
    }
    //update

    @Operation(summary = "updating a animal from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully has updated",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Return Animal exception that animal not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "animal Exception (AE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    @PutMapping("/{publicId}")
    public ResponseEntity<MyApiResponse> update(@RequestBody AnimalRequestModel animalRequestModel,@PathVariable String publicId){
        logger.info("update a animal with publicId : "+publicId);
        return animalService.updateAnimal(animalRequestModel,publicId);
    }
}
