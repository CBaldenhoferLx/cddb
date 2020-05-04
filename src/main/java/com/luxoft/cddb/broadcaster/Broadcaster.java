package com.luxoft.cddb.broadcaster;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(String message) {
        for (Consumer<String> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
    
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		userSecurityService.listLoggedInUsers();
		System.out.println("The time is now " + new Date().toString());
	}
}