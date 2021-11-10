package com.ronhan.pacypay.endpoint;

import com.ronhan.pacypay.pojo.Response;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/12
 **/
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public Response returnErr(BindException bindException) {
        String err = bindException.getFieldErrors()
                .stream()
                .map(e -> e.getField() + ':' + e.getDefaultMessage())
                .findFirst()
                .get();
        return Response.error(400, err, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response returnErr(MethodArgumentNotValidException exception) {
        String err = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ':' + e.getDefaultMessage())
                .findFirst()
                .get();
        return Response.error(400, err, null);
    }
}
