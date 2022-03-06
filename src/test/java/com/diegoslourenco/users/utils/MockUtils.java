package com.diegoslourenco.users.utils;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MockUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String PROFILE_NAME_ADMIN = "admin";
    public static String PROFILE_NAME_BASIC_USER = "basic_user";

    public static final String USER_NAME_DIEGO = "Diego Lourenco";
    public static final String USER_EMAIL_DIEGO = "diego.lourenco15@gmail.com";

    public static final String USER_NAME_BOB = "Bob Dylan";
    public static final String USER_EMAIL_BOB = "bob@email.com";

    public static final long PROFILE_ID_1 = 1L;
    public static final long PROFILE_ID_2 = 2L;

    public static List<ProfileDTO> mockProfileDTOList(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<ProfileDTO>>(){});
    }

    public static List<Profile> mockProfileListWithOneObject() {

        List<Profile> profiles = new ArrayList<>();
        profiles.add(mockProfile(PROFILE_ID_1, "admin"));

        return profiles;
    }

    public static List<Profile> mockProfileListWithTwoObjects() {

        List<Profile> profiles = new ArrayList<>();
        profiles.add(mockProfile(PROFILE_ID_1, PROFILE_NAME_ADMIN));
        profiles.add(mockProfile(2L, PROFILE_NAME_BASIC_USER));

        return profiles;
    }

    public static Profile mockProfile(Long id, String name) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setName(name);
        return profile;
    }

    public static ProfileDTO mockProfileDTO() {
        return new ProfileDTO(PROFILE_ID_1, PROFILE_NAME_ADMIN);
    }

    public static String mockString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static List<User> mockUserListWithOneObject() {

        List<User> users = new ArrayList<>();
        Profile profile = mockProfile(PROFILE_ID_1, PROFILE_NAME_ADMIN);
        users.add(mockUser(PROFILE_ID_1, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profile));

        return users;
    }

    public static List<User> mockUserListWithTwoObjects() {

        List<User> users = new ArrayList<>();
        Profile profile = mockProfile(PROFILE_ID_1, PROFILE_NAME_ADMIN);
        users.add(mockUser(PROFILE_ID_1, USER_NAME_DIEGO, USER_EMAIL_DIEGO, profile));
        users.add(mockUser(PROFILE_ID_2, USER_NAME_BOB, USER_EMAIL_BOB, profile));

        return users;
    }

    public static User mockUser(long id, String name, String email, Profile profile) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setProfile(profile);
        return user;
    }

    public static List<UserDTO> mockUserDTOList(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<UserDTO>>(){});
    }


    public static UserDTO mockUserDTO() {
        return new UserDTO(PROFILE_ID_1, USER_NAME_DIEGO, USER_EMAIL_DIEGO, PROFILE_ID_1);
    }
}

