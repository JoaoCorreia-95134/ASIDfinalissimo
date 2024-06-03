package com.iStudent.service;

import com.iStudent.model.DTOs.ClubDTO;
import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.entity.Club;
import com.iStudent.model.entity.Student;
import com.iStudent.repository.ClubRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    private final ModelMapper mapper;

    private Integer cont=0;

    @Autowired

    private KafkaTemplate<String, String> kafkaTemplate;

    public ClubService(ClubRepository clubRepository, ModelMapper mapper) {
        this.clubRepository = clubRepository;
        this.mapper = mapper;
    }

    public List<ClubDTO> getAllClubs() {
        return clubRepository.
                findAll().
                stream().
                map(this::mapToClubDTO).
                toList();
    }

    private ClubDTO mapToClubDTO(Club club) {
        return mapper.map(club, ClubDTO.class);
    }

    public Optional<ClubDTO> getClubById(Long clubId) {
        return clubRepository.
                findById(clubId).
                map(this::mapToClubDTO);
    }

    @Transactional
    public void deleteClubById(Long clubId) {
        System.out.println("rip club" + clubId);
        clubRepository.deleteById(clubId);
    }

    public long addClub(ClubDTO clubDTO) {

        Optional<Club> existingClub = clubRepository.findByName(clubDTO.getName());
        if (existingClub.isPresent()) {
            // A club with the same name already exists.
            String erroMessage = "Erro: A club with the name"  + clubDTO.getName() +  "already exists.";
            System.out.println(erroMessage);
            if(cont==0){
                kafkaTemplate.send("clubOkTopic",erroMessage);
                cont=1;
            }
            throw new IllegalArgumentException("A club with the name " + clubDTO.getName() + " already exists.");
        } else {
            Club club = mapper.map(clubDTO, Club.class);
            clubRepository.save(club);

            String confirmationMessage = "OK: " + clubRepository.findByName(club.getName()).get().getId();

            System.out.println("Sending confirmation message: " + confirmationMessage);
            kafkaTemplate.send("clubOkTopic", confirmationMessage);
            return club.getId();
        }
    }

    public void setCont(Integer cont) {
        this.cont=cont;
    }

    @Transactional
    public Optional<Club> addClubToStudent(Long clubId, Student student) {
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isPresent()) {
            optionalClub.get().addStudentToClub(student);
            clubRepository.save(optionalClub.get());
            return optionalClub;
        }else{
            return Optional.empty();
        }
    }

    public ClubDTO changeClubName(Long id, ClubDTO clubDTO) {
        Optional<Club> optionalClub = clubRepository.findById(id);

        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();
            club.setName(clubDTO.getName());

            clubRepository.save(club);
            return mapToClubDTO(club);
        }

        return null;
    }
}