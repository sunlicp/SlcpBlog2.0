package com.slcp.devops.mapper;

import com.slcp.devops.entity.RegisterUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author: Slcp
 * @date: 2020/10/29 16:06
 * @code: 一生的挚爱
 * @description:
 */
@Mapper
@Repository
public interface RegisterUserMapper {

    /**
     * 校验
     * @param username 账号
     * @return 用户
     */
    RegisterUser checkUsername(String username);

    /**
     * 保存
     * @param registerUser 注册用户
     * @return 数值
     */
    int saveRegisterUser(RegisterUser registerUser);

    /**
     * 查询用户
     * @param registerUserName 名称
     * @return 用户
     */
    RegisterUser getRegisterUserByName(String registerUserName);

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    RegisterUser userLoginByUsername(String username, String password);

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    RegisterUser userLoginByEmail(String username, String password);

    /**
     * m
     * @param username 账号
     * @param email 邮箱
     * @return 数据
     */
    String forgetPassword(String username, String email);
}
