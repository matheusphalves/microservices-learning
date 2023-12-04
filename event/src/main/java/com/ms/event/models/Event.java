package com.ms.event.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "events")
@Table(name = "events")
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int maxParticipants;

    @Min(0)
    private int registeredParticipants;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime eventDate;

}
