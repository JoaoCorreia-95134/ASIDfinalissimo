package com.iStudent.service;

import com.iStudent.model.entity.UsersWithLargeTown;
import com.iStudent.repository.ViewRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class viewServiceRabbitMQ {
    private ViewRepository viewRepository;

    public viewServiceRabbitMQ(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void receive(UsersWithLargeTown usersWithLargeTown) {
        viewRepository.save(usersWithLargeTown);

    }
}
