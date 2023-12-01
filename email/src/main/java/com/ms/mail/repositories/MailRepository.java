package com.ms.mail.repositories;

import com.ms.mail.models.MailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MailRepository extends JpaRepository<MailModel, UUID> {
}
