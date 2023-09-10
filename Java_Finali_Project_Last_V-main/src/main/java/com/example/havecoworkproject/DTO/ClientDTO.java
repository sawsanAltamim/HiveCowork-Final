package com.example.havecoworkproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDTO {

    private Integer user_id;

    private String nameClient;

    private Integer age;
}
