package com.example.havecoworkproject.Service;

import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Repository.OfficeRepository;
import com.example.havecoworkproject.Repository.ServiceRepository;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final OfficeRepository officeRepository;
    private final CompanyRepository companyRepository;

    public List<Services> getServicesByOffice(Integer company_id, Integer office_id){
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }
        return serviceRepository.findServicesByOfficeId(office_id);
    }
    public void addService(Integer company_id, Integer Office_id, Services service){
        Office office = officeRepository.findOfficeById(Office_id);
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }

        if (office == null) {
            throw new ApiException("Office not found");
        }
        if(!office.getCompany().getId().equals(company_id)){
            throw new ApiException("Not authorisation");

        }
        service.setOffice(office);
        serviceRepository.save(service);
    }

    public void updateService(Integer company_id, Integer service_id, Services service){
        Services service1 = serviceRepository.findServiceById(service_id);
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }
        if (service1 == null){
            throw new ApiException("Not found id");
        }
        if(!service1.getOffice().getCompany().getId().equals(company_id)){
            throw new ApiException("Not authorisation");

        }
        service1.setName(service.getName());
        service1.setOffice(service.getOffice());
        serviceRepository.save(service1);
    }
    public void deleteService(Integer company_id,Integer service_id){

        Services service = serviceRepository.findServiceById(service_id);
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }
        if (service == null){
            throw new ApiException("not found id");
        }
        if(!service.getOffice().getCompany().getId().equals(company_id)){
            throw new ApiException("Not authorisation");

        }
        serviceRepository.delete(service);
    }
}
