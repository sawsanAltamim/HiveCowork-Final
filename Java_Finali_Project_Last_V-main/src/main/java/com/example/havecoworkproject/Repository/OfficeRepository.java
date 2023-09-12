package com.example.havecoworkproject.Repository;


import com.example.havecoworkproject.Table.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Integer> {

    Office findOfficeById (Integer id);
   // Office findOfficeById (Integer id);
    List<Office> findOfficeByCompanyId(Integer company_id);
    List<Office> findOfficeByNumberPerson(Integer numberPerson);

    @Query("SELECT o, COALESCE(AVG(r.numRate), 0) " +
            "FROM Office o " +
            "LEFT JOIN o.ratings r " +
            "GROUP BY o " +
            "ORDER BY AVG(r.numRate) DESC")
    List<Object[]> findAllOfficesWithAvgRatings();

    List<Office> findOfficeByLocation(String location);

    //List<Office> findOfficeBynAndCompanyName(String nameCompany);
}
