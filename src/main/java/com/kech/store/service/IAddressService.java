package com.kech.store.service;

import com.kech.store.entity.Address;

public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);
}
