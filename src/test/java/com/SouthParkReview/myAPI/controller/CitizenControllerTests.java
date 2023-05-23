package com.SouthParkReview.myAPI.controller;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;
import com.SouthParkReview.myAPI.DTO.ReviewDTO;
import com.SouthParkReview.myAPI.controllers.SouthParkController;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.models.Review;
import com.SouthParkReview.myAPI.service.CitizenService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = SouthParkController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CitizenControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitizenService citizenService;

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
    public void SouthParkController_CreateCitizen_ReturnCreated() throws Exception{
        given(citizenService.createCitizen(ArgumentMatchers.any()))
                .willAnswer(invocation->invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/citizen/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citizenDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(citizenDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(citizenDTO.getDescription())));

    }
    @Test
    public void SouthParkController_GetAllCitizens_ReturnResponseDTO() throws Exception {

        CitizenResponse responseDTO = CitizenResponse.builder().pageSize(5)
                .isLast(true).pageNo(1).content(List.of(citizenDTO)).build();

        when(citizenService.getAllCitizens(1,5)).thenReturn(responseDTO);

        ResultActions response = mockMvc.perform(get("/api/citizen")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "5"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()"
                        , CoreMatchers.is(responseDTO.getContent().size())));
    }

    @Test
    public void SouthParkController_CitizenDetail_ReturnResponse() throws Exception {
        int citizenId = 1;
        when(citizenService.getCitizenById(citizenId)).thenReturn(citizenDTO);

        ResultActions response = mockMvc.perform(get("/api/citizen/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citizenDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name"
                ,CoreMatchers.is(citizenDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description"
                ,CoreMatchers.is(citizenDTO.getDescription())));
    }

    @Test
    public void SouthParkController_UpdateCitizen_ReturnResponse() throws Exception {
        int citizenId = 1;
        when(citizenService.updateCitizenById(citizenDTO, citizenId)).thenReturn(citizenDTO);

        ResultActions response = mockMvc.perform(put("/api/citizen/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(citizenDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name"
                        ,CoreMatchers.is(citizenDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description"
                        ,CoreMatchers.is(citizenDTO.getDescription())));
    }

    @Test
    public void SouthParkController_DeleteCitizen_ReturnString() throws Exception {
        int citizenId = 1;
        doNothing().when(citizenService).deleteCitizenById(citizenId);

        ResultActions response = mockMvc.perform(delete("/api/citizen/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
