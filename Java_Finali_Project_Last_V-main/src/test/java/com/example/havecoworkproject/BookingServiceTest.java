package com.example.havecoworkproject;

import com.example.havecoworkproject.Repository.AuthRepository;
import com.example.havecoworkproject.Repository.BookingRepository;
import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Service.BookingService;
import com.example.havecoworkproject.Service.ClientService;
import com.example.havecoworkproject.Table.Booking;
import com.example.havecoworkproject.Table.Client;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BookingServiceTest {

    @InjectMocks
    ClientService clientService;

    @InjectMocks
    BookingService bookingService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AuthRepository authRepository;

    @Mock
    private BookingRepository bookingRepository;

    User user;

    Client client1;

    Client client2;

    Company company1;

    List<Client> clients;

    @BeforeEach
    void setUp() {
        user = new User(1, "suu1n", "12345", "CLIENT", "sawsan@gmail.com", "0561037115", null, null);
        authRepository.save(user);

        client1 = new Client(1, "Sawsan", 18, user, null, null);
        client2 = new Client(2, "Tamim", 22, user, null, null);
        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        company1 = new Company(2,"IBM",true,"Ryidh","new company",user,null,null);
    }

    @Test
    public void testGetAllBookingsByCompany() {
        List<Booking> companyBookings = new ArrayList<>();
        companyBookings.add(new Booking(1, "Company 1", null, null, 100.0, "Pending", "Reason 1", null, company1, null));
        companyBookings.add(new Booking(2, "Company 1", null, null, 200.0, "Pending", "Reason 2", null, company1, null));

        when(bookingRepository.findBookingsByCompanyId(user.getId())).thenReturn(companyBookings);

        List<Booking> result = bookingService.getAllBookingsByCompany(user.getId());

        assertEquals(companyBookings, result);
        verify(bookingRepository, times(1)).findBookingsByCompanyId(user.getId());
    }

    @Test
    public void testGetAllBookingsByClient() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1, "Company 1", null, null, 100.0, "Pending", "Reason 1", null, company1, null));
        bookings.add(new Booking(2, "Company 1", null, null, 200.0, "Pending", "Reason 2", null, company1, null));
        when(bookingRepository.findBookingsByClientId(1)).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookingsByClient(1);

        verify(bookingRepository, times(1)).findBookingsByClientId(1);

        assertEquals(bookings, result);
    }
    @Test
    public void testGetAllPendingBookingsByClientId() {
        List<Booking> pendingBookings = new ArrayList<>();
        pendingBookings.add(new Booking(1, "Company 1", null, null, 100.0, "Pending", "Reason 1", client1, null, null));
        pendingBookings.add(new Booking(2, "Company 2", null, null, 200.0, "Pending", "Reason 2", client1, null, null));

        when(clientRepository.findClientById(client1.getId())).thenReturn(client1);
        when(bookingRepository.findBookingsByStutas("Pending")).thenReturn(pendingBookings);

        List<Booking> result = bookingService.getAllPendingBookingsByClientId(client1.getId());

        assertEquals(pendingBookings, result);

        verify(clientRepository, times(1)).findClientById(client1.getId());

        verify(bookingRepository, times(1)).findBookingsByStutas("Pending");
    }
}
