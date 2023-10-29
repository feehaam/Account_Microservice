//package com.prep.account.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//    @Autowired
//    private UserController userController;
//
//    @Test
//    void registerUserPositive() throws Exception {
//        UserCreateDTO userCreateDTO = new UserCreateDTO("Test", "User", "user@test.com", "password", "Address", "CUSTOMER");
//        Mockito.when(userService.create(any(UserCreateDTO.class))).thenReturn(true);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
//                        .content(asJsonString(userCreateDTO))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().string(containsString("Account successfully registered")));
//    }
//
//    private String asJsonString(Object object){
//        return new ObjectMapper().writeValueAsString(obj);
//    }
//}