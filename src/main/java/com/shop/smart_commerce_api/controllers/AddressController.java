package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.address.AddressRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.address.AddressResponse;
import com.shop.smart_commerce_api.services.AddressService;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ApiResponse<AddressResponse> createAddressApiResponse(@RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .code(200)
                .message("Address created successfully")
                .data(addressService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AddressResponse>> getMyAddress() {
        return ApiResponse.<List<AddressResponse>>builder()
                .code(200)
                .message("Addresses retrieved successfully")
                .data(addressService.getMyAddress())
                .build();
    }

    // @GetMapping("/{id}")
    // ApiResponse<AddressResponse> getAddressByIdApiResponse(int id) {
    // return ApiResponse.<AddressResponse>builder()
    // .code(200)
    // .message("Address retrieved successfully")
    // .data(addressService.getById(id))
    // .build();
    // }

    // @PostMapping("/{id}/delete")
    // ApiResponse<Void> deleteAddressApiResponse(@PathVariable int id) {
    // addressService.delete(id);
    // return ApiResponse.<Void>builder()
    // .code(200)
    // .message("Address deleted successfully")
    // .build();
    // }

    @PutMapping("/{id}")
    public ApiResponse<AddressResponse> updateAddressApiResponse(@PathVariable Integer id,
            @RequestBody AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .code(200)
                .message("Address updated successfully")
                .data(addressService.update(id, request))
                .build();
    }

    @PutMapping("/{id}/set-default")
    public ApiResponse<AddressResponse> setDefaultAddressApiResponse(@PathVariable Integer id) {
        return ApiResponse.<AddressResponse>builder()
                .code(200)
                .message("Default address set successfully")
                .data(addressService.setDefaultAddress(id))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<Void> deleteApiResponse(@PathVariable Integer id) {
        addressService.delete(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Address deleted successfully")
                .build();
    }
    // @GetMapping("/user/{userId}")
    // ApiResponse<List<AddressResponse>>
    // getAddressesByUserIdApiResponse(@PathVariable Integer userId) {
    // return ApiResponse.<List<AddressResponse>>builder()
    // .code(200)
    // .message("Addresses retrieved successfully for user")
    // .data(addressService.getByUserId(userId))
    // .build();
    // }
}
