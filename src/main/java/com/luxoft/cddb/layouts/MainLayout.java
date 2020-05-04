package com.luxoft.cddb.layouts;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.beans.UserRoleBean;
import com.luxoft.cddb.components.UsersOnlineComponent;
import com.luxoft.cddb.services.IUserSecurityService;
import com.luxoft.cddb.views.DashboardView;
import com.luxoft.cddb.views.LogoutView;
import com.luxoft.cddb.views.UserListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Push;

@Push
public class MainLayout extends AppLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2073405529644686890L;
	
	private static final String buttonWidth = "300px"; 
	
	public MainLayout(@Autowired IUserSecurityService userSecurityService) {
		
		HorizontalLayout layout = new HorizontalLayout();
		
		layout.setWidthFull();
		
		Label usernameLabel = new Label(userSecurityService.getCurrentUsername().orElse("No user"));
		usernameLabel.setWidth("300px");
		layout.add(usernameLabel);

		UsersOnlineComponent usersOnline = new UsersOnlineComponent();
		layout.add(usersOnline);

		final Button homeButton = new Button("Home", VaadinIcon.HOME.create());
		homeButton.addClickListener(e -> {
			UI ui = UI.getCurrent();
			ui.navigate(DashboardView.class);
		});
		homeButton.setWidth(buttonWidth);
		layout.add(homeButton);
		
		if (userSecurityService.hasRole(UserRoleBean.USER_ADMIN)) {
			final Button usersButton = new Button("Users", VaadinIcon.USERS.create());
			usersButton.addClickListener(e -> {
				UI ui = UI.getCurrent();
				ui.navigate(UserListView.class);
			});
			usersButton.setWidth(buttonWidth);
			layout.add(usersButton);
		}
		
		
		final Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
		logoutButton.addClickListener(e -> {
			UI ui = UI.getCurrent();
			ui.navigate(LogoutView.class);
		});
		logoutButton.setWidth(buttonWidth);
		
		FlexLayout logoutButtonWrapper = new FlexLayout(logoutButton);
		logoutButtonWrapper.setJustifyContentMode(JustifyContentMode.END);
		
		layout.expand(logoutButtonWrapper);
		
		
		layout.add(logoutButtonWrapper);
		
		addToNavbar(layout);
	}


}
