# TEST_SCENARIO.md

본 문서는 **EMS/PCS 전파 테스트 전용 시나리오 문서**이다.

* 목적: Provider → Service → View 전파가 **의도대로 분리 동작**하는지 검증
* 범위: 테스트 중 임시 고정된 Provider의 **의도·범위·원복 기준** 명시
* 주의: 본 문서는 **정책/계약 문서가 아님** (OPERATION / UI_CONTRACT와 혼용 금지)

---

## TEST-PCS-FAULT-01 : IGBT Fault 단독 발생

### 🎯 목적

* 단일 Fault(IGBT)가 전체 상태로 정상 전파되는지 검증
* Fault 판단과 통신 판단이 **서로 섞이지 않음**을 확인

---

### 📌 테스트 조건

#### 1️⃣ Fault Detail Provider

* 대상 파일:

    * `DestinPowerPcsFaultDetailProvider`
* 조건:

    * `IGBT Fault = TRUE`
    * 다른 모든 Fault = `FALSE`
    * `External Communication Loss = FALSE`

```text
PcsFaultType.IGBT_FAULT = true
Others = false
```

---

#### 2️⃣ Aggregate Status Raw Provider (통신 상태 고정)

* 대상 파일:

    * `DestinPowerPcsAggregateStatusRawProvider`
* 조건:

    * 내부 통신 정상
    * 외부 통신 정상

```java
isInternalCommOk() = true
isExternalCommOk() = true
```

> ⚠️ 본 설정은 **Fault 전파 테스트 전용**이며
> 통신 테스트 시에는 변경됨

---

### 👀 기대 화면 결과

#### PCS 고장정보 카드

* 🔴 IGBT Fault만 활성
* 다른 Fault 전부 비활성

#### 고장 상태 카드

| 항목       | 기대값 |
| -------- | --- |
| 고장 발생    | 발생  |
| 내부 통신 상태 | 정상  |
| 외부 통신 상태 | 정상  |

#### PCS 상태 테이블

* PCS #1 : 상태 = 고장
* PCS #2 : 상태 = 정상
* 내부/외부 통신 = 정상

#### PCS 요약 / 운전 상태 카드

* PCS 상태 = 고장
* 통신 이상 표시 ❌
* 운전 가능 여부 = 차단

---

### ❌ 실패 판정 기준

* IGBT Fault 외 다른 Fault가 표시됨
* 외부 통신 두절이 함께 표시됨
* PCS #2까지 고장으로 표시됨
* 고장정보 카드와 상태 테이블 결과 불일치

---

### 🔁 원복 기준

* Fault 테스트 종료 후:

    * `IGBT Fault = false`
    * 통신 Provider는 실제 로직 또는 다음 테스트 시나리오로 전환

---

## TEST-PCS-COMM-01 (예정)

### 🎯 목적

* 외부 통신 두절 단독 발생 시 전파 검증
* Fault 전부 OFF 상태 유지

### 📌 테스트 조건 (예정)

```text
All Fault = false
isExternalCommOk() = false
```

---

## 참고

* 본 문서는 **테스트 전용 문서**이다.
* 운영 정책(OPERATION_POLICY.md) 및 UI 계약(UI_CONTRACT.md)과 혼용하지 않는다.
* DB 연동 시 교체 대상:

    * Fault → `DestinPowerPcsFaultDetailProvider`
    * 통신 → `DestinPowerPcsAggregateStatusRawProvider`
