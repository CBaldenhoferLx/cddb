package com.luxoft.cddb.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IDomainService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = Views.DOMAIN_DETAILS_VIEW, layout=MainLayout.class)
@Secured({UserRoleBean.DATA_READER})
public class DomainDetailsView extends VerticalLayout implements HasUrlParameter<Integer> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4334777409306971774L;

	Binder<DomainBean> binder = new Binder<>(DomainBean.class);
	
	private IDomainService domainService;
	
	private boolean isNew = false;
	
	public DomainDetailsView(@Autowired IDomainService domainService) {
		this.domainService = domainService;
		
		binder.setBean(new DomainBean());
		
		Button backButton = new Button("Back", VaadinIcon.ANGLE_LEFT.create());
		backButton.addClickListener(e ->
		backButton.getUI().ifPresent(ui ->
		           ui.navigate(DomainListView.class))
		);
		
		Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
		saveButton.addClickListener(e ->
		{
			binder.validate();
			
			if (binder.validate().isOk()) {
				domainService.save(binder.getBean());
				saveButton.getUI().ifPresent(ui -> ui.navigate(DomainListView.class));
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
			.bind(DomainBean::getName, DomainBean::setName);
		
		FormLayout formLayout = new FormLayout();
		
		formLayout.setResponsiveSteps(
		        new ResponsiveStep("800px", 2)
		        );
		
		formLayout.setMaxWidth("800px");
		formLayout.add(nameField, 2);
		formLayout.add(backButton, saveButton);
		
		//addClassName("centered-content");
		
		add(formLayout);
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		if (parameter>0) {
			DomainBean domain = domainService.get(parameter);
			binder.setBean(domain);
			isNew = false;
		} else {
			binder.setBean(new DomainBean());
			isNew = true;
		}
	}
	
}
