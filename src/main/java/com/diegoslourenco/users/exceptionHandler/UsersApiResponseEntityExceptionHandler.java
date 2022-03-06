package com.diegoslourenco.users.exceptionHandler;

import com.diegoslourenco.users.dto.ErrorDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UsersApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public UsersApiResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorDTO> errors = this.createErrorsList(ex.getBindingResult());

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        List<ErrorDTO> errors = Collections.singletonList(new ErrorDTO("Resource not found", ex.toString()));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ NameNotUniqueException.class })
    public ResponseEntity<Object> handleUserNotUniqueException(NameNotUniqueException ex, WebRequest request) {

        List<ErrorDTO> errors = Collections.singletonList(new ErrorDTO("Name already in use for another resource", ex.toString()));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmailNotUniqueException.class })
    public ResponseEntity<Object> handleEmailNotUniqueException(EmailNotUniqueException ex, WebRequest request) {

        List<ErrorDTO> errors = Collections.singletonList(new ErrorDTO("Email already in use for another user", ex.toString()));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<ErrorDTO> createErrorsList(BindingResult bindingResult) {

        return bindingResult.getFieldErrors().stream()
                .map(fieldError ->new ErrorDTO(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()), fieldError.toString()))
                .collect(Collectors.toList());
    }

}
