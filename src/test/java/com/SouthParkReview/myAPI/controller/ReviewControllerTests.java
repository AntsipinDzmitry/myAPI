package com.SouthParkReview.myAPI.controller;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;
import com.SouthParkReview.myAPI.DTO.ReviewDTO;
import com.SouthParkReview.myAPI.controllers.ReviewController;
import com.SouthParkReview.myAPI.controllers.SouthParkController;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.models.Review;
import com.SouthParkReview.myAPI.service.CitizenService;
import com.SouthParkReview.myAPI.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)

public class ReviewControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;

    private Citizen citizen;
    private Review review;
    private CitizenDTO citizenDTO;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void init(){
        citizen = Citizen.builder()
                .name("Butters").description("blonde hair").build();
        citizenDTO = CitizenDTO.builder()
                .name("Butters").description("blonde hair").build();
        review = Review.builder()
                .content("norm").title("Mr").stars(4).build();
        reviewDTO = ReviewDTO.builder()
                .content(" review norm").title("Ms").stars(5).build();
    }
    @Test
    public void ReviewController_GetReviewByCitizenId_ReturnListOfReviewDTO() throws Exception {
        int citizenId = 1;
        when(reviewService.getReviewsByCitizenId(citizenId)).thenReturn(Arrays.asList(reviewDTO));

        ResultActions response = mockMvc.perform(get("/api/citizen/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()"
                , CoreMatchers.is(Arrays.asList(reviewDTO).size())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnReviewDTO() throws Exception {
        int citizenId = 1;
        int reviewId = 1;
        when(reviewService.updateReview(citizenId,reviewId,reviewDTO)).thenReturn(reviewDTO);

        ResultActions response = mockMvc.perform(put("/api/citizen/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title"
                ,CoreMatchers.is(reviewDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content"
                ,CoreMatchers.is(reviewDTO.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars"
                ,CoreMatchers.is(reviewDTO.getStars())));
    }

    @Test
    public void ReviewController_CreateReview_ReturnReviewDTO() throws Exception {
        int citizenId = 1;
        when(reviewService.createReview(citizenId, reviewDTO)).thenReturn(reviewDTO);

        ResultActions response = mockMvc.perform(post("/api/citizen/1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(reviewDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",CoreMatchers.is(reviewDTO.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars",CoreMatchers.is(reviewDTO.getStars())));
    }

    @Test
    public void ReviewController_GetReviewById_ReturnReviewDTO() throws Exception {
        int reviewId = 1;
        int citizenId = 1;
        when(reviewService.getReviewById(reviewId, citizenId)).thenReturn(reviewDTO);

        ResultActions response = mockMvc.perform(get("/api/citizen/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(reviewDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",CoreMatchers.is(reviewDTO.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars",CoreMatchers.is(reviewDTO.getStars())));
    }

    @Test
    public void ReviewController_DeleteReviewById_ReturnString() throws Exception {
        int reviewId = 1;
        int citizenId = 1;
        doNothing().when(reviewService).deleteReview(citizenId,reviewId);

        ResultActions response = mockMvc.perform(delete("/api/citizen/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
