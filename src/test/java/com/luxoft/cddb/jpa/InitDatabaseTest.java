package com.luxoft.cddb.jpa;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.cddb.Application;
import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.beans.user.UserBean.UserType;
import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.services.IUserRoleService;
import com.luxoft.cddb.services.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class InitDatabaseTest {
	
	@Autowired
	private IUserService userService;
	
	@Autowired IUserRoleService userRoleService;
	
	@Test
	public void initDatabase() {
		
		String username = "christoph.baldenhofer@googlemail.com";
		UserType type = UserType.GOOGLE;
		
		Optional<UserBean> userCheck = userService.findByName(username);
		Optional<UserRoleBean> userRole = userRoleService.findByName(UserRoleBean.USER_ADMIN);		// should be there already
		
		UserBean user;
		
		if (!userCheck.isPresent()) {
			user = new UserBean(username, type);
			user.addRole(userRole.get());
		} else {
			// check roles
			user = userCheck.get();
			if (!user.hasRole(UserRoleBean.USER_ADMIN)) {
				user.addRole(userRole.get());
			}
		}
		
		userService.save(user);
	}

}
