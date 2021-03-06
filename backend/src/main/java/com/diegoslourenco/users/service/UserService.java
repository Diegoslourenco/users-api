package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.UserBuilder;
import com.diegoslourenco.users.builder.UserDTOBuilder;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.exceptionHandler.EmailNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.NameNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.UserNotFoundException;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import com.diegoslourenco.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOBuilder userDTOBuilder;
    private final UserBuilder userBuilder;
    private final ProfileService profileService;

    public UserService(UserRepository userRepository, UserDTOBuilder userDTOBuilder, UserBuilder userBuilder, ProfileService profileService) {
        this.userRepository = userRepository;
        this.userDTOBuilder = userDTOBuilder;
        this.userBuilder = userBuilder;
        this.profileService = profileService;
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
            throw new UserNotFoundException(1);
        }

        return userSaved.get();
    }

    public Long save(UserDTO dto) {

        Profile profile = profileService.getById(dto.getProfileId());

        this.checkUniqueName(dto);
        this.checkUniqueEmail(dto);

        User user = userBuilder.build(dto, profile);

        User userSaved = userRepository.save(user);

        return userSaved.getId();
    }

    public UserDTO update(Long id, UserDTO dto) {

        User userSaved = this.getById(id);
        Profile profile = profileService.getById(dto.getProfileId());

        if (!userSaved.getName().equals(dto.getName())) {
            this.checkUniqueName(dto);
        }

        if (!userSaved.getEmail().equals(dto.getEmail())) {
            this.checkUniqueEmail(dto);
        }

        userSaved.setProfile(profile);
        userSaved.setName(dto.getName());
        userSaved.setEmail(dto.getEmail());

        User userUpdated = userRepository.save(userSaved);

        return userDTOBuilder.build(userUpdated);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void checkUniqueName(UserDTO dto) {

        Optional<User> optionalUser = userRepository.getByName(dto.getName());

        if (optionalUser.isPresent()) {
            throw new NameNotUniqueException();
        }
    }

    private void checkUniqueEmail(UserDTO dto) {

        Optional<User> optionalUser = userRepository.getByEmail(dto.getEmail());

        if (optionalUser.isPresent()) {
            throw new EmailNotUniqueException();
        }
    }

}
