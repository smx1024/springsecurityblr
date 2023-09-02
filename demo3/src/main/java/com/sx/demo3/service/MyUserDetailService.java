package com.sx.demo3.service;


import com.sx.demo3.dao.UserDao;
import com.sx.demo3.enity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MyUserDetailService implements UserDetailsService {

   @Autowired
   UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(uname);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        user.setRoles(userDao.getRolesByUid(user.getId()));
        return user;
    }
}

