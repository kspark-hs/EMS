# DEVICE_PROVIDER_COMMON_POLICY.md

본 문서는 EMS에서 사용되는 **모든 장비 Provider 계층의 공통 설계 원칙**을 정의한다.

각 장비별 Provider(PV 전력량계, PCS, 배터리, 온습도계 등)는  
본 문서를 **최상위 공통 기준(Common Policy)** 으로 반드시 준수해야 한다.

본 문서는 UI / Service / Controller / RTU 연동에서  
**단일 기준(Single Source of Truth)** 으로 사용된다.

---

## 1. 목적

본 정책은 다음 목적을 가진다.

- Provider 계층의 **역할과 책임 명확화**
- 장비별 Provider 구현 편차 방지
- 상태 판단 / 데이터 처리 기준 통일
- UI / Service 계층 침범 방지

---

## 2. Provider 계층의 정의

Provider는 **장비 데이터의 단일 진입점(Single Entry Point)** 이다.

- Raw 데이터 수집
- 데이터 유효성 판단
- 상태 계산
- UI 전달용 DTO 생성

> ❗ Provider는 “데이터 공급자”가 아니라  
> **장비 상태 해석 책임자**이다.

---

## 3. Provider 공통 책임 (필수)

모든 Provider는 다음 책임을 **반드시 수행**해야 한다.

### 3.1 데이터 수집

- RTU / DB / API 등 Raw 데이터 수집
- 통신 실패, 지연, 누락 감지

---

### 3.2 데이터 유효성 판단

- 값 범위 이상 여부 판단
- NULL / 미수신 / 고정값 판단
- 장비 특성 기반 데이터 신뢰성 평가

> 데이터가 존재해도  
> **신뢰할 수 없는 데이터는 유효하지 않다**

---

### 3.3 상태(State) 산출

Provider는 반드시 다음 중 하나의 상태를 산출한다.

| 상태 | 의미 |
|----|----|
| NORMAL | 정상 운전 가능 |
| WARNING | 제한/주의 상태 |
| FAULT | 고장 상태 |
| DISCONNECTED | 통신 단절 |

- 상태 판단 기준은 OPERATION_POLICY.md를 따른다.
- 상태 문자열은 **고정 Enum** 으로 관리한다.

---

### 3.4 View DTO 생성

- UI는 Raw 데이터를 직접 참조하지 않는다.
- Provider는 **UI 표시 전용 DTO(ViewDto)** 만 반환한다.

---

## 4. Provider가 해서는 안 되는 일 (금지 사항)

Provider는 다음을 **절대 수행하지 않는다**.

❌ UI 레이아웃 고려  
❌ 색상 판단  
❌ 사용자 언어(한글/영문) 변환  
❌ 시간 포맷 변환  
❌ 정렬 / 페이징 / 스크롤 처리

> Provider는 **의미 판단만 수행**하고  
> **표현은 UI의 책임**이다.

---

## 5. 상태 판단 공통 원칙

### 5.1 상태 판단 위치

- 상태 판단은 **Provider 또는 Service 계층의 책임**
- UI에서 상태 계산 로직 구현 **금지**

---

### 5.2 상태 판단 기준 우선순위

1. 통신 상태 (DISCONNECTED)
2. 고장 상태 (FAULT)
3. 제한 상태 (WARNING)
4. 정상 상태 (NORMAL)

> 가장 위험한 상태가 **항상 우선**한다.

---

## 6. ONLINE / OFFLINE 공통 기준

### OFFLINE 조건

다음 중 하나라도 만족하면 OFFLINE으로 판단한다.

- RTU 통신 두절
- 장비 전원 차단
- 데이터 미수신 지속
- 명시적 유지보수 상태

OFFLINE 상태는 다음을 의미한다.

- 운전 불가
- 제어 불가
- 데이터 신뢰 불가

---

## 7. Provider 인터페이스 설계 원칙

모든 Provider는 다음 원칙을 따른다.

```java
public interface DeviceProvider<TStatus, TTrend> {

    /**
     * 장비 현재 상태 조회
     */
    TStatus getCurrentStatus(Long plantId);

    /**
     * 장비 일간 트렌드 (24시간)
     */
    List<TTrend> getDailyTrend(
        Long plantId,
        LocalDate date
    );
}

---

## 8. 트렌드 데이터 공통 규칙

- 트렌드 데이터의 시간축은 **24시간(00:00 ~ 23:59)** 기준으로 고정한다.
- 실제 데이터가 존재하지 않는 시간대는 **NULL 데이터를 허용**한다.
- 태양광, 야간 정지 장비 특성에 따른 데이터 공백은 **이상 상태로 판단하지 않는다**.
- “최근 N건” 기반 트렌드 데이터 생성은 **전면 금지**한다.

트렌드 데이터의 상세 생성 규칙, 보간 여부, 표현 방식은  
다음 문서를 **단일 기준**으로 따른다.

- `TREND_DATA_POLICY.md`

---


## 10. 장비별 정책 문서 분리 원칙

장비별 Provider의 특화 정책(메모리맵, 수신 기준, 상태 판단 세부 기준 등)은  
반드시 **장비별 전용 Policy 문서로 분리**하여 관리한다.

다음 문서는 장비별 특화 정책으로 분리 관리한다.

- `PV_PVMETER_PROVIDER_POLICY.md`
- `PCS_PROVIDER_POLICY.md`
- `BATTERY_PROVIDER_POLICY.md`
- `AIR_CONDITIONER_PROVIDER_POLICY.md`

공통 원칙(Provider 책임/금지, 상태 우선순위, 트렌드 공통 규칙 등)은  
**오직 본 문서(DEVICE_PROVIDER_COMMON_POLICY.md)에서만 정의**한다.

❌ 장비별 문서에 공통 원칙을 중복 정의하지 않는다  
❌ 공통 정책 변경은 본 문서만 수정한다

---

## 11. 테스트 및 검증 원칙

Provider 구현 시 다음 항목을 반드시 검증한다.

### 11.1 통신/수신 이상 시 상태 전이

- 정상 수신 → 지연/주의 → 단절(DISCONNECTED) 전이
- 단절 복구 시 정상 복귀 조건
- “마지막 데이터 수신 시각(lastReceivedAt)” 갱신 조건 검증

### 11.2 데이터 고정값(Freeze) 발생 시 판단

- 값이 일정 시간 이상 동일하게 유지되는 경우(예: 전력/전압/주파수 등)
- 장비 특성상 “고정값이 정상”일 수 있는 케이스는 예외 규칙으로 분리
- 고정값 판단 기준은 장비별 Provider Policy 문서에 명시한다

### 11.3 NULL / 미수신 데이터 처리

- Trend 구간 중 일부 시간대 NULL 허용
- Current Status에서 NULL 발생 시 상태 산출 우선순위 검증
- UI로 전달되는 DTO는 “표시 가능한 형태”로 일관되게 제공한다

### 11.4 상태 우선순위 충돌 검증

동일 시점에 여러 조건이 동시에 발생할 수 있으므로  
다음 우선순위가 항상 유지되는지 검증한다.

1) DISCONNECTED  
2) FAULT  
3) WARNING  
4) NORMAL

---

## 12. 변경 이력

- 2026-01-01
    - Device Provider 공통 정책 초안 작성
    - Provider 책임 / 금지 사항 명확화
    - 상태 판단 우선순위 정의
    - 트렌드 기준(24시간 시간축, NULL 허용, 최근 N건 금지) 통합
    - Fault 제공 원칙(label + boolean fault) 명확화
    - 장비별 Policy 문서 분리 원칙 추가

---





