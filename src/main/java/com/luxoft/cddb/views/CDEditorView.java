package com.luxoft.cddb.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IFeatureSetService;
import com.luxoft.cddb.views.fragments.CDElementDiv;
import com.luxoft.cddb.views.fragments.CDListDiv;
import com.luxoft.cddb.views.fragments.CDVisualDiv;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = Views.CD_EDITOR_VIEW, layout=MainLayout.class)
public class CDEditorView extends SplitLayout implements HasUrlParameter<Integer> {

	private Tabs tabs;
	private CDListDiv listDiv;
	private CDVisualDiv visualDiv;
	
	private CDElementDiv elementDiv;
	
	public enum EditMode {
		TABLE,
		VISUAL
	}
	
	private EditMode currentEditMode = EditMode.TABLE;
	
	public CDEditorView(@Autowired IFeatureSetService featureSetService) {
		
		Button createNewFeature = new Button(VaadinIcon.PLUS.create());
		
		Tab visualTab = new Tab("Test");
		
		listDiv = new CDListDiv();
		visualDiv = new CDVisualDiv();
		setEditMode(currentEditMode);
		
		tabs = new Tabs(visualTab);
		
		tabs.addSelectedChangeListener(event -> {
		    // TODO: tabs.getSelectedTab());
		});
		
		Button tableModeButton = new Button(VaadinIcon.TABLE.create());
		Button visualModeButton = new Button(VaadinIcon.AREA_SELECT.create());
		
		tableModeButton.addClickListener(e ->
		{e.getSource().getUI().ifPresent(ui -> setEditMode(EditMode.TABLE));}
		);
		
		visualModeButton.addClickListener(e ->
		{e.getSource().getUI().ifPresent(ui -> setEditMode(EditMode.VISUAL));}
		);
		
		HorizontalLayout modeButtonLayout = new HorizontalLayout(tableModeButton, visualModeButton);
		modeButtonLayout.setJustifyContentMode(JustifyContentMode.END);
		
		tabs.setWidthFull();
		HorizontalLayout topBarLayout = new HorizontalLayout(createNewFeature, tabs, modeButtonLayout);
		topBarLayout.setWidthFull();
		
		VerticalLayout vertLayout = new VerticalLayout(topBarLayout, listDiv, visualDiv);
		
		elementDiv = new CDElementDiv();
		
		addToPrimary(vertLayout);
		addToSecondary(elementDiv);
		setSplitterPosition(80);
		setSizeFull();
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// TODO Auto-generated method stub
		
	}
	
	public void setEditMode(EditMode newMode) {
		currentEditMode = newMode;
		listDiv.setVisible(currentEditMode==EditMode.TABLE);
		visualDiv.setVisible(currentEditMode==EditMode.VISUAL);
	}

}
