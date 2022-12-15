package fingerorder.webapp.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode().getCode(), e.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
