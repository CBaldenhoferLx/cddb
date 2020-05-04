package com.luxoft.cddb.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value=Views.LOGOUT_EXECUTE)
public class LogoutView extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4582174915859565832L;
	
	public LogoutView() {
		VaadinSession.getCurrent().getSession().invalidate();
		UI.getCurrent().getPage().executeJs("window.location.href='logout'");
		UI.getCurrent().close();
		Button loginButton = new Button("To Login Page");
		
		loginButton.addClickListener(e ->
		loginButton.getUI().ifPresent(ui ->
        {
     	   ui.navigate(LoginView.class); 
        })
	);

		add(loginButton);
	}
}
