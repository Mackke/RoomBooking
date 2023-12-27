package com.mk.roombookingzaver.controller;


import java.time.LocalDate;

import com.mk.roombookingzaver.response.RoomListResponse;
import com.mk.roombookingzaver.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomControllerApi {

    private final RoomService roomService;

    @Override
    public ResponseEntity<RoomListResponse> GetAvailableRooms(LocalDate startDate, LocalDate endDate, int beds) { //TODO leave this one
        return new ResponseEntity<>(new RoomListResponse(roomService.getAvailableBookings(startDate, endDate, beds)), HttpStatus.OK);
    }
}
