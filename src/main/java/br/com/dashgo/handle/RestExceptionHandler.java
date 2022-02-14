package br.com.dashgo.handle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dashgo.exception.BadRequestException;
import br.com.dashgo.exception.BadRequestExceptionDetails;
import br.com.dashgo.exception.ExceptionDetails;
import br.com.dashgo.exception.NotFoundException;
import br.com.dashgo.exception.ValidationError;
import br.com.dashgo.exception.ValidationExceptionDetails;
import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException e) {
		BadRequestExceptionDetails body = BadRequestExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.title("Bad Request Exception, Check the Documentation")
			.details(e.getMessage())
			.developerMessage(e.getClass().getName())
			.inner(e.getInner())
			.build();
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleBadRequestException(NotFoundException e) {
		ExceptionDetails body = ExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.title("Not Found Exception, Check the Documentation")
			.details(e.getMessage())
			.developerMessage(e.getClass().getName())
			.build();
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleAccessDeniedException(AccessDeniedException e) {
		BadRequestExceptionDetails body = BadRequestExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.title("Access Denied Exception, Check the Documentation")
			.details(e.getMessage())
			.developerMessage(e.getClass().getName())
			.build();
		
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(ExpiredJwtException e) {
		BadRequestExceptionDetails body = BadRequestExceptionDetails.builder()
				.timestamp(LocalDateTime.now())
				.title("Bad Request Exception, Check the Documentation")
				.details(e.getMessage())
				.developerMessage(e.getClass().getName())
				.code("token.expired")
				.build();
		
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionDetails> handleBadRequestException(DataIntegrityViolationException e) {
		ExceptionDetails body = ExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.title("Data Integrity Violation Exception, Check the Documentation")
			.details(e.getMessage())
			.developerMessage(e.getClass().getName())
			.build();
		
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionDetails> handleBadRequestException(Exception e) {
		ExceptionDetails body = ExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.title("Exception, Check the Documentation")
			.details(e.getMessage())
			.developerMessage(e.getClass().getName())
			.build();
		
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));

		// YUP React
		List<ValidationError> inner = new ArrayList<ValidationError>();
		fieldErrors.forEach(item -> {
			inner.add(new ValidationError(item.getRejectedValue(), item.getField(), item.getDefaultMessage()));
		});

		return new ResponseEntity<>(ValidationExceptionDetails.builder().timestamp(LocalDateTime.now())
				.title("Bad Request Exception, Invalid Fields").details("Check the field(s) error")
				.developerMessage(exception.getClass().getName()).fields(fields).fieldsMessage(fieldsMessage)
				.inner(inner).build(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder().timestamp(LocalDateTime.now())
				.title(ex.getCause().getMessage()).details(ex.getMessage()).developerMessage(ex.getClass().getName())
				.build();

		return new ResponseEntity<>(exceptionDetails, headers, status);
	}
}
