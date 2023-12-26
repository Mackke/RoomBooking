package com.mk.roombookingzaver.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String type;
    private int beds;
    private Double price;

    public Room(String name, String description, String type, int beds, Double price) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

}
