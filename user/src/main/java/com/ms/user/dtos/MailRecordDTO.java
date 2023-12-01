package com.ms.user.dtos;

import java.util.UUID;

public record MailRecordDTO(
        UUID userId,
        String mailTo,
        String subject,
        String text) {
}
