package com.techproed.schoolmanagementbackendb326.service.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableHelper {

  public Pageable getPageable(int page, int size, String sort, String type) {
    return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
  }

  public Pageable getPageableByPageAndSize(int page, int size){
    if (page < 0) page = 0;
    if (size > 50) size = 50;
    return PageRequest.of(page,size);
  }


}
