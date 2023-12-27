package com.mk.roombookingzaver.data.repository;

import java.util.List;
import java.util.UUID;

import com.mk.roombookingzaver.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    List<Room> getRoomByBeds(int amountOfBeds);
}
