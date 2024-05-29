package com.iStudent.repository;

import com.iStudent.model.entity.UsersWithLargeTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<UsersWithLargeTown, Long> {


}
