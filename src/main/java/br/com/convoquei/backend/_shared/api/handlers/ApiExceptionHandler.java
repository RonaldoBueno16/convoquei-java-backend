package br.com.convoquei.backend._shared.api.handlers;

import br.com.convoquei.backend._shared.exceptions.DomainConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var errors = new HashMap<String, String>();

        for (var err : ex.getBindingResult().getAllErrors()) {
            if (err instanceof FieldError fe) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            } else {
                errors.put(err.getObjectName(), err.getDefaultMessage());
            }
        }

        var problem = base(HttpStatus.BAD_REQUEST, "Erro de validação.", request);
        problem.setTitle("Requisição inválida");
        problem.setType(URI.create("urn:convoquei:problem:validation"));
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido (JSON malformado).", request);
        problem.setTitle("Requisição inválida");
        problem.setType(URI.create("urn:convoquei:problem:malformed-json"));
        return problem;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.BAD_REQUEST, "Parâmetro obrigatório ausente: " + ex.getParameterName(), request);
        problem.setTitle("Requisição inválida");
        problem.setType(URI.create("urn:convoquei:problem:missing-parameter"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.BAD_REQUEST, "Parâmetro inválido: " + ex.getName(), request);
        problem.setTitle("Requisição inválida");
        problem.setType(URI.create("urn:convoquei:problem:invalid-parameter"));
        return problem;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ProblemDetail handleRouteNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.NOT_FOUND, "Rota não encontrada.", request);
        problem.setTitle("Não encontrado");
        problem.setType(URI.create("urn:convoquei:problem:route-not-found"));
        return problem;
    }

    @ExceptionHandler(DomainConflictException.class)
    public ProblemDetail handleDomain(DomainConflictException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.CONFLICT, ex.getMessage(), request);
        problem.setTitle("Não foi possível processar a requisição");
        problem.setType(URI.create("urn:convoquei:problem:domain-error"));
        return problem;
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ProblemDetail handleDomain(InternalAuthenticationServiceException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        problem.setTitle("Não foi possível processar a requisição");
        problem.setType(URI.create("urn:convoquei:problem:domain-error"));
        return problem;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleDomain(BadCredentialsException ex, HttpServletRequest request) {
        var problem = base(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        problem.setTitle("Credenciais Inválidas");
        problem.setType(URI.create("urn:convoquei:problem:domain-error"));
        return problem;
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ProblemDetail handleSpringErrorResponse(ErrorResponseException ex, HttpServletRequest request) {
        var status = HttpStatus.valueOf(ex.getStatusCode().value());
        var detail = (ex.getBody() != null && ex.getBody().getDetail() != null)
                ? ex.getBody().getDetail()
                : "Erro ao processar a requisição.";

        var problem = base(status, detail, request);
        problem.setTitle("Erro");
        problem.setType(URI.create("urn:convoquei:problem:spring-boot" + status.value()));
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest request) {
        var problem = base(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado.", request);
        problem.setTitle("Erro interno");
        problem.setType(URI.create("urn:convoquei:problem:internal-error"));
        return problem;
    }

    private ProblemDetail base(HttpStatus status, String detail, HttpServletRequest request) {
        var problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", Instant.now().toString());
        return problem;
    }
}