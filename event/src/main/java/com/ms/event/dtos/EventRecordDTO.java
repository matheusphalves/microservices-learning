package com.ms.event.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventRecordDTO
        (
                @NotNull int maxParticipants,
                @NotBlank String title,
                @NotBlank String description,
                @NotNull LocalDateTime eventDate
        ){
}
