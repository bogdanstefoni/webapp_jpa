package com.bogdan.webapp.exception;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bogdan.webapp.ErrorsEnum;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	private static final String TIMESTAMP = "timestamp";
	private static final String ERROR_CODE = "errorCode";
	private static final String ERROR_DESCRIPTION = "errorDescription";

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Object> handleException(HttpServletRequest request, Throwable ex) {
		HttpStatus httpStatus;
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDate.now());
		if (ex instanceof CustomException) {
			CustomException customException = (CustomException) ex;
			ErrorsEnum error = customException.getErrorsEnum();
			body.put(ERROR_CODE, error.getErrorCode());
			body.put(ERROR_DESCRIPTION, error.getErrorDescription());
			httpStatus = error.getHttpStatus();
		} else {
			body.put(ERROR_CODE, ErrorsEnum.GENERAL_ERROR.getErrorCode());
			body.put(ERROR_DESCRIPTION, ErrorsEnum.GENERAL_ERROR.getErrorDescription());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(body, httpStatus);
	}
}
