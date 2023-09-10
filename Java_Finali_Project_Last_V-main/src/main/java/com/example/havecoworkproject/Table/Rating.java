package com.example.havecoworkproject.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     @NotNull
    private Integer numRate;

    @ManyToOne
    @JoinColumn(name = "office_id",referencedColumnName = "id")
    @JsonIgnore
    private Office office;
    @ManyToOne
    @JoinColumn(name = "client_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Client client;

}
