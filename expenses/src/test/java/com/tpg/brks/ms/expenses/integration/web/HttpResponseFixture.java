package com.tpg.brks.ms.expenses.integration.web;

import org.apache.http.HttpStatus;

public interface HttpResponseFixture {
    default int isOk() {
        return HttpStatus.SC_OK;
    }
}
