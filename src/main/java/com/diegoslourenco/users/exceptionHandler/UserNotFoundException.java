package com.diegoslourenco.users.exceptionHandler;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserNotFoundException extends EmptyResultDataAccessException {

    public UserNotFoundException(int expectedSize) {
        super(expectedSize);
    }
}
