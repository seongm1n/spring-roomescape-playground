package roomescape.exception;

import org.slf4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.util.LoggerUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerUtils.logger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleIllegalArgumentException(EntityNotFoundException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<String> handleIllegalArgumentException(EntityAlreadyExistException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleIllegalArgumentException(TypeMismatchException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().getName());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
