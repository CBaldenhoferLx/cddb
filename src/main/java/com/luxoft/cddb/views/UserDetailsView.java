package com.luxoft.cddb.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IUserRoleService;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = Views.USER_DETAILS_VIEW, layout=MainLayout.class)
@Secured({UserRoleBean.USER_ADMIN})
public class UserDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2151911196421896767L;
	
	Binder<UserBean> binder = new Binder<>(UserBean.class);
	
	private IUserService userService;
	
	final RadioButtonGroup<String> radioGroup;
	final MultiSelectListBox<String> userRolesList;
	
	private boolean isNew = false;
	
	public UserDetailsView(@Autowired IUserService userService, @Autowired IUserRoleService userRoleService) {
		this.userService = userService;
		
		binder.setBean(new UserBean());
		
		//FormLayout detailLayout = new FormLayout();
		
		final PasswordField pwField = new PasswordField("Password");
		pwField.setPlaceholder(pwField.getLabel());
		pwField.setVisible(false);
		
		userRolesList = new MultiSelectListBox<>();
		List<String> userRoles = new ArrayList<>();
		for (UserRoleBean ur : userRoleService.findAll()) {
			userRoles.add(ur.getName());
		}
		userRolesList.setItems(userRoles);
		
		Button backButton = new Button("Back", VaadinIcon.ANGLE_LEFT.create());
		backButton.addClickListener(e ->
		backButton.getUI().ifPresent(ui ->
		           ui.navigate(UserListView.class))
		);
		
		Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
		saveButton.addClickListener(e ->
		{
			if (binder.getBean().getType()==UserBean.UserType.INTERNAL) {
				if (pwField.getValue().length()==0 && !isNew) {
					// keep pw
				} else if (pwField.getValue().length()<4) {
					Notification.show("Password must have at least 4 characters");
					return;
				} else {
					binder.getBean().setPasswordHash(userService.encodePassword(pwField.getValue()));
				}
			} else {
				binder.getBean().setPasswordHash("");
			}
			
			
			binder.validate();
			
			if (binder.validate().isOk()) {
				binder.getBean().clearRoles();
				for (String ur : userRolesList.getSelectedItems()) {
					userService.addRole(binder.getBean(), ur);
				}
				
				userService.save(binder.getBean());
				saveButton.getUI().ifPresent(ui -> ui.navigate(UserListView.class));
			}
		}
		);
		
		final TextField nameField = new TextField("Name");
		nameField.setPlaceholder(nameField.getLabel());
		
		binder.forField(nameField)
			.withValidator(name -> name.length() >= 3, "Name must be at least 3 characters")
			.withValidationStatusHandler(status -> {
				if (status.isError()) {
					Notification.show(status.getMessage().toString());
				}
		    })			
			.bind(UserBean::getUsername, UserBean::setUsername);
		
		radioGroup = new RadioButtonGroup<>();
		radioGroup.setLabel("Type");
		radioGroup.setItems(UserBean.getTypeNames());
		
		radioGroup.addValueChangeListener(event -> {
			UserBean.UserType type = UserBean.convertTypeName(event.getValue());
			pwField.setVisible(type==UserBean.UserType.INTERNAL);
			binder.getBean().setType(type);
		});
		
		FormLayout formLayout = new FormLayout();
		
		formLayout.setResponsiveSteps(
		        new ResponsiveStep("800px", 2)
		        );
		
		formLayout.setMaxWidth("800px");
		formLayout.add(nameField, 2);
		formLayout.add(pwField, 2);
		formLayout.add(radioGroup, 2);
		formLayout.add(new Label("User Roles"), userRolesList);
		formLayout.add(backButton, saveButton);
		
		//addClassName("centered-content");
		
		add(formLayout);
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		if (parameter>0) {
			UserBean user = userService.get(parameter);
			binder.setBean(user);
			
			userRolesList.deselectAll();
			for (UserRoleBean ur : user.getUserRoles()) {
				userRolesList.select(ur.getName());
			}
			
			isNew = false;
		} else {
			binder.setBean(new UserBean());
			isNew = true;
		}
		
		radioGroup.setValue(UserBean.convertTypeName(binder.getBean().getType()));
	}
	
}
