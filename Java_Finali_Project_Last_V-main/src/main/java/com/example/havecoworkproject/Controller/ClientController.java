package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.DTO.ClientDTO;
import com.example.havecoworkproject.Service.ClientService;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hive_cowork/client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/get")
    public ResponseEntity getAllClient(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClient());
    }
    @PostMapping("/add")
    public ResponseEntity addClient(@AuthenticationPrincipal User user, @RequestBody @Valid ClientDTO clientDTO){
        clientService.addClient(user.getId(),clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Client added successfully"));
    }

    @PutMapping("/update/{client_id}")
    public ResponseEntity updateClient(@AuthenticationPrincipal User user,@PathVariable Integer client_id ,@RequestBody @Valid ClientDTO clientDTO){
        clientService.updateClient(user.getId(), client_id ,clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Client update successfully"));
    }

    @DeleteMapping("/delete/{client_id}")
    public ResponseEntity deleteClient(@AuthenticationPrincipal User user, @PathVariable Integer client_id){
        clientService.deleteClient(user.getId(), client_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Client update successfully"));
    }
    @GetMapping("/Search/{numberPerson}")
    public ResponseEntity SearchNumberPerson(@PathVariable Integer numberPerson) {
        return ResponseEntity.status(200).body(clientService.SearchNumberPerson(numberPerson));
}
}
