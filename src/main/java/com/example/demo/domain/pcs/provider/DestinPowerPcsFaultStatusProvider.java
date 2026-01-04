package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS 고장 상태 Provider
 *
 * - DestinPower PCS 메모리맵 기반
 * - 현재는 더미 값
 * - 추후 RTU / DB 연동 시 이 파일만 수정
 */
@Primary
@Component
public class DestinPowerPcsFaultStatusProvider
        implements PcsFaultStatusProvider {

    @Override
    public boolean hasFault() {
        // TODO: DestinPower PCS Fault Summary Bit
        return false;
    }

    @Override
    public boolean isInternalCommOk() {
        // TODO: 내부 통신 상태 Bit
        return true;
    }

    @Override
    public boolean isExternalCommOk() {
        // TODO: 외부 통신 상태 Bit
        return true;
    }

    @Override
    public boolean isInterlockOk() {
        // TODO: 인터락 상태 Bit
        return true;
    }
}

