package com.kech.store.entity;

import lombok.Data;

@Data
public class District extends BaseEntity{
    private Integer id;
    private String parent;
    private String code;
    private String name;
}
