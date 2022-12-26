package fingerorder.webapp.core.exception;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public class SqlException extends DataIntegrityViolationException {

	private final ErrorCode errorCode;

	public SqlException(String msg, ErrorCode errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

	public SqlException(String msg, Throwable cause, ErrorCode errorCode) {
		super(msg, cause);
		this.errorCode = errorCode;
	}

}