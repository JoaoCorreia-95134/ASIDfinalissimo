package com.iStudent.service;

import com.iStudent.model.DTOs.EmployeeDTO;
import com.iStudent.model.entity.UsersWithLargeTown;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.*;

@Service
public class EmployeeCommandService {


    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    private final ModelMapper mapper;



    @Autowired
    public EmployeeCommandService(ModelMapper mapper, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    public ResponseEntity<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO) {


        UsersWithLargeTown event = mapper.map(employeeDTO, UsersWithLargeTown.class);

        rabbitTemplate.convertAndSend(exchange,routingKey,event);

    return ResponseEntity.ok().body(employeeDTO);
    }
}
