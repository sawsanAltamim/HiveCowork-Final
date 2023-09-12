package com.example.havecoworkproject;

import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Repository.ServiceRepository;
import com.example.havecoworkproject.Table.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;
    User user;
    Client client1;

    Client client2;

    List<Client> clients, clientList;

    @BeforeEach
    void setUp() {
        user = new User(1, "suu1n", "12345", "CLIENT", "sawsan@gmail.com", "0561037115", null, null);
        client1 = new Client(1, "Sawsan", 18, user, null, null);
        client2 = new Client(2, "Tamim", 22, user, null, null);

    }

    @Test
    public void findServiceById() {
        clientRepository.save(client1);
        client1 = clientRepository.findClientById(client1.getId());
        Assertions.assertThat(client1).isEqualTo(client1);
    }
    /*@Test
    public void findByUserAndNameClientAndAge() {
        List<Client> clientList1 = clientRepository.findByUserAndNameClientAndAge(user.getClient().getUser(), "ad",client1.getAge());
        Assertions.assertThat(clientList1).containsExactly(client2);
    }*/

}