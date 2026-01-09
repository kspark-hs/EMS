# Battery Menu – PCS 기준 구조 정렬 변경 로그

## 날짜
2026-01-02

## 변경 목적
Battery 메뉴를 PCS 메뉴와 **구조 / 네이밍 / 책임 분리 기준으로 완전 동일화**하기 위함

---

## 1. 구조 변경 요약

### Rack Status 영역 Provider 계층 정리
PCS 패턴과 동일하게 **StatusProvider / StatusTableProvider 계층 분리**

#### 신규 / 정리된 Provider
- BatteryRackStatusProvider
    - Rack 상태 원천 Provider (Vendor 비의존)
- SamsungBatteryRackStatusProvider
    - Samsung SDI 메모리맵 기반 Rack 상태 구현체
- BatteryRackStatusTableProvider
    - UI Table 전용 Provider
- SamsungBatteryRackStatusTableProvider
    - TableProvider의 Samsung 구현체

---

## 2. Service / View 구조

- BatteryRackStatusTableService 유지
- View DTO
    - BatteryRackStatusTableRowViewDto
    - BatteryRackStatusTableViewDto
- Row DTO에 기본 생성자 추가 (NoArgsConstructor)
    - Provider → setter 방식 사용 가능

---

## 3. PCS 기준 동일성 검증 결과

- Provider / Service / View 레이어 수 동일
- 네이밍 규칙 동일
- Vendor 종속 위치 동일
- 책임 분리 기준 동일

👉 **Battery Rack Status 영역은 PCS 기준과 구조적으로 완전 동일**

---

## 4. 현재 상태

- `/individual/battery` 정상 동작
- Rack Status / Rack Fault 카드 정상 표시
- 구조 변경에 따른 런타임 오류 없음

---

## 5. 후속 작업 (선택 사항)

- Rack 고장정보 카드 표시 항목 정리
    - DC Switch On / Count / 설명성 bit 제거 여부 검토
