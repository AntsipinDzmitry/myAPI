package com.SouthParkReview.myAPI.controllers;

import com.SouthParkReview.myAPI.DTO.CitizenDTO;
import com.SouthParkReview.myAPI.DTO.CitizenResponse;
import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/")
public class SouthParkController {

    private CitizenService citizenService;
    @Autowired
    public SouthParkController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping("citizen")
    public ResponseEntity<CitizenResponse> getAllCitizens(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(citizenService.getAllCitizens(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("citizen/{id}")
    public ResponseEntity<CitizenDTO> citizenDetail(@PathVariable int id) {
        return ResponseEntity.ok(citizenService.getCitizenById(id));
    }

    @PostMapping("citizen/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CitizenDTO> createCitizen(@RequestBody CitizenDTO citizenDTO) {
        return new ResponseEntity<>(citizenService.createCitizen(citizenDTO), HttpStatus.CREATED);
    }

    @PutMapping("citizen/{id}/update")
    public ResponseEntity<CitizenDTO> updateCitizen(@RequestBody CitizenDTO citizenDTO, @PathVariable("id") int citizenId) {
        CitizenDTO response = citizenService.updateCitizenById(citizenDTO, citizenId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("citizen/{id}/delete")
    public ResponseEntity<String> deleteCitizen(@PathVariable("id") int citizenId) {
        citizenService.deleteCitizenById(citizenId);
        return new ResponseEntity<>("Citizen was deleted successefully", HttpStatus.OK);
    }
}
