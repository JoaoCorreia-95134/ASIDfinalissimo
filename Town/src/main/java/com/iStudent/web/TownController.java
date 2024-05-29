package com.iStudent.web;

import com.iStudent.model.DTOs.TownDTO;
import com.iStudent.model.entity.Town;
import com.iStudent.service.TownService;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Town")
public class TownController {

    private final TownService townService;
    private final ModelMapper mapper;

    @Autowired

    public TownController(TownService townService, ModelMapper mapper) {
        this.townService = townService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TownDTO>> getAllTowns(){
        return ResponseEntity.
                ok(townService.getAllTowns());

    }

    @GetMapping("/getByName/{townName}")
    public ResponseEntity<Long> findIdByTown(@PathVariable("townName") String town_name){

        Town town = townService.findByTownName(town_name);
        TownDTO townDTO = mapper.map(town, TownDTO.class);
        Long idTown= townDTO.getId();

        if (idTown==null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(idTown);
        }
    }

    @GetMapping("/getBigCity")
    public ResponseEntity<List<Long>> findBigCity(){
        List<Long> idsTown= townService.findBigCity();
        if (idsTown==null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }else {
            return ResponseEntity.ok(idsTown);
        }
    }

    @GetMapping("/getPopulation/{town_id}")
    public ResponseEntity<Boolean> getPopulation(@PathVariable("town_id") Long town_id){
        Town town= townService.findByTownId(town_id);
        Boolean pop= false;
        if (town==null) {
            return ResponseEntity.ok(pop);
        }else {
            if (town.getPopulation()>=100){
                pop= true;
                return ResponseEntity.ok(pop);
            }else{
                return ResponseEntity.ok(pop);
            }
        }
    }

}

