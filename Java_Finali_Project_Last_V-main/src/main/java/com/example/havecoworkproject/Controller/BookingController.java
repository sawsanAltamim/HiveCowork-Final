package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.DTO.ScheduleDTO;
import com.example.havecoworkproject.Service.BookingService;
import com.example.havecoworkproject.Table.Booking;
import com.example.havecoworkproject.Table.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hive_cowork/booking")
public class BookingController {

    private final BookingService bookingService;


    @GetMapping("/get_by_user")
    public ResponseEntity getAllBookingsByUser(@AuthenticationPrincipal User user){

        return ResponseEntity.status(200).body(bookingService. getAllBookingsByClient(user.getId()));

    }
    @GetMapping("/get_by_company")
    public ResponseEntity getAllBookingsByCompany(@AuthenticationPrincipal User user){

        return ResponseEntity.status(200).body(bookingService. getAllBookingsByCompany(user.getId()));

    }

    @PostMapping("/add/{office_id}")
    public ResponseEntity newBooking(@AuthenticationPrincipal User user, @RequestBody @Valid ScheduleDTO scheduleDTO, @PathVariable Integer office_id){

        bookingService.newBooking(user.getId(),scheduleDTO,office_id);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

    //company
    @PutMapping("/update/{booking_id}")
    public ResponseEntity confirmBooking(@AuthenticationPrincipal User user,@PathVariable Integer booking_id){

        bookingService.confirmBooking( user.getId(),booking_id);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

    }
    //company
    @DeleteMapping("/delete/{booking_id}/{office_id}/{schedule_id}")
    public ResponseEntity cancelBooking(@AuthenticationPrincipal User user, @PathVariable Integer booking_id, @PathVariable Integer office_id, @PathVariable Integer schedule_id){

        bookingService. cancelBooking(user.getId(),booking_id, office_id, schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("delete successfully"));

    }

    @GetMapping("/bookings-pending/{client_id}")
    public ResponseEntity getAllPendingBookingsByClientId(@AuthenticationPrincipal User user) {
        List<Booking> pendingBookings = bookingService.getAllPendingBookingsByClientId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(pendingBookings);
    }

    @GetMapping("/bookings-cancel/{client_id}")
    public ResponseEntity getAllCancelBookingsByClientId(@AuthenticationPrincipal User user) {
        List<Booking> cancelBookings = bookingService.getAllCancelBookingsByClientId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(cancelBookings);
    }

    @GetMapping("/bookings-complete")
    public ResponseEntity getAllCompleteBookingsByClientId(@AuthenticationPrincipal User user) {
        List<Booking> completeBookings = bookingService.getAllCompleteBookingsByClientId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(completeBookings);
    }

    @GetMapping("/bookings-confirm/{client_id}")
    public ResponseEntity getAllConfirmBookingsByClientId(@AuthenticationPrincipal User user) {
        List<Booking> confirmBookings = bookingService.getAllConfirmBookingsByClientId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(confirmBookings);
    }

    /*@PostMapping("/newExtendBooking/{office_id}/{booking_id}/{schedule_id}")
    public ResponseEntity newExtendBooking(@AuthenticationPrincipal User user, @RequestBody @Valid Booking booking, @PathVariable Integer office_id, @PathVariable List<Integer> schedule_id){
        bookingService. newExtendBooking(user.getId(), booking,office_id,schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }*/

    @PutMapping("/complete/{bookingId}")
    public ResponseEntity completeBooking(@AuthenticationPrincipal User user ,@PathVariable Integer bookingId) {
        bookingService.BookingIsComplete(user.getId(), bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Booking marked as complete"));
    }
    @PutMapping("/cancel-office-booking/{booking_id}")
    public ResponseEntity<ApiResponse> cancelOfficeBooking(@AuthenticationPrincipal User user,@PathVariable Integer booking_id) {
        bookingService.cancelOfficeBooking(user.getId(), booking_id);
        return ResponseEntity.ok(new ApiResponse("Office booking canceled successfully"));
    }

    /*@PostMapping("/check-schedule/{office_id}")
    public ResponseEntity checkScheduleWithinOfficeHours(@RequestBody Schedule schedule, @PathVariable Integer office_id) {
        bookingService.isScheduleWithinOfficeHours(schedule, office_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Schedule is within office hours"));
    }*/

    @PostMapping("/extendBooking/{office_id}")
    public ResponseEntity extendBooking(@AuthenticationPrincipal User user, @RequestBody @Valid ScheduleDTO scheduleDTO, @PathVariable Integer office_id){

        bookingService.extendBooking(user.getId(),scheduleDTO,office_id);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

}
