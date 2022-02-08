package com.kech.store.mapper;


import com.kech.store.entity.Address;
import com.kech.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(6);
        address.setPhone("17671632245");
        address.setName("柯常浩");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(6);
        System.out.println(count);
    }

    @Test
    public void findByUid() {
        List<Address> data = addressMapper.findByUid(7);
        System.out.println(data);
    }

    @Test
    public void findByAid() {
        Address data = addressMapper.findByAid(6);
        System.out.println(data);
    }
    @Test
    public void updateNonDefault() {
        Integer integer = addressMapper.updateNonDefault(7);
        System.out.println(integer);
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(7, "管理员", new Date());
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(1);
    }
    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(7));
    }

}
