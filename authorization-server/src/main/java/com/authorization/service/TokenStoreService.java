package com.authorization.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.authorization.dao.AccessTokenMapper;
import com.authorization.dao.RefreshTokenMapper;
import com.authorization.domain.AccessTokenWithBLOBs;
import com.authorization.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;


public class TokenStoreService implements TokenStore{

	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Autowired
	private RefreshTokenMapper refreshTokenMapper;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		OAuth2Authentication oauth2Authentication=null;
		AccessTokenWithBLOBs accessToken=accessTokenMapper.selectByTokenId(token.getValue());
		oauth2Authentication= SerializationUtils.deserialize(accessToken.getAuthentication());
		return oauth2Authentication;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		OAuth2Authentication oauth2Authentication=null;
		AccessTokenWithBLOBs accessToken=accessTokenMapper.selectByTokenId(token);
		oauth2Authentication= SerializationUtils.deserialize(accessToken.getAuthentication());
		return oauth2Authentication;
		
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		AccessTokenWithBLOBs at=new AccessTokenWithBLOBs();
		String userName=authentication.isClientOnly() ? null : authentication.getName();
		String clientId=authentication.getOAuth2Request().getClientId();
		at.setTokenId(token.getValue());
		at.setToken(SerializationUtils.serialize(token));
		at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
		at.setAuthentication(SerializationUtils.serialize(authentication));
		at.setRefreshToken(refreshToken);
		at.setClientId(clientId);
		at.setUserName(userName);
		
		accessTokenMapper.insert(at);

	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		// TODO Auto-generated method stub
		OAuth2AccessToken oauth2AccessToken=null;
		AccessTokenWithBLOBs accessToken=accessTokenMapper.selectByTokenId(tokenValue);
		oauth2AccessToken= SerializationUtils.deserialize(accessToken.getToken());
		return oauth2AccessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		accessTokenMapper.deleteByTokenId(token.getValue());
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		RefreshToken rt=new RefreshToken();
		rt.setTokenId(refreshToken.getValue());
		rt.setToken(SerializationUtils.serialize(refreshToken));
		rt.setAuthentication(SerializationUtils.serialize(authentication));
		refreshTokenMapper.insert(rt);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		// TODO Auto-generated method stub
		RefreshToken refreshToken=refreshTokenMapper.selectByTokenId(tokenValue);
		OAuth2RefreshToken rt=SerializationUtils.deserialize(refreshToken.getToken());
		return rt;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		RefreshToken refreshToken=refreshTokenMapper.selectByTokenId(token.getValue());
		OAuth2Authentication rt=SerializationUtils.deserialize(refreshToken.getAuthentication());
		return rt;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		refreshTokenMapper.deleteByTokenId(token.getValue());
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		accessTokenMapper.deleteByRefreshTokenId(refreshToken.getValue());
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		String key=authenticationKeyGenerator.extractKey(authentication);
		AccessTokenWithBLOBs rt=accessTokenMapper.selectByPrimaryKey(authenticationKeyGenerator.extractKey(authentication));
		if(rt==null) 
			return null;
		OAuth2AccessToken accessToken=SerializationUtils.deserialize(rt.getToken());
		if (accessToken != null
				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			removeAccessToken(accessToken);
			// Keep the store consistent (maybe the same user is represented by this authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		// TODO Auto-generated method stub
		HashMap<String,String> params=new HashMap<>();
		params.put("clientId", clientId);
		params.put("userName",userName);
		List<AccessTokenWithBLOBs> rt=accessTokenMapper.selectByClientIdAndUserName(params);
		Collection<OAuth2AccessToken> result=new ArrayList<>();
		for(AccessTokenWithBLOBs atb :rt){
			OAuth2AccessToken accessToken=SerializationUtils.deserialize(atb.getToken());
			result.add(accessToken);
		}
		return result;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		// TODO Auto-generated method stub
		List<AccessTokenWithBLOBs> rt=accessTokenMapper.selectByClientId(clientId);
		Collection<OAuth2AccessToken> result=new ArrayList<>();
		for(AccessTokenWithBLOBs atb :rt){
			OAuth2AccessToken accessToken=SerializationUtils.deserialize(atb.getToken());
			result.add(accessToken);
		}
		return result;
	}

}
