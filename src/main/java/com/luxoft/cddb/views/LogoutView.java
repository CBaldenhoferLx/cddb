package com.luxoft.cddb.views;

import com.luxoft.cddb.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value=Views.LOGOUT_SUCCESS)
public class LogoutView extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4582174915859565832L;

	public LogoutView() {
		System.out.println("LOGOUT");
		UI ui = UI.getCurrent();
		VaadinSession session = ui.getSession();
		session.close();
		SecurityUtils.logoutUser();
		ui.navigate(LoginView.class);
	}

}
