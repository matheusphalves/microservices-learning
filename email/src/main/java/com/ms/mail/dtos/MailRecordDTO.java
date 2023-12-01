package com.ms.mail.dtos;

import java.util.UUID;

public record MailRecordDTO(
        UUID userId,
        String emailTo,
        String subject,
        String text
) {
}
