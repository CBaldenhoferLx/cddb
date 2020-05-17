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
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = Views.DOMAIN_DETAILS_VIEW, layout=MainLayout.class)
@Secured({UserRoleBean.DATA_READER})
public class DomainDetailsView extends VerticalLayout implements HasUrlParameter<Integer>, HasDynamicTitle {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4334777409306971774L;

	Binder<DomainBean> binder = new Binder<>(DomainBean.class);
	
	private IDomainService domainService;
	
	private boolean isNew = false;
	
	private String title = "";
	
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
		
		final TextField parentLabel = new TextField();
		parentLabel.setReadOnly(true);
		
		binder.bind(parentLabel,
		        domain -> { 
		        	if (domain.getParentDomain()==null) return "[ROOT]";
		        	return domain.getParentDomain().getName();
	        	},
		        (domain, name) -> {
		        	// nothing, no setter
		        });
		
		//binder.forField(parentLabel).bind(DomainBean::getParentDomain)
		
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
		formLayout.add(parentLabel, 2);
		formLayout.add(backButton, saveButton);
		
		//addClassName("centered-content");
		
		add(formLayout);
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		if (parameter>0) {
			DomainBean domain = domainService.findById(parameter);
				
			if (event.getLocation().getQueryParameters().getParameters().containsKey("new")) {
				DomainBean newDomain = new DomainBean();
				newDomain.setParentDomain(domain);
				binder.setBean(newDomain);
				isNew = true;
				title = "Create new subdomain of " + domain.getName();
			} else {
				binder.setBean(domain);
				isNew = false;
				title = "Edit Domain";
			}
		} else {
			binder.setBean(new DomainBean());
			isNew = true;
			title = "New Domain";
		}
	}

	@Override
	public String getPageTitle() {
		return title;
	}
}
