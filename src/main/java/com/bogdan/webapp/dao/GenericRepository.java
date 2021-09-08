package com.bogdan.webapp.dao;

import com.bogdan.webapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericRepository extends JpaRepository<Student, Integer> {


}
