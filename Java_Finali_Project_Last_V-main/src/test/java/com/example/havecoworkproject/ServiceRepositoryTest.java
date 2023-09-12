package com.example.havecoworkproject;

import com.example.havecoworkproject.Repository.ServiceRepository;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.Services;
import com.example.havecoworkproject.Table.User;
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
public class ServiceRepositoryTest {

    @Autowired
    ServiceRepository serviceRepository;
    Office office;
    User user;
    Services services;

    @BeforeEach
    void setUp() {
      //  user=new User(1,"afnan","aa1234@","afnan@gmail.com","05436800",null,null);
      //  office=new Office(null,12.3,12,"","12A","abha",null,null,null);
        services=new Services(null,"afnan1",null);
    }
    @Test
    public void findServiceById(){
        serviceRepository.save(services);
        services=serviceRepository.findServiceById(services.getId());
        Assertions.assertThat(services).isEqualTo(services);
    }

    @Test
    public void findServicesByOfficeId() {
        serviceRepository.save(services);
        List<Services> servicesList = serviceRepository.findServicesByOfficeId(services.getOffice().getId());
        Assertions.assertThat(servicesList).containsExactly(services);
    }
}
