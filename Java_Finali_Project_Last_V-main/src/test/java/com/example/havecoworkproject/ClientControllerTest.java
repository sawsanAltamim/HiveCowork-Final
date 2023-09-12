package com.example.havecoworkproject;


import com.example.havecoworkproject.Controller.ClientController;
import com.example.havecoworkproject.DTO.ClientDTO;
import com.example.havecoworkproject.Service.ClientService;
import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClientController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ClientControllerTest {
    @MockBean
    ClientService clientService;
    @Autowired
    MockMvc mockMvc;

    User user;


    Client client1;

    Client client2;

    List<Client> clients,clientList;

    ClientDTO clientDTO;


    @BeforeEach
    void setUp() {
        user = new User(1,"suu1n","12345","CLIENT","sawsan@gmail.com","0561037115",null,null);
        client1 = new Client(1,"Sawsan",18,user,null,null);
        client2 = new Client(2,"Tamim",22,user,null,null);
        clients=Arrays.asList(client1);
        clientList=Arrays.asList(client2);


    }
    @Test
    public void GetAllClient() throws Exception {
        Mockito.when(clientService.getAllClient()).thenReturn(clientList);
        mockMvc.perform(get("/api/v1/hive_cowork/client/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productsPrice").value("client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products").value("client1"));
    }

    /*@Test
    public void GetMyProduct() throws Exception {
        Mockito.when(productService.getMyProduct(store.getId())).thenReturn(products);
        mockMvc.perform(get("/api/v1/Product/get-my-product"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productsPrice").value("product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products").value("product2"));
    }*/
    @Test
    public void testAddClient() throws  Exception {
        mockMvc.perform(post("/api/v1/hive_cowork/client/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(client2)))
                .andExpect(status().isOk());

    }
    @Test
    public void testDeleteClient() throws Exception{
        mockMvc.perform(delete("/api/v1/hive_cowork/client/delete/{client_id}",user.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateClient() throws Exception{
        mockMvc.perform(put("/api/v1/hive_cowork/client/update/{client_id}",user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(client1)))
                .andExpect(status().isOk());

    }





}
