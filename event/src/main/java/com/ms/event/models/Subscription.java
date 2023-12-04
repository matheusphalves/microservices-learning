package com.ms.event.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "subscriptions")
@Table(name = "subscriptions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String userEmailAddress;
    private LocalDateTime subscribedAt;
    @ManyToOne
    private Event event;

    public Subscription() {

    }
}
