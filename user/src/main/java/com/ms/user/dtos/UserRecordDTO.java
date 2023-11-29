package com.ms.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDTO(
        @NotBlank String name,
        @NotBlank String email) {
}
