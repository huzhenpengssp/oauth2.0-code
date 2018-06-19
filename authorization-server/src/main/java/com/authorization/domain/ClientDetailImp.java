package com.authorization.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDetailImp implements ClientDetails{
    private ClientDetail client;

    public ClientDetailImp(ClientDetail client){
        this.client=client;
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
    	if(client.getResourceIds()==null) return null;
    	Set<String> resourceIds=new HashSet<String>();
    	resourceIds.add(client.getResourceIds());
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return client.getClientType().equals("CONFIDENTIAL");
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return getScope().size()>0;
    }

    @Override
    public Set<String> getScope() {
    	Set<String> scopes=new HashSet<String>();
    	String[] scopeSt=client.getScope().split(",");
    	for(String scope: scopeSt) {
    		scopes.add(scope);
    	}
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
    	Set<String> grantTypes=new HashSet<String>();
    	String[] grantType=client.getAuthorizedGrantTypes().split(",");
    	for(String grant: grantType) {
    		grantTypes.add(grant);
    	}
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
    	Set<String> redirectUris=new HashSet<String>();
    	String[] redirectUri=client.getWebServerRedirectUri().split(",");
    	for(String uri: redirectUri) {
    		redirectUris.add(uri);
    	}
        return redirectUris;
    }
    //该方法暂未实现
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
    	Collection<GrantedAuthority> result=AuthorityUtils.createAuthorityList();
        return result;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client.getAccessTokenValidity();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client.getRefreshTokenValidity();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return client.getAutoapprove().equals("true");
    }
    //该方法暂未实现
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
