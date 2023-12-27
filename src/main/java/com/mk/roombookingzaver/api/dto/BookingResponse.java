package com.mk.roombookingzaver.api.dto;


import com.mk.roombookingzaver.api.dto.BookingDto;
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
