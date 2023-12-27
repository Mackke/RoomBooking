package com.mk.roombookingzaver.dto;


import java.util.List;

import com.mk.roombookingzaver.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {
    private List<RoomDto> rooms;
}
