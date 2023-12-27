package com.mk.roombookingzaver.api.controller;

import com.mk.roombookingzaver.api.dto.ErrorResponse;
import com.mk.roombookingzaver.exception.BookingNotFoundException;
import com.mk.roombookingzaver.exception.RoomNotFoundException;
import com.mk.roombookingzaver.exception.RoomOccupiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        var response = new ErrorResponse("0", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RoomNotFoundException.class, BookingNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRoomNotFound(RoomNotFoundException e) {
        var response = new ErrorResponse("1", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RoomOccupiedException.class})
    public ResponseEntity<ErrorResponse> handleRoomOccupied(RoomOccupiedException e) {
        var response = new ErrorResponse("2", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
