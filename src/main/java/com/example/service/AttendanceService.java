package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.model.Attendance;
import com.example.model.AttendanceDto;
import com.example.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public void clockIn(AttendanceDto request) {
        Attendance attendance = Attendance.builder()
                .userId(request.getUserId())
                .clockInTime(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        attendanceRepository.save(attendance);
    }

    public void clockOut(AttendanceDto request) {
        // 最後の出勤記録を取得し、退勤時間を埋める（簡易ロジック）
        Optional<Attendance> latest = attendanceRepository
            .findTopByUserIdOrderByCreatedAtDesc(request.getUserId());

        latest.ifPresent(att -> {
            att.setClockOutTime(LocalDateTime.now());
            attendanceRepository.save(att);
        });
    }

//    public List<Attendance> getAll() {
//        return attendanceRepository.findAll(); // 本来は userId フィルタが必要
//    }
    
    public List<Attendance> getAllByUser(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }
    
    public enum AttendanceStatus {
        NOT_CLOCKED_IN,
        CLOCKED_IN,
        CLOCKED_OUT
    }

    public AttendanceStatus getTodayStatus(Long userId) {
        Optional<Attendance> todayRecord = attendanceRepository
            .findTopByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                userId,
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().toLocalDate().atTime(23, 59, 59)
            );

        if (todayRecord.isEmpty()) {
            return AttendanceStatus.NOT_CLOCKED_IN;
        }

        Attendance record = todayRecord.get();
        if (record.getClockInTime() != null && record.getClockOutTime() == null) {
            return AttendanceStatus.CLOCKED_IN;
        } else if (record.getClockInTime() != null && record.getClockOutTime() != null) {
            return AttendanceStatus.CLOCKED_OUT;
        } else {
            return AttendanceStatus.NOT_CLOCKED_IN;
        }
    }
}
