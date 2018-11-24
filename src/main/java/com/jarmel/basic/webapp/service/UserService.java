package com.jarmel.basic.webapp.service;

import com.jarmel.basic.webapp.entity.RegistrationUser;
import com.jarmel.basic.webapp.entity.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(RegistrationUser registrationUser);
}
