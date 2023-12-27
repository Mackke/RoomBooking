package com.mk.roombookingzaver.mapper;

import java.util.List;

import com.mk.roombookingzaver.dto.BookingDto;
import com.mk.roombookingzaver.dto.RoomDto;
import com.mk.roombookingzaver.entity.Booking;
import com.mk.roombookingzaver.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDto map(Room room);
    Room map(RoomDto roomDto);

    List<Room> mapToRooms(List<RoomDto> roomDtos);
    List<RoomDto> mapToRoomDtos(List<Room> rooms);
}
