package com.api.lareserva.core.domain.exception;

public record ErrorDetail(String field, String errorMessage, Object rejectedValue) {}
