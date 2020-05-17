package com.luxoft.cddb.views;

import java.util.HashMap;

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
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;

@SuppressWarnings("serial")
@Route(value = Views.DOMAIN_LIST_VIEW, layout=MainLayout.class)
@Secured({UserRoleBean.DATA_READER})
public class DomainListView extends VerticalLayout {

	private TreeGrid<DomainBean> domainGrid;

	public DomainListView(@Autowired IDomainService domainService) {
        // Use TextField for standard text input
		domainGrid = new TreeGrid<>();
        
		domainGrid.addHierarchyColumn(DomainBean::getName).setHeader("Domain");
		
		domainGrid.setWidthFull();
		domainGrid.setMaxHeight("80%");
        
		domainGrid.addComponentColumn(this::buildEditButton);
		domainGrid.addComponentColumn(this::buildCreateButton);
		
		/*
		HierarchicalDataProvider<DomainBean, Void> dataProvider =
		        new AbstractBackEndHierarchicalDataProvider<DomainBean, Void>() {

		    @Override
		    public int getChildCount(HierarchicalQuery<DomainBean, Void> query) {
		    	if (query.getParent()==null) return 0;
		        return query.getParent().getSubDomains().size();
		    }

		    @Override
		    public boolean hasChildren(DomainBean item) {
		        return item.getSubDomains().size()>0;
		    }

		    @Override
		    protected Stream<DomainBean> fetchChildrenFromBackEnd(
		            HierarchicalQuery<DomainBean, Void> query) {
		        return query.getParent().getSubDomains().stream();
		    }
		};

		domainGrid.setDataProvider(dataProvider);
		*/
		
		domainGrid.setItems(domainService.getRootItems(), DomainBean::getSubDomains);

        domainGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        domainGrid.addSelectionListener(selectionEvent -> {
         selectionEvent.getFirstSelectedItem().ifPresent(domain -> {
        	 if (!domain.isRoot()) {
            	 domainGrid.getUI().get().navigate(CDEditorView.class, domain.getId());
        	 }
         });
        }) ;
        
        
		Button createButton = new Button(
		        "Create Root Domain", VaadinIcon.PLUS.create());
		createButton.addClickListener(e ->
		{
			createButton.getUI().ifPresent(ui -> ui.navigate(buildBaseUrl(0)));
		}
		);

    	// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

        add(createButton, domainGrid);
    }
	
	private QueryParameters buildParameters(boolean isNew) {
    	return QueryParameters.simple(new HashMap<String, String>() {{ 
    		put("new", isNew ? "true" : "false");
    	}});
	}
	
	private String buildBaseUrl(int id) {
		return RouteConfiguration.forSessionScope().getUrl(DomainDetailsView.class, id);
	}
	
	private Button buildCreateButton(DomainBean domain) {
        Button button = new Button(VaadinIcon.PLUS.create());
        //button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
        	domainGrid.getUI().get().navigate(buildBaseUrl(domain.getId()), buildParameters(true));
        });
        
        button.setVisible(domain.isRoot());
        
        return button;
	}

	private Button buildEditButton(DomainBean domain) {
        Button button = new Button(VaadinIcon.EDIT.create());
        //button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
        	domainGrid.getUI().get().navigate(buildBaseUrl(domain.getId()));
        });
        return button;
	}

}
