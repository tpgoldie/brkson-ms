package com.tpg.brks.ms.expenses.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public interface DateGeneration {
    DateFormat DD_MM_YYYY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    default Date generateDate(int date, int month, int year) {
        return Date.from(LocalDate.of(year, month, date).atTime(0 ,0).toInstant(ZoneOffset.UTC));
    }

    default String toDdMmYyyyFormat(Date aDate) {
        return DD_MM_YYYY_FORMAT.format(aDate);
    }
}
