package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.address.AddressRequest;
import com.shop.smart_commerce_api.dto.response.address.AddressResponse;
import com.shop.smart_commerce_api.entities.Address;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.mapper.AddressMapper;
import com.shop.smart_commerce_api.repositories.AddressRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserService userService;

    public AddressResponse create(AddressRequest request) {
        Address address = addressMapper.toAddress(request);
        User user = userRepository.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        address.setUser(user);
        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public void delete(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!address.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        addressRepository.delete(address);
    }

    public AddressResponse update(Integer id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!address.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        addressMapper.updateAddressFromRequest(request, address);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    public List<AddressResponse> getMyAddress() {
        User user = userRepository.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Address> addresses = addressRepository.findByUserId(user.getId());
        return addresses.stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    @Transactional
    public void setDefaultAddress(Integer addressId) {
        User currentUser = userService.getCurrentUser();

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        // Reset all others to false
        addressRepository.resetOtherDefaults(currentUser.getId(), addressId);

        address.setIsDefault(true);
        addressRepository.save(address);
    }
}
