package org.corodiak.ahmusic.type;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	private HttpStatus httpStatus;
	private String message;
	private Object data;
	
}
