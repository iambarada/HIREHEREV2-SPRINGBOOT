package com.centroxy.reponses;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

	private HttpStatus status;
	private String message;
    private LocalDateTime dateTime;
    
   
}
