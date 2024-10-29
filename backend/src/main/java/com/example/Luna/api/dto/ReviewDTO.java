package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    //review details
    private Long id;
    private Boolean vote;
    private String text;
    private java.sql.Timestamp sqlTimestamp;

    //user details
    private Long user_id;
    private String username;
    private Integer totalReviews;
}
