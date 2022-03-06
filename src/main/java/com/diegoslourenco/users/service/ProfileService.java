package com.diegoslourenco.users.service;

import com.diegoslourenco.users.builder.ProfileBuilder;
import com.diegoslourenco.users.builder.ProfileDTOBuilder;
import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.exceptionHandler.ProfileNameNotUniqueException;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.repository.ProfileRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileDTOBuilder profileDTOBuilder;
    private final ProfileBuilder profileBuilder;

    public ProfileService(ProfileRepository profileRepository, ProfileDTOBuilder profileDTOBuilder, ProfileBuilder profileBuilder) {
        this.profileRepository = profileRepository;
        this.profileDTOBuilder = profileDTOBuilder;
        this.profileBuilder = profileBuilder;
    }

    public List<ProfileDTO> getAll() {

        List<Profile> profiles = profileRepository.findAll();

        if (profiles.isEmpty()) {
            return new ArrayList<>();
        }

        return profileDTOBuilder.buildList(profiles);
    }

    public ProfileDTO getById(Long id) {

        Optional<Profile> profileSaved = profileRepository.findById(id);

        if (!profileSaved.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        return profileDTOBuilder.build(profileSaved.get());
    }

    public Long save(ProfileDTO dto) {

        if (!checkUniqueName(dto)) {
            throw new ProfileNameNotUniqueException();
        }

        Profile profile = profileBuilder.build(dto);

        Profile profileSaved = profileRepository.save(profile);

        return profileSaved.getId();
    }

    private boolean checkUniqueName(ProfileDTO dto) {
        Optional<Profile> optionalProfile = profileRepository.getByName(dto.getName());

        if (optionalProfile.isPresent()) {
            return false;
        }

        return true;
    }
}
