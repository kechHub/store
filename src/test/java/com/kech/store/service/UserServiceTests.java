package com.kech.store.service;

import com.kech.store.entity.User;
import com.kech.store.mapper.UserMapper;
import com.kech.store.service.ex.ServiceException;
import com.kech.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    // idea有检测的功能，接口是不能够直接创建Bean的（动态代理技术）
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("kech07");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            // 获取类的对象，在获取类的名称
            System.out.println(e.getClass().getSimpleName());
            // 获取异常的具体描述信息
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void login() {
        User user = userService.login("kech", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(14, "管理员", "123", "321");
    }

    @Test
    public void getByUid() {
        User result = userService.getByUid(6);
        System.out.println(result);

    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setUid(6);
        user.setPhone("1767162224");
        user.setEmail("test@123");
        user.setGender(0);
        userService.changeInfo(6, "管理员", user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(5, "/upload/test.png", "小明");
    }


}
