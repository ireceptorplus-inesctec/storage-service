package com.ireceptorplus.storageService.MetadataServiceAPI.Controllers.ExceptionHandling;

import com.ireceptorplus.storageService.MetadataServiceAPI.Controllers.ExceptionHandling.Exceptions.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler
{
    @ExceptionHandler
    public ResponseEntity<ApiException> handleApiRequestException(ApiRequestException apiRequestException)
    {
        ApiException apiException = new ApiException(apiRequestException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error parsing received JSON object")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleJsonParseException() {
        //Handle Exception Here...
    }
}
