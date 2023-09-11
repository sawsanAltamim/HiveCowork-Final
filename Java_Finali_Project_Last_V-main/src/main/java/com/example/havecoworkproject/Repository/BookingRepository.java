package com.example.havecoworkproject.Repository;


import com.example.havecoworkproject.Table.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {


    Booking findTopByOrderByIdDesc();

    Booking findBookingById(Integer id);

    //List<Booking> findByClient_IdAndStutas(Integer clientId, String status);

    //List<Booking> findAllByClient_IdAndStutas(Integer clientId, String status);

    List<Booking> findBookingsByStutas(String status);
    List<Booking> findBookingsByClientId(Integer user_id);
    Booking findBookingByOfficeIdAndClientId(Integer office_id, Integer client_id);

    List<Booking> findBookingsByCompanyId(Integer user_id);

    //Booking findBookingByOffice(Office office);
}


