package com.youqi.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youqi.usercenter.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MR
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2025-05-16 17:22:36
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




