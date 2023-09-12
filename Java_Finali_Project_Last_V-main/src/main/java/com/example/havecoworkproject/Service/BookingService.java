package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.DTO.ScheduleDTO;
import com.example.havecoworkproject.Repository.*;
import com.example.havecoworkproject.Table.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final OfficeRepository officeRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthRepository authRepository;
    private final CompanyRepository companyRepository;


    public List<Booking> getAllBookingsByClient(Integer user_id) {
        return bookingRepository.findBookingsByClientId(user_id);
    }

    public List<Booking> getAllBookingsByCompany(Integer user_id) {

        return bookingRepository.findBookingsByCompanyId(user_id);
    }

    public void newBooking(Integer clientId, ScheduleDTO scheduleDTO, Integer officeId) {
        Office office = officeRepository.findOfficeById(officeId);
        Client client = clientRepository.findClientById(clientId);

        if (office == null) {
            throw new ApiException("Office not found");
        }
        if (client == null) {
            throw new ApiException("Client not found");
        }

        List<Integer> scheduleIds = scheduleDTO.getBookingSchedle();

        if (scheduleIds.isEmpty()) {
            throw new ApiException("No schedules provided for booking");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Integer scheduleId : scheduleIds) {
            Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

            if (schedule == null) {
                throw new ApiException("Schedule not found in the office");
            }
            if (schedule.getStartDate().isBefore(currentDateTime)) {
                throw new ApiException("Cannot book past schedules");
            }
        }

        Company company = companyRepository.findCompanyById(office.getCompany().getId());
        Booking booking = new Booking();

        booking.setOffice(office);
        booking.setClient(client);
        booking.setCompany(company);

        Double totalPrice = office.getPrice() * scheduleIds.size();
        booking.setPrice(totalPrice);
        booking.setCompanyName(office.getCompanyName());

        Schedule firstSchedule = scheduleRepository.findScheduleById(scheduleIds.get(0));
        booking.setStartDate(firstSchedule.getStartDate());

        Schedule lastSchedule = scheduleRepository.findScheduleById(scheduleIds.get(scheduleIds.size() - 1));
        LocalDateTime endDate = lastSchedule.getStartDate().plusHours(1);
        booking.setEndDate(endDate);

        booking.setReason(scheduleDTO.getReason());
        bookingRepository.save(booking);

        Booking savedBooking = bookingRepository.findTopByOrderByIdDesc();

        for (Integer scheduleId : scheduleIds) {
            Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

            if (!schedule.getIsAvailable()) {
                throw new ApiException("Unavailable time");
            }
            schedule.setIsAvailable(false);
            schedule.setBooking(savedBooking);
            scheduleRepository.save(schedule);
        }
    }


    public void confirmBooking(Integer company_id,Integer Booking_id) {
        Booking booking = bookingRepository.findBookingById(Booking_id);
        if (booking == null) {
            throw new ApiException("booking is not found");
        }
        if(!booking.getCompany().getId().equals(company_id)){
            throw new ApiException(("Unauthorized"));
        }
        booking.setStutas("Confirm");
        bookingRepository.save(booking);

    }

    //company OR clinet
    public void cancelBooking(Integer company_id,Integer booking_id, Integer office_id, Integer schedule_id) {
        Booking booking = bookingRepository.findBookingById(booking_id);
        Office office = booking.getOffice();
        if (booking == null) {
            throw new ApiException("booking is not found");
        }
        if(!booking.getCompany().getId().equals(company_id)){
            throw new ApiException(("Unauthorized"));
        }
        booking.setStutas("Cancel");
        scheduleRepository.findScheduleByOfficeAndId(office, schedule_id).setIsAvailable(true);
        bookingRepository.save(booking);

}

    public List<Booking> getAllPendingBookingsByClientId(Integer client_id) { // done
        Client client = clientRepository.findClientById(client_id);
        if (client == null) {
            throw new ApiException("Client not found");
        }
        List<Booking> pendingBookings = bookingRepository.findBookingsByStutas("Pending");
        if (pendingBookings.isEmpty()) {
            throw new ApiException("No pending Booking");
        }
        return pendingBookings;
    }

    public List<Booking> getAllConfirmBookingsByClientId(Integer client_id) { //done
        Client client = clientRepository.findClientById(client_id);
        if (client == null) {
            throw new ApiException("Client not found");
        }
        List<Booking> confirmBookings = bookingRepository.findBookingsByStutas("Confirm");
        if (confirmBookings.isEmpty()) {
            throw new ApiException("No Confirm Booking");
        }
        return confirmBookings;
    }

    public List<Booking> getAllCancelBookingsByClientId(Integer client_id) { // done
        Client client = clientRepository.findClientById(client_id);
        if (client == null) {
            throw new ApiException("Client not found");
        }
        List<Booking> cancelBookings = bookingRepository.findBookingsByStutas("Cancel");
        if (cancelBookings.isEmpty()) {
            throw new ApiException("No Cancel Booking");
        }

        return cancelBookings;
    }

    public List<Booking> getAllCompleteBookingsByClientId(Integer client_id) { // done
        Client client = clientRepository.findClientById(client_id);
        if (client == null) {
            throw new ApiException("Client not found");
        }
        List<Booking> completeBookings = bookingRepository.findBookingsByStutas("Complete");
        if (completeBookings.isEmpty()) {
            throw new ApiException("No Complete Booking");
        }
        return completeBookings;
    }


    public void BookingIsComplete(Integer company_id, Integer bookingId) { // done
        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new ApiException("Booking not found");
        }
        if (!booking.getCompany().getId().equals(company_id)) {
            throw new ApiException("Access denied. You are not the owner of this booking");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime bookingEndDate = booking.getEndDate();

        if (currentDateTime.isBefore(bookingEndDate)) {
            if ("Confirm".equals(booking.getStutas())) {
                booking.setStutas("Complete");
                bookingRepository.save(booking);
            } else {
                throw new ApiException("Booking has not been confirmed, so its status cannot be changed to completed");
            }
        } else {
            throw new ApiException("Booking has not ended yet");
        }
    }

    public void cancelOfficeBooking(Integer client_id, Integer bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new ApiException("Booking not found");
        }
        if (!booking.getClient().getId().equals(client_id)) {
            throw new ApiException("Access denied. You are not the owner of this booking.");
        }
        if (!(booking.getStutas().equals("Pending") || booking.getStutas().equals("Confirm"))) {
            throw new ApiException("Booking status is not eligible for cancellation.");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime bookingStartTime = booking.getStartDate();
        long hoursUntilBooking = ChronoUnit.HOURS.between(currentTime, bookingStartTime);

        if (hoursUntilBooking < 24) {
            throw new ApiException("Booking cannot be canceled as it is within 24 hours of the start time.");
        }

        booking.setStutas("Cancel");
        bookingRepository.save(booking);

        List<Schedule> schedules = scheduleRepository.findSchedulesByBooking(booking);

        for (Schedule schedule : schedules) {
            schedule.setIsAvailable(true);
            schedule.setBooking(null);
            scheduleRepository.save(schedule);
        }
    }

    public void extendBooking(Integer clientId, ScheduleDTO scheduleDTO, Integer officeId) {
        Office office = officeRepository.findOfficeById(officeId);
        Client client = clientRepository.findClientById(clientId);

        if (office == null) {
            throw new ApiException("Office not found");
        }
        if (client == null) {
            throw new ApiException("Client not found");
        }

        List<Integer> scheduleIds = scheduleDTO.getBookingSchedle();

        if (scheduleIds.isEmpty()) {
            throw new ApiException("No schedules provided for booking");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Integer scheduleId : scheduleIds) {
            Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

            if (schedule == null) {
                throw new ApiException("Schedule not found in the office");
            }

            if (schedule.getStartDate().isBefore(currentDateTime)) {
                throw new ApiException("Cannot book past schedules");
            }
        }

        Company company = companyRepository.findCompanyById(office.getCompany().getId());
        Booking booking = new Booking();
        if (booking.getStutas().equals("Confirm")) {
            booking.setOffice(office);
            booking.setClient(client);
            booking.setCompany(company);

            Double totalPrice = office.getPrice() * scheduleIds.size();
            booking.setPrice(totalPrice);
            booking.setCompanyName(office.getCompanyName());

            Schedule firstSchedule = scheduleRepository.findScheduleById(scheduleIds.get(0));
            booking.setStartDate(firstSchedule.getStartDate());

            Schedule lastSchedule = scheduleRepository.findScheduleById(scheduleIds.get(scheduleIds.size() - 1));
            LocalDateTime endDate = lastSchedule.getStartDate().plusHours(1);
            booking.setEndDate(endDate);

            booking.setReason(scheduleDTO.getReason());
            bookingRepository.save(booking);

            Booking savedBooking = bookingRepository.findTopByOrderByIdDesc();

            //checkScheduleWithinOfficeHours(scheduleDTO,officeId);

            for (Integer scheduleId : scheduleIds) {
                Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

                if (!schedule.getIsAvailable()) {
                    throw new ApiException("Unavailable time");
                }
                schedule.setIsAvailable(false);
                schedule.setBooking(savedBooking);
                scheduleRepository.save(schedule);
            }
        }
    }
}


