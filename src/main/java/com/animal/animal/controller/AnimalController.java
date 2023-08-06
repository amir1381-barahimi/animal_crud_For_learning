package com.animal.animal.controller;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/animals")
@Tag(name = "AnimalApis",description = "Animal Api")
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
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping("/{publicId}")
    public AnimalResponseModel getAnimal(@PathVariable String publicId){
        logger.info("get a animal with publicId : "+publicId);
        AnimalDto animalDto = animalService.getAnimal(publicId);
        return new ModelMapper().map(animalDto,AnimalResponseModel.class);
    }

    @Operation(summary = "getting all animals from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping
    public List<AnimalResponseModel> getAnimals(){
        logger.info("get all animals");
        List<AnimalDto> animalDtos = animalService.getAllAnimal();
        return animalDtos.stream().map(animalDto -> new ModelMapper().map(animalDto,AnimalResponseModel.class)).toList();
    }


    //post
    @Operation(summary = "Adding a new animal into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public AnimalResponseModel createAnimal(@RequestBody AnimalRequestModel animalRequestModel){
        logger.info("create a new animal");
        ModelMapper modelMapper = new ModelMapper();
        AnimalDto animalDto = modelMapper.map(animalRequestModel, AnimalDto.class);
        animalDto = animalService.createAnimal(animalDto);
        return modelMapper.map(animalDto,AnimalResponseModel.class);
    }
    //delete
    @Operation(summary = "deleting a animal from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @DeleteMapping("/{publicId}")
    @Transactional
    public AnimalDeleteResponseModel deleteAnimal(@PathVariable String publicId){
        logger.info("delete a animal with publicId : "+publicId);
        animalService.deleteAnimal(publicId);
        return new AnimalDeleteResponseModel(publicId,"animal with publicId "+publicId+" deleted");
    }
    //update

    @Operation(summary = "updating a animal from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @PutMapping("/{publicId}")
    public AnimalResponseModel update(@RequestBody AnimalRequestModel animalRequestModel,@PathVariable String publicId){
        logger.info("update a animal with publicId : "+publicId);
        ModelMapper modelMapper = new ModelMapper();
        AnimalDto animalDto = modelMapper.map(animalRequestModel,AnimalDto.class);
        animalDto = animalService.updateAnimal(animalDto,publicId);
        return modelMapper.map(animalDto,AnimalResponseModel.class);
    }
}
