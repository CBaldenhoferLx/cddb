package com.luxoft.cddb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.luxoft.cddb.beans.UserRoleBean;
import com.luxoft.cddb.services.IUserSecurityService;
import com.luxoft.cddb.views.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.VaadinSession;

public class MainLayout extends AppLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2073405529644686890L;
	
	public MainLayout(@Autowired IUserSecurityService userSecurityService) {
		Label usernameLabel = new Label(userSecurityService.getCurrentUsername().orElse("No user"));

		Tabs tabs = new Tabs();
		
		tabs.add(home());
		if (userSecurityService.hasRole(UserRoleBean.USER_ADMIN)) tabs.add(users());
		tabs.add(logout());
		
		addToNavbar(usernameLabel, tabs);
	}
	
	private Tab home() {
		return new Tab("Home");
	}
	
	private Tab users() {
		return new Tab("Users");
	}

	private Tab logout() {
		final Button btn = new Button();
		btn.setText("Logout");
		btn.addClickListener(e -> {
			UI ui = UI.getCurrent();
			VaadinSession session = ui.getSession();
			session.close();
			SecurityContextHolder.clearContext();
			ui.navigate(LoginView.class);
		});
		btn.setSizeFull();
		final Tab    tab   = new Tab(btn);
		//		    tab2Workspace.put(tab, new TrendsView());
		return tab;
	}

}
