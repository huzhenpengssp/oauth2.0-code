package com.authorization.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@SessionAttributes({ "authorizationRequest" }) //该注解是必须要有的
public class OAuthController {

    @RequestMapping({ "/oauth/my_approval_page" })
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
    	AuthorizationRequest authorization=(AuthorizationRequest)model.get("authorizationRequest");
    	 Map<String, String> scopes = (Map<String, String>)request.getAttribute("scopes");
        
        List<String> scopeList = new ArrayList<String>();
        for (String scope : scopes.keySet()) {
            scopeList.add(scope);
        }
        model.put("scopeList", scopeList);
        Object clientId=authorization.getClientId();
        String clientName="FUCK THE WORLD";
        if(clientId!=null){
            if(((String)clientId).equals("clientapp")){
                clientName="I COMPRROMISE";
            }
        }
        model.put("clientName",clientName);
        return "oauth_approval";
    }
}
