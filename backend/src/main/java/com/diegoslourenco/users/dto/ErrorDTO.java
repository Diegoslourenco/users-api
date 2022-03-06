package com.diegoslourenco.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ErrorDTO --- represents a error that occurs during a request.
 * @author    Diego da Silva Lourenco
 */

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private String description;
}
