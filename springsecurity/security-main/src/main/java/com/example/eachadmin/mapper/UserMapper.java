package com.example.eachadmin.mapper;

import com.example.eachadmin.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "UserMapper")
public interface UserMapper {

    User findUserByName(@Param("name")String name);
}
