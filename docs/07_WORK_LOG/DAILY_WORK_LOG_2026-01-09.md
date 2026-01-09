# DAILY WORK LOG – 2026-01-09

## 1. 작업 개요

- Battery 개별 화면 구조 재검토
- Battery / PCS 고장 구조 정합성 점검
- 중복 UI 제거 및 고장 판단 기준 단순화
- 문서 구조 정리 방향 확정

---

## 2. 핵심 결정 사항 (확정)

### 2.1 Battery 고장정보 카드 제거

#### 배경
- Battery 고장정보 카드와 Rack 고장정보 카드의 **항목이 동일**
- Battery 고장정보는 Rack 고장정보의 OR 집계에 불과
- 운영·점검·조치 기준은 **항상 Rack 단위**

#### 결정
- ❌ Battery 고장정보 카드 제거
- ✅ Rack 고장정보 카드만 유지
- ✅ PCS 개별 화면과 **완전 동일한 패턴** 유지

| 설비 | 고장 최소 단위 |
|---|---|
| PCS | pcsId |
| Battery | rackId |

---

### 2.2 OR 집계 개념 사용 범위 제한

- OR 집계는 **상태 판단 / 운전 제어 / 요약 판단** 용도로만 사용
- 고장 **상세 UI 표시에는 사용하지 않음**

> “보여주는 고장”과  
> “판단을 위한 집계”의 책임을 명확히 분리

---

## 3. 코드 변경 사항

### 3.1 Service

- `BatteryFaultDetailService`
    - ❌ `getBatteryFaultDetails(List<Long> rackIds)` 제거
    - ✅ `getRackFaultDetails(Long rackId)`만 유지
    - 고장 상세는 Rack 기준으로만 제공

---

### 3.2 Controller

- Battery 고장정보 카드 관련 model attribute 제거
- Rack 고장정보만 model에 전달

```java
model.addAttribute(
    "rackFaultItems",
    batteryFaultDetailService.getRackFaultDetails(selectedRackId)
);
