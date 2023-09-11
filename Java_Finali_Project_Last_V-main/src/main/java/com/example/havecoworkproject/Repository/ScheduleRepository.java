package com.example.havecoworkproject.Repository;


import com.example.havecoworkproject.Table.Booking;
import com.example.havecoworkproject.Table.Office;
import com.example.havecoworkproject.Table.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {


  List<Schedule> findAllScheduleByOfficeId(Integer id);
  Schedule findScheduleById(Integer schedule_id);
  Schedule findScheduleByOfficeId(Integer id);
  Schedule findScheduleByOfficeAndId(Office office, Integer schedule_id);

    List<Schedule> findSchedulesByBooking(Booking booking);
}
