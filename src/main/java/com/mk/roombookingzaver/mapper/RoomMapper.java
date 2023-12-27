package com.mk.roombookingzaver.mapper;

import com.mk.roombookingzaver.dto.RoomDto;
import com.mk.roombookingzaver.entity.Room;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDto map(Room room);
    Room map(RoomDto roomDto);
}
