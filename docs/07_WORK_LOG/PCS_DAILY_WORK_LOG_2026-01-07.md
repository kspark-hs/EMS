# PCS Daily Work Log – 2026-01-07

## 1. 작업 목적
- PCS 개별운전현황 화면에서 **PCS 상태 카드 / 운전 상태 카드 / 고장 상태 카드**
  구조와 역할을 재정렬
- “표현 가능한 정보”와 “판단/정책 정보”를 분리하여
  **운영 가능한 기준 상태로 복원**

---

## 2. 오늘 확정된 사항 (합의 완료)

### 2.1 PCS 상태 테이블 (하단 테이블)
**표시 항목 (최종 유지)**
- PCS 번호
- 상태 (정상 / 고장)
- 내부 통신 상태 (정상 / 두절)
- DC 전압 (V)
- DC 전류 (A)
- DC 전력 (kW)
- AC 선간전압 (V)
- AC 전류 (A)
- 유효전력 (kW)
- 주파수 (Hz)

**원칙**
- 판결 로직 ❌
- Snapshot 결과만 표시
- 단위 / 소수점 정책은 UI 기준에 따름

---

### 2.2 PCS 상태 카드 상단 카운트
- 표시 기준:  
  **정상 운영 중인 PCS 개수 / 전체 PCS 개수**
- 선택된 PCS 번호 ❌
- Controller에서 `pcsRunningCount` 계산하여 전달

---

## 3. 운전 상태 카드 (중요)

### 3.1 오늘 결론
- **운전 상태 카드는 제거하지 않음**
- 다만, **표현 가능한 항목만 유지**

### 3.2 현재 “표현 가능”하다고 합의된 항목
(PcsAggregateStatusSnapshot 기준)

- 종합 상태  
  → `overallStatus` (정상 / 고장)

- 충·방전 상태  
  → `chargeDischargeStatus` (STOP / IDLE / RUN 등)

- 통신 상태  
  → `commOk` (정상 / 두절)

⚠️ 아래 항목은 **현재 Snapshot에 없음**
- 동작 상태
- 운전 모드 (AUTO / MANUAL)
- 제어 모드 (REMOTE / LOCAL)

→ **내일 구조 협의 대상**

---

## 4. 오늘 발생한 핵심 문제 정리

### 4.1 문제의 본질
- UI에서 **운전 모드 / 제어 모드 / 동작 상태**를 요구
- 하지만 `PcsAggregateStatusSnapshot`에는 해당 정보가 없음
- 그 결과:
    - 카드 구성 혼선
    - 의미 중복
    - 계속된 구조 충돌 발생

### 4.2 확인된 원인
- “운전 상태 카드”의 항목 정의가
  **Snapshot 모델과 불일치**

---

## 5. 현재 Snapshot 구조 (현실 기준)

```java
public class PcsAggregateStatusSnapshot {

    private final PcsOverallStatusType overallStatus;
    private final PcsChargeDischargeStatusType chargeDischargeStatus;
    private final boolean commOk;
}
