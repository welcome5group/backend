package fingerorder.webapp.core.exception;

import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.menu.exception.MenuFindException;
import fingerorder.webapp.domain.store.exception.StoreFindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> memberExHandle(MemberFindException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(String.valueOf(HttpStatus.BAD_REQUEST),
            "찾으려는 회원이 존재하지 않습니다.");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> menuExHandle(MenuFindException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(String.valueOf(HttpStatus.BAD_REQUEST),
            "찾으려는 메뉴가 존재하지 않습니다.");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> storeExHandle(StoreFindException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult(String.valueOf(HttpStatus.BAD_REQUEST),
            "찾으려는  존재하지 않습니다.");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> storeExHandle(StoreFindException e) {
//        log.error("[exceptionHandle] ex" , e);
//        ErrorResult errorResult = new ErrorResult();
//        return new ResponseEntity<>();
//
//    }


}
