package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.Service.RatingService;
import com.example.havecoworkproject.Table.Rating;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hive_cowork/rating")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/get")
    public ResponseEntity getAllRating(){
        return ResponseEntity.status(200).body(ratingService.getAllRating());
    }

    @GetMapping("/get_by_office/{office_id}")
    public ResponseEntity getAllRating(@AuthenticationPrincipal User user, @PathVariable Integer office_id ){
        return ResponseEntity.status(200).body(ratingService.getRatingByOffice(user.getId(), office_id));
    }

    @PostMapping("/add/{office_id}")
    public ResponseEntity addRating(@AuthenticationPrincipal User user, @PathVariable Integer office_id, @RequestBody @Valid Rating rating){
        ratingService.addRatingToOffice(user.getId(),office_id,rating);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Rating added successfully"));
    }
}
