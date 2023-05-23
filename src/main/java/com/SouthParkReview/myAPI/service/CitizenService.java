package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;

import java.util.List;

public interface CitizenService {
    CitizenDTO createCitizen(CitizenDTO citizenDTO);
    CitizenResponse getAllCitizens(int pageNo, int pageSize);
    CitizenDTO getCitizenById(int id);
    CitizenDTO updateCitizenById(CitizenDTO citizenDTO, int id);
    void deleteCitizenById(int id);
}
