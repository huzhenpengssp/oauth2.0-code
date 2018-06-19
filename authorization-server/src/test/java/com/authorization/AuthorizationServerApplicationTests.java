package com.authorization;



import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.authorization.dao.AccessTokenMapper;
import com.authorization.domain.AccessTokenWithBLOBs;
import com.authorization.service.MyClientDetailsService;
import com.authorization.service.TokenStoreService;
import com.authorization.service.UserinfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationServerApplicationTests {
	@Autowired
	private UserinfoService userService;
	@Autowired 
	private MyClientDetailsService clientService;
	@Autowired
	private AccessTokenMapper tokenMapper;
	@Test
	public void contextLoads() {
		//UserDetails user=userService.loadUserByUsername("huzhenpeng");
		//System.out.println(user.getPassword());
		//BCryptPasswordEncoder util = new BCryptPasswordEncoder(11);  
		//String password = util.encode("huzhenpeng" );
		//System.out.println(password);
		//ClientDetails client=clientService.loadClientByClientId("clientapp");
		//System.out.println(client.getClientId());
		HashMap<String,String> params=new HashMap<>();
		params.put("clientId", "clientapp");
		params.put("userName","huzhenpeng");
		List<AccessTokenWithBLOBs> rt=tokenMapper.selectByClientIdAndUserName(params);
		return;
	}

}
