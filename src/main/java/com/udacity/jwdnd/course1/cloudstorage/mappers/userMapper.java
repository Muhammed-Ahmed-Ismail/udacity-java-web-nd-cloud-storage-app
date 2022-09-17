package com.udacity.jwdnd.course1.cloudstorage.mappers;

import models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {
    @Select("SELECT * FROM USERS WHERE USERNAME=#{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username,firstname,lastname,salt,password) VALUES(#{username}, #{firstName}, #{lastName}, #{salt}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);
}
