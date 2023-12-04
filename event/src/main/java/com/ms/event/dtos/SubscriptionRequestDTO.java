package com.ms.event.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SubscriptionRequestDTO(
        @NotBlank String userEmailAddress) {
}
