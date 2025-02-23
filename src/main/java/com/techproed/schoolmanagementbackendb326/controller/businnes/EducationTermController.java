package com.techproed.schoolmanagementbackendb326.controller.businnes;

import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.businnes.EducationTermService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      @Valid @RequestBody EducationTermUpdateRequest educationTermUpdateRequest,
      @PathVariable Long educationTermId){
    return educationTermService.updateEducationTerm(educationTermUpdateRequest,educationTermId);
  }



  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getAll")
  public List<EducationTermResponse>getAllEducationTerms(){
    return educationTermService.getAllEducationTerms();
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/{educationTermId}")
  public EducationTermResponse getEducationTerm(@PathVariable Long educationTermId) {
    return educationTermService.getEducationTermById(educationTermId);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getByPage")
  public Page<EducationTermResponse>getByPage(
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam (value = "size",defaultValue = "10") int size,
      @RequestParam (value = "sort",defaultValue = "term") String sort,
      @RequestParam (value = "type",defaultValue = "desc") String type) {
    return educationTermService.getByPage(page,size,sort,type);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @DeleteMapping("/delete/{educationTermId}")
  public ResponseMessage deleteEducationTerm(@PathVariable Long educationTermId) {
    return educationTermService.deleteById(educationTermId);
  }








}
