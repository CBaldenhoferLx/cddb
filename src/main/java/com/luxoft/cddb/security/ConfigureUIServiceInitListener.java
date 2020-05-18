package com.luxoft.cddb.security;

import org.springframework.stereotype.Component;

import com.luxoft.cddb.views.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5587148929661785676L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> { 
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/*
	private void authenticateNavigation(BeforeEnterEvent event) {
		if (!LoginView.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) { 
			event.rerouteTo(LoginView.class);
		}
	}*/
	
	private void beforeEnter(BeforeEnterEvent event) {
	    if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())) { // 
	        if(SecurityUtils.isUserLoggedIn()) { // 
	            event.rerouteToError(NotFoundException.class); // 
	        } else {
	            event.rerouteTo(LoginView.class); // 
	        }
	    }
	    // 
	}
}