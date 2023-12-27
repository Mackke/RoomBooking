package com.mk.roombookingzaver.api.dto;

import com.mk.roombookingzaver.api.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private RoomDto room;
}
