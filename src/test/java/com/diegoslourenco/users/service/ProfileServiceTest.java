package com.diegoslourenco.users.service;


import com.diegoslourenco.users.builder.ProfileBuilder;
import com.diegoslourenco.users.builder.ProfileDTOBuilder;
import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.exceptionHandler.ProfileNameNotUniqueException;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.repository.ProfileRepository;
import com.diegoslourenco.users.utils.MockUtils;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ProfileDTOBuilder profileDTOBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ProfileBuilder profileBuilder;

    @Test
    public void getAllForEmptyListTest() {

        // Given
        List<Profile> profilesMock = new ArrayList<>();
        List<ProfileDTO> expected = new ArrayList<>();

        // When
        when(profileRepository.findAll()).thenReturn(profilesMock);

        // Then
        List<ProfileDTO> result = profileService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getAllForListWithOneProfileDTOTest() throws IOException {

        // Given
        List<Profile> profilesMock = MockUtils.mockProfileListWithOneObject();
        List<ProfileDTO> expected = MockUtils.mockProfileDTOList("src/test/resources/json/response/profileDTOListWithOneObject.json");

        // When
        when(profileRepository.findAll()).thenReturn(profilesMock);

        // Then
        List<ProfileDTO> result = profileService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getAllForListWithTwoProfileDTOTest() throws IOException {

        // Given
        List<Profile> profilesMock = MockUtils.mockProfileListWithTwoObjects();
        List<ProfileDTO> expected = MockUtils.mockProfileDTOList("src/test/resources/json/response/profileDTOListWithTwoObjects.json");

        // When
        when(profileRepository.findAll()).thenReturn(profilesMock);

        // Then
        List<ProfileDTO> result = profileService.getAll();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getByIdTest() {

        // Given
        Profile profile = MockUtils.mockProfile(1L, MockUtils.PROFILE_NAME_ADMIN);
        ProfileDTO expected = MockUtils.mockProfileDTO();

        // When
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Then
        ProfileDTO result = profileService.getById(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByIdNotFoundTest() {

        // When
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        ProfileDTO result = profileService.getById(1L);
    }

    @Test
    public void createTest() {

        // Given
        ProfileDTO profileDTO = MockUtils.mockProfileDTO();
        Profile profileMocked = MockUtils.mockProfile(1L, MockUtils.PROFILE_NAME_ADMIN);

        // When
        when(profileRepository.getByName(profileDTO.getName())).thenReturn(Optional.empty());
        when(profileRepository.save(any())).thenReturn(profileMocked);

        // Then
        Long result = profileService.save(profileDTO);
        assertThat(result).isEqualTo(1L);
    }

    @Test(expected = ProfileNameNotUniqueException.class)
    public void createProfileNotUniqueTest() {

        // Given
        ProfileDTO profileDTO = MockUtils.mockProfileDTO();
        Profile profile = MockUtils.mockProfile(1L, MockUtils.PROFILE_NAME_ADMIN);

        // When
        when(profileRepository.getByName(profileDTO.getName())).thenReturn(Optional.of(profile));

        // Then
        Long result = profileService.save(profileDTO);
    }

}