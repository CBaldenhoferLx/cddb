package com.luxoft.cddb.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.views.fragments.CDListDiv;
import com.luxoft.cddb.views.fragments.CDVisualDiv;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = Views.CD_EDITOR_VIEW, layout=MainLayout.class)
public class CDEditorView extends VerticalLayout implements HasUrlParameter<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2663442887877110201L;
	
	private Tabs tabs;
	private CDListDiv listDiv;
	private CDVisualDiv visualDiv;
	
	public CDEditorView() {
		
		Tab listTab = new Tab("List View");
		Tab visualTab = new Tab("Visual View");
		
		listDiv = new CDListDiv();
		visualDiv = new CDVisualDiv();
		visualDiv.setVisible(false);
		
		tabs = new Tabs(listTab, visualTab);
		
		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(listTab, listDiv);
		tabsToPages.put(visualTab, visualDiv);
		Div pages = new Div(listDiv, visualDiv);
		Set<Component> pagesShown = Stream.of(listDiv)
		        .collect(Collectors.toSet());

		tabs.addSelectedChangeListener(event -> {
		    pagesShown.forEach(page -> page.setVisible(false));
		    pagesShown.clear();
		    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
		    selectedPage.setVisible(true);
		    pagesShown.add(selectedPage);
		});
		
		pages.setSizeFull();
		
		add(tabs, pages);
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// TODO Auto-generated method stub
		
	}

}
