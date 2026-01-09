package com.example.demo.domain.equipment.firesystem.provider;

import com.example.demo.domain.equipment.firesystem.status.FireSystemHealthStatusType;
import com.example.demo.domain.equipment.firesystem.view.FireSystemStatusTableRowViewDto;
import com.example.demo.domain.equipment.firesystem.view.FireSystemStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FiroProFireSystemStatusTableProvider
        implements FireSystemStatusTableProvider {

    private final FireSystemProvider fireSystemProvider;

    @Override
    public FireSystemStatusTableViewDto getStatusTable() {

        Boolean extinguishingReleased = fireSystemProvider.isExtinguishingReleased();

        /* Raw 해석 */
        boolean fireDetected = Boolean.TRUE.equals(extinguishingReleased);
        boolean communicationAlive = extinguishingReleased != null;

        FireSystemHealthStatusType status;

        if (!communicationAlive) {
            status = FireSystemHealthStatusType.DISCONNECTED;
        } else if (fireDetected) {
            status = FireSystemHealthStatusType.FAULT;
        } else {
            status = FireSystemHealthStatusType.NORMAL;
        }

        FireSystemStatusTableRowViewDto row =
                new FireSystemStatusTableRowViewDto(
                        "FIRE-SYSTEM-01",
                        status,
                        fireDetected,
                        communicationAlive
                );

        String overallStatusText;

        switch (status) {
            case DISCONNECTED -> overallStatusText = "통신 단절";
            case FAULT -> overallStatusText = "화재 감지";
            case WARNING -> overallStatusText = "주의";
            default -> overallStatusText = "정상";
        }

        return new FireSystemStatusTableViewDto(
                overallStatusText,
                List.of(row)
        );
    }
}
