package com.shop.smart_commerce_api.services;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.address.AddressRequest;
import com.shop.smart_commerce_api.dto.response.address.AddressResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.entities.Address;
import com.shop.smart_commerce_api.mapper.AddressMapper;
import com.shop.smart_commerce_api.repositories.AddressRepository;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServicer {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    public AddressResponse create(AddressRequest request) {
        Address address = addressMapper.toAddress(request);
        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public void delete(int id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        addressRepository.save(address);
    }

    public AddressResponse update(Integer id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        addressMapper.updateAddressFromRequest(request, address);
        System.out.println(address);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    public List<AddressResponse> getAll() {
        var addresses = addressRepository.findAll();
        return addresses.stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    public AddressResponse getById(int id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        return addressMapper.toAddressResponse(address);
    }
}
