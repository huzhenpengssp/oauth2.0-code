package com.authorization.service;

import com.authorization.dao.ClientDetailMapper;
import com.authorization.domain.ClientDetail;
import com.authorization.domain.ClientDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

@Component
public class MyClientDetailsService implements ClientDetailsService {
	@Autowired
    private ClientDetailMapper clientDetailMapper;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetail client=clientDetailMapper.selectByPrimaryKey(clientId);
        ClientDetailImp clientImp=new ClientDetailImp(client);

        return clientImp;
    }
}
