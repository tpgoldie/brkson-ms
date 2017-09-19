package com.tpg.brks.ms.utils;

import java.util.UUID;

public interface UniqueIdGeneration {
    default String generateId() {
        return UUID.randomUUID().toString();
    }
}
