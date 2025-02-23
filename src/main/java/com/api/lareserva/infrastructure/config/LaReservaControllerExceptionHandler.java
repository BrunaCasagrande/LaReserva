package com.api.lareserva.infrastructure.config;

import com.api.lareserva.application.usecase.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LaReservaControllerExceptionHandler {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<Object> handlerBusinessException(final BusinessException ex) {
    log.error(ex.getMessage());
    final var errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode());

    return ResponseEntity.badRequest().body(errorResponse);
  }
}
