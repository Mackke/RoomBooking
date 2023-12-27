package com.mk.roombookingzaver.api.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {
    private List<RoomDto> rooms;

    // {
    //   "rooms": []
    //}
}
