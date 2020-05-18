package com.luxoft.cddb.views;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.security.SecurityUtils;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = Views.LOGIN_SUCCESS)
public class LoginSuccessView extends VerticalLayout implements BeforeEnterObserver {
	
	private Class<? extends Component> nextClass;

	public LoginSuccessView(@Autowired OAuth2AuthorizedClientService authorizedClientService, @Autowired IUserService userService) {
    	System.out.println("Login " + authorizedClientService);
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (authentication instanceof OAuth2AuthenticationToken) {
    		System.out.println("Oauth2 auth");
    		
            OAuth2AuthorizedClient client = authorizedClientService
                    .loadAuthorizedClient(
                      ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(), 
                        authentication.getName());
            
            System.out.println(client);
            
            String userInfoEndpointUri = client.getClientRegistration()
          		  .getProviderDetails().getUserInfoEndpoint().getUri();
          		 
	  		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
	  		    RestTemplate restTemplate = new RestTemplate();
	  		    HttpHeaders headers = new HttpHeaders();
	  		    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
	  		      .getTokenValue());
	  		    HttpEntity entity = new HttpEntity("", headers);
	  		    ResponseEntity <Map>response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
	  		    Map userAttributes = response.getBody();
	  		    
	  		    System.out.println(userAttributes.size());
	  		    
	  		    for (Object attrib : userAttributes.keySet()) {
	  		    	System.out.println(attrib);
	  		    	System.out.println(userAttributes.get(attrib));
	  		    }
	  		    
	  		    String email = userAttributes.get("email").toString();
	  		    
	  		    Optional<UserBean> user = userService.findByName(email);
	  		    
	  		    if (user.isPresent()) {
	  		    	System.out.println("Oauth2 user " + email + " logging in");
	  		    	
	  		    	userService.updateEmail(email, email);
	  		    	
	  		    	nextClass = DashboardView.class;
	  		    } else {
	  		    	System.out.println("Oauth2 user " + email + " not in DB");
	  		    	SecurityUtils.logoutUser();
	  		    	nextClass = LogoutView.class;
	  		    }
	  		}

    	} else {
	    	nextClass = DashboardView.class;
    	}   	
    	
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		event.forwardTo(nextClass);
	}

}
