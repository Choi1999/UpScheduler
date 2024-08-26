package com.sparta.upscheduler.repository;

import com.sparta.upscheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}