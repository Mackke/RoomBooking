package com.mk.roombookingzaver.data.entity;


import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate archived;

    public Booking(Room room, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Booking(UUID id, Room room, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.archived = null;
    }
}
