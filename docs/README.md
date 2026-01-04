# EMS Design Documents

본 디렉토리는 EMS/PMS 시스템의  
**설계 기준(Contract & Policy)** 문서 모음이다.

각 문서는 UI, Backend, 제어 로직, 장비 연동에서  
**공통 기준(Single Source of Truth)** 으로 사용된다.

본 문서들의 합의 없이는  
임의의 구조 변경, 상태 해석 변경, 제어 로직 변경을 허용하지 않는다.

---

## 문서 읽기 순서 (권장)

1. ENVIRONMENT_POLICY.md
    - 발전소 환경 기준 (온습도)
    - 충·방전 가능 여부의 최상위 판단 기준

2. OPERATION_POLICY.md
    - 운전 / 제어 판단 기준
    - 운전 가능 / 제한 / 중단 조건 정의

3. DEVICE_STATUS_MODEL.md
    - 장비 공통 상태 값의 의미 정의
    - NORMAL / PARTIAL / FAULT / Fail-Safe 기준

4. SERVICE_LAYER_POLICY.md
    - Backend 구현 책임 규칙
    - Provider / Service / Scheduler 역할 분리

5. UI_CONTRACT*.md
    - UI 표현 계약
    - 상태 색상 / 카드 구조 / 표현 규칙

---

## 문서 구성 및 역할

### 🔹 최상위 정책 (Policy)

- ENVIRONMENT_POLICY.md
  : 발전소 환경 기준 정책  
  (온습도 기준, 충·방전 가능 판단의 최상위 기준)

- OPERATION_POLICY.md
  : EMS 최상위 운전 판단 기준  
  (충·방전 가능 여부, 제어 가능 조건, 데이터 신뢰성 판단)

---

### 🔹 장비 상태 모델 (Model)

- DEVICE_STATUS_MODEL.md
  : 장비 공통 상태 값 정의  
  (NORMAL / PARTIAL / LIMITED / FAULT / DISCONNECTED / Fail-Safe)

- TEMPERATURE_HUMIDITY_MODEL.md
  : 온습도계 상태 모델  
  (환경 조건 표현용, 장비 상태와 분리)

- AIR_CONDITIONER_MODEL.md
  : 에어컨 상태 / 제어 모드 모델  
  (AUTO / MANUAL / 제어 상태 정의)

- FAULT_CODE_MODEL.md
  : 고장 코드 정의 및 DB 연계 기준  
  (장비별 Fault → 공통 Fault Model 매핑)

---

### 🔹 제어 및 구현 규칙 (Implementation Policy)

- SERVICE_LAYER_POLICY.md
  : Backend 구현 규칙  
  - Provider / Service / Scheduler 책임 분리  
  - 제조사·모델 독립 구조  
  - 상태 해석 / 문구 / 색상 결정 위치 정의

- AIR_CONDITIONER_OPERATION_POLICY.md
  : 에어컨 전용 제어 정책  
  (AUTO / MANUAL 우선순위, 제어 차단 조건)

---

### 🔹 UI 계약 (UI Contract)

- UI_CONTRACT.md
- UI_CONTRACT_APPENDIX.md
- UI_FILE_CONTRACT.md
- UI_STATUS_COLOR_POLICY.md

  : UI 표현 계약  
  - 카드 구조  
  - 필드 순서  
  - 색상 정책  
  - 상태 표현 규칙

---

## 원칙

- 상태 판단은 Service Layer의 책임이다
- 환경 조건은 제어 허용 여부만 결정한다
- UI는 판단하지 않고 표현만 한다
- 문서와 충돌하는 구현은 허용하지 않는다

---

※ 본 기준본에는 OPERATION / DEVICE_STATUS / SERVICE_LAYER / ENVIRONMENT / SCHEDULER 정책이 포함된다.


---

본 문서는 v1.0 기준본으로 고정되며,
이후 구조 변경은 변경 이력 문서를 통해서만 관리한다.

