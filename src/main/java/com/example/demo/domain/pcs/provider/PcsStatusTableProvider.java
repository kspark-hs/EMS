package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.dto.PcsStatusTableRowDto;

import java.util.List;

/**
 * PCS 상태 테이블(Row) Provider
 *
 * 책임:
 * - PCS 상태 테이블에 필요한 Raw 데이터 제공
 * - 해석/표현/문자열 변환 ❌
 * - Service 계층에서 ViewDto로 조립
 */
public interface PcsStatusTableProvider {

    /**
     * PCS 상태 Row 목록 조회
     *
     * @return PCS 상태 Row DTO 리스트
     */
    List<PcsStatusTableRowDto> getStatusRows();
}

