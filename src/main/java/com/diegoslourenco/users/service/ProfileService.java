package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.ProfileBuilder;
import com.diegoslourenco.users.builder.ProfileDTOBuilder;
import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.exceptionHandler.NameNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.ProfileHasUsersException;
import com.diegoslourenco.users.exceptionHandler.ProfileNotFoundException;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import com.diegoslourenco.users.repository.ProfileRepository;
import com.diegoslourenco.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileDTOBuilder profileDTOBuilder;
    private final ProfileBuilder profileBuilder;
    private final UserRepository userRepository;


    public ProfileService(ProfileRepository profileRepository, ProfileDTOBuilder profileDTOBuilder, ProfileBuilder profileBuilder, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.profileDTOBuilder = profileDTOBuilder;
        this.profileBuilder = profileBuilder;
        this.userRepository = userRepository;
    }

    public List<ProfileDTO> getAll() {

        List<Profile> profiles = profileRepository.findAll();

        if (profiles.isEmpty()) {
            return new ArrayList<>();
        }

        return profileDTOBuilder.buildList(profiles);
    }

    public ProfileDTO getOne(Long id) {

        Profile profileSaved = this.getById(id);

        return profileDTOBuilder.build(profileSaved);
    }

    public Long save(ProfileDTO dto) {

        this.checkUniqueName(dto);

        Profile profile = profileBuilder.build(dto);

        Profile profileSaved = profileRepository.save(profile);

        return profileSaved.getId();
    }

    public ProfileDTO update(Long id, ProfileDTO dto) {

        Profile profileSaved = this.getById(id);

        if (!profileSaved.getName().equals(dto.getName())) {
            this.checkUniqueName(dto);
        }

        BeanUtils.copyProperties(dto, profileSaved, "id");

        Profile profileUpdated = profileRepository.save(profileSaved);

        return profileDTOBuilder.build(profileUpdated);
    }

    public Profile getById(Long id) {

        Optional<Profile> profileSaved = profileRepository.findById(id);

        if (!profileSaved.isPresent()) {
            throw new ProfileNotFoundException(1);
        }

        return profileSaved.get();
    }


    private void checkUniqueName(ProfileDTO dto) {

        Optional<Profile> optionalProfile = profileRepository.getByName(dto.getName());

        if (optionalProfile.isPresent()) {
            throw new NameNotUniqueException();
        }

    }

    public void delete(Long id) {

        Profile profileSaved = this.getById(id);

        List<User> users = userRepository.getByProfile(profileSaved.getId());

        if (!users.isEmpty()) {
            throw new ProfileHasUsersException();
        }

        profileRepository.deleteById(id);
    }
}


