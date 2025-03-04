package com.api.lareserva.infrastructure.gateway.exception;

import lombok.Getter;

@Getter
public class GatewayException extends RuntimeException {

  private static final String DEFAULT_CODE = "gateway_exception";
  private final String code;

  public GatewayException(final String message, final Throwable throwable) {
    super(message, throwable);
    this.code = DEFAULT_CODE;
  }

  public GatewayException(final String message) {
    super(message);
    this.code = DEFAULT_CODE;
  }

  public GatewayException(final String codeError, final String message, final Throwable cause) {
    super(message, cause);
    this.code = codeError;
  }
}
