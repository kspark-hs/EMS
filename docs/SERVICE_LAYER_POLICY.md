# SERVICE_LAYER_POLICY.md

본 문서는 EMS Backend의 **Service Layer 설계 원칙과 책임 범위**를 정의한다.
UI_CONTRACT.md, DEVICE_STATUS_MODEL.md, OPERATION_POLICY.md를
**코드 레벨에서 실제로 구현하기 위한 규칙 문서**이다.

---

## 1. 목적

본 정책은 다음을 목적으로 한다.

* Controller / Service / Provider / ViewDto 간 **역할 분리**
* “Service가 판단하고, View는 표현만 한다”는 원칙의 명문화
* UI 변경과 무관하게 **운전/상태 판단 로직을 보호**
* Battery / PCS / PV 전력량계 등 **모든 장비에 공통 적용**

---

## 2. 계층별 책임 정의

### 2.1 Controller Layer

**책임**

* 요청 수신
* Service 호출
* View(Model)에 결과 전달

**금지 사항**

* 상태 판단 로직 ❌
* 조건 분기 ❌
* 상태 의미 해석 ❌

**허용 예**

```java
BatterySummaryViewDto summary =
        batterySummaryService.getSummary(batteryId);

model.addAttribute("batterySummary", summary);
```

> Controller는 **흐름만 연결**하며
> “왜 이런 상태인지”에 대한 판단을 포함해서는 안 된다.

---

### 2.2 Service Layer (핵심 계층)

Service Layer는 EMS Backend에서 **가장 중요한 판단 계층**이다.

**책임**

* 장비 상태 판단
* 운전 가능 여부 판단
* 고장 개수 집계
* 파셜 / 제한 / 정지 상태 결정
* View에 전달할 **최종 결과 구조 생성**

**반드시 Service에서 수행해야 하는 판단**

* NORMAL / WARNING / FAULT / DISCONNECTED 판정
* PARTIAL 운전 여부
* Rack 일부 탈락 판단
* 운전 가능 / 운전 불가 판단

**허용 예**

```java
BatteryRackStatusTableViewDto getRackStatusTable(Long batteryId);
```

Service 내부에서 수행해야 할 작업:

* Rack 목록 구성
* 전체 Rack 개수 계산
* 운전 중 Rack 개수 계산
* FAULT / DISCONNECTED Rack 개수 계산

> OPERATION_POLICY.md의 모든 판단 기준은
> **Service Layer에서 구현되어야 한다.**

---

### 2.3 Provider Layer

Provider는 **원천 데이터 제공 전용 계층**이다.

**책임**

* DB / RTU / 외부 시스템 데이터 조회
* 더미(Mock) 데이터 제공 가능

**금지 사항**

* 상태 판단 ❌
* 운전 가능 여부 판단 ❌
* 색상/표현 의미 판단 ❌

**허용 범위**

* boolean 수준의 고장 발생 여부 제공
* 원시 수치 데이터 제공

**예**

```java
List<FaultItemViewDto> getBatteryFaultItems(Long batteryId);
```

> Provider는
> “무슨 값이 들어왔는지”만 책임지고
> “이게 무슨 의미인지”는 책임지지 않는다.

---

### 2.4 ViewDto Layer

ViewDto는 **이미 판단이 끝난 결과를 담는 컨테이너**이다.

**책임**

* UI 출력에 필요한 데이터 구조 제공

**금지 사항**

* 상태 판단 로직 ❌
* 조건 분기 ❌
* 정책 해석 ❌

**허용 사항**

* 단순 getter
* 이미 결정된 상태 기준의 단순 집계

**허용 예**

```java
public int getFaultRackCount() {
    return faultRackCount;
}
```

> ViewDto는 **똑똑하면 안 된다.**
> 판단은 Service에서 끝나야 한다.

---

### 2.5 View (HTML / Thymeleaf)

View는 **표현 전용 계층**이다.

**책임**

* 값 출력
* 색상 적용
* UX 이벤트 처리 (클릭, 이동)

**금지 사항**

* 상태 판단 ❌
* 고장 판단 ❌
* 운전 가능 여부 판단 ❌

**금지 예**

```html
<th:if="${value > 3}">
```

> View는 “결과를 보여주는 역할”만 수행한다.

---

## 2.6 에어컨 제어 관련 Service Layer 책임

본 절은 ESS 배터리 룸 **에어컨 제어 기능에 대한 Service Layer의 책임과 역할**을 정의한다.  
에어컨 제어는 단순 장비 제어가 아닌 **운전 안정성과 안전 정책이 결합된 판단 영역**이므로,  
모든 판단은 반드시 Service Layer에서 수행되어야 한다.

---

### 2.6.1 Service Layer 책임 (에어컨 제어)

Service Layer는 에어컨 제어와 관련하여 다음 책임을 가진다.

- 에어컨 제어 모드 판단 (AUTO / MANUAL)
- 에어컨 통신 상태 기반 제어 가능 여부 판단
- 온도 / 습도 기준 충족 여부 판단
- 냉방 / 난방 / 제습 / FAN 상태 결정
- View에 전달할 **최종 제어 상태 결과 생성**

Service Layer는  
**“제어를 할 것인가 / 말 것인가”**,  
**“어떤 제어 상태로 갈 것인가”**를 최종 결정한다.

---

### 2.6.2 Service Layer에서 반드시 수행해야 하는 판단

다음 판단은 **반드시 Service Layer에서 수행**되어야 한다.

- MANUAL 모드 여부 판단
- 통신 불가 상태 시 제어 차단 판단
- AUTO 제어 가능 여부 판단
- 발전소별 설정된 온도 / 습도 기준 충족 여부 판단
- 제어 결과 상태 결정
    - COOLING
    - HEATING
    - DRY
    - FAN

> 에어컨 제어 판단 기준은  
> **OPERATION_POLICY.md의 “에어컨 AUTO / MANUAL 제어 정책”을 절대 기준으로 한다.**

---

### 2.6.3 에어컨 제어 관련 Provider Layer 책임

Provider Layer는 에어컨 제어 판단을 위한 **원천 데이터 제공 전용 계층**이다.

**Provider의 책임**

- 현재 온도 값 제공
- 현재 습도 값 제공
- 에어컨 통신 상태 제공
- 에어컨 현재 운전 상태 값 제공

**Provider 금지 사항**

- AUTO / MANUAL 판단 ❌
- 제어 필요 여부 판단 ❌
- 기준값 비교 ❌
- 제어 상태 결정 ❌

> Provider는  
> “어떤 값이 들어왔는지”만 책임지고  
> “그 값이 의미하는 바”는 책임지지 않는다.

---

### 2.6.4 에어컨 제어 관련 ViewDto / View 원칙

**ViewDto 원칙**

- ViewDto는 Service Layer에서 **이미 판단이 완료된 제어 결과만 포함**한다
- 제어 모드, 제어 상태, 제어 가능 여부는 모두 Service에서 결정된 값이다

**View 원칙**

- View는 제어 상태를 **표현만 수행**한다
- View에서 온도 / 습도 기준 비교 로직 구현을 금지한다

**금지 예**

```html
<!-- View에서 기준 비교로 제어 상태를 "판단"하면 안 됨 -->
<div th:if="${temperature >= 27}">냉방</div>
<div th:if="${temperature <= 19}">난방</div>
```
---

## 3. Fault 처리 공통 규칙

### 3.1 FaultItemViewDto 공통 사용

Battery / Rack / PCS 고장 항목은
**동일한 ViewDto 구조를 사용한다.**

```java
class FaultItemViewDto {
    boolean fault;
    String label;
}
```

* `fault = true` → 강조 표시
* `fault = false` → 비강조 표시

**중요**

* fault 값의 판단은 Provider 또는 Service에서 완료
* View는 fault 값만 사용

---

## 4. Battery / Rack Fault 카드 적용 규칙

### 4.1 Battery 고장정보 카드

* 배터리 단위 고장 표시
* 배터리 선택 select 없음
* 현재 선택된 배터리 기준 표시

### 4.2 Rack 고장정보 카드

* Rack 단위 고장 표시
* Rack 선택 select 필수
* batteryNo 컨텍스트 유지
* rackNo만 변경

> 두 카드는 **UI 구조는 유사하지만 책임과 맥락이 다르다.**

---

## 5. 변경 및 확장 원칙

* 새로운 상태 추가 시

    * enum 추가
    * Service 로직 반영
    * Provider 수정 불필요

* 판단 기준 변경 시

    * OPERATION_POLICY.md 수정 필수

* UI 구조 변경 시

    * UI_CONTRACT.md 수정 필수

---

## 환경 판단 책임 분리

- 기준 정의 책임: 발전소 정책 (ENVIRONMENT_POLICY.md)
- 데이터 제공 책임: Provider
- 판단 책임: Service
- 표현 책임: View / UI

Service는 정책을 하드코딩하지 않는다.

---

## Provider – Service 책임 분리 원칙 (업데이트)

### Provider 책임
- 장비 제조사 / 모델 / 통신 방식 차이 흡수
- Raw 데이터 조회만 담당
- UI 문구 / 상태 판단 / 색상 결정 금지

### Service 책임
- Provider로부터 Raw 데이터 조회
- 상태 해석 (정상 / 비정상 / Fail-safe)
- UI 표시용 문구 결정
- UI 상태 색상 결정
- ViewDto 조립

### 금지 사항
- Service는 Provider 구현체를 직접 선택하지 않는다
- Service는 제조사 정보를 알지 못한다
- Service 내부에 스케줄링 로직을 두지 않는다

---

### 제어형 설비 예외 규칙 (AirConditioner)

- 제어 명령은 Service에서 제공
- 주기적 AUTO 제어는 Scheduler로 분리
- Scheduler → Service → Provider 방향만 허용

---

## 6. Battery / Rack Fault Service 정책

### 6-1. Fault Service 단일화 원칙

- Battery 고장 정보는
  **BatteryFaultDetailService 단일 Service**에서만 관리한다.
- Rack 전용 Fault Service는 생성하지 않는다.

### 6-2. Rack 고장정보 처리 방식

- Rack 고장정보는
  BatteryFaultDetailService 결과를
  **rackNo 기준으로 필터링**하여 제공한다.
- Rack 고장정보는 View/UX 계층의 표현 개념이다.

### 6-3. 금지 사항

- RackFaultService, Rack 전용 Fault Provider 생성 금지
- Battery 고장 코드와 Rack 고장 코드를 분리 정의 금지

---

본 문서는
**현재 구현된 Service Layer 구조를 기준본으로 고정**하기 위해 작성되었다.

본 문서를 위반한 구현은
기능이 정상 동작하더라도 **설계 오류로 간주**한다.

- 2026-01-01
    - 환경 조건 판단(온습도, 에어컨)을 Service Layer 제어 게이트로 분리
    - 장비 상태(OperationStatus)와 환경 조건(Environment Condition) 책임 분리


## Related Documents
- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md

---