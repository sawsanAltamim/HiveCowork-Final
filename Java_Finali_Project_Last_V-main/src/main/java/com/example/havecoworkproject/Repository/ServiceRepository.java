package com.example.havecoworkproject.Repository;


import com.example.havecoworkproject.Table.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Integer> {
    Services findServiceById(Integer id);
    List<Services> findServicesByOfficeId(Integer office_id);
}
