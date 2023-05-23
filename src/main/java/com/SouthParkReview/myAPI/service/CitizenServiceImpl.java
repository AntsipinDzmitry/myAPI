package com.SouthParkReview.myAPI.service;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;
import com.SouthParkReview.myAPI.exceptions.CitizenNotFoundException;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {
    private CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public CitizenDTO createCitizen(CitizenDTO citizenDTO) {
        Citizen citizen = new Citizen();
        citizen.setName(citizenDTO.getName());
        citizen.setDescription(citizenDTO.getDescription());

        Citizen newCitizen = citizenRepository.save(citizen);
        CitizenDTO citizenResponse = new CitizenDTO();
        citizenResponse.setId(newCitizen.getId());
        citizenResponse.setName(newCitizen.getName());
        citizenResponse.setDescription(newCitizen.getDescription());

        return citizenResponse;

    }

    @Override
    public CitizenResponse getAllCitizens(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Citizen> citizens = citizenRepository.findAll(pageable);
        List<Citizen> listOfCitizens = citizens.getContent();
        List<CitizenDTO> content =  listOfCitizens.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());

        CitizenResponse citizenResponse = new CitizenResponse();
        citizenResponse.setContent(content);
        citizenResponse.setPageNo(citizens.getNumber());
        citizenResponse.setPageSize(citizens.getSize());
        citizenResponse.setTotalPages(citizens.getTotalPages());
        citizenResponse.setTotalNumberOfElements(citizens.getNumberOfElements());
        citizenResponse.setLast(citizens.isLast());
        return citizenResponse;
    }

    @Override
    public CitizenDTO getCitizenById(int id) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(
                () -> new CitizenNotFoundException("Citizen could not be found"));
        return mapToDTO(citizen);
    }

    @Override
    public CitizenDTO updateCitizenById(CitizenDTO citizenDTO, int id) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(
                () -> new CitizenNotFoundException("Citizen could not be updated"));
        citizen.setName(citizenDTO.getName());
        citizen.setDescription(citizenDTO.getDescription());
        Citizen updatedCitizen = citizenRepository.save(citizen);
        return mapToDTO(updatedCitizen);
    }

    @Override
    public void deleteCitizenById(int id) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(
                () -> new CitizenNotFoundException("Citizen could not be deleted"));
        citizenRepository.delete(citizen);
    }

    private CitizenDTO mapToDTO(Citizen citizen) {
        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setId(citizen.getId());
        citizenDTO.setName(citizen.getName());
        citizenDTO.setDescription(citizen.getDescription());
        return citizenDTO;
    }

    private Citizen mapToEntity(CitizenDTO citizenDTO) {
        Citizen citizen = new Citizen();
        citizen.setName(citizenDTO.getName());
        citizen.setDescription(citizenDTO.getDescription());
        return citizen;
    }
}
