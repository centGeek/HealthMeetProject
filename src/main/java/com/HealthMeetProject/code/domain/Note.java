package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
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
