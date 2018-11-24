package com.jarmel.basic.webapp.dao;

import com.jarmel.basic.webapp.entity.security.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);
    
}
