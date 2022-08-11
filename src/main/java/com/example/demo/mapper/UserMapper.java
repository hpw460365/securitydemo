package com.example.demo.mapper;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username= #{username}")
    User loadUserByUserName(String username);
}
