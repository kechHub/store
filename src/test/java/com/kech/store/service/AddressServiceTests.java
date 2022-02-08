package com.kech.store.service;

import com.kech.store.entity.Address;
import com.kech.store.service.impl.AddressServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private AddressServiceImpl addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("15774561256");
        address.setName("男朋友");
        addressService.addNewAddress(6,"管理员", address);
    }

    @Test
    public void delete() {
        addressService.delete(2, 6, "管理员");
    }

}
