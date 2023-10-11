package com.HealthMeetProject.code.domain;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {
    private int noteId;
    private String description;

    private String illness;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Patient patient;

    private Doctor doctor;

}
