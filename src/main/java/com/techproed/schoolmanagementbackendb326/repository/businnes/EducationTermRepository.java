package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.enums.Term;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {


  @Query("select (count (e) > 0) from EducationTerm e where e.term=?1 and extract(year from e.startDate) =?2 ")
  boolean existByTermAndYear(Term term, int year);

  //girilen yilda var olan education term'leri geri dönduren sorgu
  @Query("select e from EducationTerm e where extract(year from e.startDate) =?1 ")
  List<EducationTerm>findByYear(int year);


}
