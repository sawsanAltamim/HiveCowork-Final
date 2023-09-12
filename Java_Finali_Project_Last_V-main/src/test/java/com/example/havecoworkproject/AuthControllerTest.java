package com.example.havecoworkproject;

import com.example.havecoworkproject.Controller.AuthController;
import com.example.havecoworkproject.Service.AuthService;

import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AuthControllerTest {

    @MockBean
    AuthService authService;
    @Autowired
    MockMvc mockMvc;

    User user;
    Client client1;
    Company company;

    @BeforeEach
    void setUp() {
        client1 = new Client(1,"Sawsan",18,user,null,null);
        company=new Company(null,"non",true,"A1212","good",null,null,null);
        user = new User(1,"suu1n","12345","CLIENT","sawsan@gmail.com","0561037115",client1,company);


    }


    @Test
    public void testUpdateClient() throws Exception{
        mockMvc.perform(put("/api/v1/hive_cowork/auth/reject_company_account/{company_id}",user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(company)))
                .andExpect(status().isOk());

    }
    @Test
    public void rejectCompanyAccount() throws Exception{
        mockMvc.perform(put("/api/v1/hive_cowork/auth/reject_company_account/{company_id}",user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(company)))
                .andExpect(status().isOk());

    }
}
