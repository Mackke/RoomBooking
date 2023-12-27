package com.mk.roombookingzaver.service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import com.mk.roombookingzaver.dto.RoomDto;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.mapper.RoomMapper;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    private final RoomMapper roomMapper;

    public List<RoomDto> getAvailableBookings(LocalDate startDate, LocalDate endDate, int beds) {

        List<UUID> occupiedRooms = bookingRepository.currentBookings(startDate, endDate)
                .stream()
                .map(booking -> booking.getRoom().getId())
                .toList();

        List<Room> rooms = roomRepository.getRoomByBeds(beds);

        rooms.removeIf(isOccupied(occupiedRooms));

        return roomMapper.mapToRoomDtos(rooms);
    }
    private Predicate<Room> isOccupied(List<UUID> occupiedRooms) {
        return room -> occupiedRooms.contains(room.getId());
    }
}
