package com.mk.roombookingzaver.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private UUID id;
    private RoomDto room;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate archived;
}
