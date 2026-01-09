package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryFaultStatusProvider;
import com.example.demo.domain.battery.status.BatteryFaultSnapshot;
import org.springframework.stereotype.Service;

@Service
public class BatteryFaultSnapshotService {

    private final BatteryFaultStatusProvider faultStatusProvider;

    public BatteryFaultSnapshotService(
            BatteryFaultStatusProvider faultStatusProvider
    ) {
        this.faultStatusProvider = faultStatusProvider;
    }

    /**
     * 랙 단위 고장 스냅샷 조회
     */
    public BatteryFaultSnapshot getSnapshot(Long rackId) {
        return new BatteryFaultSnapshot(
                faultStatusProvider.hasFault(rackId),
                faultStatusProvider.isInternalCommOk(rackId),
                faultStatusProvider.isExternalCommOk(rackId),
                faultStatusProvider.isInterlockOk(rackId)
        );
    }
}
