package com.example.havecoworkproject.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final UserDetailsService userDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(userDetailsService);
       daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
       return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http)
            throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/api/v1/hive_cowork/auth/register/client").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/auth/register/company").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/auth/register/admin").permitAll()
                .requestMatchers ("/api/v1/auth/get_order_company_register").hasAuthority("ADMIN")
                .requestMatchers ("/api/v1/auth//confirm_company_account/{company_id}").hasAuthority("ADMIN")
                .requestMatchers ("/api/v1/auth/reject_company_account/{company_id}").hasAuthority("ADMIN")
                .requestMatchers ("/api/v1/hive_cowork/company/get").hasAuthority("ADMIN")
                .requestMatchers ("/api/v1/hive_cowork/client/get").hasAuthority("ADMIN")
                .requestMatchers ("/api/v1/hive_cowork/office/get_by_company").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/office/add").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/office/update/{office_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/office/delete/{office_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/schedule/update/{office_id}/{schedule_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/schedule/delete/{office_id}/{schedule_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/schedule/add-times/{office_id}").hasAuthority("COMPANY")
                .requestMatchers ("api/v1/hive_cowork/services/add/{office_id}").hasAuthority("COMPANY")
                .requestMatchers ("api/v1/hive_cowork/services/update/{service_id}").hasAuthority("COMPANY")
                .requestMatchers ("api/v1/hive_cowork/services/delete/{service_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/booking/get_by_company").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/booking/update/{booking_id}").hasAuthority("COMPANY")//CONFIRM BOOKING
                .requestMatchers ("/api/v1/hive_cowork/booking/delete/{booking_id}/{office_id}/{schedule_id}").hasAuthority("COMPANY")//REJECT BOOKING
                .requestMatchers ("/api/v1/hive_cowork/rating/get_by_office/{office_id}").hasAuthority("COMPANY")
                .requestMatchers ("/api/v1/hive_cowork/booking/get_by_user").hasAuthority("CLIENT")
                .requestMatchers ("/api/v1/hive_cowork/booking/add/{office_id}").hasAuthority("CLIENT")
                .requestMatchers ("/api/v1/hive_cowork/booking/cancel-office-booking/{booking_id}").hasAuthority("CLIENT")
                .requestMatchers ("/api/v1/hive_cowork/rating/add/{office_id}").hasAuthority("CLIENT")
                .requestMatchers ("/api/v1/hive_cowork/office/get").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/office/all-sorted-by-rating").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/schedule/get/{office_id}").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/services/get_by_Office/{office_id}").permitAll()
                .requestMatchers ("/api/v1/hive_cowork/client/Search/{numberPerson}").permitAll()

                .anyRequest().authenticated()
//                .requestMatchers ("/api/v1/hive_cowork/booking/bookings-pending/{client_id}").authenticated()

                //                .requestMatchers ("/api/v1/hive_cowork/booking/bookings-complete").authenticated()
//                .requestMatchers ("/api/v1/hive_cowork/booking/bookings-cancel/{client_id}").authenticated()
//                .requestMatchers ("/api/v1/hive_cowork/booking/bookings-confirm/{client_id}").authenticated()
//                .requestMatchers ("/api/v1/hive_cowork/booking/newExtendBooking/{office_id}/{booking_id}/{schedule_id}").authenticated()

//
                //.anyRequest().authenticated()
                //.anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/vi/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession (true)
                .and ()
                .httpBasic() ;
        return http.build();
    }
}
