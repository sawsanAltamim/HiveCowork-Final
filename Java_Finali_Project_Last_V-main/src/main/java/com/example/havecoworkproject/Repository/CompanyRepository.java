package com.example.havecoworkproject.Repository;

import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(Integer id_company);
    List<Company> findCompaniesByAcceptable(Boolean isAcceptable);

    Company findByUserAndNameCompany(User user, String nameCompany);
}
