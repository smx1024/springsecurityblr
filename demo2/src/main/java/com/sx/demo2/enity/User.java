package com.sx.demo2.enity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {
     private Integer id;
     private String username;
     private String password;
     private Boolean enabled;
     private Boolean accountNonExpired;
     private Boolean accountNonLocked;
     private Boolean credentialsNonExpired;
     private List<Role> roles = new ArrayList<>();//关系属性，用来存储当前用户所有角色信息

     //返回权限信息
     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
         Set<SimpleGrantedAuthority> authorities = new HashSet<>();
         roles.forEach(role -> {
             SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
             authorities.add(simpleGrantedAuthority);
         });
         return authorities;
     }

     @Override
     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     @Override
     public String getUsername() {
         return username;
     }

     public void setUsername(String username) {
         this.username = username;
     }

     @Override
     public boolean isAccountNonExpired() {
         return accountNonLocked;
     }

     @Override
     public boolean isAccountNonLocked() {
         return accountNonLocked;
     }

     @Override
     public boolean isCredentialsNonExpired() {
         return credentialsNonExpired;
     }

     @Override
     public boolean isEnabled() {
         return enabled;
     }

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public Boolean getEnabled() {
         return enabled;
     }

     public void setEnabled(Boolean enabled) {
         this.enabled = enabled;
     }

     public Boolean getAccountNonExpired() {
         return accountNonExpired;
     }

     public void setAccountNonExpired(Boolean accountNonExpired) {
         this.accountNonExpired = accountNonExpired;
     }

     public Boolean getAccountNonLocked() {
         return accountNonLocked;
     }

     public void setAccountNonLocked(Boolean accountNonLocked) {
         this.accountNonLocked = accountNonLocked;
     }

     public Boolean getCredentialsNonExpired() {
         return credentialsNonExpired;
     }

     public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
         this.credentialsNonExpired = credentialsNonExpired;
     }

     public List<Role> getRoles() {
         return roles;
     }

     public void setRoles(List<Role> roles) {
         this.roles = roles;
     }
 }

