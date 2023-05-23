package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.repository.CitizenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitizenServiceTests {
    @Mock
    private CitizenRepository citizenRepository;

    @InjectMocks
    private CitizenServiceImpl citizenService;

    @Test
    public void CitizenService_CreateCitizen_ReturnsCitizenDTO(){
        Citizen citizen = Citizen.builder().name("Butters").description("blonde hair").build();
        CitizenDTO citizenDTO = CitizenDTO.builder().name("Butters").description("blonde hair").build();

        when(citizenRepository.save(Mockito.any(Citizen.class))).thenReturn(citizen);

        CitizenDTO savedCitizen = citizenService.createCitizen(citizenDTO);

        Assertions.assertThat(savedCitizen).isNotNull();
    }

    @Test
    public void CitizenService_GetAllCitizens_ReturnedCitizenResponseIsNotNull(){
        CitizenResponse citizenResponse = Mockito.mock(CitizenResponse.class);
        Page<Citizen> citizens = Mockito.mock(Page.class);

        when(citizenRepository.findAll(Mockito.any(Pageable.class))).thenReturn(citizens);

        CitizenResponse returnedResponse = citizenService.getAllCitizens(1,4);

        Assertions.assertThat(returnedResponse).isNotNull();

    }
    @Test
    public void CitizenService_GetCitizenByUd_ReturnCitizenDTO(){
        Citizen citizen = Citizen.builder().name("Butters").description("blonde hair").build();

        when(citizenRepository.findById(1)).thenReturn(Optional.ofNullable(citizen));

        CitizenDTO savedCitizen = citizenService.getCitizenById(1);

        Assertions.assertThat(savedCitizen).isNotNull();
    }

    @Test
    public void CitizenService_UpdateCitizen_ReturnsCitizenDTO(){
        Citizen citizen = Citizen.builder().name("Butters").description("blonde hair").build();
        CitizenDTO citizenDTO = CitizenDTO.builder().name("Butters").description("blonde hair").build();

        when(citizenRepository.findById(1)).thenReturn(Optional.ofNullable(citizen));
        when(citizenRepository.save(Mockito.any(Citizen.class))).thenReturn(citizen);

        CitizenDTO savedCitizen = citizenService.updateCitizenById(citizenDTO,1);

        Assertions.assertThat(savedCitizen).isNotNull();
    }

    @Test
    public void CitizenService_DeleteCitizenByUd_ReturnCitizenDTO(){
        Citizen citizen = Citizen.builder().name("Butters").description("blonde hair").build();

        when(citizenRepository.findById(1)).thenReturn(Optional.ofNullable(citizen));

        assertAll(()->citizenService.deleteCitizenById(1));
    }
}
