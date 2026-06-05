package com.devon.building.exception;

import com.devon.building.model.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseDTO> handlerAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(responseDTO);
    }
}
