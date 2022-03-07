package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.exceptionHandler.NameNotUniqueException;
import com.diegoslourenco.users.exceptionHandler.ProfileHasUsersException;
import com.diegoslourenco.users.exceptionHandler.ProfileNotFoundException;
import com.diegoslourenco.users.service.ProfileService;
import com.diegoslourenco.users.utils.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProfileService profileService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void  getAllTest() throws Exception {

        // Given
        String uri = "/profiles";
        String expected = "[]";

        // When
        when(profileService.getAll()).thenReturn(new ArrayList<>());

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void  getByIdSuccessTest() throws Exception {

        // Given
        String uri = "/profiles/1";

        ProfileDTO profileMocked = MockUtils.mockProfileDTO();

        String expected = MockUtils.mockString("src/test/resources/json/profile/response/profileDTO.json");

        // When
        when(profileService.getOne(1L)).thenReturn(profileMocked);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  getByIdNotFoundTest() throws Exception {

        // Given
        String uri = "/profiles/1";

        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileNotFound.json");

        // When
        when(profileService.getOne(1L)).thenThrow(new ProfileNotFoundException(1));

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createSuccessTest() throws Exception {

        // Given
        String uri = "/profiles";

        String profileMocked = MockUtils.mockString("src/test/resources/json/profile/request/profileDTORequest.json");

        String expected = "1";

        // When
        when(profileService.save(any())).thenReturn(1L);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  createBadRequestTest() throws Exception {

        // Given
        String uri = "/profiles";

        String profileMocked = MockUtils.mockString("src/test/resources/json/profile/request/profileDTORequest.json");

        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileNameNotUnique.json");

        // When
        when(profileService.save(any())).thenThrow(NameNotUniqueException.class);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  updateSuccessTest() throws Exception {

        // Given
        String uri = "/profiles/1";

        String profileMocked = MockUtils.mockString("src/test/resources/json/profile/request/profileDTORequest.json");

        ProfileDTO profileDTOMocked = MockUtils.mockProfileDTO();

        String expected = MockUtils.mockString("src/test/resources/json/profile/response/profileDTO.json");

        // When
        when(profileService.update(any(), any())).thenReturn(profileDTOMocked);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  updateBadRequestTest() throws Exception {

        // Given
        String uri = "/profiles/1";
        String profileMocked = MockUtils.mockString("src/test/resources/json/profile/request/profileDTORequest.json");
        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileNameNotUnique.json");

        // When
        when(profileService.update(any(), any())).thenThrow(NameNotUniqueException.class);

        // Then
        MvcResult mvcResult = mockMvc
                .perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  updateNotFoundTest() throws Exception {

        // Given
        String uri = "/profiles/1";
        String profileMocked = MockUtils.mockString("src/test/resources/json/profile/request/profileDTORequest.json");
        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileNotFound.json");

        // When
        when(profileService.update(any(), any())).thenThrow(new ProfileNotFoundException(1));

        // Then
        MvcResult mvcResult = mockMvc
                .perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(profileMocked))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void deleteTest() throws Exception {

        // Given
        String uri = "/profiles/1";

        // Then
        MvcResult mvcResult = mockMvc
                .perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertThat(status).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteForNotFoundTest() throws Exception {

        // Given
        String uri = "/profiles/1";
        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileNotFound.json");

        // When
        doThrow(new ProfileNotFoundException(1)).when(profileService).delete(any());

        // Then
        MvcResult mvcResult = mockMvc
                .perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void deleteForProfileHasUsersTest() throws Exception {

        // Given
        String uri = "/profiles/1";
        String expected = MockUtils.mockString("src/test/resources/json/profile/error/profileHasUsers.json");

        // When
        doThrow(new ProfileHasUsersException()).when(profileService).delete(any());

        // Then
        MvcResult mvcResult = mockMvc
                .perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

}
