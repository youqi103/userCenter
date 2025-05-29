package com.youqi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youqi.usercenter.mapper.UserMapper;
import com.youqi.usercenter.model.domain.User;
import com.youqi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.youqi.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 * @author youqi
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private  UserMapper userMapper;
    /**
     * 盐值，混淆密码
     */
    private static final String SALT="youqi";
    /**
     * 用户登录键
     */

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
       //1.校验
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)){
             return null;
        }

        //检查账号是否有特殊字符
            if (!userAccount.matches("^[a-zA-Z0-9_]+$")) {
            return null;
        }
            if(planetCode.length() > 5){
                return null;
            }
        //检查两次密码是否相同
        if(!userPassword.equals(checkPassword)) {
            return null;
        }
        //用户查重
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
         Long count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            return null;
        }

        System.out.println(1);
        //用户编号查重
       queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            return null;
        }

        //2.对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        if(!this.save(user)){
            return null;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        //2.检查账号是否有特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9_]+$")) {
            return null;
        }


        //3..对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询密码是否存在
          QueryWrapper<User> queryWrapper = new QueryWrapper<>();
          queryWrapper.eq("userAccount",userAccount);
          queryWrapper.eq("userPassword",encryptPassword);

        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            log.info("user login fail,userAccount can not match passWord!");
            return null;
        }
        //用户信息脱敏
        User safetyUser = getSafetyUser(user);

        //记录脱敏后用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;

    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override

    public  User getSafetyUser(User originUser){
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setStatus(originUser.getStatus());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        if (request == null){
            return 0;
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 0;
    }
}




