package com.example.havecoworkproject.DTO;

import com.example.havecoworkproject.Table.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ScheduleDTO {

    private List<Integer> bookingSchedle;

    private String companyName = "name";

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime startDate = LocalDateTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime endDate = LocalDateTime.now();

   // @NotNull(message = "Price is null")
   // @Positive(message = "Price should be positive")
    @Column(columnDefinition = "double not null")
    private Double price = 150.0;

    @Column(columnDefinition = "varchar(20) not null")
    private String stutas = "Pending";

    @NotEmpty(message = "You must enter the reason")
    @Column(columnDefinition = "varchar(50) not null")
    private String reason;

}
