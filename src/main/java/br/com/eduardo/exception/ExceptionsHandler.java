package br.com.eduardo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.eduardo.dto.exception.CustomExceptionDTO;

@RestControllerAdvice
public class ExceptionsHandler {
	
	
	@ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<?> handleBusinessException(BusinessException ex){
		 return ResponseEntity
			        .status(HttpStatus.BAD_REQUEST)
			        .body(new CustomExceptionDTO(ex.getMessage(), LocalDateTime.now()));
        
    }

	 @ExceptionHandler(value = NotFoundException.class)
	    protected ResponseEntity<?> handleNotFoundException(NotFoundException ex){
		 return ResponseEntity
			        .status(HttpStatus.NOT_FOUND)
			        .body(new CustomExceptionDTO(ex.getMessage(), LocalDateTime.now()));
	 }
	 
    @ExceptionHandler(value = IntegrationException.class)
    protected ResponseEntity<?> handleIntegrationException(IntegrationException ex){
    	
    	 return ResponseEntity
			        .status(HttpStatus.BAD_REQUEST)
			        .body(new CustomExceptionDTO(ex.getMessage(), LocalDateTime.now()));
    }
    
    

}
