package com.iStudent.service;

import com.iStudent.model.DTOs.TownDTO;
import com.iStudent.model.entity.Town;
import com.iStudent.repository.TownRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TownService {

    private final TownRepository townRepository;
    private final ModelMapper mapper;

    @Autowired
    public TownService(TownRepository townRepository, ModelMapper mapper) {
        this.townRepository = townRepository;
        this.mapper = mapper;
    }

    public Town findByTownId(Long townId){
        return this.townRepository.findById(townId).orElseThrow();
    }


    public Town findByTownName(String townName) {
        return this.townRepository.findByName(townName).orElseThrow();
    }



    public List<Long> findBigCity() {
        List<Town> towns = townRepository.findAll();
        List<Long> idTowns = new ArrayList<>();;

        for (Town town : towns) {
            if(town.getPopulation()>=100){
                idTowns.add(town.getId());
            }
        }

        if(idTowns.isEmpty()){
            return null;
        }else{
            return idTowns;
        }
    }



    private TownDTO mapToTownDTO(Town Town) {
        return mapper.map(Town, TownDTO.class);
    }

    public List<TownDTO> getAllTowns() {
        return townRepository
                .findAll()
                .stream()
                .map(this::mapToStudentDTO)
                .toList();
    }

    private TownDTO mapToStudentDTO(Town town) {
        return mapper.map(town, TownDTO.class);
    }
}
