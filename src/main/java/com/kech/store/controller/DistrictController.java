package com.kech.store.controller;

import com.kech.store.entity.District;
import com.kech.store.service.IDistrictService;
import com.kech.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseContorller {
    @Autowired
    private IDistrictService districtService;
    // districts开头的请求都被拦截到getByParent
    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
