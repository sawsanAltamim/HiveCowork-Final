package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.DTO.CompanyDTO;
import com.example.havecoworkproject.Service.CompanyService;
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
@RequestMapping("/api/v1/hive_cowork/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/get")
    public ResponseEntity getAllCompany(){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompany());
    }
    @PostMapping("/add")
    public ResponseEntity addCompany(@AuthenticationPrincipal User user,  @RequestBody @Valid CompanyDTO companyDTO){
        companyService.addCompany(user.getId(), companyDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Company added successfully"));
    }

    @PutMapping("/update/{id_company}")
    public ResponseEntity updateCompany(@AuthenticationPrincipal User user, @PathVariable Integer id_company, @RequestBody @Valid CompanyDTO companyDTO){
        companyService.updateCompany(user.getId(),id_company, companyDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Client update successfully"));
    }

    @DeleteMapping("/delete/{id_company}")
    public ResponseEntity deleteCompany(@AuthenticationPrincipal User user,@PathVariable Integer id_company){
        companyService.deleteCompany(user.getId(), id_company);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Client delete successfully"));
    }
}
