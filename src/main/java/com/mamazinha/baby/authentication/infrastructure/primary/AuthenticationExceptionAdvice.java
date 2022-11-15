package com.mamazinha.baby.authentication.infrastructure.primary;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 20_000)
class AuthenticationExceptionAdvice implements SecurityAdviceTrait {

  private static final String MESSAGE_KEY = "message";

  @ExceptionHandler
  public ResponseEntity<Problem> handleNotAuthenticateUser(NotAuthenticatedUserException ex, NativeWebRequest request) {
    Problem problem = Problem
      .builder()
      .withStatus(Status.UNAUTHORIZED)
      .withTitle("not authenticated")
      .with(MESSAGE_KEY, "error.http.401")
      .build();

    return create(ex, problem, request);
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleUnknownAuthentication(UnknownAuthenticationException ex, NativeWebRequest request) {
    Problem problem = Problem
      .builder()
      .withStatus(Status.INTERNAL_SERVER_ERROR)
      .withTitle("unknown authentication")
      .with(MESSAGE_KEY, "error.http.500")
      .build();

    return create(ex, problem, request);
  }
}
