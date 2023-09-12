package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.AuthRepository;

import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final CompanyRepository companyRepository;


    public void registerClient(User user) {
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("CLIENT");
        authRepository.save(user);

    }
    public void registerCompany(User user) {
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("COMPANY");
        authRepository.save(user);

    }
//    public void registerAdmin(User user) {
//        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(hash);
//            user.setRole("ADMIN");
//        authRepository.save(user);
//
//    }
    //Admin
    public List<Company> getAllOrderCompanyRegister(){
        return companyRepository.findCompaniesByAcceptable(false);

    }
    //Admin
    public void confirmCompanyAccount(Integer company_id) {
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null) {
            throw new ApiException("Company is not found");
        }
        company.setAcceptable(true);
        companyRepository.save(company);

    }

    //Admin
    public void  rejectCompanyAccount(Integer company_id) {
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null) {
            throw new ApiException("Company is not found");
        }
        companyRepository.delete(company);

    }
    public List<User> getAllUser(){
        return authRepository.findAll();
    }

    public void addUser(User user){
        authRepository.save(user);
    }
}
