package com.luxoft.cddb.views.fragments;

import org.github.legioth.imagemap.ImageMap;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;

@SuppressWarnings("serial")
public class CDVisualDiv extends Div {

	public CDVisualDiv() {
		
		Image img = new Image("https://dummyimage.com/600x400/000/fff", "test");
		
		ImageMap imageMap = new ImageMap("https://dummyimage.com/600x400/000/fff", "test");
		
		img.addClickListener(new ComponentEventListener<ClickEvent<Image>>() {
			
			@Override
			public void onComponentEvent(ClickEvent<Image> event) {
				System.out.println(event.getScreenX() + "x" + event.getScreenY());				
				System.out.println(event.getClientX() + "x" + event.getClientY());
				
			}
		});
		
		imageMap.addArea(99, 115, 400, 316).addClickListener(event -> Notification.show("Left antler"));
		
		add(imageMap);
	}

}
