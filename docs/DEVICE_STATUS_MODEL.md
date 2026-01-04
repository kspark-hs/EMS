# DEVICE_STATUS_MODEL.md

본 문서는 EMS에서 사용되는 **장비 공통 상태 모델(Device Status Model)** 을 정의한다.  
본 문서는 UI, Backend(Service), 제어 로직, 알람, 이력에서 **동일한 의미로 사용되는 상태 값의 기준**이다.

- UI_CONTRACT.md : 화면 구조 / 카드 구성 정의
- OPERATION_POLICY.md : 운전/제어 판단 기준 정의
- DEVICE_STATUS_MODEL.md : **상태 값의 의미와 범위 정의**

본 문서와 충돌하는 임의의 상태 정의는 허용하지 않는다.

---

## 1. 공통 원칙

### 1.1 상태 계층 구조

모든 장비 상태는 다음 3단계로 구분한다.

1. **동작 상태 (Action Status)** : 지금 무엇을 하고 있는가 (행위 중심)
2. **운전 상태 (Operation Status)** : 정상적으로 운전 가능한가 (운영 관점)
3. **고장 상태 (Fault Status)** : 고장이 존재하는가 (알람/이력 관점)

각 단계는 **서로 다른 목적**을 가지며, 혼용하지 않는다.

---

## 2. ActionStatus (동작 상태)

### 2.1 정의

- 장비가 **현재 무엇을 하고 있는지**를 나타낸다.
- 물리적 동작 관점의 상태이다.
- 충/방전, 정지, 점검 등 **행위(Action)** 중심

### 2.2 상태 목록

| 코드 | 의미 | 운전 가능 |
|-----|------|-----------|
| CHARGING | 충전 중 | 가능 |
| DISCHARGING | 방전 중 | 가능 |
| READY | 대기 | 가능 |
| STOPPED | 정지 | 불가 |
| MAINTENANCE | 점검 | 불가 |

### 2.3 적용 장비

- Battery
- PCS
- ESS Power Meter (제어 연계 시)

---

## 3. OperationStatus (운전 상태)

### 3.1 정의

- 장비가 **정상적으로 운전 가능한 상태인지**를 나타낸다.
- 제어 로직, 운전 허용 여부 판단에 사용된다.
- 다수의 내부 상태를 종합한 **운영 관점 상태**

### 3.2 상태 목록

| 코드 | 의미 | 설명 |
|-----|------|------|
| NORMAL | 정상 운전 | 모든 조건 충족 |
| PARTIAL | 파셜 운전 | 일부 구성요소 이상, 제한 운전 |
| LIMITED | 제한 운전 | 출력/동작 제한 상태 |
| FAULT | 고장 | 운전 불가 |

### 3.3 운전 판단 기준

- `NORMAL` : 제어 및 운전 **완전 허용**
- `PARTIAL` / `LIMITED` : 제어 **조건부 허용**
- `FAULT` : 제어 및 운전 **금지**

---

## 4. FaultStatus (고장 상태)

### 4.1 정의

- 장비에 **고장이 존재하는지 여부**를 나타낸다.
- 알람, 이력, 점검 대상 판단에 사용된다.

### 4.2 상태 목록

| 코드 | 의미 |
|-----|------|
| NONE | 고장 없음 |
| ACTIVE | 고장 발생 |

### 4.3 원칙

- 고장 상세 원인은 **별도 Fault Detail** 로 관리한다.
- FaultStatus는 **요약 상태**만 표현한다.

---

## 5. AbnormalType (이상 유형)

### 5.1 정의

- 운전 상태를 저하시킬 수 있는 **비정상 원인 유형**
- 반드시 단일 값이 아니어도 된다. (복수 동시 발생 가능)

### 5.2 Battery AbnormalType 예시

| 코드 | 의미 |
|-----|------|
| RACK_COMM_LOSS | Rack 통신 끊김 |
| RACK_PARTIAL_DROP | Rack 일부 탈락 |
| INTERLOCK_ACTIVE | 인터락 활성 |
| PROTECTION_STOP | 보호 정지 |

---

## 6. 상태 간 관계 규칙

### 6.1 기본 규칙

- FaultStatus = ACTIVE → OperationStatus ≠ NORMAL
- OperationStatus = FAULT → ActionStatus ∈ {STOPPED, MAINTENANCE}
- ONLINE 상태가 아니면 ActionStatus는 의미를 갖지 않는다.

### 6.2 색상 표현 원칙 (UI 연계)

> 색상 자체는 UI_STATUS_COLOR_POLICY.md를 따른다.

| 상태 | 권장 색상 |
|-----|----------|
| NORMAL | 🟢 |
| PARTIAL / LIMITED | 🟠 |
| FAULT / OFFLINE | 🔴 |

---

## 7. Service / View 역할 분리 원칙

### 7.1 Service

- 상태 계산
- 상태 간 관계 판단
- 정책 적용

### 7.2 ViewDto

- 문자열 변환
- 라벨 제공
- 조건 판단 금지

---

## 8. RackHealthStatus (Rack 개별 상태)

### 8.1 정의

- RackHealthStatus는 **Battery 내부 개별 Rack의 상태 모델**이다.
- Battery / PCS의 OperationStatus와 **직접 혼용하지 않는다**.
- Rack 단위의 통신, 보호, 고장 상태를 표현하기 위한 **로컬 상태 모델**이다.

### 8.2 상태 목록

| 상태 | 의미 | 운전 가능 여부 | 비고 |
|----|----|----|----|
| NORMAL | 정상 | 가능 | 정상 운전 |
| WARNING | 경고 / 제한 | 가능 (제한) | 파셜 운전 원인 |
| FAULT | 고장 | 불가 | 즉시 확인 대상 |
| DISCONNECTED | 통신 단절 | 불가 | OFFLINE 취급 |

### 8.3 상위 상태와의 관계

- RackHealthStatus는 **Service Layer에서 집계**되어
  Battery / PCS의 OperationStatus 판단에 사용된다.

예시:
- FAULT / DISCONNECTED Rack 증가  
  → OperationStatus = PARTIAL 또는 FAULT

> Rack 상태는 **원인**, Battery / PCS 상태는 **결과**이다.

---

## 9. Rack 상태 UX 적용 원칙

- RackHealthStatus = FAULT 인 경우에도
  해당 Rack은 **선택 및 클릭 가능 상태를 유지**한다.
- 클릭 시 Rack 고장 상세 영역으로 이동한다.
- 이는 OPERATION_POLICY.md에 정의된  
  **“Rack FAULT 상세 이동 정책”**을 따른다.

---

## 10. Fire System (소방설비) 상태 모델

소방설비는 ESS 안전을 위한 **Fail-Safe 설비**이다.  
화재 신호의 부재 또는 통신 불가는 **위험 상태로 간주**한다.

### 10.1 소방설비 상태 정의 (UI 표현)

| 상태 | 의미 |
|---|---|
| 정상 | 화재 신호 없음 |
| 소화약제 방출 | 화재 발생으로 소화약제 방출 |
| 신호 이상 | 신호 미수신 또는 통신 장애 |

### 10.2 상태 처리 원칙 (Fail-Safe)

- 소방설비는 **확인 불가 상태를 허용하지 않는다**
- `소화약제 방출` 및 `신호 이상`은 동일한 **위험 상태**로 취급한다

### 10.3 Device Status Model 매핑

Fire System은 제어 대상이 아니므로 ActionStatus를 가지지 않는다.

| Fire 상태 | OperationStatus | FaultStatus | 비고 |
|---|---|---|---|
| 정상 | NORMAL | NONE | 안전 상태 |
| 소화약제 방출 | FAULT | ACTIVE | 즉시 대응 필요 |
| 신호 이상 | FAULT | ACTIVE | Fail-Safe 적용 |

- Fire System은 **운전 허용 개념이 존재하지 않는다**
- 모든 비정상 상태는 즉시 `FAULT` 로 승격된다

---

## 11. Temperature / Humidity 상태 모델

Temperature / Humidity 장비는 PCS, FireSystem과 동일하게  
**StatusTableViewDto 기반 상태 모델**을 사용한다.

### 11.1 Row 상태 (채널 단위)

온습도계는 다중 채널(CH 1~CH 4)을 지원한다.  
각 채널은 다음 상태를 가진다.

| 상태 | 의미 |
|---|---|
| NORMAL | 기준 범위 내 |
| HIGH_TEMP | 온도 상한 초과 |
| LOW_TEMP | 온도 하한 미만 |
| HIGH_HUMIDITY | 습도 상한 초과 (표기: 과습) |
| DISCONNECTED | 채널 통신 단절 |

⚠️ “주의” 단독 표현 금지  
→ UI에서는 반드시 원인 기반으로 표현한다.

- HIGH_TEMP → **고온**
- LOW_TEMP → **저온**
- HIGH_HUMIDITY → **과습**
- DISCONNECTED → **통신 단절**
- NORMAL → **정상**

### 11.2 Summary 대표 값 (상단 카드용)

대표 값은 위험 판단 기준(Worst Case)을 사용한다.

| 항목 | 기준 |
|---|---|
| 대표 온도 | 전체 정상 채널 중 **최대 온도(tempMax)** |
| 대표 습도 | 전체 정상 채널 중 **최대 습도(humidityMax)** |

> 습도는 최소값을 보지 않는다. (0%는 정상 데이터로 간주하지 않음)

### 11.3 온습도 상태와 OperationStatus의 관계 (중요)

온습도계의 `OPERABLE / NOT_OPERABLE` 개념은  
장비 자체의 OperationStatus가 아니라, 상위 설비(Battery/PCS)의 운전 제어를 위한  
**환경 조건(Environment Condition) 게이트**이다.

- 온습도 = NOT_OPERABLE
    - 의미: 충·방전 제어 **정지/차단**
    - 처리:
        - PCS 충·방전 정지 또는 출력 제한
        - AUTO 제어 차단
        - 이력/알람에 “환경 기준 이탈” 기록
    - **Battery / PCS OperationStatus에는 영향을 주지 않는다**

금지 원칙:
- 환경 조건(온습도, 외기, 일사 등)을 이유로  
  Battery / PCS OperationStatus를 PARTIAL / FAULT로 승격시키지 않는다.

> 환경은 **제어 조건**, 장비 상태는 **장비 책임 영역**이다.

---

## 12. Fail-Safe (신호 이상) 상태 정의

- Raw 데이터가 null 인 경우
- 통신 두절 / 신호 이상으로 판단
- UI 상에서는 비정상 상태로 표시한다

예시:
- 온습도계: 온도 또는 습도 null → 환경 판단 불가 → 운전 정지(상위 제어)
- 소방설비: 신호 null → 신호 이상(위험) → FAULT 취급

---

## 13. Battery / Rack 고장 상태 모델

### 13.1 고장 판단의 주체

- **모든 고장은 Battery(BMS) 기준으로 판단한다.**
- Rack은 고장의 독립 주체가 아니며,
  Battery 고장의 **발생 위치(속성)** 로만 취급한다.

즉,
- Battery 고장 = 시스템 고장 판단 기준
- Rack 고장 = Battery 고장의 세부 정보

### 13.2 Rack 고장의 위치 개념

- 동일한 Battery 고장 코드가
    - 특정 Rack
    - 여러 Rack
    - Rack 미지정 (Battery 전체)
      로 발생할 수 있다.

이를 위해 Rack 정보는
- 고장 코드의 **속성(rackNo)** 로만 관리한다.

---

## 14. 변경 이력

- 2025-XX-XX
    - Device Status Model 초안 작성
    - RackHealthStatus 정의 및 역할 명확화

- 2026-01-01
    - 환경 조건(온습도)은 OperationStatus에 영향을 주지 않도록 명확화
    - 온습도 기반 충·방전 제어를 제어 게이트로 분리

- 2026-01-03
    - 온습도 StatusTable 구조 확정(PCS/FireSystem 동일)
    - 다중 채널 기반 대표값(tempMax/humidityMax) 원칙 확정
    - “주의” 표기 금지 → 고온/저온/과습으로 원인 표기 확정
    - 일부 채널 통신 단절 허용(전체 통신 불가 시 Fail-Safe)

---

## Related Documents

- OPERATION_POLICY.md
- ENVIRONMENT_POLICY.md
- UI_STATUS_COLOR_POLICY.md
- SERVICE_LAYER_POLICY.md

---


