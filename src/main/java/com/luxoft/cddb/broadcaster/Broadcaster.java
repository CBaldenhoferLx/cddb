package com.luxoft.cddb.broadcaster;

import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.services.IUserSecurityService;
import com.vaadin.flow.shared.Registration;

@Component
public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();
    
    @Autowired
    private IUserSecurityService userSecurityService;

    static LinkedList<Consumer<String>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<String> listener) {
    	
    	System.out.println("Register listener");
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(String message) {
    	System.out.println("broadcasting to " + listeners.size());
    	
        for (Consumer<String> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
    
	@Scheduled(fixedRate = 10000)
	public void reportCurrentTime() {
		Set<UserBean> users = userSecurityService.listLoggedInUsers();
		
		broadcast("" + users.size());
		
		for (UserBean user : users) {
			System.out.println("LOGGED IN: " + user.getUsername());
		}
	}
}