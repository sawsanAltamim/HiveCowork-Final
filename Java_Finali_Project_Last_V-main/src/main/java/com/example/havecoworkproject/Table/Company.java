package com.example.havecoworkproject.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Company Name must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String nameCompany;

    private Boolean acceptable = false;

    @NotEmpty(message = "Location must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String location;

    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy ="company")
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private Set<Office> officeSet;

}
