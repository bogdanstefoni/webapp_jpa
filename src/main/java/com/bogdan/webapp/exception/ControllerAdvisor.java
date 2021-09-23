package com.bogdan.webapp.exception;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.bogdan.webapp.ErrorsEnum;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Object> handleException(HttpServletRequest request, Throwable ex) {
		CustomException customException = (CustomException) ex;
		ErrorsEnum error = customException.getErrorsEnum();
		Map<String, Object> body = new LinkedHashMap<>();

		if(ex instanceof CustomException) {

			body.put("timestamp", LocalDate.now());
			body.put("error code", error.getErrorCode());
			body.put("error description", error.getErrorDescription());

		} else {

			body.put("timestamp", LocalDate.now());
			body.put("error code",ErrorsEnum.GENERAL_ERROR.getErrorCode());
			body.put("error description", ErrorsEnum.GENERAL_ERROR.getErrorDescription());

		}

		return new ResponseEntity<>(body, error.getHttpStatus());

	}
}
