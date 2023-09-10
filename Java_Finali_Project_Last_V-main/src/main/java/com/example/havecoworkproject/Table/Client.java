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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Client Name must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String nameClient;

    @NotNull(message = "Client Age must not be null")
    @Positive(message = "Age must be positive")
    private Integer age;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy ="client")
    private Set<Booking> bookingSet;
    @OneToMany( cascade =CascadeType.ALL, mappedBy = "client")
    private Set<Rating> ratings;
}
