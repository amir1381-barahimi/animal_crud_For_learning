package com.animal.animal.controller;
import com.animal.animal.model.dto.AnimalDto;
import com.animal.animal.model.request.AnimalRequestModel;
import com.animal.animal.model.response.AnimalDeleteResponseModel;
import com.animal.animal.model.response.AnimalResponseModel;
import com.animal.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/animals")
@Tag(name = "AnimalApis",description = "Animal Api")
public class AnimalController {

    //injection


    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    //get
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping("/{publicId}")
    public AnimalResponseModel getAnimal(@PathVariable String publicId){
        AnimalDto animalDto = animalService.getAnimal(publicId);
        return new ModelMapper().map(animalDto,AnimalResponseModel.class);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @GetMapping
    public List<AnimalResponseModel> getAnimals(){
        List<AnimalDto> animalDtos = animalService.getAllAnimal();
        return animalDtos.stream().map(animalDto -> new ModelMapper().map(animalDto,AnimalResponseModel.class)).toList();
    }


    //post
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public AnimalResponseModel createAnimal(@RequestBody AnimalRequestModel animalRequestModel){

        ModelMapper modelMapper = new ModelMapper();
        AnimalDto animalDto = modelMapper.map(animalRequestModel, AnimalDto.class);
        animalDto = animalService.createAnimal(animalDto);
        return modelMapper.map(animalDto,AnimalResponseModel.class);
    }
    //delete
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @DeleteMapping("/{publicId}")
    @Transactional
    public AnimalDeleteResponseModel deleteAnimal(@PathVariable String publicId){
        animalService.deleteAnimal(publicId);
        return new AnimalDeleteResponseModel(publicId,"animal with publicId "+publicId+" deleted");
    }
    //update

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")
    })
    @PutMapping("/{publicId}")
    public AnimalResponseModel update(@RequestBody AnimalRequestModel animalRequestModel,@PathVariable String publicId){

        ModelMapper modelMapper = new ModelMapper();
        AnimalDto animalDto = modelMapper.map(animalRequestModel,AnimalDto.class);
        animalDto = animalService.updateAnimal(animalDto,publicId);
        return modelMapper.map(animalDto,AnimalResponseModel.class);
    }
}
