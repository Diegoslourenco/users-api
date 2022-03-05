package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.ProfileDTO;
import com.diegoslourenco.users.model.Profile;
import com.diegoslourenco.users.service.ProfileService;
import com.diegoslourenco.users.utils.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
        MvcResult mvcResult = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

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

        String expected = MockUtils.mockString("src/test/resources/json/response/profileDTO.json");

        // When
        when(profileService.getById(1L)).thenReturn(profileMocked);

        // Then
        MvcResult mvcResult = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.OK.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

    @Test
    public void  getByIdNotFoundTest() throws Exception {

        // Given
        String uri = "/profiles/1";

        ProfileDTO profileMocked = MockUtils.mockProfileDTO();

        String expected = MockUtils.mockString("src/test/resources/json/error/profileNotFound.json");

        // When
        when(profileService.getById(1L)).thenThrow(new EmptyResultDataAccessException(1));

        // Then
        MvcResult mvcResult = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

}
