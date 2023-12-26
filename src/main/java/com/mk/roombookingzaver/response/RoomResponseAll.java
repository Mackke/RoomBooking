package com.mk.roombookingzaver.response;


import java.util.List;

import com.mk.roombookingzaver.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseAll {
    private List<RoomDto> rooms;
}
