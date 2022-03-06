package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.UserDTOBuilder;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import com.diegoslourenco.users.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOBuilder userDTOBuilder;

    public UserService(UserRepository userRepository, UserDTOBuilder userDTOBuilder) {
        this.userRepository = userRepository;
        this.userDTOBuilder = userDTOBuilder;
    }

    public List<UserDTO> getAll() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return new ArrayList<>();
        }

        return userDTOBuilder.buildList(users);
    }

    public UserDTO getOne(Long id) {

        User userSaved = this.getById(id);

        return userDTOBuilder.build(userSaved);
    }

    private User getById(Long id) {

        Optional<User> userSaved = userRepository.findById(id);

        if (!userSaved.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        return userSaved.get();
    }
}
