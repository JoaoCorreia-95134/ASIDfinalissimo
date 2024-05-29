package com.iStudent.web;

 // Importe o serviço de pais
import com.iStudent.model.DTOs.ParentDTO;
import com.iStudent.model.entity.Parent;
import com.iStudent.model.entity.base.BasePersonEntity;
import com.iStudent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public ResponseEntity<List<ParentDTO>> getAllParents() {
        return ResponseEntity.ok(parentService.getAllParents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable("id") Long parentId) {
        Optional<ParentDTO> parent = this.parentService.getParentById(parentId);

        if (parent.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(parent.get());
        }
    }

    @GetMapping("/getListByTown/{idTown}")
    public ResponseEntity<ParentDTO[]> getListByTown(@PathVariable("idTown") Long idTown) {
        ParentDTO[] lista = this.parentService.getListByTown(idTown);


        if (lista.length == 0) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(lista);
        }
    }

    @PostMapping
    public ResponseEntity<ParentDTO> addParent(@Valid @RequestBody ParentDTO parentDTO,
                                               UriComponentsBuilder uriComponentsBuilder) {
        long newParentId = parentService.addParent(parentDTO);

        return ResponseEntity.created(uriComponentsBuilder.path("/parents/{id}").build(newParentId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParentDTO> deleteParentById(@PathVariable("id") Long parentId) {
        this.parentService.deleteParentById(parentId);

        return ResponseEntity.noContent().build();
    }
}
