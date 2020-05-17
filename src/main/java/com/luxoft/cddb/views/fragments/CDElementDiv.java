package com.luxoft.cddb.views.fragments;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class CDElementDiv extends Div {

	public CDElementDiv() {
		
		TextField uniqueId = new TextField();
		uniqueId.setPlaceholder("uniqueId");
		uniqueId.setLabel("UniqueId");
		
		Select<String> status = new Select<>();
		status.setLabel("Status");
		status.setPlaceholder("Status");
		status.setItems("New", "Deleted"); // TODO
		
		VerticalLayout basicDetails = new VerticalLayout(uniqueId, status);
		
		Details details = new Details("Basic", basicDetails);
		details.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
		
		add(details);
	}

}
