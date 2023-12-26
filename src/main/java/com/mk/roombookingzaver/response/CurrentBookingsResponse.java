package com.mk.roombookingzaver.response;


import java.util.List;

import com.mk.roombookingzaver.dto.BookingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBookingsResponse {
    private List<BookingDto> booking;
}
