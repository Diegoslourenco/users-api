package com.diegoslourenco.users.builder;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.model.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProfileDTOBuilder {

    public List<ProfileDTO> buildList(List<Profile> profiles) {

        return profiles.stream()
                .filter(Objects::nonNull)
                .map(this::build)
                .collect(Collectors.toList());
    }

    public ProfileDTO build(Profile profile) {
        return new ProfileDTO(profile.getId(), profile.getName());
    }
}
