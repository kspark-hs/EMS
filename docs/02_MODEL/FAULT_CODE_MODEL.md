# FAULT_CODE_MODEL.md

본 문서는 EMS 시스템에서 사용되는 **고장 코드(Fault Code) 모델**을 정의한다.
고장 코드는 DB, Service, 알람, 이력, UI에서 **동일한 의미로 사용되는 식별자**이며,
표현(label)·색상·운전 판단과 **분리된 기준 식별값**이다.

* OPERATION_POLICY.md : 고장 발생 시 운전 판단 기준
* SERVICE_LAYER_POLICY.md : 고장 판단·집계 책임 위치
* DEVICE_STATUS_MODEL.md : 상태(enum) 의미 정의
* 본 문서 : **고장 코드의 구조와 사용 규칙 정의**

본 문서와 충돌하는 임의의 Fault Code 정의는 허용하지 않는다.

---

## 1. 목적

* 고장 항목의 **DB 저장 기준 통일**
* 장비(Battery / Rack / PCS / PV 등) 간 **고장 코드 재사용 가능성 확보**
* UI 다국어·문구 변경 시 **코드 불변성 보장**
* 알람 / 이력 / 통계 처리의 기준 키 제공

---

## 2. Fault Code 기본 원칙

### 2.1 코드 불변성

* Fault Code는 **영구 불변**이다.
* label(표시 문구)은 변경 가능하나, code는 변경 금지한다.

> ❗ DB, 로그, 이력, 알람은 **code 기준으로만 연결**한다.

---

### 2.2 코드 vs Label 분리

| 항목        | 설명                  |
| --------- | ------------------- |
| faultCode | 시스템 내부 식별자 (영문, 불변) |
| label     | UI 표시용 문구 (변경 가능)   |

UI는 **label만 사용**하며,
Service / DB / Alarm은 **faultCode만 사용**한다.

---

## 3. Fault Code 공통 구조

### 3.1 Fault Code 네이밍 규칙

```
<DEVICE>_<CATEGORY>_<DETAIL>
```

예시:

* `BATTERY_RACK_COMM_LOSS`
* `PCS_DC_OVERVOLTAGE`
* `RACK_CELL_OVERTEMP`

**규칙**

* 모두 대문자 + 언더스코어
* 의미 중복 금지
* 약어 사용 가능하나 사내 공통 규칙 준수

---

### 3.2 공통 속성

모든 Fault Code는 다음 속성을 가진다.

| 필드          | 설명                  |
| ----------- | ------------------- |
| faultCode   | 고장 코드 (Primary Key) |
| label       | UI 표시 문구            |
| deviceType  | 적용 장비 유형            |
| severity    | 고장 심각도              |
| operable    | 운전 가능 여부            |
| description | 상세 설명 (선택)          |

---

## 4. Severity (고장 심각도)

| 값        | 의미      |
| -------- | ------- |
| INFO     | 정보성 이벤트 |
| WARNING  | 경고 / 제한 |
| CRITICAL | 중대 고장   |

> Severity는 **알람 우선순위 및 이력 필터링**에 사용된다.

---

## 5. Operable (운전 가능 여부)

| 값     | 의미                |
| ----- | ----------------- |
| true  | 고장 발생 상태에서도 운전 가능 |
| false | 고장 발생 시 운전 불가     |

* operable 값은 **Service Layer 판단의 입력값**으로 사용된다.
* UI는 operable 값을 직접 사용하지 않는다.

---

## 6. Battery Fault Code 예시

| faultCode                 | label      | severity | operable |
| ------------------------- | ---------- | -------- | -------- |
| BATTERY_EMERGENCY_STOP    | 비상 정지      | CRITICAL | false    |
| BATTERY_RACK_PARTIAL_DROP | Rack 일부 탈락 | WARNING  | true     |
| BATTERY_INTERLOCK_ACTIVE  | 인터락 동작     | CRITICAL | false    |

---

## 7. Rack Fault Code 예시

| faultCode              | label      | severity | operable |
| ---------------------- | ---------- | -------- | -------- |
| RACK_COMM_LOSS         | Rack 통신 단절 | CRITICAL | false    |
| RACK_CELL_OVERTEMP     | 셀 과온       | CRITICAL | false    |
| RACK_VOLTAGE_IMBALANCE | 전압 불균형     | WARNING  | true     |

---

## 8. FaultItemViewDto 연계 규칙

FaultItemViewDto는 **Fault Code의 UI 표현 결과물**이다.

```java
class FaultItemViewDto {
    boolean fault;   // 현재 발생 여부
    String label;    // faultCode에 매핑된 표시 문구
}
```

* faultCode → Service에서 판단
* label → Fault Code 테이블 기준 매핑
* View는 fault 값만 사용

---

## 9. DB 테이블 설계 예시

```sql
CREATE TABLE fault_code (
    fault_code VARCHAR(64) PRIMARY KEY,
    device_type VARCHAR(32) NOT NULL,
    label VARCHAR(128) NOT NULL,
    severity VARCHAR(16) NOT NULL,
    operable BOOLEAN NOT NULL,
    description TEXT
);
```

---

## 10. 적용 원칙 (중요)

* Fault Code는 **Service Layer 기준**으로만 해석한다.
* UI에서 code 기반 판단 금지
* DB / 로그 / 알람 / 이력은 code 기준으로 연결
* label 변경은 **운영 중에도 허용**된다.

---

## 11. Battery Fault Code / Rack 관계 정의

### 11-1. Fault Code 기준

- 모든 Fault Code는 Battery 기준으로 정의한다.
- Rack 전용 Fault Code는 정의하지 않는다.

### 11-2. Rack 정보 처리

- rackNo는 Fault Code의 속성 정보이다.
- rackNo는 null 가능하다.
    - null: Battery 전체 고장
    - 값 존재: 특정 Rack 고장

### 11-3. 다중 Rack 고장

- 동일 Fault Code가
  여러 Rack에 동시에 발생할 수 있다.

---

## 12. 변경 이력

* 2025-XX-XX

    * Fault Code Model 초안 작성 (DB 연계 대비)


## Related Documents
- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md

---

