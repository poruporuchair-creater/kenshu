package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.AttendanceDto;
import com.example.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public String showAttendancePage(Model model) {
        Long userId = 1L;
        model.addAttribute("history", attendanceService.getAllByUser(userId));
        model.addAttribute("status", attendanceService.getTodayStatus(userId));
        return "attendance";
    }

    @PostMapping("/start")
    public String clockIn(RedirectAttributes redirectAttributes) {
        AttendanceDto request = new AttendanceDto();
        request.setUserId(1L);
        attendanceService.clockIn(request);

        redirectAttributes.addFlashAttribute("message", "出勤打刻しました");
        return "redirect:/attendance";
    }

    @PostMapping("/end")
    public String clockOut(RedirectAttributes redirectAttributes) {
        AttendanceDto request = new AttendanceDto();
        request.setUserId(1L);
        attendanceService.clockOut(request);

        redirectAttributes.addFlashAttribute("message", "退勤打刻しました");
        return "redirect:/attendance";
    }
}
