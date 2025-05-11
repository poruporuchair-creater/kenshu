package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findTopByUserIdOrderByCreatedAtDesc(Long userId);
    
    Optional<Attendance> findTopByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
    	    Long userId,
    	    LocalDateTime start,
    	    LocalDateTime end
    	);
    
    List<Attendance> findByUserId(Long userId);
}
