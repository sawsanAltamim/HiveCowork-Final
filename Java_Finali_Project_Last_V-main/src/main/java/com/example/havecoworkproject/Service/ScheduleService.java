package com.example.havecoworkproject.Service;


import com.example.havecoworkproject.Api.ApiException;
import com.example.havecoworkproject.Repository.CompanyRepository;
import com.example.havecoworkproject.Repository.OfficeRepository;
import com.example.havecoworkproject.Repository.ScheduleRepository;
import com.example.havecoworkproject.Table.Company;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final OfficeRepository officeRepository;
    private final CompanyRepository companyRepository;

    public List<Schedule> getAllSchedulesByOfficeId(Integer office_id){
       return scheduleRepository.findAllScheduleByOfficeId(office_id);
    }
    public void addSchedule(Integer company_id, Integer office_id, Schedule schedule){ // done
        Office office = officeRepository.findOfficeById(office_id);
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

        if (!office.getCompany().getId().equals(company_id)){
            throw new ApiException("Access denied. You are not the owner of this office.");
        }

        schedule.setOffice(office);
        scheduleRepository.save(schedule);

    }

    //clinet
    public void updateSchedule(Integer company_id,Integer office_id,Integer schedule_id) { // done
        Office office = officeRepository.findOfficeById(office_id);
        Schedule schedule = scheduleRepository.findScheduleByOfficeAndId(office, schedule_id);
        Company company = companyRepository.findCompanyById(company_id);
        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }

        if (schedule == null) {
            throw new ApiException("schedule not found");
        }
        if (schedule.getIsAvailable() == false) {
            throw new ApiException("Schedule is unavailable ");
        }
        if(!office.getCompany().getId().equals(company_id)) {
            throw new ApiException("Access denied. You are not the owner of this office.");
        }
        schedule.setIsAvailable(true);
        scheduleRepository.save(schedule);
    }

    //company
    public void deleteSchedule(Integer company_id ,Integer office_id, Integer schedule_id){ // done
        Office office = officeRepository.findOfficeById(office_id);
        Schedule schedule = scheduleRepository.findScheduleByOfficeAndId(office,schedule_id);
        Company company = companyRepository.findCompanyById(company_id);

        if (company == null){
            throw new ApiException("Company not found");
        }

        if (company.getAcceptable() == false){
            throw new ApiException("Unauthorized");
        }
        if (schedule == null){
            throw new ApiException("Schedule not found");
        }
        if (schedule.getIsAvailable() == false){
            throw new ApiException("Schedule is booked up ");
        }
        if(!office.getCompany().getId().equals(company_id)) {
            throw new ApiException("Access denied. You are not the owner of this office.");
        }
        schedule.setIsAvailable(false);
        scheduleRepository.save(schedule);
    }

    public void addOfficeTimesAvailable(Integer company_id,Integer office_id, List<Schedule> schedules) { // done
        Office office = officeRepository.findOfficeById(office_id);
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

        if (!office.getCompany().getId().equals(company_id)){
            throw new ApiException("Access denied. You are not the owner of this office.");
        }

        for (Schedule schedule : schedules) {
            schedule.setOffice(office);
            scheduleRepository.save(schedule);
        }
    }
}
