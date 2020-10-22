package com.service.rest.users;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    private static final Faker FAKER = new Faker();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldReturnEmptyList_whenNoUsersPresentOnTheDatabase() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnNonEmptyUserList_WhenUserArePersisted() throws Exception {
        UserData userData1 = UserData.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .phone(FAKER.phoneNumber().cellPhone())
                .build();
        UserData userData2 = UserData.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .phone(FAKER.phoneNumber().cellPhone())
                .build();
        entityManager.persist(userData1);
        entityManager.persist(userData2);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].id", is(userData1.getId().intValue())))
                .andExpect(jsonPath("[0].first_name", is(userData1.getFirstName())))
                .andExpect(jsonPath("[0].last_name", is(userData1.getLastName())))
                .andExpect(jsonPath("[0].phone", is(userData1.getPhone())))
                .andExpect(jsonPath("[1].id", is(userData2.getId().intValue())))
                .andExpect(jsonPath("[1].first_name", is(userData2.getFirstName())))
                .andExpect(jsonPath("[1].last_name", is(userData2.getLastName())))
                .andExpect(jsonPath("[1].phone", is(userData2.getPhone())));
    }
}
