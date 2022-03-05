package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Return a list of profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of profiles",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProfileDTO.class))) })
    })
    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return new ResponseEntity<>(profileService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Return a profile by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(profileService.getById(id), HttpStatus.OK);
    }
}
