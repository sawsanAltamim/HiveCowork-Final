package com.example.havecoworkproject;

import com.example.havecoworkproject.DTO.ClientDTO;
import com.example.havecoworkproject.Repository.AuthRepository;
import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Service.ClientService;
import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AuthRepository authRepository;

    User user;

    Client client1;

    Client client2;

    List<Client> clients;

    ClientDTO clientDTO;


    @BeforeEach
    void setUp() {
        user = new User(1,"suu1n","12345","CLIENT","sawsan@gmail.com","0561037115",null,null);
        authRepository.save(user);

        client1 = new Client(1,"Sawsan",18,user,null,null);
        client2 = new Client(2,"Tamim",22,user,null,null);
        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

    }

    @Test
    public void deleteClient(){
        when(clientRepository.findClientById(client1.getId())).thenReturn(client1);
        when(authRepository.findUserById(user.getId())).thenReturn(user);

        clientService.deleteClient(user.getId(), client1.getId());

        verify(clientRepository,times(1)).findClientById(client1.getId());
        verify(authRepository,times(1)).findUserById(user.getId());
        verify(clientRepository,times(1)).delete(client1);
    }
}
