package com.example.demo.domain.pvmeter.service;

import com.example.demo.domain.pvmeter.view.PvMeterStatusRowViewDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PvMeterStatusRowService {

    /**
     * PV 전력량계 상태 테이블 조회
     * - 현재는 더미
     * - DB / RTU / 메모리맵 연결 시 이 메서드만 수정
     */
    public List<PvMeterStatusRowViewDto> getStatusRows(Long pvMeterId) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String lastReceivedAt =
                LocalDateTime.now()
                        .minus(Duration.ofMillis(180))
                        .format(formatter);

        return List.of(
                new PvMeterStatusRowViewDto(
                        "#1",
                        "NORMAL",
                        32.92,
                        32.80,
                        60.03,
                        lastReceivedAt,
                        200L
                )
        );
    }
}
