package com.example.SpringTutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;

@ControllerAdvice
public class ExceptionHandleController {
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		return new ResponseEntity<>("Exception Handle:" + exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = JsonParseException.class)
	public ResponseEntity<Object> exceptionJsonParse(JsonParseException exception) {
		return new ResponseEntity<>("JsonParseException Handle:" + exception.getMessage(), HttpStatus.NOT_FOUND);
	}

}
