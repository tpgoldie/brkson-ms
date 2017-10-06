package com.tpg.brks.ms.expenses.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ControllerErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerErrorHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
        LOGGER.error("Failed to handle request successfully. {}", ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public void handleMediTypeNotAcceptableException(Exception ex) {
        LOGGER.error("Failed to handle media type successfully. {}", ex.getMessage());
    }
}
