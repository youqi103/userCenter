package com.youqi.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youqi.usercenter.common.BaseResponse;
import com.youqi.usercenter.common.ResultUtils;
import com.youqi.usercenter.model.domain.User;
import com.youqi.usercenter.model.domain.request.UserLoginRequest;
import com.youqi.usercenter.model.domain.request.UserRegisterRequest;
import com.youqi.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.youqi.usercenter.contant.UserConstant.ADMIN_USER;
import static com.youqi.usercenter.contant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

     @PostMapping("/register")
    public BaseResponse<Long> userRegister( @RequestBody  UserRegisterRequest userRegisterRequest){
         if(userRegisterRequest == null){
             return null;
         }
         String userAccount = userRegisterRequest.getUserAccount();
         String userPassword = userRegisterRequest.getUserPassword();
         String checkPassword = userRegisterRequest.getCheckPassword();
         String planetCode = userRegisterRequest.getPlanetCode();
         if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
             return null;
         }
        long result= userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        return ResultUtils.success(result);
     }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
         if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
       User user= userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogin(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        int i = userService.userLogout(request);
        return ResultUtils.success(i);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User)userObject;
        if(currentUser == null){
            return null;
        }
        long userId = currentUser.getId();
        //TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
       if(!isAdmin(request)){
//           return new ArrayList<>();
           return null;
       }
        QueryWrapper<User>  queryWrapper= new QueryWrapper<>();
         if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
         }
        List<User> userList = userService.list(queryWrapper);
        List<User> collect = userList.stream().map(user -> {
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(collect);

    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        if(!isAdmin(request)){
            return null;
        }
        if(id<=0){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }
    public  boolean isAdmin(HttpServletRequest request){
        //鉴权只有管理员才能删除用户
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObject;
        // 在获取userObject的地方检查
       return user.getUserRole() == ADMIN_USER && user != null;
    }

}
