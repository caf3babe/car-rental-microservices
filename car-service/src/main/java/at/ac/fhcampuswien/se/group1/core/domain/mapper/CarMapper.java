package at.ac.fhcampuswien.se.group1.core.domain.mapper;

import at.ac.fhcampuswien.se.group1.core.domain.dto.CarRequest;
import at.ac.fhcampuswien.se.group1.core.models.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
@Mapper(componentModel = "spring")
public abstract class CarMapper {

    //CHECK_DAVID  
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    abstract Car create(CarRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    abstract Car update(CarRequest request, @MappingTarget Car car);
}
