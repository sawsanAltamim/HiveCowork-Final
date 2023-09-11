package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.Service.ServiceService;
import com.example.havecoworkproject.Table.Services;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hive_cowork/services")
public class ServicesController {
    private final ServiceService serviceService;

    //@GetMapping("/get")
   // public ResponseEntity getAllService(){
       // return ResponseEntity.status(200).body(serviceService.getAllServices());
   // }

    @GetMapping("/get_by_office/{office_id}")
    public ResponseEntity getAllServiceByOffice(@AuthenticationPrincipal User user, @PathVariable Integer office_id){
        return ResponseEntity.status(200).body(serviceService.getServicesByOffice(user.getId(),office_id));
    }
    @PostMapping("/add/{office_id}")
    public ResponseEntity addService(@AuthenticationPrincipal User user, @PathVariable Integer office_id,@RequestBody @Valid Services service){
        serviceService.addService(user.getId(),office_id,service);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Service added successfully"));
    }
    @PutMapping("/update/{service_id}")
    public ResponseEntity updateService(@AuthenticationPrincipal User user, @PathVariable Integer service_id, @RequestBody @Valid Services service){
        serviceService.updateService(user.getId(),service_id,service);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Service update successfully"));
    }
    @DeleteMapping("/delete/{service_id}")
    public ResponseEntity deleteService(@AuthenticationPrincipal User user,@PathVariable Integer service_id){
        serviceService.deleteService(user.getId(),service_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Service deleted successfully"));
    }
}
