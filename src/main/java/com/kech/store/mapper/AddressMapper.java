package com.kech.store.mapper;

import com.kech.store.entity.Address;

public interface AddressMapper {
    /**
     * 插入收货地址数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 统计某用户的收货地址数据的数量
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    Integer countByUid(Integer uid);

}