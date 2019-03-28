package com.yehuda.coupons.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> toResponse(Throwable exception, WebRequest request) {

		if (exception instanceof ApplicationException) {

			ApplicationException appException = (ApplicationException) exception;

			int errorCode = appException.getErrorType().getErrorCode();
			String externalMessage = appException.getErrorType().getExternalMessage();

			// errors of system
			if (errorCode > 600 && errorCode < 650) {

				// in the next version, here i will log the internalMessage and the
				// stackTrace to data base / file
				String internalMessage = appException.getErrorType().getInternalMessage();
				System.out.println(internalMessage);
				appException.printStackTrace();

				// return the message for the customer, in case that something wrong.
				if (errorCode > 610 && errorCode < 650) {

					return ResponseEntity.status(errorCode).body(externalMessage);
//				return handleExceptionInternal(new Exception("error"), "general error", new HttpHeaders(),
//						HttpStatus.valueOf(errorCode), request);
				}
			}

			// errors that depended of client - errorCode > 650
			return ResponseEntity.status(errorCode).body(externalMessage);
//			return handleExceptionInternal(new Exception("error"), externalMessage, new HttpHeaders(),
//					HttpStatus.CONFLICT, request);
		}

		// runtime exception
		String internalMessage = exception.getMessage();

		// in the next version, here i will log the internalMessage
		// and the stackTrace to data base / file
		System.out.println(internalMessage);
		exception.printStackTrace();

		// return the message for the customer, in case that something wrong.
		return ResponseEntity.status(601).body("general error");
//		return handleExceptionInternal(new Exception("general error"), "general error", new HttpHeaders(),
//				HttpStatus.CONFLICT, request);
	}

}