package com.example.havecoworkproject;

import com.example.havecoworkproject.Repository.ClientRepository;
import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Table.Company;
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
public class CompanyRepositoryTest {
    @Autowired
    CompanyRepository companyRepository;
    User user,user2;
    Company company1;
    Company company2;

    @BeforeEach
    void setUp() {
        user = new User(1,"suu1n","12345","CLIENT","sawsan@gmail.com","0561037115",null,null);

        company1=new Company(1,"non",true,"A1212","good",user,null,null);
        company2=new Company(2,"appal",true,"A1212","good",user,null,null);
        companyRepository.saveAll(List.of(company1,company2));
    }
    @Test
    public void findCompanyById() {
       // companyRepository.save(company1);
          Company company = companyRepository.findCompanyById(company1.getId());
        Assertions.assertThat(company).isEqualTo(company1);
    }
    @Test
    public void findCompaniesByAcceptableTest() {
        List<Company> companies = companyRepository.findCompaniesByAcceptable(true);
        Assertions.assertThat(companies).containsExactly(company1);
    }
    @Test
    public void findByUserAndNameCompany() {
        //companyRepository.save(company1);
        Company company = companyRepository.findByUserAndNameCompany(company1.getUser(), company1.getNameCompany());
        Assertions.assertThat(company).isEqualTo(company1);
    }
}
