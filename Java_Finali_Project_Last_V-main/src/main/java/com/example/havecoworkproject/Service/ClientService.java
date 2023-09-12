package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.DTO.ClientDTO;
import com.example.havecoworkproject.Repository.AuthRepository;
import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Repository.OfficeRepository;

import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final AuthRepository authRepository;
    private final OfficeRepository officeRepository;
    public List<Client> getAllClient(){
        return clientRepository.findAll();
    }

    public Client addClient(Integer user_id, ClientDTO clientDTO) {
        User user = authRepository.findUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Client existingClient = clientRepository.findByUserAndNameClientAndAge(user, clientDTO.getNameClient(), clientDTO.getAge());
        if (existingClient != null) {
            throw new ApiException("Client with the same name and age already exists for this user");
        }

        Client client = new Client();
        client.setNameClient(clientDTO.getNameClient());
        client.setAge(clientDTO.getAge());
        client.setUser(user);

        return clientRepository.save(client);
    }

    public void updateClient(Integer user_id, Integer client_id, ClientDTO clientDTO){ // done
        Client client = clientRepository.findClientById(client_id);
        if(client == null){
            throw new ApiException("Client not found");
        }
        if(!client.getUser().getClient().getId().equals(user_id)){
            throw new ApiException("Assess denied");
        }
        client.setNameClient(clientDTO.getNameClient());
        client.setAge(clientDTO.getAge());

        clientRepository.save(client);
    }

    public void deleteClient(Integer user_id, Integer client_id){ // done
        Client client = clientRepository.findClientById(client_id);
        if(client == null){
            throw new ApiException("Client not found");
        }
        if(!client.getUser().getClient().getId().equals(user_id)){
            throw new ApiException("Assess denied");
        }
        clientRepository.delete(client);
    }
    public List<Office> SearchNumberPerson(Integer numberPerson){
        List<Office> offices=officeRepository.findOfficeByNumberPerson(numberPerson);
        if (offices == null) {
            throw new ApiException(" numberPerson not found");
        }
        return offices;
    }

}
