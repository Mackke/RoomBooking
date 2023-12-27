package com.mk.roombookingzaver.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mk.roombookingzaver.data.entity.Booking;
import com.mk.roombookingzaver.data.entity.Room;
import com.mk.roombookingzaver.data.repository.BookingRepository;
import com.mk.roombookingzaver.data.repository.RoomRepository;
import com.mk.roombookingzaver.api.dto.BookingRequest;
import com.mk.roombookingzaver.api.dto.BookingResponse;
import com.mk.roombookingzaver.api.dto.BookingListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.DataUtil;
import util.FileUtils;
import util.MapperUtil;


@SpringBootTest
@AutoConfigureMockMvc
public class BookingRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private final String baseUrl = "/app/v1/bookings";

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUpData() throws IOException {

        String roomsJson = FileUtils.readFromFileToString("/rooms.json");
        List<Room> rooms = MapperUtil.deserializeRooms(roomsJson);

        roomRepository.saveAll(rooms);

        String bookingsJson = FileUtils.readFromFileToString("/bookings.json");
        List<Booking> bookings = MapperUtil.deserializeBookings(bookingsJson);

        bookingRepository.saveAll(bookings);
    }

private static List<BookingRequest> SuccessfulBookings() {
    return List.of(new BookingRequest(
            UUID.fromString("6e96d7bb-1a0c-4e95-bd3a-7a74e21f71ef"),
            LocalDate.of(2023,12,15),
            LocalDate.of(2023,12,20)),
            new BookingRequest(
                    UUID.fromString("6e96d7bb-1a0c-4e95-bd3a-7a74e21f71ef"),
                    LocalDate.of(2023,12,1),
                    LocalDate.of(2023,12, 5)),
            new BookingRequest(
                    UUID.fromString("6e96d7bb-1a0c-4e95-bd3a-7a74e21f71ef"),
                    LocalDate.of(2023,12,25),
                    LocalDate.of(2023,12,31)));
}

    @ParameterizedTest
    @MethodSource("SuccessfulBookings")
    public void createBookingTest(BookingRequest bookingRequest) throws Exception {
        //given
        //when
        String contentAsString = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DataUtil.convertObjectToString(bookingRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookingResponse response = mapper.readValue(contentAsString, BookingResponse.class);

        //then
        assertNotNull(response.getBooking().getId());
        assertEquals(response.getBooking().getStartDate(), bookingRequest.getStartDate());
        assertEquals(response.getBooking().getEndDate(), bookingRequest.getEndDate());
    }


    private static Stream<Arguments> currentBookings() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 1, 18), LocalDate.of(2024, 1, 21)),
                Arguments.of(LocalDate.of(2024, 1, 21), LocalDate.of(2024, 1, 28)),
                Arguments.of(LocalDate.of(2024, 1, 28), LocalDate.of(2024, 1, 31)),
                Arguments.of(LocalDate.of(2024, 1, 17), LocalDate.of(2024, 1, 31))
        );
    }

    @ParameterizedTest
    @MethodSource("currentBookings")
    public void givenDates_whenGetCurrentBookings_returnAllActiveBookingsBetweenDateRange(LocalDate startDate, LocalDate endDate) throws Exception {

        //when
        String contentAsString = mockMvc.perform(get(baseUrl)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookingListResponse response = mapper.readValue(contentAsString, BookingListResponse.class);

        //then
        assertEquals(response.getBookings().size(), 1);
        assertNull(response.getBookings().get(0).getArchived());
    }

}
