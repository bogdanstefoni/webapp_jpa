package com.bogdan.webapp.repo;

import com.bogdan.webapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("select s from Student s where s.username = :username")
    List<Student> findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("delete from Student s where s.username = :username")
    void deleteByUsername(@Param("username") String username);

}
