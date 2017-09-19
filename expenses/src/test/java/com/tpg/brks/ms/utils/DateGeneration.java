package com.tpg.brks.ms.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public interface DateGeneration {
    default Date generateDate(int date, int month, int year) {
        return Date.from(LocalDate.of(year, month, date).atTime(0 ,0).toInstant(ZoneOffset.UTC));
    }
}
