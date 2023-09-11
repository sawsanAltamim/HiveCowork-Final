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
//    public void addRatinToOffice (Integer client_id, Integer office_id,Rating rating){
//        Office office = officeRepository.findOfficeById(office_id);
//        Client client = clientRepository.findClientById(client_id);
//        Booking booking = bookingRepository.findBookingByOfficeIdAndClientId(office_id,client_id);
//
//        if (client == null) {
//            throw new ApiException("Client not found");
//        }
//
//        if (office == null) {
//            throw new ApiException("Office not found");
//        }
//
//        if (bookingRepository.findBookingByOfficeIdAndClientId(office_id ,client_id) == null){
//            throw new ApiException("You can not rating you should booking first");
//        }
//
//        rating.setOffice(office);
//        rating.setClient(client);
//        rating.setNumRate(rating.getNumRate());
//        calculateAverageRating(client_id, rating.getId(),booking.getId());
//        ratingRepository.save(rating);
//
//    }
//    public void calculateAverageRating(Integer client_id,Integer rating_id,Integer booking_id) {
//       // Rating rating = ratingRepository.findRatingById(rating_id);
//       // if (rating == null) {
//       //     throw new ApiException("Rating not found");
//      //  }
//
//
//       // Office office = officeRepository.findOfficeById(office_id);
//     //   Client client = clientRepository.findClientById(client_id);
//
////        if (office == null) {
////            throw new ApiException("Office not found");
////        }
//
//        /*if (client == null) {
//            throw new ApiException("client not found");
//        }*/
//         Booking booking=bookingRepository.findBookingById(booking_id);
//        Office office = booking.getOffice();
//        if (!booking.getStutas().equals("Complete")) {
//            throw new ApiException("booking not Completed");
//        }
//            List<Rating> ratings = ratingRepository.findRatingByOfficeId(office.getId());
//            Integer totalRatings = ratings.size();
//           // Double sum = 0.0;
//            Double sum = ratings.stream().mapToDouble(Rating::getNumRate).sum();
//           // for (Rating rating1 : ratings) {
//               // sum += rating1.getNumRate();
//
//           // }
//            Double avg = sum / totalRatings;
//            office.setAvgRating(avg);
//            officeRepository.save(office);
//
//
//    }

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
        if (!booking.getStutas().equals("Complete")) {
            throw new ApiException("You can only rate an office after completing the booking");
        }


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
