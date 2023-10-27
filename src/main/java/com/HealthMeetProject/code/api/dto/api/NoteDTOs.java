package com.HealthMeetProject.code.api.dto.api;

import com.HealthMeetProject.code.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
@NoArgsConstructor
public class NoteDTOs {
    private List<Note> noteList;
}
