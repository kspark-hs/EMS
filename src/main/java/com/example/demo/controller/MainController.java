package com.example.demo.controller;

import com.example.demo.domain.battery.service.*;
import com.example.demo.domain.battery.view.*;
import com.example.demo.domain.equipment.airconditioner.service.AirConditionerService;
import com.example.demo.domain.equipment.firesystem.service.FireSystemStatusTableService;
import com.example.demo.domain.equipment.temperaturehumidity.service.TemperatureHumidityStatusTableService;
import com.example.demo.domain.pvmeter.service.*;
import com.example.demo.domain.pcs.service.PcsStatusTableService;
import com.example.demo.domain.pcs.service.*;

import com.example.demo.domain.pcs.view.PcsOperationStatusViewDto;
import com.example.demo.domain.pcs.view.PcsSummaryViewDto;
import com.example.demo.model.PlantCardDto;
import com.example.demo.model.PlantEquipmentDto;
import com.example.demo.model.PlantInfoDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final String SELECTED_PLANT_ID = "SELECTED_PLANT_ID";

    private final PcsFaultDetailService pcsFaultDetailService;
    private final PcsSettingService pcsSettingService;
    private final PcsSummaryService pcsSummaryService;
    private final PcsOperationStatusService pcsOperationStatusService;
    private final PcsStatusTableService pcsStatusRowService;

    private final PvMeterSummaryService pvMeterSummaryService;
    private final PvMeterDetailService pvMeterDetailService;
    private final PvMeterOperationStatusService pvMeterOperationStatusService;
    private final PvMeterFaultStatusService pvMeterFaultStatusService;
    private final PvMeterStatusRowService pvMeterStatusRowService;
    private final PcsFaultStatusService pcsFaultStatusService;
    private final BatteryRackStatusTableService batteryRackStatusTableService;
    private final BatteryOperationStatusService batteryOperationStatusService;
    private final BatteryFaultStatusService batteryFaultStatusService;
    private final BatterySummaryService batterySummaryService;
    private final BatteryFaultDetailService batteryFaultDetailService;

    private final TemperatureHumidityStatusTableService
            temperatureHumidityStatusTableService;
    private final AirConditionerService airConditionerService;
    private final FireSystemStatusTableService fireSystemStatusTableService;

    /* ==================================================
       ROOT
     ================================================== */
    @GetMapping("/")
    public String root(HttpSession session) {
        Long plantId = (Long) session.getAttribute(SELECTED_PLANT_ID);
        return (plantId == null) ? "redirect:/plant/list" : "redirect:/dashboard";
    }

    /* ==================================================
       DASHBOARD
     ================================================== */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Long plantId = (Long) session.getAttribute(SELECTED_PLANT_ID);

        String plantName = switch (plantId != null ? plantId.intValue() : -1) {
            case 1 -> "송촌에너지";
            case 2 -> "화인9호";
            default -> "선택된 발전소 없음";
        };

        model.addAttribute("selectedPlantName", plantName);
        model.addAttribute("contentTemplate", "fragment/dashboard");
        return "index";
    }

    /* ==================================================
       PLANT INFO
     ================================================== */
    @GetMapping("/plant/info")
    public String plantInfo(HttpSession session, Model model) {

        Long plantId = (Long) session.getAttribute(SELECTED_PLANT_ID);
        if (plantId == null) {
            return "redirect:/plant/list";
        }

        PlantInfoDto info = new PlantInfoDto(
                plantId,
                "송촌에너지",
                "전남 ○○군",
                LocalDate.of(2019, 7, 3),
                LocalDate.of(2019, 7, 9)
        );

        List<PlantEquipmentDto> equipments = List.of(
                new PlantEquipmentDto("태양광 모듈", null, null, null, null),
                new PlantEquipmentDto("태양광 인버터", null, null, null, null),
                new PlantEquipmentDto("배터리", null, null, null, null),
                new PlantEquipmentDto("RACK", null, null, null, null),
                new PlantEquipmentDto("PCS", null, null, null, null),
                new PlantEquipmentDto("온습도계", null, null, null, null)
        );

        model.addAttribute("plant", info);
        model.addAttribute("equipments", equipments);
        model.addAttribute("contentTemplate", "plant/plant-info");
        return "index";
    }

    /* ==================================================
       PLANT LIST (CARD)
     ================================================== */
    @GetMapping("/plant/list")
    public String plantList(Model model) {

        List<PlantCardDto> plants = List.of(

                // 정상 케이스
                new PlantCardDto(
                        1L,
                        "송촌에너지",
                        "00080001",
                        "전남 ○○군",
                        19.9,
                        114.5,
                        0.0,
                        "09:10:22",

                        // P M B C F
                        false,  // pcsFault
                        false,  // pvMeterZero
                        false,  // batteryAlarm
                        false,  // commDown
                        false,  // flowViolation

                        false,  // anyIssue
                        "모든 상태 정상"
                ),

                // 이상 케이스
                new PlantCardDto(
                        2L,
                        "화인9호",
                        "00080004",
                        "전남 ○○군",
                        3.0,
                        0.0,
                        20.0,
                        "09:10:26",

                        // P M B C F
                        true,   // pcsFault
                        true,   // pvMeterZero (PV actorPower = 0)
                        true,   // batteryAlarm
                        true,   // commDown
                        true,   // flowViolation (ESS > PV)

                        true,   // anyIssue
                        "PCS Fault · 배터리 Alarm · 통신 끊김 · PV=0 충전"
                )
        );

        model.addAttribute("plants", plants);
        model.addAttribute("contentTemplate", "plant/plant-list");
        return "index";
    }

    /* ==================================================
       PLANT SELECT
     ================================================== */
    @GetMapping("/plant/select/{plantId}")
    public String selectPlant(@PathVariable Long plantId, HttpSession session) {
        session.setAttribute(SELECTED_PLANT_ID, plantId);
        return "redirect:/dashboard";
    }

    /* ==================================================
       이하 기존 메뉴 유지
     ================================================== */

    @GetMapping("/individual/pv-inverter")
    public String pvInverter(Model model) {
        model.addAttribute("contentTemplate", "individual/individual-pv-inverter");
        return "index";
    }

    @GetMapping("/individual/battery")
    public String individualBattery(
            @RequestParam(value = "batteryNo", defaultValue = "1") int batteryNo,
            @RequestParam(value = "rackNo", defaultValue = "1") int rackNo,
            @RequestParam(value = "airConditionerId", required = false) String airConditionerId,
            @RequestParam(value = "sensorNo", required = false) Integer sensorNo,
            Model model
    ) {


        model.addAttribute("contentTemplate", "individual/individual-battery");
        model.addAttribute("batteryNo", batteryNo);
        model.addAttribute("selectedRackNo", rackNo);
        model.addAttribute("selectedSensorNo", sensorNo != null ? sensorNo : 1
        );

        Long batteryId = (long) batteryNo;

        /* =========================
         * Rack 상태
         * ========================= */
        BatteryRackStatusTableViewDto rackStatusTable =
                batteryRackStatusTableService.getRackStatusTable(batteryId);

        model.addAttribute("rackStatusTable", rackStatusTable);


        // ✅ 운전 상태 (Service에서 계산)
        BatteryOperationStatusViewDto operationStatus =
                batteryOperationStatusService.decide(batteryId);

        model.addAttribute("operationStatus", operationStatus);

        /* =========================
         * 배터리 요약 (Service 기준)
         * ========================= */
        BatterySummaryViewDto batterySummary =
                batterySummaryService.getSummary(batteryId);

        model.addAttribute("batterySummary", batterySummary);


        /* =========================
         * 🔴 배터리 고장 상태 카드용 DTO (PCS와 동일 패턴)
         * ========================= */
        BatteryFaultStatusViewDto batteryFaultStatus =
                batteryFaultStatusService.getStatus(batteryId);

        model.addAttribute("batteryFaultStatus", batteryFaultStatus);


        /* =========================
         * 🔴 Rack 고장 상세 카드
         *  - Battery 고장 상세를 Rack 기준으로 필터링
         * ========================= */
        List<BatteryFaultDetailViewDto> allFaultDetails =
                batteryFaultDetailService.getFaultDetails(batteryId);

        /* 임시 Rack 필터 (rackNo 기준) */
        List<BatteryFaultDetailViewDto> rackFaultDetails = allFaultDetails;

        model.addAttribute("rackFaultItems", rackFaultDetails);

        /* Rack 선택 옵션 (더미) */
        model.addAttribute("rackOptions", List.of(1, 2, 3, 4, 5, 6));


        /* =========================
         * 🔴 배터리 고장 상세 카드 (Detail)
         * ========================= */
        List<BatteryFaultDetailViewDto> batteryFaultDetails =
                batteryFaultDetailService.getFaultDetails(batteryId);

        model.addAttribute("batteryFaultDetail", batteryFaultDetails);


        /* =========================
         * 🔥 소방설비 상태
         * ========================= */
        model.addAttribute(
                "fireSystemTable",
                fireSystemStatusTableService.getStatusTable()
        );


        /* =========================
         * 🟦 온습도계
         * ========================= */
        model.addAttribute(
                "temperatureHumidityTable",
                temperatureHumidityStatusTableService.getStatusTable()
        );

        /* =========================
         * 🟦 온습도계 Select (더미)
         * ========================= */
        model.addAttribute(
                "temperatureHumiditySensorOptions",
                List.of(1, 2)
        );

        model.addAttribute(
                "selectedSensorNo",
                sensorNo != null ? sensorNo : 1
        );


        /* =========================
         * 🟦 에어컨
         * ========================= */

        /* 1️⃣ 기본 선택 에어컨 */
        String selectedAirConditionerId =
                (airConditionerId != null && !airConditionerId.isBlank())
                        ? airConditionerId
                        : "AC-01";

        /* 2️⃣ 에어컨 상태 (Service) */
        model.addAttribute(
                "airConditioner",
                airConditionerService.getView(selectedAirConditionerId)
        );

        /* 3️⃣ 에어컨 목록 (Service) */
        model.addAttribute(
                "airConditionerList",
                airConditionerService.getAirConditionerList()
        );

        /* 4️⃣ 선택된 에어컨 ID */
        model.addAttribute(
                "selectedAirConditionerId",
                selectedAirConditionerId
        );


        return "index";
    }



    @GetMapping("/individual/pcs")
    public String individualPcs(
            @RequestParam(value = "pcsNo", required = false) Integer pcsNo,
            Model model
    ) {
        model.addAttribute("contentTemplate", "individual/individual-pcs");

        int pcsCount = pcsSettingService.getSetting().getPcsCount();
        if (pcsCount < 1) pcsCount = 1;

        int selectedPcsNo = (pcsNo == null)
                ? 1
                : Math.min(Math.max(pcsNo, 1), pcsCount);



        List<Integer> pcsOptions = new ArrayList<>();
        for (int i = 1; i <= pcsCount; i++) {
            pcsOptions.add(i);
        }

        model.addAttribute("pcsOptions", pcsOptions);
        model.addAttribute("selectedPcsNo", selectedPcsNo);

        /* =========================
         * 2️⃣ PCS 요약 카드 DTO
         * ========================= */
        PcsSummaryViewDto pcsSummary = pcsSummaryService.getSummary();
        model.addAttribute("pcsSummary", pcsSummary);

        /* =========================
         * 3️⃣ PCS 운전 상태 카드 DTO
         * ========================= */
        PcsOperationStatusViewDto pcsOperationStatus =
                pcsOperationStatusService.getStatus();
        model.addAttribute("pcsOperationStatus", pcsOperationStatus);

        /* =========================
         * 4️⃣ PCS 고장 상태 카드
         * ========================= */
        model.addAttribute(
                "pcsFaultStatus",
                pcsFaultStatusService.getStatus((long) selectedPcsNo)
        );

        /* =========================
         * 5️⃣ PCS 상태 테이블
         * ========================= */
        model.addAttribute("pcsRunningCount", pcsStatusRowService.getRunningCount());
        model.addAttribute("pcsTotalCount", pcsCount);
        model.addAttribute("pcsList", pcsStatusRowService.getStatusRows());



        /* =========================
         * 6️⃣ 🔴 선택된 PCS 기준 고장정보
         * ========================= */
        model.addAttribute(
                "pcsFaultItems",
                pcsFaultDetailService.getFaultDetails((long) selectedPcsNo)
        );

        return "index";
    }



    @GetMapping("/individual/pv-power-meter")
    public String pvPowerMeter(Model model) {

        model.addAttribute("contentTemplate", "individual/individual-pv-power-meter");

        /* =========================
         * 1️⃣ PV Meter Summary
         * ========================= */
        model.addAttribute(
                "pvMeterSummary",
                pvMeterSummaryService.getSummary()
        );


        /* =========================
         * 2️⃣ Operation (PV)
         * ========================= */
        model.addAttribute(
                "pvmeterOperationStatus",
                pvMeterOperationStatusService.getStatus(1L)
        );

        /* =========================
         * 3️⃣ Fault Status (PV)
         * ========================= */
        model.addAttribute(
                "pvmeterFaultStatus",
                pvMeterFaultStatusService.getStatus(1L)
        );


        /* =========================
         * 4️⃣ PV Meter Status
         * ========================= */
        model.addAttribute(
                "pvMeterStatusList",
                pvMeterStatusRowService.getStatusRows(1L)
        );


        /* =========================
         * 6️⃣ PV Meter Detail
         * ========================= */
        model.addAttribute(
                "pvDetail",
                pvMeterDetailService.getDetail(1L)
        );

        return "index";
    }




    @GetMapping("/individual/ess-power-meter")
    public String essPowerMeter(Model model) {
        model.addAttribute("contentTemplate", "individual/individual-ess-power-meter");
        return "index";
    }

    @GetMapping("/individual/ess-internal-power-meter")
    public String essInternalPowerMeter(Model model) {
        model.addAttribute("contentTemplate", "individual/individual-ess-internal-power-meter");
        return "index";
    }

    @GetMapping("/history/operation")
    public String historyOperation(Model model) {
        model.addAttribute("contentTemplate", "history/history-operation");
        return "index";
    }

    @GetMapping("/history/failure")
    public String historyFailure(Model model) {
        model.addAttribute("contentTemplate", "history/history-failure");
        return "index";
    }

    @GetMapping("/history/kesco")
    public String historyKesco(Model model) {
        model.addAttribute("contentTemplate", "history/history-kesco");
        return "index";
    }

    @GetMapping("/trend/pvmeter")
    public String trendPvMeter(Model model) {
        model.addAttribute("contentTemplate", "trend/trend-pvmeter");
        return "index";
    }

    @GetMapping("/trend/pcs")
    public String trendPcs(Model model) {
        model.addAttribute("contentTemplate", "trend/trend-pcs");
        return "index";
    }

    @GetMapping("/trend/grid")
    public String trendGrid(Model model) {
        model.addAttribute("contentTemplate", "trend/trend-grid");
        return "index";
    }

    @GetMapping("/trend/report")
    public String trendReport(Model model) {
        model.addAttribute("contentTemplate", "trend/trend-report");
        return "index";
    }
}
