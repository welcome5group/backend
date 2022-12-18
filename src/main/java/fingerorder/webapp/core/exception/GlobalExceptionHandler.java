package fingerorder.webapp.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SqlException.class)
	public ResponseEntity<ErrorResponse> handleSqlException(SqlException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}


//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<List<ExceptionDto>> constraintViolationException(ConstraintViolationException e) {
//		return ResponseEntity.badRequest().body(extractErrorMessages(e));
//	}
//
//	private List<ExceptionDto> extractErrorMessages(ConstraintViolationException e) {
//		return e.getConstraintViolations()
//			.stream()
//			.map(ConstraintViolation::getMessage)
//			.map(ExceptionDto::new)
//			.collect(Collectors.toList());
//	}

}
