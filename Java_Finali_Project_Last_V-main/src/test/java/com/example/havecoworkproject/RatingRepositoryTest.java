package com.example.havecoworkproject;

import com.example.havecoworkproject.Repository.RatingRepository;
import com.example.havecoworkproject.Table.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RatingRepositoryTest {
    @Autowired
    RatingRepository ratingRepository;
    Client client;
    Office office;
    Company company;
    User user;
    Rating rating1;
    Rating rating2;

    @BeforeEach
    void setUp() {
        //client= new Client(1,"Sawsan",18,user,null,null);
       // company=new Company(null,"non",true,"A1212","good",null,null,null);
        office=new Office(null,12.3,"non",12,"","12AA","qq",12.3,null,null,null,null,null);
       // user = new User(1,"suu1n","12345","CLIENT","sawsan@gmail.com","0561037115",client,company);
        rating1=new Rating(null,12.7,office,null);
        rating2=new Rating(null,13.5,office,null);
        ratingRepository.saveAll(List.of(rating2,rating1));

    }
    @Test
    public void findRatingByIdTest() {
        Rating rating = ratingRepository.findRatingById(rating1.getId());
        Assertions.assertThat(rating).isEqualTo(rating1);
    }
   /* @Test
    public void findRatingByOfficeId() {
        List<Rating> ratings = ratingRepository.findRatingByOfficeId(rating1.getId());
        Assertions.assertThat(ratings).containsExactly(rating1);
    }*/
}
