package com.ms.event.repositories;

import com.ms.event.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Subscription findSubscriptionByEventIdAndUserEmailAddress(UUID eventId, String userEmailAddress);
}
