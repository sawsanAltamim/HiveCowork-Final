package com.example.havecoworkproject.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Price must not be null")
    @Positive(message = "price must be positive")
    private Double price;

    private String companyName = "name";

    @NotNull(message = "Number Person must not be null")
    private Integer numberPerson;

    @NotEmpty(message = "category must not be empty")
    @Column(columnDefinition = "varchar(20)  not null check(category='Private Office' or category='Dedicated Desk' or category='Meeting Room')")
    private String category;

    @NotEmpty(message = "Location must not be empty")
    private String location;

    @NotEmpty(message = "Address must not be empty")
    private String address;

    private Double avgRating = 3.5;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "office")
    private Set<Services> service;


    @OneToMany( cascade = CascadeType.ALL, mappedBy = "office")
    private Set<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Company company;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "office")
    private Set<Schedule> schedules;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "office")
    private Set<Booking> bookings ;

}
