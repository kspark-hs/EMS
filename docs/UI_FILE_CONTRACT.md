
- 카드 단위 Service 병합 ❌
- 역할이 불명확한 범용 Service ❌ (예: PvMeterService)
- 네이밍은 반드시 **카드 역할을 포함**해야 한다.

---

### 1-2. 내부 Item DTO 생성 규칙

내부 Item DTO는 **아래 조건을 만족할 때만 생성**한다.

#### ✅ 생성 허용
- 동일 구조의 항목이 **List 형태로 반복**되는 경우
    - 고장 항목 리스트
    - 상태 테이블 Row
    - 이벤트 / 알람 목록

#### ❌ 생성 금지
- 항목 개수 고정
- 구조가 불변인 상세 정보

> **DetailCard에는 내부 Item DTO를 두지 않는다.**

---

## 2. 카드 타입 정의 (공통)

| 카드 타입 | 설명 |
|---|---|
| SummaryCard | 요약 정보 |
| OperationStatusCard | 운전 상태 |
| FaultStatusCard | 고장 상태 |
| StatusTableCard | 상태 테이블 |
| DetailCard | 상세 정보 |

---

## 3. PCS 파일 대응 기준표 (Reference)

> **PCS는 모든 설비 UI의 기준(reference)이다.**

| UI 카드 타입 | HTML | Service | ViewDto | 내부 Item DTO |
|---|---|---|---|---|
| SummaryCard | pcs-summary-card.html | PcsSummaryService | PcsSummaryViewDto | - |
| OperationStatusCard | pcs-operation-card.html | PcsOperationStatusService | PcsOperationStatusViewDto | - |
| FaultStatusCard | pcs-fault-card.html | PcsFaultService | PcsFaultStatusViewDto | PcsFaultItemViewDto |
| StatusTableCard | pcs-status-card.html | PcsStatusRowService | PcsStatusRowViewDto | - |
| DetailCard | pcs-detail-card.html | PcsDetailService | PcsDetailViewDto | - |

---

## 4. PV 전력량계 파일 대응 기준표

(PCS 기준 적용)

| UI 카드 타입 | HTML | Service                       | ViewDto | 내부 Item DTO | 상태 |
|---|---|-------------------------------|---|---|---|
| SummaryCard | pvmeter-summary-card.html | PvMeterSummaryService         | PvMeterSummaryViewDto | - | 구현 |
| OperationStatusCard | pvmeter-operation-card.html | PvMeterOperationStatusService | PvMeterOperationStatusViewDto | - | ViewDto만 |
| FaultStatusCard | pvmeter-fault-card.html | PvMeterFaultStatusService     | PvMeterFaultStatusViewDto | - | ViewDto만 |
| StatusTableCard | pvmeter-status-card.html | PvMeterStatusRowService       | PvMeterStatusRowViewDto | - | ViewDto만 |
| DetailCard | pvmeter-detail-card.html | PvMeterDetailService          | PvMeterDetailViewDto | - | 예정 |

---

## 5. 배터리 파일 대응 기준표

(PCS 기준 적용)

| UI 카드 타입 | HTML                                | Service | ViewDto | 내부 Item DTO | 상태 |
|---|-------------------------------------|---|---|---|---|
| SummaryCard | battery-summary-card.html           | BatterySummaryService | BatterySummaryViewDto | - | 네이밍 불일치 |
| OperationStatusCard | battery-operation-card.html         | BatteryOperationStatusService | BatteryOperationStatusViewDto | - | 미구현 |
| FaultStatusCard | battery-fault-status-card.html      | BatteryFaultService | BatteryFaultStatusViewDto | BatteryFaultItemViewDto | 부분 |
| StatusTableCard | battery-rack-status-table-card.html | BatteryRackStatusService | BatteryRackStatusViewDto | BatteryRackItemViewDto | 구현 |
| DetailCard | battery-detail-card.html            | BatteryDetailService | BatteryDetailViewDto | - | 미구현 |

---

## 6. 수정 작업 허용 범위

### ✅ 허용
- 기준표의 **빈 칸 채우기**
- 기준과 어긋난 **파일명 / 클래스명 수정**
- 카드 단위 Service 분리

### ❌ 금지
- 카드 타입 추가
- Service 병합
- 기준표에 없는 파일 생성
- 구조 선변경 후 문서 미반영

---

## 7. 최종 선언

> 본 문서는 **UI 파일 구조의 단일 기준**이다.
>
> 향후 모든 UI / Service / DTO 수정은  
> **본 문서를 기준으로만 수행한다.**

---

## 8. 권장 저장 위치

## Related Documents
- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md

