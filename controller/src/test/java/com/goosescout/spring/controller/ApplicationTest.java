package com.goosescout.spring.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ApplicationTest {
    @Autowired
    private MockMvc mvc;
    private static String adminToken;
    private static String userToken;

    @Test
    @Order(1)
    void getAllOwners_Unauthorized() throws Exception {
        mvc.perform(get("/api/owners/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    void login_Ok() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\", \"password\": \"admin\"}"))
                .andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        adminToken = response.replace("{\"token\":\"", "").replace("\"}", "");
    }

    @Test
    @Order(3)
    void createUserAsAdmin_Ok() throws Exception {
        mvc.perform(post("/api/owners/create")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Roma\", \"username\": \"kotenochek\", \"password\": \"+20_points_to_Goosescout\", \"roles\": [\"USER\"], \"birthDate\": \"2022-01-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Roma"));
    }

    @Test
    @Order(4)
    void loginAsUser_Ok() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"kotenochek\", \"password\": \"+20_points_to_Goosescout\"}"))
                .andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        userToken = response.replace("{\"token\":\"", "").replace("\"}", "");
    }

    @Test
    @Order(5)
    void addCat_Ok() throws Exception {
        mvc.perform(post("/api/cats/add")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Roma\", \"birthDate\": \"2022-01-01\", \"breed\": \"Kotenochek\", \"color\": \"BLACK\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Roma"))
                .andExpect(jsonPath("$.breed").value("Kotenochek"))
                .andExpect(jsonPath("$.color").value("BLACK"))
                .andExpect(jsonPath("$.catOwnerId").value(2));

        mvc.perform(post("/api/cats/add")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Georgy\", \"birthDate\": \"2022-01-01\", \"breed\": \"Kotenochek\", \"color\": \"BROWN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Georgy"))
                .andExpect(jsonPath("$.breed").value("Kotenochek"))
                .andExpect(jsonPath("$.color").value("BROWN"))
                .andExpect(jsonPath("$.catOwnerId").value(2));
    }

    @Test
    @Order(6)
    void getAllCats2Found() throws Exception {
        mvc.perform(get("/api/cats/getAll")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Roma"))
                .andExpect(jsonPath("$[1].name").value("Georgy"));
    }

    @Test
    @Order(7)
    void getCatsByBreed2Found() throws Exception {
        mvc.perform(get("/api/cats/getByBreed?breed=Kotenochek")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Roma"))
                .andExpect(jsonPath("$[1].name").value("Georgy"));
    }

    @Test
    @Order(8)
    void addFriendsOk() throws Exception {
        mvc.perform(post("/api/cats/addFriend")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"catId\": 1, \"friendId\": 2}"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void checkFriendsFriendsFound() throws Exception {
        mvc.perform(get("/api/cats/getById?id=1")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.friendIds", hasSize(1)))
                .andExpect(jsonPath("$.friendIds[0]").value(2));
    }

    @Test
    @Order(10)
    void removeFriendOk() throws Exception {
        mvc.perform(post("/api/cats/removeFriend")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"catId\": 2, \"friendId\": 1}"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(11)
    void checkFriendsFriendsNoneFound() throws Exception {
        mvc.perform(get("/api/cats/getById?id=1")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.friendIds", hasSize(0)));
    }

    @Test
    @Order(12)
    void deleteOwnerOk() throws Exception {
        mvc.perform(post("/api/owners/delete?id=2")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(13)
    void getAllCatsNoneFound() throws Exception {
        mvc.perform(get("/api/cats/getAll")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
