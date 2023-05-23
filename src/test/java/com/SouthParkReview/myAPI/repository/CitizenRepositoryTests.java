package com.SouthParkReview.myAPI.repository;

import com.SouthParkReview.myAPI.models.Citizen;
import com.SouthParkReview.myAPI.repository.CitizenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CitizenRepositoryTests {
    @Autowired
    private CitizenRepository citizenRepository;
@Test
    public void CitizenRepository_SaveAll_ReturnSavedCitizen(){
    //Arrange
    Citizen citizen = Citizen.builder()
            .name("Kyle")
            .description("green hat").build();
    //Act
    Citizen savedCitizen = citizenRepository.save(citizen);

    //Assert
    Assertions.assertThat(savedCitizen).isNotNull();
    Assertions.assertThat(savedCitizen.getId()).isGreaterThan(0);
    }

    @Test
    public void CitizenRepository_GetAll_ReturnMoreThenOneCitizen(){
        Citizen citizen = Citizen.builder()
                .name("Kyle")
                .description("green hat").build();
        Citizen citizen2 = Citizen.builder()
                .name("Stan")
                .description("blue hat").build();

        citizenRepository.save(citizen);
        citizenRepository.save(citizen2);
        List<Citizen> citizens = citizenRepository.findAll();

        Assertions.assertThat(citizens).isNotNull();
        Assertions.assertThat(citizens.size()).isEqualTo(2);
    }

    @Test
    public void CitizenRepository_FindById_ReturnCitizen(){
        Citizen citizen = Citizen.builder()
                .name("Kyle")
                .description("green hat").build();
        citizenRepository.save(citizen);

        Citizen checkedCitizen = citizenRepository.findById(citizen.getId()).get();

        Assertions.assertThat(checkedCitizen).isNotNull();
    }

    @Test
    public void CitizenRepository_FindByDescription_ReturnCitizenNotNull(){
        Citizen citizen = Citizen.builder()
                .name("Kyle")
                .description("green hat").build();
        citizenRepository.save(citizen);

        Citizen checkedCitizen = citizenRepository.findByDescription(citizen.getDescription()).get();

        Assertions.assertThat(checkedCitizen).isNotNull();
    }

    @Test
    public void CitizenRepository_UpdateCitizen_ReturnCitizenNotNull(){
        Citizen citizen = Citizen.builder()
                .name("Kyle")
                .description("green hat").build();
        citizenRepository.save(citizen);

        Citizen savedCitizen = citizenRepository.findById(citizen.getId()).get();
        savedCitizen.setName("Kenny");
        savedCitizen.setDescription("No voice");
        Citizen updatedCitizen = citizenRepository.save(savedCitizen);

        Assertions.assertThat(updatedCitizen).isNotNull();
        Assertions.assertThat(updatedCitizen.getName()).isNotNull();
        Assertions.assertThat(updatedCitizen.getDescription()).isNotNull();
    }
    @Test
    public void CitizenRepository_DeleteCitizen_ReturnCitizenIsEmpty(){
        Citizen citizen = Citizen.builder()
                .name("Kyle")
                .description("green hat").build();
        citizenRepository.save(citizen);

        citizenRepository.deleteById(citizen.getId());
        Optional<Citizen> deletedCitizen = citizenRepository.findById(citizen.getId());

        Assertions.assertThat(deletedCitizen).isEmpty();

    }
}
