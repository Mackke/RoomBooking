package com.mk.roombookingzaver.dto;


import java.util.List;

import com.mk.roombookingzaver.dto.BookingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingListResponse {
    private List<BookingDto> bookings;
}
