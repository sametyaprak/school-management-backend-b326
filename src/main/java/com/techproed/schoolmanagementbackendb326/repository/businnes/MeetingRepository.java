package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meet,Long> {

}
