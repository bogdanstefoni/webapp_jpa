package com.bogdan.webapp.repo;

import com.bogdan.webapp.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {
}
