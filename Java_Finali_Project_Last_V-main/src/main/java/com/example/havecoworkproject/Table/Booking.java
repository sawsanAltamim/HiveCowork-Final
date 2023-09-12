package com.example.havecoworkproject.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName = "name";

    //@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime startDate; //= LocalDateTime.now();

    //@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime endDate;//= LocalDateTime.now();

    //@NotNull(message = "Price is null")
    //@Positive(message = "Price should be positive")
    @Column(columnDefinition = "double not null")
    private Double price = 0.0;

    @Column(columnDefinition = "varchar(20) not null")
    private String stutas = "Pending";

    @NotEmpty(message = "You must enter the reason")
    @Column(columnDefinition = "varchar(50) not null")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "client_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Company company;

    private Boolean isRating=false;

    @ManyToOne
    @JoinColumn(name = "office_id",referencedColumnName = "id")
    @JsonIgnore
    private Office office;

//   @OneToMany( cascade = CascadeType.ALL, mappedBy = "booking")
//   private Set<Booking> bookings;


    @OneToMany( cascade = CascadeType.ALL, mappedBy = "booking")
    private Set<Schedule> schedules;

}
