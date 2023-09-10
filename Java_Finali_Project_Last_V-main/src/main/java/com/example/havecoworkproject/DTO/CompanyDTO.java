package com.example.havecoworkproject.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDTO {

    private Integer user_id;

    private String nameCompany;

    private String location;

    private String description;
}
