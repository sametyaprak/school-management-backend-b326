package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meet,Long> {


  List<Meet>findByStudentList_IdEquals(Long studentId);

  List<Meet>getByAdvisoryTeacher_IdEquals(Long teacherId);


    Page<Meet> findByAdvisoryTeacher_Id(Long teacherId, Pageable pageable);
}
