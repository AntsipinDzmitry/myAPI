package com.SouthParkReview.myAPI.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDTO {
    private int id;
    private String name;
    private String description;
}
