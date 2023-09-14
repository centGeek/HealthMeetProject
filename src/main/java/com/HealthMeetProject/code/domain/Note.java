package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {
    private int noteId;
    private String description;

    private String illness;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private Patient patient;

    private Doctor doctor;

}
