package com.example.havecoworkproject.Repository;

import com.example.havecoworkproject.Table.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

    Rating findRatingById(Integer rating_id);
    List<Rating> findRatingByOfficeId(Integer office_id);
}
