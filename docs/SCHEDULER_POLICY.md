# SCHEDULER_POLICY.md

본 문서는 EMS Backend에서 사용하는 **Scheduler 계층의 설계 원칙과 운용 정책**을 정의한다.  
Scheduler는 사용자 요청과 무관하게 자동 실행되는 계층이므로,  
본 문서의 기준은 **안전·운영·책임 분리 관점에서 최상위 강제 규칙**으로 적용한다.

본 문서와 충돌하는 구현은 허용하지 않는다.

---

## 1. Scheduler의 정의

Scheduler는 다음 조건을 만족하는 **자동 실행 계층**이다.

- 사용자 요청 없이 실행된다
- 시간 기반으로 반복 실행된다
- Service Layer를 호출한다
- 판단 결과에 따라 **제어 트리거를 발생시킬 수 있다**

Scheduler는 **UI / Controller의 대체물이 아니다**.

---

## 2. Scheduler의 역할 범위

### 2.1 Scheduler가 할 수 있는 일 (허용)

- 일정 주기로 Service 메서드 호출
- AUTO 제어 트리거 실행
- 상태 동기화 트리거 실행
- 알람 검사 트리거 실행
- 실행 성공 / 실패 로그 기록

---

### 2.2 Scheduler가 절대 하면 안 되는 일 (금지)

| 금지 항목 | 설명 |
|---|---|
| 상태 판단 | 정상 / 고장 / 운전 가능 여부 판단 ❌ |
| 정책 해석 | 기준값 비교, 정책 분기 ❌ |
| Provider 직접 호출 | Raw 데이터 접근 ❌ |
| 제조사/모델 분기 | 특정 장비 종속 로직 ❌ |
| View / UI 접근 | 화면 상태 변경 ❌ |
| DB 직접 수정 | 상태/제어 결과 직접 반영 ❌ |

> Scheduler는 **판단자(Decision Maker)가 아니다**  
> Scheduler는 **실행자(Trigger)** 이다.

---

## 3. Scheduler ↔ Service 관계 원칙

Scheduler와 Service의 관계는 다음 단방향 구조만 허용한다.

---

## 4. Scheduler 실행 공통 규칙

### 4.1 실행 주기 원칙

- Scheduler 실행 주기는 **정책으로 고정**한다
- 코드 내부에 임의 주기 하드코딩을 금지한다
- 주기 변경은 **본 문서 개정 사항**이다

---

### 4.2 중복 실행 방지 원칙

- 동일 Scheduler는 **동시 실행을 허용하지 않는다**
- 이전 실행이 종료되지 않은 경우:
    - 다음 실행은 즉시 skip
    - 실행 중복을 허용하지 않는다

---

### 4.3 실패 처리 원칙

- Scheduler 실행 실패 시:
    - 예외를 삼키지 않는다
    - 오류 로그를 반드시 남긴다
- Scheduler 실패는:
    - 시스템 전체 중단 사유가 아니다
    - 단, **제어 명령 재시도는 수행하지 않는다**

---

## 5. 에어컨 AUTO 제어 Scheduler 정책

### 5.1 대상 Scheduler

- `AirConditionerAutoControlScheduler`

---

### 5.2 실행 목적

- 배터리 룸 온습도 상태를 기반으로
- 에어컨 AUTO 제어 필요 여부를 **주기적으로 Service에 위임**

---

### 5.3 실행 주기

- 기본 주기: **10초**
- 주기 변경 시:
    - `SCHEDULER_POLICY.md` 개정 필수
    - 코드 단독 수정 금지

---

### 5.4 Scheduler 레벨 실행 조건

Scheduler는 다음 조건에서만 Service를 호출한다.

- EMS 시스템 기동 상태 정상
- Scheduler 자체 비활성화 상태 아님

> 다음 조건은 **Scheduler에서 판단하지 않는다**
> - MANUAL / AUTO 여부
> - 에어컨 통신 상태
> - 온도 / 습도 기준 충족 여부

모든 판단은 **Service Layer 책임**이다.

---

### 5.5 Service 호출 방식

Scheduler는 다음 메서드만 호출한다.

```java
airConditionerService.executeAutoControl();

Scheduler는:
- 반환값을 해석하지 않는다
- 제어 성공/실패 의미를 판단하지 않는다

Service 호출 결과에 대한 모든 해석 책임은
**Service Layer에 있다.**

---

### 5.6 제어 차단 책임

다음 조건에 대한 **제어 차단 판단은 전부 Service 책임**이다.

- MANUAL 모드
- 에어컨 통신 불가
- 온습도계 데이터 무효
- 온습도 기준 이탈

Scheduler는
- 위 조건을 판단하지 않으며
- 항상 동일한 Service 호출만 수행한다

---

## 6. 환경 조건과 Scheduler의 관계

- Scheduler는 환경 상태(온습도)를 **직접 판단하지 않는다**
- Scheduler는 환경 상태를 **Service 판단 결과로만 소비**한다

환경 상태가 `NOT_OPERABLE` 인 경우:

- Service:
    - 충·방전 중단
    - AUTO 제어 차단
    - 이력/알람 기록
- Scheduler:
    - 주기 실행 유지
    - 판단 개입 없음

---

## 7. Scheduler 로그 정책

Scheduler는 다음 로그를 반드시 남긴다.

- 실행 시작 로그
- 실행 종료 로그
- 실행 실패 로그 (Exception 포함)

로그 예시는 다음과 같다.


---

## 8. Scheduler 확장 원칙

향후 다음 Scheduler가 추가될 수 있다.

- 알람 검사 Scheduler
- 상태 동기화 Scheduler
- 통계 집계 Scheduler

모든 신규 Scheduler는 다음 원칙을 따른다.

1. 본 문서를 따른다
2. Service만 호출한다
3. 판단 로직을 포함하지 않는다

---

## 9. 문서 변경 원칙

다음 변경은 반드시 문서 개정을 선행한다.

- Scheduler 실행 주기 변경
- 실행 대상 Service 변경
- 제어 트리거 정책 변경

변경 순서는 다음을 따른다.

1. `SCHEDULER_POLICY.md` 수정
2. 코드 수정
3. 변경 이력 기록

---

# SCHEDULER_POLICY.md

본 문서는 EMS의 **스케줄러(Scheduler)** 가  
언제, 어떤 조건에서, 어떤 제어를 실행해야 하는지를 정의한다.

본 문서는 OPERATION_POLICY.md의 **하위 실행 정책**이다.

---

## 10. Scheduler 역할 정의

Scheduler는 다음 역할만 수행한다.

- 상태 모델 조회
- 운전 정책 결과 확인
- 제어 명령 실행 또는 차단

⚠️ Scheduler는 **판단 로직을 가지지 않는다**

---

## 11. 입력 정보

Scheduler는 다음 정보를 입력으로 사용한다.

| 항목 | 출처 |
|----|----|
| operable | OPERATION_POLICY |
| 장비 상태 | Device Status Model |
| 시간 조건 | 스케줄 설정 |

---

## 12. 충·방전 제어 정책

### 12.1 운전 가능 상태

조건:
- operable = true

행동:
- 충·방전 스케줄 실행 가능
- 제어 명령 송신 허용

---

### 12.2 운전 정지 상태

조건:
- operable = false

행동:
- 모든 충·방전 스케줄 **즉시 차단**
- 신규 제어 명령 **송신 금지**
- 기존 운전 중인 경우 → **정지 명령 송신**

---

## 13. 환경 이상 시 동작 규칙

| 상태 | Scheduler 동작 |
|----|----|
| 환경 이상 | 충·방전 정지 유지 |
| 환경 정보 수신 불가 | 충·방전 정지 유지 |
| 환경 정상 복귀 | 다음 스케줄 시점부터 재개 |

⚠️ 자동 재개 여부는 **추후 정책으로 분리 가능**

---

## 14. 예외 처리 원칙

- Scheduler는 상태를 “해석”하지 않는다
- 모든 판단은 Operation Policy 결과만 신뢰한다
- 불명확한 경우 항상 **보수적(정지)** 으로 동작한다

---

## 15. 변경 이력

- 2025-XX-XX
    - Scheduler Policy 초안 작성
    - AirConditioner AUTO Scheduler 기준 확정

---

## Related Documents

- OPERATION_POLICY.md
- SERVICE_LAYER_POLICY.md
- DEVICE_STATUS_MODEL.md
- ENVIRONMENT_POLICY.md

### v1.0 (2026-01-02)
- Scheduler 책임 범위 명확화
- operable 기반 제어 정책 확정

---

> **Scheduler는 판단하지 않는다.  
> 자동으로 Service를 호출하는 실행기일 뿐이다.**

---

