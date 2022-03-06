package com.diegoslourenco.users.exceptionHandler;

import org.springframework.dao.EmptyResultDataAccessException;

public class ProfileNotFoundException extends EmptyResultDataAccessException {

    public ProfileNotFoundException(int expectedSize) {
        super(expectedSize);
    }
}
