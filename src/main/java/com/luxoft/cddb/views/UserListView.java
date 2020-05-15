package com.luxoft.cddb.views;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = Views.USER_LIST_VIEW, layout=MainLayout.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class UserListView extends VerticalLayout {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4925824383570516595L;
	
	private IUserService userService;
	
	private Grid<UserBean> userGrid;

	public UserListView(@Autowired IUserService userService) {
		this.userService = userService;
        // Use TextField for standard text input
        userGrid = new Grid<>(UserBean.class);
        
        userGrid.setColumns("username", "email", "type");
        userGrid.setWidthFull();
        userGrid.setMaxHeight("80%");
        
        userGrid.addComponentColumn(this::buildDeleteButton);
        
        userGrid.setItems(userService.findAll());
        
        CallbackDataProvider<UserBean, Void> dataProvider = DataProvider.fromCallbacks(
        		query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    final List<UserBean> users = userService.findAll(offset, limit);
        			return users.stream();
        		},
        		query -> {
        			return userService.getCount();
        		}
        		);
        	    		
        	    		
        userGrid.setDataProvider(dataProvider);
        
        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        userGrid.addSelectionListener(selectionEvent -> {
         selectionEvent.getFirstSelectedItem().ifPresent(user -> {
        	 userGrid.getUI().get().navigate(UserDetailsView.class, user.getId());
         });
        }) ;
        
        
		Button createButton = new Button(
		        "Create User", VaadinIcon.PLUS.create());
		createButton.addClickListener(e ->
		{
			createButton.getUI().ifPresent(ui -> ui.navigate(UserDetailsView.class, 0));
		}
		);

    	// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

        add(createButton, userGrid);
    }
	
	private Button buildDeleteButton(UserBean user) {
        Button button = new Button(VaadinIcon.TRASH.create());
        //button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
        	userService.delete(user);
        	userGrid.getDataProvider().refreshAll();
        });
        return button;
	}
}
