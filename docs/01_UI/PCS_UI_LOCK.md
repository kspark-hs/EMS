# 🔒 PCS_UI_LOCK.md

본 문서는 **PCS 개별 화면(UI)의 최종 봉인(LOCK) 선언 문서**이다.  
본 문서에 명시된 기준은 UI_CONTRACT.md와 **동일한 강제력**을 가진다.

본 문서 이후, 명시적 해제 선언 없이는  
PCS 화면에 대한 구조·의미·UX 변경을 **허용하지 않는다.**

---

## 1. 봉인 대상 범위

본 봉인은 다음 대상에 적용된다.

- PCS 개별 화면 (`/individual/pcs`)
- 포함 카드
    - SummaryCard
    - OperationStatusCard
    - FaultStatusCard
    - StatusTableCard
    - Fault Detail Card

---

## 2. 화면 구조 (고정)

PCS 개별 화면은 아래 순서를 **절대 변경하지 않는다.**

[Summary]  
[OperationStatus]  
[FaultStatus]  
[StatusTable]  
[Detail]

- 카드 병합 ❌
- 카드 분리 ❌
- 카드 순서 변경 ❌
- 신규 카드 삽입 ❌

---

## 3. SummaryCard (PCS 요약 카드) 봉인 규칙

### 포함 수치 (고정)

| 항목 | 의미 |
|---|---|
| PV 유효전력 | PV 메모리맵 기준 실측 발전 전력 |
| 충·방전 Reference | PCS 제어 목표 전력 |
| PCS 유효전력 | PCS 실제 출력 전력 |

### 설계 의도

- **원인(PV) → 목표(Reference) → 결과(PCS)** 흐름을 한눈에 표현
- 단순 수치 표시가 아닌 **운전 판단 보조용 요약 정보**

### 금지 사항

- 수치 삭제 ❌
- 의미 재해석 ❌
- Trend 전용으로 분리 ❌

---

## 4. StatusTableCard 봉인 규칙

- PCS 대수는 **관리자 설정값** 기준
- `PCS #1 ~ PCS #n` 자동 Row 생성
- Row 클릭 시:
    - `/individual/pcs?pcsNo={n}`
    - GET 방식 페이지 이동

### 금지 사항

- 테이블 구조 변경 ❌
- Row 클릭 제거 ❌
- Ajax 전환 ❌

---

## 5. PCS 고장정보 카드 (Fault Detail) 봉인 규칙

- 상단 PCS 선택 Select:
    - `PCS #1 ~ PCS #n` 자동 생성
- Select 변경 시:
    - GET 방식 페이지 이동
    - 선택된 PCS 기준으로 고장 정보 갱신

### UX 통일 규칙

- StatusTable Row 클릭
- Fault Select 변경  
  → **동일한 이동 규칙 사용**

---

## 6. Aggregate 개념 사용 제한

- PCS 개별 화면에서는
    - **Aggregate 전용 요약 카드 사용 금지**
- Aggregate 개념은:
    - 대시보드 설계 시점에만 재논의
    - 사용하지 않을 가능성 포함

---

## 7. OperationMode / ControlMode 고정값 정책

- 현재 단계에서는 enum 고정값 사용
- 목적:
    - UI 구조 검증
    - 상태 전파 검증
- 메모리맵 / 설정 연동 시
    - **해당 라인만 교체**
- 상태에 따른 동적 변경 로직은
    - 의도적으로 미구현

---

## 8. 최종 선언

본 문서 기준으로:

- PCS 화면은 **LOCK 상태**이다.
- UI_CONTRACT 수정 없이
    - 구조 변경
    - 의미 변경
    - UX 변경  
      을 **절대 허용하지 않는다.**

해제는 반드시 명시적 문서로만 선언한다.

---
