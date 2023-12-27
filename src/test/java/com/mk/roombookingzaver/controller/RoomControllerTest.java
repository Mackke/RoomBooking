package com.mk.roombookingzaver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.roombookingzaver.entity.Booking;
import com.mk.roombookingzaver.entity.Room;
import com.mk.roombookingzaver.repository.BookingRepository;
import com.mk.roombookingzaver.repository.RoomRepository;
import com.mk.roombookingzaver.response.RoomListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.DataUtil;
import util.FileUtils;
import util.MapperUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private final String baseUrl = "/app/v1/rooms";

    @BeforeEach
    public void resetDb() throws IOException {
        String roomsJson = FileUtils.readFromFileToString("/rooms.json");
        List<Room> rooms = MapperUtil.deserializeRooms(roomsJson);

        roomRepository.saveAll(rooms);

        String bookingsJson = FileUtils.readFromFileToString("/bookings.json");
        List<Booking> bookings = MapperUtil.deserializeBookings(bookingsJson);

        bookingRepository.saveAll(bookings);
    }

    @Test
    void givenRoomBetweenDatesAndBeds_whenGetAllAvailableRooms_thenReturnAllAvailableRooms() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2023, 12, 23);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        int beds = 1;

        //when
        String contentAsString = mockMvc.perform(get(baseUrl + "/availability")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("beds", Integer.toString(beds))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RoomListResponse response = DataUtil.readValue(contentAsString, RoomListResponse.class);

        //then
        assertEquals(response.getRooms().size(), 2);
    }
}
