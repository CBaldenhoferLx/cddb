package com.luxoft.cddb.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.services.IUserSecurityService;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

@Route(value = Views.LOGIN_VIEW) 
@PageTitle("Login | CDDB")
public class LoginView extends VerticalLayout implements HasUrlParameter<String> {

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
		
		/*
		Button internalLogin = new Button("Internal login");
		internalLogin.addClickListener(e ->
		internalLogin.getUI().ifPresent(ui ->
		           {
		        	   userSecurityService.internalLogin("test");
		        	   ui.navigate(DashboardView.class); 
		           })
		);
		internalLogin.setWidth("320px");
		*/
		
		Button googleLogin = new Button("Google Login", new Icon(VaadinIcon.GOOGLE_PLUS));
		googleLogin.addClickListener(e ->
			googleLogin.getUI().ifPresent(ui ->
	        {
	     	   ui.getPage().setLocation("/oauth2/authorization/google"); 
	        })
		);
		googleLogin.setWidth("320px");
		
		add(new H1("CDDB"), login, /*internalLogin,*/ googleLogin);
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        
        String errorParam = queryParameters.getParameters().containsKey("error") ? "error" : "";
        System.out.println(errorParam);

        login.setError(queryParameters.getParameters().containsKey("error"));
	}
}