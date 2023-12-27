package com.mk.roombookingzaver.api.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private UUID id;
    private String name;
    private String description;
    private String type;
    private int beds;
    private Double price;
}
