package com.luxoft.cddb.views;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.MainLayout;
import com.luxoft.cddb.beans.UserBean;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "users", layout=MainLayout.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class UserListView extends VerticalLayout {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4925824383570516595L;

	public UserListView(@Autowired IUserService userService) {
        // Use TextField for standard text input
        Grid<UserBean> userGrid = new Grid<>(UserBean.class);
        
        userGrid.setColumns("username");
        
        userGrid.setItems(userService.findAll());
        
        CallbackDataProvider<UserBean, Void> dataProvider = DataProvider.fromCallbacks(
        		query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    final List<UserBean> users = userService.fetchUsers(offset, limit);
        			return users.stream();
        		},
        		query -> {
        			return userService.getUserCount();
        		}
        		);
        	    		
        	    		
        userGrid.setDataProvider(dataProvider);
        
        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        userGrid.addSelectionListener(selectionEvent -> {
         selectionEvent.getFirstSelectedItem().ifPresent(user -> {
         Notification.show(user.getUsername() + " is selected");
         userGrid.getUI().get().navigate(UserDetailView.class, user.getId());
         });
        }) ;
        
        
		Button createButton = new Button(
		        "Create User");
		createButton.addClickListener(e ->
		{
			createButton.getUI().ifPresent(ui -> ui.navigate(UserDetailView.class, 0));
		}
		);

    	// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(userGrid, createButton);
    }
}
