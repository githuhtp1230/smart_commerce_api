package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.shop.smart_commerce_api.dto.request.address.AddressRequest;
import com.shop.smart_commerce_api.dto.response.address.AddressResponse;
import com.shop.smart_commerce_api.entities.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toAddressResponse(Address address);

    Address toAddress(AddressRequest request);

    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);
}
