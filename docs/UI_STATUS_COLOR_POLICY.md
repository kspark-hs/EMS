# UI_STATUS_COLOR_POLICY.md

본 문서는 EMS UI에서 사용되는 **상태 색상(Status Color)의 의미와 적용 원칙**을 정의한다.  
색상은 “보기 좋게”가 아니라 **운영 위험을 즉시 인지시키기 위한 신호**이다.

본 문서는 UI_CONTRACT.md, SERVICE_LAYER_POLICY.md, DEVICE_STATUS_MODEL.md와 함께  
**UI 표현의 공통 기준 문서**로 사용된다.

---

## 1. 목적

본 정책은 다음을 목적으로 한다.

- Battery / PCS / PV 전력량계 간 **상태 색상 통일**
- OFFLINE / FAULT / PARTIAL 상태를 **즉시 식별 가능**하게 함
- UI 개발자가 색상 의미를 임의로 해석하지 않도록 차단

---

## 2. 기본 원칙 (가장 중요)

### 2.1 색상은 “상태의 의미”를 표현한다

- 색상은 단순 장식이 아니다
- **운전 가능 / 위험 / 불가**를 직관적으로 전달해야 한다

### 2.2 UI는 색상을 판단하지 않는다

- 색상 선택의 근거는 **Service에서 계산된 상태 값**
- UI는 전달받은 상태 코드에 따라 **정해진 색상만 적용**

---

## 3. 공통 상태 색상 정의

### 3.1 상태 색상 테이블 (공통)

| 상태 의미 | 상태 코드 예시 | 색상 | 의미 |
|---------|--------------|------|------|
| 정상 | NORMAL / ONLINE / RUNNING | 🟢 Green | 운전 안전 |
| 주의 | WARNING / PARTIAL | 🟡 Yellow | 성능 저하, 감시 필요 |
| 고장 | FAULT | 🔴 Red | 즉시 조치 필요 |
| 통신 두절 | OFFLINE / DISCONNECTED | 🔴 Red | 제어 불가 |
| 정지 | STOPPED | ⚪ Gray | 의도된 정지 |
| 점검 | MAINTENANCE | 🔵 Blue | 점검 중 |

---

## 4. OFFLINE 상태 규칙 (중요)

### 4.1 OFFLINE은 “정상”이 아니다

다음 조건이 하나라도 만족되면 **OFFLINE**으로 판단한다.

- RTU ↔ 장비 통신 두절
- RTU ↔ EMS 통신 두절
- 유효 데이터 미수신

### 4.2 OFFLINE 색상 규칙

| 항목 | 색상 |
|----|----|
| ONLINE | 🟢 Green |
| OFFLINE | 🔴 Red |

❗ OFFLINE은 **항상 위험 상태**이며  
“단순 꺼짐”으로 표현하지 않는다.

---

## 5. Battery / PCS / PV 공통 적용 규칙

### 5.1 Battery 요약 카드 예시

| 조건 | 표현 |
|----|----|
| ONLINE + Relay ON | 🟢 Green |
| OFFLINE | 🔴 Red |
| Relay OFF (의도적 정지) | ⚪ Gray |
| Relay OFF + Fault | 🔴 Red |

> ONLINE이 아니면 초록색 사용 금지

---

### 5.2 PCS 상태 카드 예시

| 상태 | 색상 |
|----|----|
| 운전 중 | 🟢 Green |
| 정지 (스케줄) | ⚪ Gray |
| Fault 발생 | 🔴 Red |
| 통신 두절 | 🔴 Red |

---

### 5.3 PV 전력량계 상태 예시

| 상태 | 색상 |
|----|----|
| NORMAL | 🟢 Green |
| WARNING | 🟡 Yellow |
| DISCONNECTED | 🔴 Red |

---

## 6. UI 구현 규칙

### 6.1 UI에서 허용되는 것

- 상태 코드 → CSS 클래스 매핑
- 색상 값은 **정적 상수**로 관리

### 6.2 UI에서 금지되는 것

❌ 상태 의미 해석  
❌ 조건 분기 (`if offline then red`)  
❌ 장비별 색상 기준 분기

---

## 7. Service와의 역할 분리

- Service:
    - 상태 결정
    - 의미 판단
    - 상태 코드 생성

- UI:
    - 상태 코드 수신
    - 색상 매핑
    - 표현

---

## 8. 문서 우선순위

상태/색상 충돌 시 다음 우선순위를 따른다.

1. OPERATION_POLICY.md
2. DEVICE_STATUS_MODEL.md
3. UI_STATUS_COLOR_POLICY.md
4. UI_CONTRACT.md

---

## 9. 상태 색상 기본 원칙 (절대 변경 불가)

모든 장비 상태 색상은 **HealthStatusType / OperationStatusType enum 기준**으로만 판단한다.

### 색상 규칙

- NORMAL  → 🟢 green
- 그 외 모든 상태
    - FAULT
    - WARNING
    - DISCONNECTED
      → 🔴 red

⚠️ boolean 값(true/false/null)을 직접 사용하여
UI에서 색상을 판단하는 행위를 금지한다.

---

## 10. View Layer 금지 규칙

UI(View, Thymeleaf)는 다음을 수행해서는 안 된다.

- fireDetected, communicationAlive 등
  Raw boolean 값을 직접 비교하여 색상 판단 ❌
- statusText와 색상을 분리 판단 ❌

UI는 반드시 다음 규칙을 따른다.

```text
status == NORMAL ? green : red

---

# UI_STATUS_COLOR_POLICY.md

본 문서는 EMS UI에서 사용하는  
**상태(Status) → 색상(Color) 매핑 기준**을 정의한다.

본 정책은 다음 모든 영역에서 **동일하게 적용**된다.

- 요약 카드 (Summary Card)
- 상세 테이블 (Status Table / Detail Card)
- 알람 / 상태 배지
- Scheduler 상태 표시

본 문서와 충돌하는 임의의 색상 사용은 허용하지 않는다.

---

## 11. 기본 원칙

### 11.1 상태 기준 우선순위

UI 색상은 다음 우선순위로 결정된다.

1. operable (운전 가능 여부)
2. overallStatus (상태 표현)
3. Row 개별 상태 (상세 표시용)

> ⚠️ **UI는 operable 값을 최우선으로 신뢰한다**

---

### 11.2 색상 의미 정의

| 색상 | 의미 |
|----|----|
| 🟢 Green | 정상 / 운전 가능 |
| 🟡 Yellow | 환경 이상 감지 (운전 정지 상태) |
| 🔴 Red | 통신 단절 / 판단 불가 |
| ⚪ Gray | 데이터 없음 / 미적용 |

---

## 12. 공통 상태 색상 매핑

### 12.1 overallStatus 기준

| overallStatus | 색상 | 의미 |
|-------------|------|------|
| NORMAL | 🟢 Green | 정상 |
| WARNING | 🟡 Yellow | 환경 이상 |
| DISCONNECTED | 🔴 Red | 환경 정보 수신 불가 |

---

### 12.2 operable 기준 (제어 판단 기준)

| operable | 색상 | 표시 문구 |
|---------|------|----------|
| true | 🟢 Green | 충·방전 운전가능 |
| false | 🔴 Red | 충·방전 운전정지 |

> operable=false 인 경우  
> overallStatus가 WARNING여도 **제어 상태는 정지**로 표현한다.

---

## 13. Temperature / Humidity UI 정책 (온습도계)

### 13.1 채널(Row) 상태 텍스트 & 색상

| 상태 원인 | status | 표시 텍스트 | 색상 |
|---------|--------|------------|------|
| 정상 | NORMAL | 정상 | 🟢 Green |
| 온도 초과 | WARNING | 고온 | 🟡 Yellow |
| 온도 미만 | WARNING | 저온 | 🟡 Yellow |
| 습도 초과 | WARNING | 과습 | 🟡 Yellow |
| 통신 단절 | DISCONNECTED | 통신 단절 | 🔴 Red |

⚠️ 단순 "주의" 표현은 사용하지 않는다.

---

### 13.2 상세 테이블(Channel Table) 색상 기준

- 상태 컬럼
    - status 기준 색상 적용
- 통신 컬럼
    - communicationAlive = false → 🔴 Red
    - communicationAlive = true → 🟢 Green

---

### 13.3 요약 카드(상단) 색상 기준

- 상태 배지 색상
    - overallStatus 기준
- 충·방전 가능 여부
    - operable 기준 (RED 우선)

---

### 13.4 대표 온습도 값 표시 정책

- 대표 온도
    - 전체 채널 중 **최대 온도**
- 대표 습도
    - 전체 채널 중 **최대 습도**

> 위험 판단에 가장 민감한 값을 UI에 노출

---

## 14. 통신 단절 표현 정책

### 14.1 단일 채널 통신 단절

- Row 상태: `통신 단절`
- overallStatus: 변경 없음
- operable: 판단 로직에 따라 결정

### 14.2 전체 채널 통신 단절

- overallStatus: DISCONNECTED
- operable: false
- UI 색상: 🔴 Red
- 표시 문구: `환경 정보 수신 불가`

---

## 15. 다른 장비와의 공통 정책

본 색상 정책은 다음 장비에도 동일하게 적용한다.

- PCS
- Battery
- Fire System
- PV 전력량계 (상태 배지)

> 장비별 상태 enum은 다를 수 있으나  
> **색상의 의미는 절대 변경되지 않는다**

---

## 16. 정책 변경 절차

- 본 문서 변경 시
    - OPERATION_POLICY.md
    - DEVICE_STATUS_MODEL.md
    - UI_CONTRACT.md
와의 정합성 검증 필수

- 합의 없는 임의 변경 금지

---

## 17. 변경 이력

- 2026-01-01
    - PV 전력량계 상태 예시에서 DELAY 상태 제거
    - WARNING으로 상태 단일화

## Related Documents
- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md


- v1.0 (2026-01-04)
    - Temperature / Humidity 상태 표현 정책 확정
    - 고온 / 저온 / 과습 상태 명문화
    - operable 중심 UI 색상 기준 확정

---

