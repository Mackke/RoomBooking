package com.mk.roombookingzaver.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mk.roombookingzaver.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {


    @Query(value = """
                SELECT *
                FROM booking
                WHERE (
                    -- Case 1: New booking starts during the existing booking
                    (start_date >= :startDate AND start_date < :endDate)
                    OR
                    -- Case 2: New booking ends during the existing booking
                    (end_date > :startDate AND end_date <= :endDate)
                    OR
                    -- Case 3: New booking completely covers an existing booking
                    (start_date <= :startDate AND end_date >= :endDate)
                    OR
                    -- Case 4: New booking is completely within an existing booking
                    (start_date >= :startDate AND end_date <= :endDate)
                )
                AND archived IS NULL;
                
                
""",nativeQuery = true)
    List<Booking> currentBookings(LocalDate startDate, LocalDate endDate);


    @Query(value = "SELECT * FROM booking " +
            "WHERE (start_date < :endDate AND end_date > :startDate) " +
            "AND archived IS NULL", nativeQuery = true)
    List<Booking> currentBookings2(LocalDate startDate, LocalDate endDate);

    @Query(value = """
                SELECT * 
                FROM booking as b
                WHERE b.room_id = :roomId
""",nativeQuery = true)
    List<Booking> getRoomsBookings(UUID roomId);
}
