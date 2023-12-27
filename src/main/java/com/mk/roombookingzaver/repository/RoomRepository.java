package com.mk.roombookingzaver.repository;

import java.util.List;
import java.util.UUID;

import com.mk.roombookingzaver.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> getRoomByBeds(int beds);
}
