package com.service.rest.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers_fromRepository() throws Exception {
        UserData userData = UserData.builder()
                .id(10L)
                .firstName("Bilbo")
                .lastName("Baggins")
                .phone("+44 9999-99999")
                .build();
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(userData));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()", is(1)))
                .andExpect(jsonPath("[0].id", is(10)))
                .andExpect(jsonPath("[0].first_name", is("Bilbo")))
                .andExpect(jsonPath("[0].last_name", is("Baggins")))
                .andExpect(jsonPath("[0].phone", is("+44 9999-99999")));
    }

    @Test
    public void shouldReturnSpecificUserData_fromRepositoryById() throws Exception {
        UserData userData = UserData.builder()
                .id(10L)
                .firstName("Bilbo")
                .lastName("Baggins")
                .phone("+44 9999-99999")
                .build();
        Mockito.when(userRepository.findById(10L))
                .thenReturn(Optional.of(userData));

        mvc.perform(get("/users/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(10)))
                .andExpect(jsonPath("first_name", is("Bilbo")))
                .andExpect(jsonPath("last_name", is("Baggins")))
                .andExpect(jsonPath("phone", is("+44 9999-99999")));
    }

    @Test
    public void shouldCreateNewUserData_ToTheRepository() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserData requestUserData = UserData.builder()
                .firstName("Frodo")
                .lastName("Baggins")
                .phone("+44 7777-7777")
                .build();
        UserData savedUserData = UserData.builder()
                .id(11L)
                .firstName(requestUserData.getFirstName())
                .lastName(requestUserData.getLastName())
                .phone(requestUserData.getPhone())
                .build();
        Mockito.when(userRepository.save(requestUserData))
                .thenReturn(savedUserData);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestUserData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(11)))
                .andExpect(jsonPath("first_name", is("Bilbo")))
                .andExpect(jsonPath("last_name", is("Baggins")))
                .andExpect(jsonPath("phone", is("+44 9999-99999")));
    }

    @Test
    public void shouldReturnDeleteSpecificUser_fromRepositoryById() throws Exception {
        mvc.perform(delete("/users/10"))
                .andExpect(status().isOk());

        Mockito.verify(userRepository).deleteById(10L);
    }

}