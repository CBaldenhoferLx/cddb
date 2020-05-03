package com.luxoft.cddb.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.luxoft.cddb.MainLayout;
import com.luxoft.cddb.beans.UserBean;
import com.luxoft.cddb.beans.UserRoleBean;
import com.luxoft.cddb.services.IUserRoleService;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "userDetail", layout=MainLayout.class)
@Secured({UserRoleBean.USER_ADMIN})
public class UserDetailView extends VerticalLayout implements HasUrlParameter<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2151911196421896767L;
	
	Binder<UserBean> binder = new Binder<>(UserBean.class);
	
	private IUserService userService;
	
	final RadioButtonGroup<String> radioGroup;
	final MultiSelectListBox<String> userRolesList;
	
	private boolean isNew = false;
	
	public UserDetailView(@Autowired IUserService userService, @Autowired IUserRoleService userRoleService) {
		this.userService = userService;
		
		binder.setBean(new UserBean());
		
		setSizeFull();
		
		final PasswordField pwField = new PasswordField("Password");
		final Label pwFieldStatus = new Label();
		
		pwField.setVisible(false);
		
		pwField.addValueChangeListener(event -> {
			pwFieldStatus.setText("");
		});
		

		userRolesList = new MultiSelectListBox<>();
		List<String> userRoles = new ArrayList<>();
		for (UserRoleBean ur : userRoleService.findAll()) {
			userRoles.add(ur.getName());
		}
		userRolesList.setItems(userRoles);
		
		Button backButton = new Button(
		        "Back");
		backButton.addClickListener(e ->
		backButton.getUI().ifPresent(ui ->
		           ui.navigate(MainView.class))
		);
		
		Button saveButton = new Button(
		        "Save");
		saveButton.addClickListener(e ->
		{
			if (binder.getBean().getType()==UserBean.UserType.EXTERNAL) {
				if (pwField.getValue().length()==0 && !isNew) {
					// keep pw
				} else if (pwField.getValue().length()<4) {
					pwFieldStatus.setText("Password must have at least 4 characters");
					return;
				} else {
					pwFieldStatus.setText("");
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
				saveButton.getUI().ifPresent(ui -> ui.navigate(MainView.class));
			}
		}
		);
		
		final TextField nameField = new TextField("Name");
		final Label nameFieldStatus = new Label();
		
		binder.forField(nameField)
			.withValidator(name -> name.length() >= 3, "Name must be at least 3 characters")
			.withValidationStatusHandler(status -> {
				nameFieldStatus.setText(status
		                .getMessage().orElse(""));
				nameFieldStatus.setVisible(status.isError());
		    })			
			.bind(UserBean::getUsername, UserBean::setUsername);
		
		radioGroup = new RadioButtonGroup<>();
		radioGroup.setLabel("Type");
		radioGroup.setItems(UserBean.getTypeNames());
		
		radioGroup.addValueChangeListener(event -> {
			UserBean.UserType type = UserBean.convertTypeName(event.getValue());
			pwField.setVisible(type==UserBean.UserType.EXTERNAL);
			binder.getBean().setType(type);
		});
		
        add(nameFieldStatus, nameField, pwFieldStatus, pwField, radioGroup, userRolesList, backButton, saveButton);
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
