package com.sx.demo2.dao;

import com.sx.demo2.enity.Role;
import com.sx.demo2.enity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    //根据用户名查询用户
    User loadUserByUsername(String username);

  	//根据用户id查询角色
  	List<Role> getRolesByUid(Integer uid);
  	//当登录时可以自动升级密码
    Integer updatePassword(@Param("username") String username, @Param("password") String password);
}
