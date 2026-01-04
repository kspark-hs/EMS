# ALARM_POLICY.md

본 문서는 EMS에서 사용하는 **알람(Alarm) 정책**을 정의한다.  
알람은 시스템의 이상 상태를 사용자 및 외부 시스템에 **통지(Notification)** 하기 위한 수단이며,  
제어 판단이나 상태 해석의 주체가 아니다.

알람 정책은
- DEVICE_STATUS_MODEL.md
- OPERATION_POLICY.md  
  의 **결과를 소비**하는 하위 정책이다.

---

## 1. 알람의 정의

알람(Alarm)이란 다음을 의미한다.

- 시스템이 **주의 또는 즉각 대응이 필요한 상태**임을 알리는 신호
- UI 표시, 로그 기록, 이력 저장, 외부 연동의 트리거
- **제어 판단 로직이 아니다**

⚠️ 알람 발생 여부로  
운전 가능 / 불가를 **판단해서는 안 된다**

---

## 2. 알람 책임 분리 원칙

| 계층 | 책임 |
|---|---|
| Provider | Raw 이벤트 / 값 제공 |
| Device Status Model | 상태 해석 |
| Operation Policy | 운전 판단 |
| **Alarm Policy** | 알람 발생 조건 정의 |
| Service Layer | 알람 생성 / 해제 |
| UI | 알람 표시 |
| Scheduler | 알람 검사 트리거 |

---

## 3. 알람 발생의 입력 기준

알람은 다음 정보 중 하나 이상을 입력으로 사용한다.

- Device Status Model 결과
- Operation Policy 결과 (operable)
- Fault Code (FAULT_CODE_MODEL.md)
- 통신 상태 (DISCONNECTED)
- Fail-Safe 상태

---

## 4. 알람 분류 체계

### 4.1 알람 심각도 (Severity)

| 등급 | 의미 |
|---|---|
| INFO | 정보성 알림 |
| WARNING | 주의 필요 |
| CRITICAL | 즉각 대응 필요 |

---

### 4.2 알람 성격

| 구분 | 설명 |
|---|---|
| STATUS_ALARM | 상태 변화 알람 |
| FAULT_ALARM | 고장 알람 |
| ENVIRONMENT_ALARM | 환경 이상 알람 |
| COMMUNICATION_ALARM | 통신 이상 알람 |

---

## 5. 환경(온습도) 알람 정책

### 5.1 환경 알람 발생 조건

다음 조건 중 하나라도 만족 시 알람 발생

| 조건 | 알람 유형 | Severity |
|---|---|---|
| 전체 채널 통신 불가 | ENVIRONMENT | CRITICAL |
| 고온 발생 | ENVIRONMENT | WARNING |
| 저온 발생 | ENVIRONMENT | WARNING |
| 과습 발생 | ENVIRONMENT | WARNING |

> 환경 알람은  
> **운전 판단은 하지 않고, 알림만 수행**한다.

---

### 5.2 환경 알람과 운전 판단 관계

- 환경 알람 발생 ≠ 장비 고장
- 환경 알람 발생 ≠ Battery / PCS OperationStatus 변경
- 충·방전 중단 여부는 **OPERATION_POLICY.md 기준만 따른다**

---

## 6. 고장(Fault) 알람 정책

### 6.1 Fault 알람 발생 기준

- `FaultStatus = ACTIVE`
- Fault Code 발생 시

| 대상 | 알람 발생 |
|---|---|
| Battery Fault | O |
| PCS Fault | O |
| Rack Fault | X (Battery Fault에 종속) |

> Rack은 고장의 **위치 정보**일 뿐  
> 독립 알람 주체가 아니다.

---

### 6.2 Fault 알람 해제 기준

- FaultStatus = NONE
- 동일 Fault Code 해제 확인

---

## 7. 통신 알람 정책

| 대상 | 조건 | Severity |
|---|---|---|
| Battery | 통신 단절 | CRITICAL |
| PCS | 통신 단절 | CRITICAL |
| 온습도계 | 전체 채널 단절 | CRITICAL |
| 온습도계 | 일부 채널 단절 | WARNING |
| 소방설비 | 통신 단절 | CRITICAL |

---

## 8. 알람 중복 방지 원칙

- 동일 알람 코드 + 동일 대상에 대해
    - 상태 유지 중 반복 발생 금지
- 상태 변화 시에만
    - 알람 발생 / 해제 이벤트 생성

---

## 9. 알람 이력 기록 정책

알람은 반드시 다음 정보를 포함하여 이력으로 기록한다.

- 발생 시각
- 해제 시각
- 알람 코드
- 알람 대상 (Battery / PCS / Sensor 등)
- Severity
- 원인 설명 (Text)

---

## 10. UI 알람 표현 원칙

- 색상 기준은 `UI_STATUS_COLOR_POLICY.md`를 따른다
- 알람 문구는 **원인 기반 명확 표현** 사용
    - ❌ “주의”
    - ⭕ “고온”, “과습”, “통신 단절”

---

## 11. Scheduler와 알람의 관계

- Scheduler는 알람을 **판단하지 않는다**
- Scheduler는:
    - 알람 검사 Service를
    - **주기적으로 호출**할 수 있다

---

## 12. 금지 원칙

- 알람 발생을 이유로
    - Device Status를 변경 ❌
    - OperationStatus를 변경 ❌
- 알람은 **결과 통지**일 뿐이다

---

## 13. 변경 이력

### v1.0 (2026-01-03)
- 알람 정책 문서 신규 작성
- 환경 / 고장 / 통신 알람 책임 분리
- Operation Policy와의 관계 명확화

---

## Related Documents

- DEVICE_STATUS_MODEL.md
- OPERATION_POLICY.md
- FAULT_CODE_MODEL.md
- UI_STATUS_COLOR_POLICY.md
- SCHEDULER_POLICY.md

---
