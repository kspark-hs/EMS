# EMS Design Documents – Change Log

## v1.0 (2026-01-01)
- 초기 설계 기준본 고정 (Baseline)
- 정책 문서 체계 확정
    - ENVIRONMENT_POLICY.md
    - OPERATION_POLICY.md
    - DEVICE_STATUS_MODEL.md
    - DEVICE_PROVIDER_COMMON_POLICY.md
    - TREND_DATA_POLICY.md
    - UI_CONTRACT.md
    - UI_STATUS_COLOR_POLICY.md
- 환경 조건(Environment)과 장비 상태(Device Status) 책임 완전 분리
- 충·방전 제어 판단과 장비 상태 판단 분리 확정
- Scheduler 역할 정의
    - 주기적 판단만 수행
    - 제어 판단은 Service 책임
- Provider / Service / UI 책임 경계 명문화
- PV 전력량계 트렌드 설계 기준 확정 (24시간 시간축, NULL 유지, 최근 N건 금지)

---

## 2026-01-02

### Battery / Rack Fault 구조 정리

- Rack 고장을 독립 개념에서 제거
- BatteryFaultDetailService 기준으로 고장 정보 단일화
- Rack 고장정보는 rackNo 필터링 결과로 표현
- Rack 전용 Fault Service 삭제 결정
- ViewDto 네이밍 정책 정리 및 legacy 파일 정리

---

## 2026-01-03

### Temperature / Humidity

- StatusTable 구조 확정 (PCS / FireSystem과 동일)
- 상태 enum 및 색상 분기 기준 확정
- 온도/습도 기준값 적용 (임시 상수)
- 충방전 운전 가능 여부 판단 로직 구현
- UI 카드 정상/비정상/통신단절 색상 검증 완료

---

## 2026-01-04

### Temperature / Humidity (운전 정책 확정)

- 온습도계 다중 채널 구조 확정
    - 채널 수: CH 1 ~ CH 4
    - 채널별 독립 값 / 독립 통신 상태 보유
- Raw Provider / StatusTable Provider 책임 분리 확정
    - TemperatureHumidityProvider
        - 채널별 Raw 온도 / 습도 / 통신 상태만 제공
        - 판단 로직 없음
    - TemperatureHumidityStatusTableProvider
        - 상태 해석
        - 운전 가능 여부 판단
        - UI 표시용 대표 값 계산

---

### 채널별 상태 판단 기준 확정

- 채널 상태는 다음 중 하나로 표현
    - 정상
    - 고온 (온도 최대 기준 초과)
    - 저온 (온도 최소 기준 미만)
    - 과습 (습도 최대 기준 초과)
    - 통신 단절

- 상태 문자열은 단순 "주의" 표현을 사용하지 않음
    - 원인 기반 상태 텍스트 사용
    - UI / 알람 / 운영자 인지 용이성 확보

---

### 환경 기준 정책 확정

- 온도 기준
    - 최소 온도 / 최대 온도 기준 모두 적용
- 습도 기준
    - 최대 습도 기준만 적용
    - 습도 0 값은 존재하지 않는 것으로 가정

---

### 충·방전 운전 판단 정책 (중요)

다음 조건 중 **하나라도 만족 시 즉시 충·방전 정지**

1. 온습도 채널 중 하나라도 기준 범위 초과
2. 모든 온습도 채널 통신 불가
3. 환경 정보 판단 불가 상태

- 단일 채널 통신 단절은 운전 정지 조건 아님
- 하나 이상의 채널이 살아 있으면 환경 판단 가능

---

### operable 단일 판단 기준 도입

- 충·방전 운전 가능 여부는 `operable` 값 하나로 판단
- Scheduler / Service / UI는 operable 값만 신뢰

| operable | 의미 |
|---------|------|
| true | 충·방전 운전가능 |
| false | 충·방전 운전정지 |

---

### 전체 상태(overallStatus) 판단 기준

- NORMAL
    - 모든 살아있는 채널이 기준 범위 내
- WARNING
    - 하나 이상의 채널이 기준 범위 초과
- DISCONNECTED
    - 모든 채널 통신 불가

> overallStatus는 **표현용 상태**이며  
> 실제 제어 판단은 operable 기준으로 수행

---

### 대표 표시 값(UI 요약 카드) 정책 확정

- 상단 온습도 요약 카드 표시 값
    - 대표 온도: 전체 채널 중 **최대 온도**
    - 대표 습도: 전체 채널 중 **최대 습도**
- 위험 판단 기준에 가장 민감한 값 사용

---

### Scheduler 연계 정책 확정

- Scheduler는 판단 로직을 가지지 않음
- OPERATION_POLICY 결과만 기반으로 동작
- 환경 이상 시
    - 충·방전 스케줄 즉시 차단
    - 운전 중이면 정지 명령 수행

---

## 2026-01-09 — PCS 화면 구조 확정 및 봉인

### 1. 핵심 이슈 해결

- ❗ **개별 PCS 화면에서 상단 카드가 전체 PCS를 대표하던 구조 문제 해결**
- 모든 상단 카드(Summary / Operation / Fault)를
    - `pcsNo` 기준 **개별 PCS 단위로 고정**

---

### 2. Service / Controller 구조 개선

- 모든 PCS 관련 Service 호출에
    - `pcsId(Long)` 명시적 전달 구조 확정
- 대표 PCS 하드코딩 구조 제거

---

### 3. PcsAggregateStatusSnapshot 역할 확정

- 단일 PCS 기준 **최종 판결 결과 Snapshot**
- UI는 Snapshot 결과를 **해석하지 않고 그대로 표현**
- 통신 / 고장 / 운전 가능 여부 판단 책임 단일화

---

### 4. PCS SummaryCard 수치 의미 확정

- PV 유효전력
- 충·방전 Reference
- PCS 유효전력

→ **원인–목표–결과 흐름 요약용 수치**로 확정

---

### 5. PV 유효전력 용어 정리

- PV 발전량 / PV 출력 / PV 전력
  → **PV 유효전력**으로 단일 통합
- Summary / Status / Trend 공통 개념 사용 확정

---

### 6. StatusTableCard 동작 확정

- 관리자 설정 PCS 대수 기준
    - `PCS #1 ~ PCS #n` 자동 Row 생성
- Row 클릭 시
    - GET 방식 페이지 이동
    - 해당 PCS 화면으로 전환

---

### 7. PCS 고장정보 카드 UX 확정

- 상단 PCS Select:
    - PCS 대수 자동 반영
- Select 변경 시:
    - GET 방식 페이지 이동
    - 고장 정보 즉시 갱신
- StatusTable Row 클릭과 UX 완전 통일

---

### 8. 테스트 결과

- 메모리맵 기반 Fault 전파 정상 확인
    - DC CB Trip 단일 Fault 테스트 성공
- PCS #1 / PCS #2 상태 분리 정상 동작
- Summary / Operation / Fault / Table / Detail
  → **모든 카드 간 일관성 확보**

---

### 9. 최종 상태

- PCS 개별 화면: 🔒 **LOCK**
- 관련 문서:
    - UI_CONTRACT.md 유지
    - PCS_UI_LOCK.md 신규 추가
- 다음 작업 대상:
    - Battery
    - PV 전력량계
    - Dashboard (Aggregate 재논의)

---



### 비고

- 온습도계는 본 버전부터
  PCS / Battery / PV 전력량계와 동일한 정책 레벨로 승격
- 추후 DB 연동 시 Provider 교체만으로 확장 가능
- 본 정책은 OPERATION_POLICY.md / SCHEDULER_POLICY.md 상위 기준으로 사용

---
