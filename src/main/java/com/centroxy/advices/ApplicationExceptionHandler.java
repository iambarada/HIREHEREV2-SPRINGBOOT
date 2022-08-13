package com.centroxy.advices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.centroxy.exceptions.EmployeeNotFoundException;
import com.centroxy.exceptions.ProjectNotFoundException;
import com.centroxy.reponses.ExceptionResponse;



/**
 * @author Mahesh 07-Jul-2022
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

	// This method handle the custom exception
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noElementFound(NoSuchElementException noSuchElementException) {
		return new ResponseEntity<String>("Data is Not present in this id", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Badal 12-Jul-2022 5:22:53 pm
	 */
	// This method is used for handle ProjectNotFoundException
	@ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException projectNotFoundException) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Project is not present in this ProjectId");
		response.setDateTime(LocalDateTime.now());
		response.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Badal 12-Jul-2022 5:28:01 pm
	 */
	// This method is used for handle EmployeeNotFoundException
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Employee is not present in this EmployeeNo");
		response.setDateTime(LocalDateTime.now());
		response.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> noElementFound(EmptyResultDataAccessException emptyResultDataAccessException) {
		return new ResponseEntity<String>("Data is Not present in this id", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleEmployeeNotFoundException(ConstraintViolationException constraintViolationException) {
		String error ="";
		List<ExceptionResponse> errors = new ArrayList<>();

		for (ConstraintViolation<?> violation : constraintViolationException.getConstraintViolations()) {
			ExceptionResponse response = new ExceptionResponse();
			response.setMessage(violation.getMessage());
			response.setDateTime(LocalDateTime.now());
			errors.add(response);
		}
		return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
	}


}
