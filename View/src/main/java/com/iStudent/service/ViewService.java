package com.iStudent.service;

import com.iStudent.model.entity.UsersWithLargeTown;
import com.iStudent.repository.ViewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ViewService {

    private final ViewRepository viewRepository;
    private final ModelMapper mapper;

    @Autowired
    public ViewService(ModelMapper mapper, ViewRepository viewRepository) {
        this.mapper = mapper;
        this.viewRepository=viewRepository;
    }

    public List<UsersWithLargeTown> getView() {
        return viewRepository
                .findAll()
                .stream()
                .toList();
    }


    @Transactional
    public UsersWithLargeTown addUser(UsersWithLargeTown user) {
        UsersWithLargeTown userCreated = mapper.map(user, UsersWithLargeTown.class);

        viewRepository.save(userCreated);
        return (userCreated);
    }


}