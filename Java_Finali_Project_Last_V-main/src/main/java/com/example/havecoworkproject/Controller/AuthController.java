package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.Service.AuthService;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hive_cowork/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register/client")
    public ResponseEntity registerClient(@RequestBody @Valid User user){
        authService.registerClient(user);
        return ResponseEntity.status(200).body(new ApiResponse("Client added successfully"));
    }

    @PostMapping("/register/company")
    public ResponseEntity registerCompany(@RequestBody @Valid User user){
        authService.registerCompany(user);
        return ResponseEntity.status(200).body(new ApiResponse("Waiting for the account to be accepted"));
    }



//    @PostMapping("/register/admin")
//    public ResponseEntity registerAdmin(@RequestBody @Valid User user){
//        authService.registerAdmin(user);
//        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
//    }

    @GetMapping("/get_order_company_register")
    public ResponseEntity getAllOrderCompanyRegister(){
        return ResponseEntity.status(200).body(authService.getAllOrderCompanyRegister());
    }

    @PutMapping("/confirm_company_account/{company_id}")
    public ResponseEntity confirmCompanyAccount(@PathVariable Integer company_id){
        authService.confirmCompanyAccount(company_id);
        return ResponseEntity.status(200).body(new ApiResponse("Creat Company account "));
    }
    @PutMapping("/reject_company_account/{company_id}")
    public ResponseEntity rejectCompanyAccount(Integer company_id){
        authService.rejectCompanyAccount(company_id);
        return ResponseEntity.status(200).body(new ApiResponse(" Reject Company account "));
    }

    @GetMapping("/get")
    public ResponseEntity getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.getAllUser());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        authService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User added successfully"));
    }
}