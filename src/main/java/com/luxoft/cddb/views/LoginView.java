package com.luxoft.cddb.views;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.services.IUserSecurityService;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login") 
@PageTitle("Login | CDDB")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -297053990467142453L;
	private LoginForm login = new LoginForm(); 

	public LoginView(@Autowired IUserService userService, @Autowired IUserSecurityService userSecurityService){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		
		login.setForgotPasswordButtonVisible(false);
		
		Button internalLogin = new Button("Internal login");
		internalLogin.addClickListener(e ->
		internalLogin.getUI().ifPresent(ui ->
		           {
		        	   userSecurityService.internalLogin("test");
		        	   ui.navigate(MainView.class); 
		           }
		           )
		);
		
		add(new H1("CDDB"), login, internalLogin);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// inform the user about an authentication error
		if(!event.getLocation() 
			.getQueryParameters()
			.getParameters()
			.getOrDefault("error", Collections.emptyList())
			.isEmpty()) {
			login.setError(true);
		}
	}
}