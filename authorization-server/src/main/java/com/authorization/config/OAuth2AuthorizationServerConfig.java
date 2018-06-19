package com.authorization.config;

//import com.alibaba.druid.pool.DruidDataSource;

import com.authorization.service.MyClientDetailsService;
import com.authorization.service.TokenStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends
        AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;
    @Autowired
    private MyClientDetailsService clientDetailsService;


    @Bean
    public TokenStore  getTokenStore() {
        return new TokenStoreService();
        //return new JdbcTokenStore(dataSource);
        //return new InMemoryTokenStore();
    }

    @PostConstruct//修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次
    public void init(){
        //配置授权页面
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/my_approval_page");

    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
    	clients.withClientDetails(clientDetailsService);

        //@formatter:off
//        clients.inMemory()
//            .withClient("clientapp")
//            .secret("123456")
//            .redirectUris("http://localhost:9000/callback")
//            .authorizedGrantTypes("authorization_code",
//                    "implicit", "password")
//            .scopes("read_profile", "read_contacts");
      //@formatter:on
    }
  
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .tokenStore(getTokenStore())
        .exceptionTranslator(loggingExceptionTranslator());

    }
    
    @Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
                e.printStackTrace();
                // Carry on handling the exception
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }
}
