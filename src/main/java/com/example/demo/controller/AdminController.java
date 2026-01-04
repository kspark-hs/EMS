package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    /* ===========================
       ✅ 관리자 대시보드
    ============================ */

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("contentTemplate", "admin/admin-dashboard");
        return "index";
    }

    /* ===========================
       발전소 관리
    ============================ */

    @GetMapping("/management/member")
    public String managementMember(Model model) {
        model.addAttribute("contentTemplate", "admin/management/member");
        return "index";
    }

    @GetMapping("/management/plant")
    public String managementPlant(Model model) {
        model.addAttribute("contentTemplate", "admin/management/plant");
        return "index";
    }

    @GetMapping("/management/info")
    public String managementInfo(Model model) {
        model.addAttribute("contentTemplate", "admin/management/info");
        return "index";
    }

    /* ===========================
       통계 / 리포트
    ============================ */

    @GetMapping("/detail/pv-inverter")
    public String detailPvInverter(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/pv-inverter");
        return "index";
    }

    @GetMapping("/detail/battery")
    public String detailBattery(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/battery");
        return "index";
    }

    @GetMapping("/detail/battery-rack")
    public String detailBatteryRack(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/battery-rack");
        return "index";
    }

    @GetMapping("/detail/pcs")
    public String detailPcs(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/pcs");
        return "index";
    }

    @GetMapping("/detail/pv-power-meter")
    public String detailPvPowerMeter(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/pv-power-meter");
        return "index";
    }

    @GetMapping("/detail/environment-module")
    public String detailEnvironmentModule(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/environment-module");
        return "index";
    }

    @GetMapping("/detail/drive-detail")
    public String detailDriveDetail(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/drive-detail");
        return "index";
    }

    @GetMapping("/detail/fault-detail")
    public String detailFaultDetail(Model model) {
        model.addAttribute("contentTemplate", "admin/detail/fault-detail");
        return "index";
    }

    /* ===========================
       기본 설정
    ============================ */

    @GetMapping("/setting/basic/setting-info")
    public String settingBasicInfo(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/basic/setting-info");
        return "index";
    }

    @GetMapping("/setting/basic/basic-drive")
    public String settingBasicDrive(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/basic/basic-drive");
        return "index";
    }

    @GetMapping("/setting/basic/capacity")
    public String settingBasicCapacity(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/basic/capacity");
        return "index";
    }

    /* ===========================
       배터리 설정
    ============================ */

    @GetMapping("/setting/battery/charge-discharge-drive")
    public String batteryChargeDischargeDrive(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/charge-discharge-drive");
        return "index";
    }

    @GetMapping("/setting/battery/bat-fault")
    public String batteryFault(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/bat-fault");
        return "index";
    }

    @GetMapping("/setting/battery/bat-drive-condition")
    public String batteryDriveCondition(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/bat-drive-condition");
        return "index";
    }

    @GetMapping("/setting/battery/charge-discharge-power")
    public String batteryChargeDischargePower(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/charge-discharge-power");
        return "index";
    }

    @GetMapping("/setting/battery/bat-relay")
    public String batteryRelay(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/bat-relay");
        return "index";
    }

    @GetMapping("/setting/battery/bat-room-temp-humidity")
    public String batteryRoomTempHumidity(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/battery/bat-room-temp-humidity");
        return "index";
    }

    /* ===========================
       충/방전 스케줄
    ============================ */

    @GetMapping("/setting/schedule/charge-discharge-schedule")
    public String chargeDischargeSchedule(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/schedule/charge-discharge-schedule");
        return "index";
    }

    /* ===========================
       PCS 설정
    ============================ */

    @GetMapping("/setting/pcs/charge-level")
    public String pcsChargeLevel(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/pcs/charge-level");
        return "index";
    }

    @GetMapping("/setting/pcs/discharge-level")
    public String pcsDischargeLevel(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/pcs/discharge-level");
        return "index";
    }

    @GetMapping("/setting/pcs/pcs-fault")
    public String pcsFault(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/pcs/pcs-fault");
        return "index";
    }

    /* ===========================
       발전금액관리
    ============================ */

    @GetMapping("/cost/weighting")
    public String costWeighting(Model model) {
        model.addAttribute("contentTemplate", "admin/cost/weighting");
        return "index";
    }

    @GetMapping("/cost/smp")
    public String costSmp(Model model) {
        model.addAttribute("contentTemplate", "admin/cost/smp");
        return "index";
    }

    @GetMapping("/cost/rec")
    public String costRec(Model model) {
        model.addAttribute("contentTemplate", "admin/cost/rec");
        return "index";
    }

    /* ===========================
       시스템 관리
    ============================ */

    @GetMapping("/system/common-code")
    public String systemCommonCode(Model model) {
        model.addAttribute("contentTemplate", "admin/system/common-code");
        return "index";
    }

    @GetMapping("/system/program-list")
    public String programList(Model model) {
        model.addAttribute("contentTemplate", "admin/system/program-list");
        return "index";
    }

    @GetMapping("/system/program-authority")
    public String programAuthority(Model model) {
        model.addAttribute("contentTemplate", "admin/system/program-authority");
        return "index";
    }

    @GetMapping("/system/facility-code")
    public String facilityCode(Model model) {
        model.addAttribute("contentTemplate", "admin/system/facility-code");
        return "index";
    }

    /* ===========================
       긴급 제어
    ============================ */

    @GetMapping("/emergency/manual-drive-control")
    public String manualDriveControl(Model model) {
        model.addAttribute("contentTemplate", "admin/emergency/manual-drive-control");
        return "index";
    }

    @GetMapping("/emergency/emergency-control")
    public String emergencyControl(Model model) {
        model.addAttribute("contentTemplate", "admin/emergency/emergency-control");
        return "index";
    }

    @GetMapping("/emergency/rtu-control")
    public String rtuControl(Model model) {
        model.addAttribute("contentTemplate", "admin/emergency/rtu-control");
        return "index";
    }

    @GetMapping("/emergency/time-sync")
    public String timeSync(Model model) {
        model.addAttribute("contentTemplate", "admin/emergency/time-sync");
        return "index";
    }

    /* ===========================
       에어컨 설정
    ============================ */

    @GetMapping("/setting/aircon/air-con-schedule")
    public String airConSchedule(Model model) {
        model.addAttribute("contentTemplate", "admin/setting/aircon/air-con-schedule");
        return "index";
    }
}

