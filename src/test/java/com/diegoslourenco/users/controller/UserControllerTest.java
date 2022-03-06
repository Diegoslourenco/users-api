package com.diegoslourenco.users.controller;

import com.diegoslourenco.users.dto.UserDTO;
import com.diegoslourenco.users.service.UserService;
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
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void  getAllTest() throws Exception {

        // Given
        String uri = "/users";
        String expected = "[]";

        // When
        when(userService.getAll()).thenReturn(new ArrayList<>());

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
        String uri = "/users/1";

        UserDTO UserMocked = MockUtils.mockUserDTO();

        String expected = MockUtils.mockString("src/test/resources/json/User/response/userDTO.json");

        // When
        when(userService.getOne(1L)).thenReturn(UserMocked);

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
        String uri = "/users/1";

        UserDTO userMocked = MockUtils.mockUserDTO();

        String expected = MockUtils.mockString("src/test/resources/json/user/error/userNotFound.json");

        // When
        when(userService.getOne(1L)).thenThrow(new EmptyResultDataAccessException(1));

        // Then
        MvcResult mvcResult = mockMvc
                .perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String result = mvcResult.getResponse().getContentAsString();

        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result).usingDefaultComparator().isEqualTo(expected);
    }

}