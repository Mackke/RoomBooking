package com.mk.roombookingzaver.response;

import com.mk.roombookingzaver.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private RoomDto room;
}
