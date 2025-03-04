package com.api.lareserva.application.domain.exception;

public record ErrorDetail(String field, String errorMessage, Object rejectedValue) {}
