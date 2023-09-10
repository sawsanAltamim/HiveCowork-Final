package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.DTO.CompanyDTO;
import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Repository.AuthRepository;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AuthRepository authRepository;

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public void addCompany(Integer user_id, CompanyDTO companyDTO) { //done
        User user = authRepository.findUserById(user_id);
        if (user == null) {
            throw new ApiException("ID not found");
        }

        Company existingCompany = companyRepository.findByUserAndNameCompany(user, companyDTO.getNameCompany());
        if (existingCompany != null) {
            throw new ApiException("Company with the same name already exists for this user");
        }

        Company company = new Company();
        company.setNameCompany(companyDTO.getNameCompany());
        company.setDescription(companyDTO.getDescription());
        company.setLocation(companyDTO.getLocation());

        company.setUser(user);
        companyRepository.save(company);
    }

    public void updateCompany(Integer user_id, Integer company_id, CompanyDTO companyDTO) { // done
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null) {
            throw new ApiException("Company not found");
        }

        //User user = authRepository.findUserById(user_id);
        if (!company.getUser().getCompany().getId().equals(user_id)) {
            throw new ApiException("Assess denied");
        }
        company.setNameCompany(companyDTO.getNameCompany());
        company.setDescription(companyDTO.getDescription());
        company.setLocation(companyDTO.getLocation());

        companyRepository.save(company);
    }

    public void deleteCompany(Integer user_id, Integer company_id) { // done
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null) {
            throw new ApiException("Company not found");
        }
        if (!company.getUser().getCompany().getId().equals(user_id)) {
            throw new ApiException("Assess denied");
        }
        companyRepository.delete(company);
    }
}