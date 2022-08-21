package xyz.chaobei.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import xyz.chaobei.error.exceptions.AbstractException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param exception 异常信息
     * @param request   请求
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity methodArgumentNOtValid(MethodArgumentNotValidException exception, WebRequest request) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return ResponseEntity.badRequest().body(fieldError);
    }

    /**
     * @param exception 异常信息
     * @param request   请求
     * @return 错误信息
     */
    @ExceptionHandler(AbstractException.class)
    @ResponseBody
    public ResponseEntity abstractExceptionHandler(AbstractException exception, WebRequest request) {
        log.error("abstractExceptionHandler", exception);
        return ResponseEntity.internalServerError().body(exception.getInfo().getMessage());
    }


}