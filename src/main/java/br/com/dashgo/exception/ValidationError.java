package br.com.dashgo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ValidationError {    	
	private Object value;
	private String path;
	private String message; 
}