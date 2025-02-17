package com.techproed.schoolmanagementbackendb326.controller.businnes;

import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.businnes.EducationTermService;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

  private final EducationTermService educationTermService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean')")
  @PostMapping("/save")
  public ResponseMessage<EducationTermResponse>save(
      @Valid @RequestBody EducationTermRequest educationTermRequest) {
    return educationTermService.save(educationTermRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @PutMapping("/update/{educationTermId}")
  public ResponseMessage<EducationTermResponse>updateEducationTerm(
      @Valid @RequestBody EducationTermRequest educationTermRequest,
      @PathVariable Long educationTermId){
    return educationTermService.updateEducationTerm(educationTermRequest,educationTermId);
  }


  //TODO
  // ummu
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getAll")
  public List<EducationTermResponse>getAllEducationTerms(){
    //return educationTermService.getAllEducationTerms();
    return null;
  }



}
