package com.luxoft.cddb.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.layouts.MainLayout;
import com.luxoft.cddb.services.IDomainService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;

@Route(value = Views.DOMAIN_LIST_VIEW, layout=MainLayout.class)
@Secured({UserRoleBean.DATA_READER})
public class DomainListView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6745370994277379046L;
	
	private Grid<DomainBean> domainGrid;

	public DomainListView(@Autowired IDomainService domainService) {
        // Use TextField for standard text input
		domainGrid = new Grid<>(DomainBean.class);
        
		domainGrid.setColumns("name");
		domainGrid.setWidthFull();
		domainGrid.setMaxHeight("80%");
        
		domainGrid.addComponentColumn(this::buildEditButton);

		domainGrid.setItems(domainService.findAll());
        
        CallbackDataProvider<DomainBean, Void> dataProvider = DataProvider.fromCallbacks(
        		query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    final List<DomainBean> domains = domainService.findAll(offset, limit);
        			return domains.stream();
        		},
        		query -> {
        			return domainService.getCount();
        		}
        		);
        	    		
        	    		
        domainGrid.setDataProvider(dataProvider);
        
        domainGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        domainGrid.addSelectionListener(selectionEvent -> {
         selectionEvent.getFirstSelectedItem().ifPresent(domain -> {
        	 domainGrid.getUI().get().navigate(CDEditorView.class, domain.getId());
         });
        }) ;
        
        
		Button createButton = new Button(
		        "Create Domain", VaadinIcon.PLUS.create());
		createButton.addClickListener(e ->
		{
			createButton.getUI().ifPresent(ui -> ui.navigate(DomainDetailsView.class, 0));
		}
		);

    	// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

        add(createButton, domainGrid);
    }

	private Button buildEditButton(DomainBean domain) {
        Button button = new Button(VaadinIcon.EDIT.create());
        //button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
        	domainGrid.getUI().get().navigate(DomainDetailsView.class, domain.getId());
        });
        return button;
	}

}
