package com.techproed.schoolmanagementbackendb326.repository;

import com.techproed.schoolmanagementbackendb326.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactMessage, Long> {
	List<ContactMessage> findByEmail(
				String email);

	List<ContactMessage> findByCreatedAtBetween(
				LocalDateTime createdAtAfter,
				LocalDateTime createdAtBefore);

	List<ContactMessage> findBySubjectContainsIgnoreCase(
				String subject);

	//This query created with help of AI
	@Query("SELECT c FROM ContactMessage c WHERE TO_CHAR(c.createdAt, 'HH24:MI') BETWEEN :startTime AND :endTime")
	List<ContactMessage> findByCreatedAtTimeBetween(
				@Param("startTime") String startTime,
				@Param("endTime") String endTime);
}
