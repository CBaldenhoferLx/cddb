package com.luxoft.cddb.views.fragments;

import com.luxoft.cddb.beans.master.ElementBean;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class CDListDiv extends Div {
	
	private Grid<ElementBean> grid;
	
	public CDListDiv() {
		VerticalLayout layout = new VerticalLayout();
		
		MenuBar menuBar = new MenuBar();
		//menuBar.addItem("New", e -> grid.getData);
		
		grid = new Grid<>(ElementBean.class);
		grid.setColumns("uniqueId");
		

		layout.add(menuBar, grid);
		
		
		add(layout);
	}

}
