package com.example.demo.controller;

import com.example.demo.domain.battery.service.*;
import com.example.demo.domain.battery.status.BatteryAggregateStatusSnapshot;
import com.example.demo.domain.battery.view.*;
import com.example.demo.domain.equipment.airconditioner.service.AirConditionerService;
import com.example.demo.domain.equipment.airconditioner.service.AirConditionerStatusTableService;
import com.example.demo.domain.equipment.firesystem.service.FireSystemStatusTableService;
import com.example.demo.domain.equipment.temperaturehumidity.service.TemperatureHumidityStatusTableService;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableViewDto;
import com.example.demo.domain.pcs.fault.PcsFaultSnapshot;
import com.example.demo.domain.pcs.fault.PcsFaultSnapshotService;
import com.example.demo.domain.pcs.service.*;
import com.example.demo.domain.pcs.status.PcsAggregateStatusSnapshot;
import com.example.demo.domain.pcs.status.PcsFaultType;
import com.example.demo.domain.pcs.status.PcsOverallStatusType;
import com.example.demo.domain.pcs.view.PcsAggregateStatusViewDto;
import com.example.demo.domain.pcs.view.PcsFaultStatusViewDto;
import com.example.demo.domain.pcs.view.PcsStatusTableRowViewDto;
import com.example.demo.domain.pcs.view.PcsSummaryViewDto;
import com.example.demo.domain.pvmeter.service.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final BatteryRackStatusTableService batteryRackStatusTableService;
    private final BatteryOperationStatusService batteryOperationStatusService;
    private final BatteryFaultStatusService batteryFaultStatusService;
    private final BatterySummaryService batterySummaryService;
    private final BatteryFaultDetailService batteryFaultDetailService;

    private final TemperatureHumidityStatusTableService
            temperatureHumidityStatusTableService;
    private final AirConditionerService airConditionerService;
    private final AirConditionerStatusTableService airConditionerStatusTableService;
    private final FireSystemStatusTableService fireSystemStatusTableService;
    private final PcsAggregateStatusService pcsAggregateStatusService;

    private final PcsStatusTableService pcsStatusTableService;
    private final PcsFaultSnapshotService pcsFaultSnapshotService;
    private final BatteryAggregateStatusService batteryAggregateStatusService;


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

        /* =========================
         * 기본 화면 설정
         * ========================= */
        model.addAttribute("contentTemplate", "individual/individual-battery");
        model.addAttribute("batteryNo", batteryNo);

        /* =========================
         * 🔑 Controller 책임: rackIds 확보
         * ========================= */
        // TODO: 추후 DB / 설정 기반으로 교체
        List<Long> rackIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);

        // rackNo 보정 (1 ~ rackCount)
        int rackCount = rackIds.size();
        int selectedRackNo = Math.min(Math.max(rackNo, 1), rackCount);
        model.addAttribute("selectedRackNo", selectedRackNo);

        // sensorNo 보정
        int selectedSensorNo = (sensorNo != null) ? sensorNo : 1;
        model.addAttribute("selectedSensorNo", selectedSensorNo);

        /* =========================
         * Rack 선택 옵션 (번호 기준)
         * ========================= */
        List<Integer> rackOptions = new ArrayList<>();
        for (int i = 1; i <= rackCount; i++) {
            rackOptions.add(i);
        }
        model.addAttribute("rackOptions", rackOptions);

        /* =========================
         * Rack 상태 테이블
         * ========================= */
        BatteryRackStatusTableViewDto rackStatusTable =
                batteryRackStatusTableService.getRackStatusTable(rackIds);
        model.addAttribute("rackStatusTable", rackStatusTable);

        /* =========================
         * Battery Aggregate 상태
         * ========================= */
        BatteryAggregateStatusSnapshot aggregateStatus =
                batteryAggregateStatusService.getSnapshot(rackIds);
        model.addAttribute("aggregateStatus", aggregateStatus);

        /* =========================
         * Battery Operation 상태
         * ========================= */
        BatteryOperationStatusViewDto operationStatus =
                batteryOperationStatusService.decide(rackIds);
        model.addAttribute("operationStatus", operationStatus);

        /* =========================
         * Battery Summary
         * ========================= */
        BatterySummaryViewDto batterySummary =
                batterySummaryService.getSummary(rackIds);
        model.addAttribute("batterySummary", batterySummary);

        /* =========================
         * Battery Fault Status (Aggregate)
         * ========================= */
        BatteryFaultStatusViewDto batteryFaultStatus =
                batteryFaultStatusService.getStatus(rackIds);
        model.addAttribute("batteryFaultStatus", batteryFaultStatus);

        /* =========================
         * Rack Fault Detail (선택 Rack)
         * - 현재는 rackId == rackNo 가정
         * ========================= */
        Long selectedRackId = (long) selectedRackNo;

        model.addAttribute(
                "rackFaultItems",
                batteryFaultDetailService.getRackFaultDetails(selectedRackId)
        );


        /* =========================
         * 소방설비 상태
         * ========================= */
        model.addAttribute(
                "fireSystemTable",
                fireSystemStatusTableService.getStatusTable()
        );

        /* =========================
         * 온습도계
         * ========================= */
        TemperatureHumidityStatusTableViewDto temperatureHumidityTable =
                temperatureHumidityStatusTableService.getStatusTable();
        model.addAttribute("temperatureHumidityTable", temperatureHumidityTable);

        model.addAttribute("temperatureHumiditySensorOptions", List.of(1, 2));
        model.addAttribute("selectedSensorNo", selectedSensorNo);

        /* =========================
         * 에어컨
         * ========================= */
        String selectedAirConditionerId =
                (airConditionerId != null && !airConditionerId.isBlank())
                        ? airConditionerId
                        : "AC-01";

        model.addAttribute(
                "airConditionerStatusTable",
                airConditionerStatusTableService.getStatusTable(
                        Long.parseLong(selectedAirConditionerId.replace("AC-", "")),
                        temperatureHumidityTable.getTempMax(),
                        temperatureHumidityTable.getHumidityMax()
                )
        );

        model.addAttribute(
                "airConditionerList",
                airConditionerService.getAirConditionerList()
        );

        model.addAttribute("selectedAirConditionerId", selectedAirConditionerId);

        return "index";
    }







    @GetMapping("/individual/pcs")
    public String individualPcs(
            @RequestParam(value = "pcsNo", required = false) Integer pcsNo,
            Model model
    ) {
        model.addAttribute("contentTemplate", "individual/individual-pcs");

        /* =========================
         * PCS 개수 / 선택 번호
         * ========================= */
        int pcsCount = pcsSettingService.getSetting().getPcsCount();
        if (pcsCount < 1) pcsCount = 1;

        int selectedPcsNo = (pcsNo == null)
                ? 1
                : Math.min(Math.max(pcsNo, 1), pcsCount);

        Long selectedPcsId = (long) selectedPcsNo;

        /* =========================
         * PCS 선택 옵션
         * ========================= */
        List<Integer> pcsOptions = new ArrayList<>();
        for (int i = 1; i <= pcsCount; i++) {
            pcsOptions.add(i);
        }
        model.addAttribute("pcsOptions", pcsOptions);
        model.addAttribute("selectedPcsNo", selectedPcsNo);

        /* =====================================================
         * 🔵 0️⃣ PCS 요약 (선택된 PCS 기준)
         * ===================================================== */
        PcsSummaryViewDto pcsSummary =
                pcsSummaryService.getSummary(selectedPcsId);

        model.addAttribute("pcsSummary", pcsSummary);

        /* =====================================================
         * 🟦 1️⃣ 단일 PCS 운전 상태 (운전/모드/제어)
         * - Aggregate Snapshot 사용 (Source of Truth)
         * ===================================================== */
        model.addAttribute(
                "pcsOperationStatus",
                pcsOperationStatusService.getOperationStatus(selectedPcsId)
        );

        // =========================
        // PCS Aggregate Status
        // =========================
        PcsAggregateStatusSnapshot aggregate =
                pcsAggregateStatusService.getSnapshot(selectedPcsId);

        // =========================
        // PCS Fault Status (표시 전용)
        // =========================
        PcsFaultStatusViewDto pcsFaultStatus = new PcsFaultStatusViewDto();

        // 고장 발생 여부
        pcsFaultStatus.setHasFault(
                aggregate.getOverallStatus() == PcsOverallStatusType.FAULT
        );

        // 통신 상태 (요약 카드 기준: 통합 comm)
        pcsFaultStatus.setInternalCommOk(aggregate.isInternalCommOk());
        pcsFaultStatus.setExternalCommOk(aggregate.isExternalCommOk());

        model.addAttribute("pcsFaultStatus", pcsFaultStatus);



        /* =====================================================
         * 🟥 3️⃣ PCS별 내부 통신 상태 (Snapshot 기준)
         * ===================================================== */
        Map<Long, String> pcsCommStatusMap = new HashMap<>();

        for (long pcsId = 1; pcsId <= pcsCount; pcsId++) {

            PcsFaultSnapshot snapshot =
                    pcsFaultSnapshotService.getSnapshot(pcsId);

            boolean internalCommLoss =
                    Boolean.TRUE.equals(
                            snapshot.getFaultMap()
                                    .get(PcsFaultType.INTERNAL_COMM_LOSS)
                    );

            pcsCommStatusMap.put(pcsId, internalCommLoss ? "두절" : "정상");
        }

        /* =====================================================
         * 🟧 4️⃣ PCS별 상태 판결 (Snapshot 기준)
         * - 하나라도 fault true → 고장
         * ===================================================== */
        Map<Long, String> pcsStatusMap = new HashMap<>();

        for (long pcsId = 1; pcsId <= pcsCount; pcsId++) {

            PcsFaultSnapshot snapshot =
                    pcsFaultSnapshotService.getSnapshot(pcsId);

            boolean hasFault =
                    snapshot.getFaultMap()
                            .values()
                            .stream()
                            .anyMatch(Boolean.TRUE::equals);

            pcsStatusMap.put(pcsId, hasFault ? "고장" : "정상");
        }


        /* =====================================================
         * 🟨 5️⃣ PCS 상태 테이블 (표시 전용)
         * - 판결 ❌
         * ===================================================== */
        // PCS 개수
        model.addAttribute("pcsTotalCount", pcsCount);

        // PCS 상태 테이블 (1회 생성)
        List<PcsStatusTableRowViewDto> pcsList =
                pcsStatusTableService.getRows(pcsCount);
        model.addAttribute("pcsList", pcsList);

        // 정상 운영중인 PCS 개수
        long pcsRunningCount =
                pcsList.stream()
                        .filter(r -> "정상".equals(r.getStatus()))
                        .count();
        model.addAttribute("pcsRunningCount", pcsRunningCount);



        /* =====================================================
         * 🔴 6️⃣ 선택된 PCS 고장 상세
         * ===================================================== */
        model.addAttribute(
                "pcsFaultItems",
                pcsFaultDetailService.getFaultItems(selectedPcsId)
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
