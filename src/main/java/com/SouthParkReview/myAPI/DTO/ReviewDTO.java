package com.SouthParkReview.myAPI.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private int id;
    private String content;
    private String title;
    private int stars;
}
