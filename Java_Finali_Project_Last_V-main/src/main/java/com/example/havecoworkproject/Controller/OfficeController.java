package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.Service.OfficeService;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hive_cowork/office")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/get")
    public ResponseEntity getAllOffice(){
        return ResponseEntity.status(200).body(officeService.getAllOffice());
    }

    @GetMapping("/get_by_company")
    public ResponseEntity getOfficeByCompany(@AuthenticationPrincipal User user){
      return ResponseEntity.status(200).body(officeService.getOfficeByCompany(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addOffice(@AuthenticationPrincipal User user, @RequestBody @Valid Office office){
        officeService.addOffice(user.getId(),office);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Office added successfully"));
    }


    @PutMapping("/update/{office_id}")
    public ResponseEntity updateOffice(@AuthenticationPrincipal User user, @PathVariable Integer office_id, @RequestBody @Valid Office office){
        officeService.updateOffice(user.getId(),office_id, office);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Office update successfully"));
    }

    @DeleteMapping("/delete/{office_id}")
    public ResponseEntity deleteOffice(@AuthenticationPrincipal User user, @PathVariable Integer office_id){
        officeService.deleteOffice(user.getId(),office_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Office deleted successfully"));
    }

    @GetMapping("/all-sorted-by-rating")
    public ResponseEntity getAllOfficesSortedByRating() {
        List<Office> sortedOffices = officeService.getAllOfficesOrderByRating();
        return ResponseEntity.status(HttpStatus.OK).body(sortedOffices);
    }
    @GetMapping("/all-sorted-by-price")
    public ResponseEntity getAllOfficesSortedByPrice() {
        List<Office> sortedOffices = officeService.getAllOfficesOrderByPrice();
        return ResponseEntity.status(HttpStatus.OK).body(sortedOffices);
    }
    @GetMapping("/Search/{location}")
    public ResponseEntity SearchLocation(@PathVariable String location) {
        return ResponseEntity.status(200).body(officeService.SearchLocation(location));
    }
    /*@GetMapping("/Search/{nameCompany}")
    public ResponseEntity SearchNameCompany(@PathVariable String nameCompany) {
        return ResponseEntity.status(200).body(officeService.SearchNameCompany(nameCompany));
    }*/
    /*@GetMapping("/Search/{numberPerson}")
    public ResponseEntity SearchNumberPerson(@PathVariable Integer numberPerson) {
        return ResponseEntity.status(200).body(officeService.SearchNumberPerson(numberPerson));
}*/

}