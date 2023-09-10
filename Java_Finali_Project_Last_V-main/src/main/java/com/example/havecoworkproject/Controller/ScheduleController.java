package com.example.havecoworkproject.Controller;

import com.example.havecoworkproject.Api.ApiResponse;
import com.example.havecoworkproject.Service.ScheduleService;
import com.example.havecoworkproject.Table.Schedule;
import com.example.havecoworkproject.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hive_cowork/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
//    private final OfficeController officeController;
//    private final BookingService bookingService;

    @GetMapping("/get/{office_id}")
    public ResponseEntity getAllSchedulesByOfficeId(@PathVariable Integer office_id){

        return ResponseEntity.status(200).body(scheduleService.getAllSchedulesByOfficeId(office_id));

    }
    @PutMapping("/update/{office_id}/{schedule_id}")
    public ResponseEntity updateSchedule(@AuthenticationPrincipal User user, @PathVariable Integer office_id, @PathVariable Integer schedule_id){

        scheduleService.updateSchedule(user.getId() ,office_id,schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));

    }
    @DeleteMapping("/delete/{office_id}/{schedule_id}")
    public ResponseEntity deleteSchedule(@AuthenticationPrincipal User user, @PathVariable Integer office_id,@PathVariable Integer schedule_id){

        scheduleService.deleteSchedule(user.getId(),office_id,schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("delete successfully"));
    }

    @PostMapping("/add-times/{office_id}")
    public ResponseEntity addOfficeTimesAvailable(@AuthenticationPrincipal User user, @PathVariable Integer office_id, @RequestBody List<Schedule> schedules) {
        scheduleService.addOfficeTimesAvailable(user.getId(), office_id, schedules);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Office times added successfully"));
    }
}
