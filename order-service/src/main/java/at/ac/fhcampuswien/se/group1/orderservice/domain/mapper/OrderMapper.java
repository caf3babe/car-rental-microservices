package at.ac.fhcampuswien.se.group1.orderservice.domain.mapper;

import at.ac.fhcampuswien.se.group1.orderservice.domain.dto.OrderRequest;
import at.ac.fhcampuswien.se.group1.orderservice.model.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Order create(OrderRequest request);
    
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    Order update(OrderRequest request, @MappingTarget Order order);
}
