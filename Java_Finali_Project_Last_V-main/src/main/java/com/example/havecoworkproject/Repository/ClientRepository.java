package com.example.havecoworkproject.Repository;

import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    Client findClientById(Integer id_client);

    Client findByUserAndNameClientAndAge(User user, String nameClient, Integer age);
    // Client findClientBy(Integer id_client);

}
