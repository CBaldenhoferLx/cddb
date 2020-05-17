package com.luxoft.cddb.views;


import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route(value = Views.DASHBOARD_VIEW, layout=MainLayout.class)
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class DashboardView extends SplitLayout {
	
	public DashboardView(@Autowired IUserService userService) {
        // Use TextField for standard text input
		
		Label commentsLabel = new Label("Latest comments");
		Grid<String> userCommentsList = new Grid<String>();

    	// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");
		
		setOrientation(Orientation.VERTICAL);

        addToPrimary(commentsLabel,  userCommentsList);
        addToSecondary(new Label("Test")); 
    }
}
