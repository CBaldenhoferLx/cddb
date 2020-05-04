package com.luxoft.cddb.views;

import com.luxoft.cddb.broadcaster.Broadcaster;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Push
@Route("usersOnline")
public class UsersOnlineView extends Div {
	
	Button usersButton;
	
	Registration broadcasterRegistration;
	
	public UsersOnlineView() {
		usersButton = new Button(VaadinIcon.USER_CARD.create());
	}
	
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            //ui.access(() -> messages.add(new Span(newMessage)));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

}
