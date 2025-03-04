package com.api.lareserva.infrastructure.config;

import com.api.lareserva.application.domain.exception.DomainException;
import com.api.lareserva.application.domain.exception.ErrorDetail;
import com.api.lareserva.application.usecase.exception.BusinessException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LaReservaControllerExceptionHandler {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<Object> handlerBusinessException(final BusinessException ex) {
    log.error(ex.getMessage());
    final var errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode(), null);

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler({DomainException.class})
  public ResponseEntity<ErrorResponse> handleDomainException(final DomainException ex) {
    log.error("Domain error: {}", ex.getMessage());

    final var errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode(), ex.getErrors());

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    log.error("Validation error: {}", ex.getMessage());

    final List<ErrorDetail> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    new ErrorDetail(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        fieldError.getRejectedValue()))
            .toList();

    final DomainException domainException =
        new DomainException("Validation failed", "domain_exception", errors);

    return ResponseEntity.badRequest()
        .body(
            new ErrorResponse(
                domainException.getMessage(),
                domainException.getErrorCode(),
                domainException.getErrors()));
  }
}
