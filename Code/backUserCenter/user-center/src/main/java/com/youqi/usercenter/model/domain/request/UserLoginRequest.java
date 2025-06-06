package com.youqi.usercenter.model.domain.request;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2L;
    private  String userAccount;
    private  String userPassword;

    /**
     * 用户
     * @TableName user
     */
    @TableName(value ="user")
    @Data
    public static class User {
        /**
         * 用户id
         */
        @TableId
        private Long id;

        /**
         * 用户角色：0：普通用户 1：管理员
         */
        private Integer userRole;

        /**
         *
         */
        private String username;

        /**
         * 用户账号
         */
        private String userAccount;

        /**
         * 性别
         */
        private Integer gender;

        /**
         * 密码
         */
        private String userPassword;

        /**
         * 电话
         */
        private String phone;

        /**
         *
         */
        private String email;

        /**
         * 用户状态：0正常
         */
        private Integer status;

        /**
         * 用户头像
         */
        private String avatarUrl;

        /**
         * 创建时间
         */
        private Date createTime;

        /**
         * 更新时间
         */
        private Date updateTime;

        /**
         * 是否删除
         */
            @TableLogic
            private Integer isDelete;
    }
}
