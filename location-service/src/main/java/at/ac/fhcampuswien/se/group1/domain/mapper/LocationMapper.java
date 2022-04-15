package at.ac.fhcampuswien.se.group1.domain.mapper;

import at.ac.fhcampuswien.se.group1.domain.dto.CreateLocationRequest;
import at.ac.fhcampuswien.se.group1.domain.dto.UpdateLocationRequest;
import at.ac.fhcampuswien.se.group1.models.Location;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Location create(CreateLocationRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Location update(UpdateLocationRequest request, @MappingTarget Location location);
}
