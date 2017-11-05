package com.tpg.brks.ms.expenses.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateFormatting {
    DateFormat DD_MM_YYYY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    default String toDdMmYyyyFormat(Date aDate) {
        return DD_MM_YYYY_FORMAT.format(aDate);
    }
}
