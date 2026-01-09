# PV_PVMETER_PROVIDER_POLICY.md

본 문서는 **PV 전력량계(PV Power Meter)** 데이터에 대한  
Provider 계층의 역할, 책임, 상태 판단 기준을 정의한다.

본 문서는 UI / Service / Controller / RTU 연동에서  
**단일 기준(Single Source of Truth)** 으로 사용된다.

---

## 1. Provider의 책임 범위

PV 전력량계 Provider는 다음 책임을 가진다.

1. Raw 계측 데이터 수집 (RTU / DB / API)
2. 데이터 유효성 및 품질 판단
3. 상태(NORMAL / WARNING / DISCONNECTED) 산출  
   ※ FAULT 판단은 Service / OPERATION_POLICY.md의 책임
4. UI 전달용 View DTO 생성

PV 전력량계 Provider는 다음 항목만 근거로 상태를 판단한다.

- 통신 상태
- 데이터 수신 주기
- 데이터 유효성

❌ 장비 보호 / 운전 차단 / 인터락 / FAULT 판단 금지  
❌ Controller / UI는 Raw 데이터 직접 접근 금지  
❌ UI에서 상태 판단 로직 구현 금지

---

## 2. 시간 기준 정책

- 트렌드 데이터 시간축은 **24시간(00:00 ~ 23:59)** 고정
- 데이터 미존재 시간대는 **NULL 허용**
- 태양광 특성상 야간 데이터 공백은 **정상**
- **최근 N건 기반 트렌드 생성 금지**
- 시간축 기반 데이터만 반환

상세 기준은 다음 문서를 따른다.

- `TREND_DATA_POLICY.md`

---

## 3. Provider 인터페이스 정책

```java
public interface PvPowerMeterProvider {

    /**
     * PV 전력량계 현재 상태 (현재 시점 기준)
     */
    PvPowerMeterStatusDto getCurrentStatus(Long plantId);

    /**
     * PV 전력량계 일간 트렌드 (24시간)
     */
    List<PvPowerMeterTrendPointDto> getDailyTrend(
        Long plantId,
        LocalDate date
    );
}

---

## 4. 상태 코드 범위 제한

PV 전력량계 Provider가 산출 가능한 상태는 다음으로 **제한한다**.

- NORMAL
- WARNING
- DISCONNECTED

❌ FAULT 상태 산출 금지  
FAULT 판단은 `OPERATION_POLICY.md`에 따라 **Service 계층의 책임**이다.

---

## 5. 데이터 수신 주기 판단 기준

PV 전력량계의 상태 판단은 **데이터 수신 주기(ms)** 를 기준으로 한다.

| 수신 주기(ms) | 상태 |
|--------------|------|
| ≤ 220        | NORMAL |
| 220 ~ 400    | WARNING |
| 데이터 미수신 | DISCONNECTED |

- 수신 주기는 RTU → Provider 기준으로 측정한다
- 장비 자체 내부 주기와 무관하다

---

## 6. lastReceivedAt 기준

- `lastReceivedAt`은 **마지막 정상 데이터 프레임 수신 시각**을 의미한다
- 통신 단절(DISCONNECTED) 판단의 **기계적 기준값**으로 사용한다
- UI 노출 여부 및 표현 방식은 UI 정책의 책임이다

---

## 7. 상태 코드와 UI 표현 분리 원칙

- Provider / Service 계층은 **상태 코드(Enum)** 만 반환한다
- 문자열, 색상, 아이콘, 강조 표현은 UI 정책 문서에서 처리한다

참조 문서:

- `UI_STATUS_COLOR_POLICY.md`
- `UI_CONTRACT.md`

---

## 8. 변경 이력

- 2026-01-01
    - PV 전력량계 Provider 정책 초안 작성
    - 상태 판단 책임 분리
    - 수신 주기 기반 상태 기준 명시
    - 24시간 트렌드 기준 확정

---



