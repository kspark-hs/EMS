# 🔒 BATTERY_UI_LOCK.md

본 문서는 **Battery 개별 화면(UI)의 최종 봉인(LOCK) 선언 문서**이다.  
본 문서에 명시된 기준은 `UI_CONTRACT.md`와 **동일한 강제력**을 가진다.

본 문서 이후, 명시적 해제 선언 없이는  
Battery 화면에 대한 구조·의미·UX 변경을 **허용하지 않는다.**

---

## 1. 봉인 대상 범위

본 봉인은 다음 대상에 적용된다.

- Battery 개별 화면 (`/individual/battery`)
- 포함 카드
    - SummaryCard
    - OperationStatusCard
    - FaultStatusCard
    - StatusTableCard
    - Rack Fault Detail Card

---

## 2. 화면 구조 (고정)

Battery 개별 화면은 아래 순서를 **절대 변경하지 않는다.**

[Summary]  
[OperationStatus]  
[FaultStatus]  
[StatusTable]  
[Rack Fault Detail]

### 금지 사항

- 카드 병합 ❌
- 카드 분리 ❌
- 카드 순서 변경 ❌
- 신규 카드 삽입 ❌
- Battery 전용 고장 카드 추가 ❌

---

## 3. SummaryCard (Battery 요약 카드) 봉인 규칙

### 포함 정보 (고정)

- 온라인 상태
- 메인 릴레이 상태
- 동작 상태 (충전 / 방전 / 대기 등)
- SOC (%)
- DC 전압 (V)
- DC 전류 (A)

### 설계 의도

- Battery **전체 상태 요약** 제공
- 고장 상세 판단이 아닌
    - 운전 가능 여부
    - 에너지 상태
    - 통신/릴레이 상태
      를 빠르게 파악하기 위함

### 금지 사항

- 고장 항목 추가 ❌
- Rack 단위 정보 혼합 ❌
- Trend 전용 지표 전환 ❌

---

## 4. StatusTableCard (Rack 상태 테이블) 봉인 규칙

- Rack 수는 **관리자 설정값** 기준
- `Rack #1 ~ Rack #n` 자동 Row 생성
- Row 클릭 시:
    - `/individual/battery?rackNo={n}`
    - GET 방식 페이지 이동

### 금지 사항

- 테이블 컬럼 구조 변경 ❌
- Row 클릭 제거 ❌
- Ajax 전환 ❌

---

## 5. Rack 고장정보 카드 (Fault Detail) 봉인 규칙

### 고장 최소 단위 고정

- **고장 판단 및 표시는 Rack 단위로만 수행**
- Battery 전체 OR 집계 고장 정보는 **UI에 표시하지 않음**

### 카드 구성

- 상단 Rack 선택 Select:
    - `Rack #1 ~ Rack #n` 자동 생성
- Select 변경 시:
    - GET 방식 페이지 이동
    - 선택된 Rack 기준 고장 정보 갱신

### UX 통일 규칙

- StatusTable Row 클릭
- Rack Select 변경  
  → **동일한 이동 규칙 사용**

---

## 6. Aggregate 개념 사용 제한

- Battery 개별 화면에서는
    - **Battery 전체 OR 집계 고장 카드 사용 금지**
- Aggregate 개념은:
    - 상태 판단
    - 운전 제어
    - 요약 판단
      용도로만 사용
- UI 고장 상세 표현에는 사용하지 않는다.

---

## 7. Operation / Control 관련 정책

- OperationMode / ControlMode는
    - enum 고정값 사용
- 목적:
    - UI 구조 검증
    - 상태 전파 검증
- 메모리맵 / 설정 연동 시
    - **해당 로직만 교체**
- 동적 제어 로직은
    - 의도적으로 본 화면에서 구현하지 않는다.
