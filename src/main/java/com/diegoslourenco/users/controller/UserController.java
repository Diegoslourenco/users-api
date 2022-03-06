package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.ErrorDTO;
import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Return a list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class))) })
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Return a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class) )})})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getOne(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class) )})})
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);
    }
}
