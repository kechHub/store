package com.kech.store.service.impl;

import com.kech.store.entity.User;
import com.kech.store.mapper.UserMapper;
import com.kech.store.service.IUserService;
import com.kech.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 通过user参数获取传递过来的username
        String username = user.getUsername();
        // 调用findByUsername(username) 判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        // 判断结果集是否不为null 则抛出用户名被占用的异常
        if(result != null) {
            // 抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }
        // 密码加密处理的实现：md5算法的形式67dhdsgh0ye-dasdasfsa-fsafageg-gedg
        // 串 + password + 串 ------md5算法进行加密,连续加载3次
        // 盐值 +password + 盐值 -------盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        // 获取盐值（随机生成一个盐值）
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        // 将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);
        user.setSalt(salt);
        // 补全数据：is_delete设置为0
        user.setIsDelete(0);
        // 补全数据: 4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务功能的实现(row == 1)
        Integer rows = userMapper.insert(user);
        if(rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        // 根据用户名称查询用户的数据是否存在，如果不在抛出异常
        User result = userMapper.findByUsername(username);
        if(result == null) {
            throw new UserNotFoundException("用户不存在");
        }

        // 检查用户的密码是否匹配
        // 1.先获取到数据中的加密之后的密码
        String oldPassword = result.getPassword();
        // 2.和用户的传递过来的密码进行比较
        // 2.1 先获取盐值：上次在注册时自动生成的盐值'
        String salt = result.getSalt();
        // 2.2 将用户的密码按照相同的Md5算法的规则进行加密
        String newMd5Password = getMD5Password(password, salt);
        // 3.将密码进行比较
        if(!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        System.out.println(result);
        // 判断is_delete字段的值是否为1表示被标记为删除
        if(result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 调用mapper层的findByUsername 来查询用户的数据
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 原始密码和数据库中密码进行比较
        String oldMd5Password =
                getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        // 将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * user对象中数据phone/email/gender，手动再将uid/username封装
     * user对象中
     * @param uid 用户id
     * @param username 用户名
     * @param user 用户对象的数据
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        // 查询当前的用户数据是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像产生未知的异常");
        }

    }

    /**
     * 定义一个md5加密算法
     */
    public String getMD5Password(String password, String salt) {
        // md5加密算法的调用
        for (int i = 0; i < 3; i++) {
            // md5加密算法方法调用三次
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
