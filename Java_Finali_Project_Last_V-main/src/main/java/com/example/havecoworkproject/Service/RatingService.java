package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.BookingRepository;
import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Repository.OfficeRepository;
import com.example.havecoworkproject.Repository.RatingRepository;

import com.example.havecoworkproject.Table.Booking;
import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final OfficeRepository officeRepository;
    private final ClientRepository clientRepository;
    private final BookingRepository bookingRepository;
    public List<Rating> getAllRating(){
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingByOffice(Integer company_id,Integer office_id){
        Office office = officeRepository.findOfficeById(office_id);
        if(!office.getCompany().getId().equals(company_id)){
            throw new ApiException("Unauthorized");
        }
        return ratingRepository.findRatingByOfficeId(office_id);
    }

    public void addRatingToOffice(Integer clientId, Integer officeId, Rating rating) {
        Office office = officeRepository.findOfficeById(officeId);
        Client client = clientRepository.findClientById(clientId);

        if (client == null) {
            throw new ApiException("Client not found");
        }

        if (office == null) {
            throw new ApiException("Office not found");
        }

        Booking booking = bookingRepository.findBookingByOfficeIdAndClientId(officeId, clientId);
        if (booking == null) {
            throw new ApiException("You cannot rate this office without a booking");
        }


        bookingRepository.save(booking);

        if (!booking.getStutas().equals("Complete")) {
            throw new ApiException("You can only rate an office after completing the booking");
        }

        if (booking.getIsRating()){
            throw new ApiException("Your rating has already been registered");
        }
        booking.setIsRating(true);
        rating.setOffice(office);
        rating.setClient(client);
        ratingRepository.save(rating);

        calculateAverageRating(officeId);
    }

    public void calculateAverageRating(Integer officeId) {
        Office office = officeRepository.findOfficeById(officeId);

        if (office == null) {
            throw new ApiException("Office not found");
        }

        List<Rating> ratings = ratingRepository.findRatingByOfficeId(office.getId());
        if (!ratings.isEmpty()) {
            Integer totalRatings = ratings.size();
            Double sum = ratings.stream().mapToDouble(Rating::getNumRate).sum();
            Double avg = sum / totalRatings;
            office.setAvgRating(avg);
        } else {
            office.setAvgRating(0.0);
        }
        officeRepository.save(office);
    }
}
