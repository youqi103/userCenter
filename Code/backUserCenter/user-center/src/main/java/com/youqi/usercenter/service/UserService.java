package com.youqi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youqi.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 用户检查密码
     * @param planetCode 用户编号
     * @return
     */
    Long userRegister(String userAccount, String  userPassword , String checkPassword, String planetCode);


    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    default User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        return null;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    int userLogout(HttpServletRequest request);
}
