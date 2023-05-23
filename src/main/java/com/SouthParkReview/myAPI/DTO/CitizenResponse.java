package com.SouthParkReview.myAPI.DTO;

import com.SouthParkReview.myAPI.models.Citizen;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenResponse {
    private List<CitizenDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalNumberOfElements;
    private int totalPages;
    private boolean isLast;
}
