package com.mk.roombookingzaver.service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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

    public RoomResponseAll getAvailableBookings(LocalDate startDate, LocalDate endDate, int numberOfBeds) {
        List<Room> rooms = roomRepository.getRoomByBeds(numberOfBeds);

        List<UUID> roomIds = bookingRepository.currentBookings(startDate, endDate).stream().map(booking -> booking.getRoom().getId()).toList();

        List<Room> roomsWithVacancy = rooms.stream().filter(room -> !roomIds.contains(room.getId())).toList();


        return new RoomResponseAll(roomsWithVacancy.stream().map(room -> RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .beds(room.getBeds())
                .description(room.getDescription())
                .price(room.getPrice())
                .build()).toList());
    }
}
