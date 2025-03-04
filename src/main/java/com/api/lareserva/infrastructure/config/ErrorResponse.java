package com.api.lareserva.infrastructure.config;

import com.api.lareserva.application.domain.exception.ErrorDetail;
import java.util.List;
import lombok.Generated;

@Generated
public record ErrorResponse(String message, String error, List<ErrorDetail> errors) {}
