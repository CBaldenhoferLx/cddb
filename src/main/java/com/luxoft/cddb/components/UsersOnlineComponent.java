package com.luxoft.cddb.components;

import com.luxoft.cddb.broadcaster.Broadcaster;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
@Tag("div")
public class UsersOnlineComponent extends Component implements HasComponents {
	
	Button usersButton;
	
	Registration broadcasterRegistration;
	
	public UsersOnlineComponent() {
		usersButton = new Button("0", VaadinIcon.USER_CARD.create());
		
		add(usersButton);
	}
	
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        
        ui.setPollInterval(1000);
        
        broadcasterRegistration = Broadcaster.register(newMessage -> {
        	ui.access(() -> {
        		System.out.println("Received message " + newMessage);
        		usersButton.setText(newMessage);
        		});
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

}
