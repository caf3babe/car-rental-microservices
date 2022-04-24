package at.ac.fhcampuswien.se.group1.domain.mapper;

import at.ac.fhcampuswien.se.group1.domain.dto.CarRequest;
import at.ac.fhcampuswien.se.group1.models.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
@Mapper(componentModel = "spring")
public interface CarMapper {
    
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Car create(CarRequest request);
    
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Car update(CarRequest request, @MappingTarget Car car);
}