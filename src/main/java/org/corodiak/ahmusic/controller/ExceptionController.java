package org.corodiak.ahmusic.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.corodiak.ahmusic.type.Message;
import org.corodiak.ahmusic.type.exception.UserTokenInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler({
		NoHandlerFoundException.class
	})
	public ResponseEntity<Message> notFoundError(Exception e) {
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.NOT_FOUND);
		message.setMessage("존재하지 않는 페이지 또는 자원입니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	
	@ExceptionHandler({
		MissingServletRequestParameterException.class,
		MissingServletRequestPartException.class
	})
	public ResponseEntity<Message> parameterError(Exception e) {
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.BAD_REQUEST);
		message.setMessage("요청에 필요한 파라미터가 비어있거나 적절하지 않습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@ExceptionHandler({
		HttpRequestMethodNotSupportedException.class
	})
	public ResponseEntity<Message> unexceptedRequestMethod(Exception e) {
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		message.setMessage("허용되지 않은 HTTP Request Method 입니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@ExceptionHandler({
		SQLIntegrityConstraintViolationException.class
	})
	public ResponseEntity<Message> duplicatedError(Exception e) {
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.CONFLICT);
		message.setMessage("이미 존재하거나 중복된 값을 받았습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@ExceptionHandler({
		UserTokenInvalidException.class
	})
	public ResponseEntity<Message> userTokenError(Exception e) {
		Message message = new Message();
		message.setHttpStatus(HttpStatus.UNAUTHORIZED);
		message.setMessage("인증된 사용자의 요청이 아니거나, 인증의 유효시간이 만료되었습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	

	
	@ExceptionHandler({
		ClassCastException.class
	})
	public ResponseEntity<Message> unexceptedException(Exception e) {
		Message message = new Message();
		message.setHttpStatus(HttpStatus.UNAUTHORIZED);
		message.setMessage("인증된 사용자의 요청이 아니거나, 인증의 유효시간이 만료되었습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
}
