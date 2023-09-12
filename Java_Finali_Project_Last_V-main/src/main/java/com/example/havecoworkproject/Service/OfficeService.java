package com.example.havecoworkproject.Service;


import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Repository.OfficeRepository;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OfficeService {
  private final OfficeRepository officeRepository;
  private final CompanyRepository companyRepository;
  //private final AuthRepository authRepository;

  public List<Office> getAllOffice(){
    return officeRepository.findAll();
  }

  public List<Office> getOfficeByCompany(Integer company_id){
    Company company = companyRepository.findCompanyById(company_id);
    if (company == null){
      throw new ApiException("Company not found");
    }

    if (company.getAcceptable() == false){
      throw new ApiException("Unauthorized");
    }

    return officeRepository.findOfficeByCompanyId(company_id);
  }
  public void addOffice(Integer user_id,Office office){
    Company company = companyRepository.findCompanyById(user_id);
    if (company == null){
      throw new ApiException("Company not found");
    }

    if (company.getAcceptable() == false){
      throw new ApiException("Unauthorized");
    }

    office.setCompany(company);
    office.setCompanyName(company.getNameCompany());
    officeRepository.save(office);
  }

  public void updateOffice(Integer user_id,Integer office_id, Office office){ // error
    Office office1=officeRepository.findOfficeById(office_id);
    Company company = companyRepository.findCompanyById(user_id);
    if (office1 == null){
      throw new ApiException("not found id");
    }
    if (company == null){
      throw new ApiException("Company not found");
    }
    if(!office.getCompany().getId().equals(user_id)) {
      throw new ApiException(" Unauthorized");
    }
    if (company.getAcceptable() == false){
      throw new ApiException("Unauthorized");
    }
    office1.setService(office.getService());
    office1.setCategory(office.getCategory());
    office1.setAddress(office.getAddress());
    office1.setLocation(office.getLocation());
    office1.setRatings(office.getRatings());
    office1.setNumberPerson(office.getNumberPerson());
    office1.setPrice(office.getPrice());
    officeRepository.save(office1);
  }

  public void deleteOffice(Integer user_id,Integer office_id){
    Office office=officeRepository.findOfficeById(office_id);
    Company company = companyRepository.findCompanyById(user_id);

    if (company == null){
      throw new ApiException("Company not found");
    }
    if ( office == null){
      throw new ApiException("Not found id");
    }
    if(!office.getCompany().getId().equals(user_id) ) {
      throw new ApiException("Not Authorisation");
    }
    if (company.getAcceptable() == false){
      throw new ApiException("Unauthorized");
    }
    officeRepository.delete(office);
  }

  public List<Office> getAllOfficesOrderByRating() {
    List<Object[]> officesWithAvgRatings = officeRepository.findAllOfficesWithAvgRatings();
    List<Office> offices = new ArrayList<>();

    for (Object[] row : officesWithAvgRatings) {
      Office office = (Office) row[0];
      Double avgRating = (Double) row[1];
      office.setAvgRating(avgRating);
      offices.add(office);
    }
    offices.sort(Comparator.comparing(Office::getAvgRating).reversed());
    return offices;
  }

  public List<Office> getAllOfficesOrderByPrice() {
    List<Office> offices = officeRepository.findAll();
    offices.sort(Comparator.comparing(Office::getPrice));

    return offices;
  }

  public List<Office> SearchLocation(String location){
    List<Office> offices=officeRepository.findOfficeByLocation(location);
    if (offices == null) {
      throw new ApiException(" Location not found");
    }
    return offices;
  }
}
