package com.epam.jwd.core_final.service.impl;

import java.time.LocalDate;
import java.util.Random;

public enum DateGenerator {
    INSTANCE;

    private final LocalDate date = LocalDate.now();
    private final Random random = new Random();

    public LocalDate generateDate() {
        int maxDays = 15;
        return date.plusDays(random.nextInt(maxDays) + 1);
    }
}
