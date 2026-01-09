# UI CONTRACT APPENDIX (Editable)

본 문서는 UI_CONTRACT.md의 부속 문서이다.  
UI_CONTRACT의 “구조 / 단계 / 카드 타입”을 변경하지 않는 범위에서,  
운영 규칙(네이밍, 정렬, 체크리스트)을 관리한다.

- UI_CONTRACT.md와 충돌 시, UI_CONTRACT.md가 우선한다.

---

## A1. 파일 / 클래스 네이밍 원칙 (PCS 기준)

### 목적
- 파일 이름만 보고  
  **페이지 / 카드 타입 / 계층 역할(Provider, Service, ViewDto, Fragment)**을  
  추론 가능해야 한다.
- 신규 페이지(PV 등)는 **PCS 네이밍 규칙을 기준으로 한다.**

---

### A1-1. 템플릿(Thymeleaf) 네이밍

- 페이지 템플릿  
  `templates/<domain>/<page>.html`
    - 예: `templates/pcs/individual-pcs.html`

- 카드 fragment  
  `templates/fragment/<domain>/<card-name>-card.html`
    - 예: `templates/fragment/pcs/pcs-summary-card.html`
    - 예: `templates/fragment/pcs/pcs-fault-status-card.html`

**권장 규칙**
- `-card` 접미사는 카드 단위 fragment에만 사용한다.
- `summary / operation-status / fault-status / status-table / detail`
  키워드는 카드 타입과 1:1로 매칭한다.

---

## A2. 배터리 네이밍 최소 정렬 체크리스트
*(PV 전력량계 페이지 완료 후 바로 수행)*

배터리 네이밍은 기존 구현 이력으로 인해  
PCS 기준과 일부 상이한 구조를 포함하고 있다.

본 체크리스트는  
**기능 동작 변경이나 대규모 리팩토링을 요구하지 않는  
“최소 정렬 기준”**을 정의한다.

---

### A2-1. 정렬 범위

- 대상
    - battery 관련 template / fragment 파일명
    - DTO / ViewDto / Service / Provider 클래스명
- 비대상
    - 동작에 영향이 큰 라우팅 / URL 구조 변경  
      (필요 시 별도 합의)

---

### A2-2. 체크리스트

- [ ] fragment 파일명이 카드 타입을 명확히 포함하는가?
    - 예: `battery-summary-card.html`
    - 예: `battery-operation-status-card.html`

- [ ] 카드 fragment가 `fragment/battery/*` 하위에 일관되게 위치하는가?

- [ ] 동일 개념이 다른 용어로 분산되지 않았는가?
    - summary / overview
    - fault / error
    - status / state 혼재 제거

- [ ] Provider / Service / ViewDto 네이밍이 역할을 드러내는가?
    - Provider: `BatteryXxxProvider`
    - Service:  `BatteryXxxService`
    - ViewDto:  `BatteryXxxViewDto`

- [ ] 카드 fragment에서 데이터와 단위 표현이 분리되어 있는가?
    - 숫자 포맷 / 단위 표기는 공통 Formatter 또는 DTO 규칙을 따른다.

- [ ] “찾기 어려운 파일”이 발생한 경우, 원인을 기록했는가?
    - 발견 즉시 A3(네이밍 예외 기록)에 추가한다.

---

## A3. 네이밍 예외 기록 (누적 관리)

네이밍 규칙을 지키기 어렵거나  
레거시 호환으로 예외가 필요한 경우 여기에 기록한다.

**기록 항목 예시**
- 예외 대상 파일 / 클래스
- 예외 사유
- 대안 또는 향후 정렬 방안
- 결정일

(예시)
- 예외: battery 기존 fragment 파일명 유지
- 사유: 외부 참조 경로 및 다중 include 영향
- 대안: alias fragment 추가 후 단계적 전환
- 결정일: YYYY-MM-DD

---

## A4. Provider / Status 계층 단계적 적용 정책

본 프로젝트는  
설비별 상태 복잡도에 따라  
Provider / Status 계층을 단계적으로 도입한다.

---

### 현재 단계

- PV 전력량계 페이지는  
  UI 및 카드 구조 완성에 집중한다.
- Provider / Status 계층은 도입하지 않는다.

---

### 다음 단계
*(PV 전력량계 페이지 완성 이후)*

- 배터리 / PCS / PV 전력량계 페이지에  
  **동일한 Provider / Status 구조를 일괄 적용한다.**
- 해당 작업은  
  설비 간 상태 표현 및 Fault 처리의  
  구조적 통일을 목적으로 한다.

---

## A5. 도메인 구조 통일 계획 (Battery 기준)

본 섹션은 A4 정책을  
**도메인 구조 관점에서 구체화한 것이다.**

---

### 기준 구조 (Reference)

배터리 도메인은  
다음 하위 구조를 포함한  
**완성형 도메인 구조를 기준**으로 한다.

- dto
- provider
- service
- status
- view

해당 구조는  
상태 표현, Fault 처리, UI 연계를  
명확히 분리하기 위한 기준 구조이다.

---

### 단계적 적용 정책

#### 현재 단계
- PV 전력량계 페이지는  
  UI 및 카드 구조 완성에 집중하며  
  Provider / Status 계층은 도입하지 않는다.

#### 다음 단계
*(PV 전력량계 페이지 완성 이후)*

- 배터리 / PCS / PV 전력량계 도메인에  
  동일한 하위 구조  
  (dto / provider / service / status / view)를  
  일괄 적용한다.

본 통일 작업은  
설비 간 구조적 일관성 확보를 목적으로 하며,  
기능 추가와는 분리된 작업으로 진행한다.

---

## A6. PV 전력량계 트렌드 페이지 UI 규칙 (고정)

### 목적
- PV 실측 발전 상태를 **시간 흐름 기준으로 분석**하기 위함
- 현재 상태(Summary/Status)와 **완전히 분리된 분석 전용 영역**

---

### 트렌드 그래프 구성 (고정)

- 시간축: **24시간 고정 (00:00 ~ 23:59)**
- 기준 문서:
    - TREND_DATA_POLICY.md
    - PV_PVMETER_PROVIDER_POLICY.md

#### 필수 트렌드 항목
- Active Power (실측 발전 전력) ⭐ 필수

#### 선택 트렌드 항목 (확장 가능)
- 누적 발전량(Energy)
- 계통 유효전력

---

### 데이터 표시 규칙 (절대)

- 시간축 재계산 금지 ❌
- 최근 N건 기반 그래프 생성 금지 ❌
- NULL 데이터:
    - 보정 ❌
    - 연결 ❌
    - 0 처리 ❌
    - **그래프 단절(끊김)으로 표현**

- 야간 시간대(NULL)는 **정상 상태로 간주**

---

### 상태(Event / Warning) 표시 규칙

- 상태는 트렌드 값과 **분리된 오버레이 정보**
- 표현 방식:
    - 그래프 상 마커(marker)
- 색상 기준은 UI_STATUS_COLOR_POLICY.md 참조

> 트렌드 값 자체를 상태로 해석하지 않는다.

---

## A7. 환경 조건(온습도) UI 표시 규칙 (고정)

### 기본 원칙

- 온습도 상태는 **환경 조건(Environment Condition)** 이다.
- Battery / PCS의 OperationStatus와 **절대 연동하지 않는다**.
- 환경 조건을 이유로 장비 상태 색상 변경 금지 ❌

---

### UI 표시 위치

- 환경 조건은 다음 위치에만 표시한다.
    - 페이지 상단 보조 정보 영역
    - 또는 SummaryCard 하단 보조 문구 영역

❌ OperationStatusCard 내부 표시 금지  
❌ FaultStatusCard와 혼합 금지

---

### 표시 문구 (고정)

| 환경 상태 | 표시 문구 |
|---|---|
| 기준 만족 | 충방전 운전 가능 |
| 기준 이탈 | 충방전 운전 불가 |
| 데이터 수신 불가 | 충방전 운전 불가 (환경 확인 불가) |

- 문구 외 추가 해석 금지
- 색상/아이콘은 UI_STATUS_COLOR_POLICY.md 기준만 사용

---


본 정책은  
단기 구현 편의보다  
**중·장기 구조 일관성을 우선한다.**

## Related Documents
- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md
