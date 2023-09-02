package com.sx.demo3.dao;

import com.sx.demo3.enity.Role;
import com.sx.demo3.enity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    //根据用户名查询用户
    User loadUserByUsername(String username);

  	//根据用户id查询角色
  	List<Role> getRolesByUid(Integer uid);
}
