package com.mk.roombookingzaver.dto;


import com.mk.roombookingzaver.dto.BookingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private BookingDto booking;
}
