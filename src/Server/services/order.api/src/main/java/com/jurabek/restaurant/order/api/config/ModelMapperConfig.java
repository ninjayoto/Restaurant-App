package com.jurabek.restaurant.order.api.config;

import com.jurabek.restaurant.order.api.dtos.CustomerBasketDto;
import com.jurabek.restaurant.order.api.dtos.CustomerBasketItemDto;
import com.jurabek.restaurant.order.api.dtos.CustomerOrderDto;
import com.jurabek.restaurant.order.api.dtos.CustomerOrderItemsDto;
import com.jurabek.restaurant.order.api.mappers.CustomerBasketItemDtoToOrderItemsMap;
import com.jurabek.restaurant.order.api.mappers.CustomerBasketToOrderMap;
import com.jurabek.restaurant.order.api.mappers.OrderToCustomerOrderDtoMap;
import com.jurabek.restaurant.order.api.models.Order;
import com.jurabek.restaurant.order.api.models.OrderItems;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapperConfig
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
       
        PropertyMap<CustomerBasketItemDto, OrderItems> customerBasketItemDtoToOrderItemsMap = new CustomerBasketItemDtoToOrderItemsMap();
        PropertyMap<CustomerBasketDto, Order> customerBasketToOrderMap = new CustomerBasketToOrderMap(modelMapper);
        PropertyMap<Order, CustomerOrderDto> orderToCustomerOrderDto = new OrderToCustomerOrderDtoMap(modelMapper);
        
        modelMapper.addMappings(customerBasketItemDtoToOrderItemsMap);
        modelMapper.createTypeMap(CustomerBasketDto.class, Order.class).addMappings(customerBasketToOrderMap);

        modelMapper.createTypeMap(OrderItems.class, CustomerOrderItemsDto.class);
        modelMapper.createTypeMap(Order.class, CustomerOrderDto.class).addMappings(orderToCustomerOrderDto);

        return modelMapper;
    }
}