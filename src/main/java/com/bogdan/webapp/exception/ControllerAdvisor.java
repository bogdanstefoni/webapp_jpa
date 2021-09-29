package com.bogdan.webapp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bogdan.webapp.ErrorsEnum;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<String> handleException(HttpServletRequest request, Throwable ex) {
		ErrorsEnum error;
		if (ex instanceof CustomException) {
			CustomException customException = (CustomException) ex;
			error = customException.getErrorsEnum();
		} else {
			error = ErrorsEnum.GENERAL_ERROR;
		}

		return RestResponse.createErrorResponse(error);
	}
}
