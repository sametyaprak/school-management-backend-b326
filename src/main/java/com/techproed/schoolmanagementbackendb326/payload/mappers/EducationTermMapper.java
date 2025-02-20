package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        //with this parameter, MapStruct will always check source properties if they have null value or not.
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //If a source bean property equals null, the target bean property will be ignored and retain its existing value. So, we will be able to perform partial update.
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EducationTermMapper {

    EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest);

    EducationTerm updateEducationTermWithEducationTermRequest(EducationTermRequest educationTermRequest, @MappingTarget EducationTerm educationTerm);

    EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm);
}
