package com.jarmel.basic.webapp.dao;

import com.jarmel.basic.webapp.entity.security.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
