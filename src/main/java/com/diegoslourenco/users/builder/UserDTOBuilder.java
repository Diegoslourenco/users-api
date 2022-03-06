package com.diegoslourenco.users.builder;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDTOBuilder {

    public List<UserDTO> buildList(List<User> users) {

        return users.stream()
                .filter(Objects::nonNull)
                .map(this::build)
                .collect(Collectors.toList());
    }

    public UserDTO build(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

}
