package com.example.demo.domain.equipment.firesystem.provider;

/**
 * Fire System Provider Contract
 */
public interface FireSystemProvider {

    /**
     * @return TRUE  : 소화약제 방출
     *         FALSE : 이상 없음
     *         null  : 통신/신호 이상
     */
    Boolean isExtinguishingReleased();
}


