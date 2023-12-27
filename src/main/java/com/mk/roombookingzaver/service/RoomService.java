package com.mk.roombookingzaver.service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import com.mk.roombookingzaver.dto.RoomDto;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import com.mk.roombookingzaver.response.RoomResponseAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    //Rename to Rooms
    public RoomResponseAll getAvailableBookings(LocalDate startDate, LocalDate endDate, int numberOfBeds) {
        List<UUID> occupiedRooms = bookingRepository.currentBookings(startDate, endDate)
                .stream()
                .map(booking -> booking.getRoom().getId())
                .toList();

        return new RoomResponseAll(roomRepository.getRoomByBeds(numberOfBeds).stream()
                .filter(isAvailable(occupiedRooms))
                .map(room -> new RoomDto())
                .toList());

        //Option 2
        //List<Room> rooms = roomRepository.getRoomByBeds(numberOfBeds);

        //rooms.removeIf(isOccupied(occupiedRooms));

        /* return new RoomResponseAll(rooms.stream().map(room -> RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .beds(room.getBeds())
                .description(room.getDescription())
                .price(room.getPrice())
                .build()).toList());*/
    }

    private Predicate<Room> isOccupied(List<UUID> occupiedRooms) {
        return room -> occupiedRooms.contains(room.getId());
    }

    private Predicate<Room> isAvailable(List<UUID> occupiedRooms) {
        return room -> !occupiedRooms.contains(room.getId());
    }
}
