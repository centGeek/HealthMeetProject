package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Receipt;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Receipts {
    private List<Receipt> receipts;
}
